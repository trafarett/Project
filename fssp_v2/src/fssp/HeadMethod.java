package fssp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.net.URLEncoder;

import org.primefaces.PrimeFaces;

//@ManagedBean(name = "headMethod")
//@SessionScoped
public class HeadMethod implements Serializable
{
  private String surname; 
  public String getSurname(){return surname;}
  public void setSurname(String surname){this.surname=surname;}
  
  private String name; 
  public String getName(){return name;}
  public void setName(String name){this.name=name;}
  
  private String secondname; 
  public String getSecondname(){return secondname;}
  public void setSecondname(String secondname){this.secondname=secondname;}
  
  private String fio; 
  public String getFio(){return fio;}
  public void setFio(String fio){this.fio=fio;}
  
  private String birthdate; 
  public String getBirthdate(){return birthdate;}
  public void setBirthdate(String birthdate){this.birthdate=birthdate;}
  
  private String birthdate_fio; 
  public String getBirthdate_fio(){return birthdate_fio;}
  public void setBirthdate_fio(String birthdate_fio){this.birthdate_fio=birthdate_fio;}
  
  private String outHtml;
  public String getOutHtml(){return outHtml;}
  public void setOutHtml (String outHtml){this.outHtml=outHtml;}
  
  private String capcha;
  public String getCapcha(){return capcha;}
  public void setCapcha (String capcha){this.capcha=capcha;}
  
  public void response(int param) throws Exception
   { 
    //long startTime = System.currentTimeMillis();
    DataBases_v2 db=new DataBases_v2();
    WorkClass workClass=new WorkClass();    
    String zapros,json,udid;
    String surname_req,name_req,secondname_req,birthdate_req,timeRequest;         
    //udid f1 = udid=re71644a-4c50-4d46-9766-21a87d95341y
    //udid="ab71644a-4c50-4d46-9766-21a87d95917y";
    udid="ab71644a-4c50-4d46-9766-21a87d95917y";
    try
     {
      if(param==1)
       {
        Fio_v2 getFio=new Fio_v2(fio);
        surname=getFio.getSurname();
        name=getFio.getName();
        secondname=getFio.getPatronymic();
        birthdate=birthdate_fio;
        }       
      surname_req=URLEncoder.encode(surname,"utf-8"); 
      name_req=URLEncoder.encode(name,"utf-8");    
      secondname_req=URLEncoder.encode(secondname,"utf-8");      
      birthdate_req=URLEncoder.encode(birthdate,"utf-8"); 
      if(birthdate_req.equals("01.01.1900"))
       birthdate_req="00.00.0000"; 
      if(name_req.equals("?"))
        birthdate_req="*";                   
      zapros="last_name="+surname_req+"&"+"first_name="+name_req+"&"+"patronymic="+secondname_req+"&"+"date="+birthdate_req+"&"+"region_id=0&"+
               "udid="+udid+"&type=form&ver=44";       
      outHtml="<span>Запрос. Фамилия: <b>"+surname+"</b> Имя: <b>"+(name.equals("*") ? "-" : name)+
              "</b> Отчество: <b>"+secondname+"</b> ДР: <b>"+
              (birthdate.equals("01.01.1900") ? "-" : birthdate)+"</b></span></br>";  
     //System.out.println(zapros);
      if(!surname.equals("") && !name.equals("") && !birthdate.equals("") && !birthdate.equals("00.00.0000"))
       {
        Http_request http=new Http_request();
        if(2>1)                                              //db.checkCapcha().equals(""))
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
       else outHtml="<table><tr><td><img Class=\"image\" src=\"resources/Image/icons8-medium.png\" alt=\"Резутат\"></td><td>Не указана фамилия, имя или дата рождения</td><?td></table>";
       //long endTime=System.currentTimeMillis();
       //long duration=(endTime-startTime);       
       //outHtml+="Время выполенения запроса: "+duration+"ms";
      }
     catch(Exception e)
      {
       System.out.println("Ошибка "+e.getMessage());
       outHtml+="Ошибка запроса "+e;  
       db.insertResult("Error_catch_response",-1,null);     
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
    outHtml=""; 
    surname=""; name=""; secondname=""; fio=""; birthdate=""; birthdate_fio="";
    PrimeFaces.current().executeScript("PF('loader').hide();"); 
    //PrimeFaces.current().ajax().update("pers_form");
    //PrimeFaces.current().ajax().update("form1");
    PrimeFaces.current().ajax().update("@widgetVar(result)");
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
        response(0);
        }
       else 
        {
         JsonNode nodeCaptcha=jsonNode.at("/data/captcha/url");                           
         capcha=nodeCaptcha.asText();
         db.insertCapcha(capcha);
         response(0);
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
