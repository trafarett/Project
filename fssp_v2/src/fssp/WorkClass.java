package fssp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.HashMap;
import java.util.List;

import org.primefaces.PrimeFaces;

public class WorkClass
{
 public String getHtml(String json,int param) throws Exception
  {
   DataBases_v2 db=new DataBases_v2();
   HashMap<String,String> mapRegions=new HashMap<>();
   PostRegions postRegions=new PostRegions();
   mapRegions=postRegions.regions();
   JsonNode jsonNode=null;   
   String outHtml,error_code,capcha,uniq,name,headTable,outUniq,postCode,namePost;  
   int i=0;    
   uniq=""; name=""; headTable=""; outUniq=""; outHtml=""; postCode=""; namePost="";
   try
    {
     if(param==1)
      error_code="5";
      else
       { 
        ObjectMapper objectMapper=new ObjectMapper();               
        jsonNode=objectMapper.readTree(json);
        error_code=jsonNode.get("error_code").asText();
        }     
     switch(error_code)
      {
       case ("0"):     
       JsonNode node=jsonNode.at("/data/list");        
       ArrayNode arrayNode=(ArrayNode) node;                       
       BigDecimal sumDolg=new BigDecimal(0);
       ArrayList<String> listUniq=new ArrayList<>();
       for (JsonNode jsonnode : arrayNode)
        {
         ObjectNode objectNode=(ObjectNode)jsonnode;          
         Subjects_v2 subject=new Subjects_v2();
         subject.setName(objectNode.get("name").toString());
         subject.setExe_production(objectNode.get("exe_production").toString()); 
         subject.setDetails(objectNode.get("details").toString());
         subject.setSubject(objectNode.get("subject").toString());
         subject.setDepartment(objectNode.get("department").toString());
         subject.setBailiff(objectNode.get("bailiff").toString());         
         subject.setSum(objectNode.get("subject").toString());                 
         
         name=subject.getName();
         uniq=getUniq(name);        
         postCode=getPostCode(subject.getDepartment());
         namePost=mapRegions.get(postCode);
         uniq=uniq+(namePost==null? "" : " - "+namePost);
          
         sumDolg=sumDolg.add(subject.getSum());                  
         outHtml+="<tr><td><b>"+name+"</b></td>";
         outHtml+="<td>"+subject.getExe_production()+"</td>";
         outHtml+="<td>"+subject.getDetails()+"</td>";
         outHtml+="<td>"+subject.getSubject()+"</td>";
         outHtml+="<td>"+subject.getDepartment()+"</td>";
         outHtml+="<td>"+subject.getBailiff()+"</td></tr>";                                          
         i++; 
        
        if(!listUniq.contains(uniq))
         {          
          listUniq.add(uniq);
          }
         }         
       if(i!=0)
        {
         for(String dolzhnik : listUniq)
          {
           outUniq+="<span>"+dolzhnik+"</span><br>";
           }
         headTable="<table><tr><td><img Class=\"image\" src=\"resources/Image/icons8-good-3.png\" alt=\"Резутат\"></td>" +
                   "<td>Итого: "+i+" задолженностей. Общая сумма долга: "+ sumDolg+" руб. </td>"+
                   (listUniq.size()>1 ? "<td>Уникальных должников: "+listUniq.size() : "")+"</td></tr></table>"+
                   (listUniq.size()>1 ? outUniq : "")+ 
                  "<table class=\"table\"><thead><tr>"+                  
                  "<th>Должник</th><th>Исполнительное производство</th>"+
                  "<th>Реквизиты исполнительного документа</th>"+
                  "<th>Предмет исполнения</th>"+
                  "<th>Судебный пристав-исполнитель</th>"+
                  "<th>Отдел судебных приставов</th></tr></thead>";  
         outHtml=headTable+outHtml+"</table>";                
         }       
        else 
         outHtml="<table><tr><td><img Class=\"image\" src=\"resources/Image/icons8-good-3.png\" alt=\"Резутат\"></td><td>Задолженностей не найдено</td></td></table>";
       //db.insertResult("Success",i,sumDolg);       
       break;
       case ("5"):
       if(param==0)
        {       
         JsonNode nodeCaptcha=jsonNode.at("/data/captcha/url");                           
         capcha=nodeCaptcha.asText();                  
         }
        else capcha=db.checkCapcha();
       outHtml+="<img Class=\"image\" src=\"resources/Image/icons8-medium.png\">ВВЕДИТЕ КАПЧУ !!!</br><p><img src="+capcha+" alt=\"Картинка\"></p>";
       db.insertResult("Capcha",-1,null);
       db.insertCapcha(capcha);
       PrimeFaces.current().executeScript("openCapcha();");       
       break;
       default:     
       outHtml="<span>Ошибка, повторите запрос</span>";
       db.insertResult("Error_getHtml",-1,null);       
       break;
       }     
     }
    catch(Exception e)
     {
      outHtml="<span>Ошибка, повторите запрос "+e.getMessage()+"</span>";
      db.insertResult("Error_catch_getHtml",-1,null);      
      } 
    finally
     {
      PrimeFaces.current().executeScript("PF('loader').hide();"); 
      PrimeFaces.current().ajax().update("@widgetVar(result)");    
      }
   return outHtml;
   }  
 
 public String getUniq(String subject)
  {
   String outSubject="";
   int i;
   for(i=0; subject.length()>i;i++)
    {
     if(Character.isDigit(subject.charAt(i)))
      {
       i=i+10; 
       break; 
       }     
     }
   outSubject=subject.substring(0,i);
   outSubject.toUpperCase();
   return outSubject;
   }
 
 public String getPostCode(String department)
  {
   String postRegion="";
   try
    {      
     for(int i=0;department.length()>i;i++)
      {    
       if(Character.isDigit(department.charAt(i)) && Character.isDigit(department.charAt(i+1)) && Character.isDigit(department.charAt(i+2)) &&
        Character.isDigit(department.charAt(i+3)) && Character.isDigit(department.charAt(i+4)) && Character.isDigit(department.charAt(i+5)))
        {
         postRegion=department.substring(i,i+3); 
         break;
         }
       } 
     }
   catch (Exception e){postRegion="";}
   return postRegion;
   }  
}
