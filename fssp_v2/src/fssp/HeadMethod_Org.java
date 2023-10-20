package fssp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.net.URLEncoder;
import org.primefaces.PrimeFaces;
import frsb.WorkClass_frsb;


//@ManagedBean(name = "headMethod")
//@SessionScoped
public class HeadMethod_Org implements Serializable
{
  private String name_org; 
  public String getName_org(){return name_org;}
  public void setName_org(String name_org){this.name_org=name_org;}
  
  private String adres; 
  public String getAdres(){return adres;}
  public void setAdres(String adres){this.adres=adres;}  
  
  private String idRegion; 
  public String getIdRegion(){return idRegion;}
  public void setIdRegion(String idRegion){this.idRegion=idRegion;}   
  
  private String outHtml;
  public String getOutHtml(){return outHtml;}
  public void setOutHtml (String outHtml){this.outHtml=outHtml;}
  
  private String INN; 
  public String getINN(){return INN;}
  public void setINN(String INN){this.INN=INN;}   
   
  private String outHtml2;
  public String getOutHtml2(){return outHtml2;}
  public void setOutHtml2 (String outHtml2){this.outHtml2=outHtml2;}
  
  private String capcha;
  public String getCapcha(){return capcha;}
  public void setCapcha (String capcha){this.capcha=capcha;}
  
  public void response()
   { 
    DataBases_v2 db=new DataBases_v2();
    WorkClass_org workClass=new WorkClass_org();
    String zapros,json;
    String name_org_req,adres_req;         
     //udid f1 = udid=re71644a-4c50-4d46-9766-21a87d95341y
    try
     {      
      name_org_req=URLEncoder.encode(name_org,"utf-8"); 
      adres_req=URLEncoder.encode(adres,"utf-8");           
      zapros="address="+adres_req+"&drtr_name="+name_org_req+"&udid=8afb81bc-4ef2-4e79-867a-969e37d50ca8&region_id="+idRegion+"&type=yur&ver=44";      
      outHtml="<span>Запрос. Организация: <b>"+name_org+".</b> Адрес: <b>"+adres+"</b>. Регион: "+idRegion+".</span></br>";   
      if(!name_org.equals(""))
        {
        Http_request http=new Http_request();
        if(db.checkCapcha().equals(""))
         {         
          json=http.fssp_connection(zapros);
          if(json.equals("error"))
           {
            outHtml+="Ошибка при отправке запроса";
            db.insertResult("Ошибка соединения с ФССП",-1,null);
            }            
          else outHtml+=workClass.getHtml(json,0);  
          }
         else outHtml+=workClass.getHtml("",1); 
        } 
       else  
      outHtml="<img Class=\"image_org\" src=\"resources/Image/icons8-medium.png\" alt=\"Резутат\">Не указано наименование организации";
     
      // ЗАПРОС ФРСБ
      /* WorkClass_frsb frsb=new WorkClass_frsb();
      if(INN.equals(""))
      outHtml2=frsb.responseFRSB(name_org_req);
      else
      outHtml2=frsb.responseFRSB(INN); */
      }
     catch(Exception e)
      {
       System.out.println("Ошибка "+e.getMessage());
       outHtml+="Ошибка запроса "+e;       
       }    
     finally
      {   
       PrimeFaces.current().executeScript("PF('loader').hide();");
       PrimeFaces.current().ajax().update("@widgetVar(result)");  
       //outHtml="";
       }     
    }
  
  public void clear()
   {
    outHtml=""; outHtml2="";
    name_org=""; adres=""; INN="";
    PrimeFaces.current().executeScript("PF('loader').hide();"); 
    //PrimeFaces.current().ajax().update("pers_form");
    PrimeFaces.current().ajax().update("@widgetVar(pers_head)");
    }
  
  public void capcha() throws Exception
   {
    String error_code;
    try
     {   
      DataBases_v2 db=new DataBases_v2();
      Http_request http=new Http_request();
      capcha=URLEncoder.encode(capcha,"utf-8"); 
      System.out.println(capcha);      
         
      String out=http.capcha(capcha);        
      ObjectMapper objectMapper=new ObjectMapper();  
      JsonNode jsonNode;          
      jsonNode=objectMapper.readTree(out);
      error_code=jsonNode.get("error_code").asText();     
      if(error_code.equals("0"))
       {       
        db.deleteCapcha();
        response();
        }
       else 
        {
         JsonNode nodeCaptcha=jsonNode.at("/data/captcha/url");                           
         capcha=nodeCaptcha.asText();
         db.insertCapcha(capcha);
         response();
         //outHtml="Ошибка";   
         }
      capcha="";    
       }
     catch(Exception e)
      {
       outHtml="Ошибка при отсправке капчи";
       PrimeFaces.current().executeScript("PF('loader').hide();"); 
       PrimeFaces.current().ajax().update("@widgetVar(result)");                                             
       }    
    }  
}
