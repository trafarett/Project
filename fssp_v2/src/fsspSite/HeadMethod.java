package fsspSite;

import fssp.*;

import java.io.Serializable;
import java.net.URLEncoder;

import java.util.ArrayList;

import java.util.Map;

import java.util.TreeMap;

import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.context.PrimeFacesContextFactory;

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
  
  private String outImageCaptcha;
  public String getOutImageCaptcha(){return outImageCaptcha;}
  public void setOutImageCaptcha (String outImageCaptcha){this.outImageCaptcha=outImageCaptcha;}  
  
  private Map<String,String> listHtml=new TreeMap<>();
  public void setListHtml(Map<String,String> listHtml){this.listHtml=listHtml;}
  public Map getListHtml(){return listHtml;};
   
  private String outHtmlFms;
  public String getOutHtmlFms(){return outHtmlFms;}
  public void setOutHtmlFms (String outHtmlFms){this.outHtmlFms=outHtmlFms;}   
  
  private String captcha;
  public String getCaptcha(){return captcha;}
  public void setCaptcha (String captcha){this.captcha=captcha;}
  
  private String sid;
  public String getSid(){return sid;}
  public void setSid (String sid){this.sid=sid;}  
  
  private String page="";
  public String getPage(){return page;}
  public void setPage (String page){this.page=page;} 
  
  private String URL ="https://is-node7.fssp.gov.ru/ajax_search?";
  
  public void response(int param,String url) throws Exception
   { 
    OutHttpRequest response=new OutHttpRequest();   
    OutHtmlResult resultHtml=new OutHtmlResult();
    DataBases_v2 db=new DataBases_v2();    
    WorkClass_fsspSite workClass_fsspSite=new WorkClass_fsspSite();    
    String zapros,html;
    String surname_req,name_req,secondname_req,birthdate_req;
    long systemTime=System.currentTimeMillis();
    try
     { 
      clearResult();
      if(param==3)
      page=workClass_fsspSite.getNumberPage(url);
      /*
      Runnable runFms;
      runFms=new CheckFMS(passport,PrimeFaces.current(),FacesContext.getCurrentInstance());
      new Thread(runFms).start();      */
      listHtml.clear();
      
      if(param==1)
       {
        page="";
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
       birthdate_req="*";
      zapros="callback=jQuery34002699358782772554_"+systemTime+"&" +
       "system=ip&is[extended]=1&nocache=1&is[variant]=1&" +
       "is[region_id][0]=-1&" +
       "is[last_name]="+surname_req+"&" +
       "is[first_name]="+name_req+"&" +
       "is[drtr_name]=&" +
       "is[ip_number]=&" +
       "is[patronymic]="+secondname_req+"&" +
       "is[date]="+birthdate_req+"&" +
       "is[address]=&is[id_number]=&is[id_type][0]=&is[id_issuer]=&is[inn]=&" +
       "_="+(systemTime+1)+page;       
      outHtml="<span>Запрос ФССП. Фамилия: <b>"+surname+"</b> Имя: <b>"+(name.equals("*") ? "-" : name)+
              "</b> Отчество: <b>"+secondname+"</b> ДР: <b>"+
              (birthdate.equals("01.01.1900") ? "-" : birthdate)+"</b></span></br>";
      if(param==2)
      {
       //page="";
       captcha=URLEncoder.encode(captcha,"utf-8"); 
       zapros=zapros+"&code="+captcha;
      }
     
      if((!surname.equals("") && !name.equals("") && !birthdate.equals("") && !birthdate.equals("00.00.0000")) || param ==3) 
       {
        Http_request http=new Http_request();
        if(param==3)
         response=http.fssp_connection(url.substring(1, url.length()-1),param,sid);
         else
        response=http.fssp_connection(URL+zapros,param,sid); //server        
        html=response.getHtml();
        sid=response.getSid();
        if(response.getResul().equals("captcha"))
        {
         outImageCaptcha=workClass_fsspSite.parseCaptcha(html);
         //PrimeFaces.current().ajax().update("@widgetVar(loader)"); 
         PrimeFaces.current().ajax().update("@widgetVar(captcha)");  
         PrimeFaces.current().executeScript("PF('captcha').show();");
         }
        else
        {
         resultHtml=workClass_fsspSite.parseHTML(html);
         outHtml+=resultHtml.getHtml();
         this.listHtml=resultHtml.getListPages();
         PrimeFaces.current().ajax().update("@widgetVar(pages)");
         PrimeFaces.current().ajax().update("@widgetVar(result)");  
         }       
        }
       else
       {
        outHtml="<table><tr><td><img Class=\"image\" src=\"resources/Image/icons8-medium.png\" alt=\"Резутат\"></td><td>Не указана фамилия, имя или дата рождения</td><?td></table>"; 
        //PrimeFaces.current().executeScript("PF('loader').hide();");
        PrimeFaces.current().ajax().update("@widgetVar(loader)"); 
        PrimeFaces.current().ajax().update("@widgetVar(result)");         
        }       
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
       PrimeFaces.current().ajax().update("@widgetVar(loader)");       
       //PrimeFaces.current().ajax().update("@widgetVar(result)");          
       captcha="";
       }     
    }
  
  public void clear()
   {
    outHtml=""; outImageCaptcha="";
    surname=""; name=""; secondname=""; fio=""; birthdate=""; birthdate_fio="";
    listHtml.clear();
    PrimeFaces.current().executeScript("PF('loader').hide();"); 
    PrimeFaces.current().executeScript("PF('captcha').hide();"); 
    
    PrimeFaces.current().ajax().update("@widgetVar(result)");
    PrimeFaces.current().ajax().update("@widgetVar(pages)");
    }
  
  public void clearResult()
  {
   outHtml=""; 
   //PrimeFaces.current().ajax().update("@widgetVar(result)");
  }
  
 public void setHtml(String html)
 {
  outHtmlFms=html; 
  PrimeFaces.current().ajax().update("@widgetVar(resultFms)");
 }
}

