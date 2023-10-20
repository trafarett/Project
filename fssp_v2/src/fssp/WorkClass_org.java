package fssp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import org.primefaces.PrimeFaces;

public class WorkClass_org
{
 public String getHtml(String json,int param) throws Exception
  {
   DataBases_v2 db=new DataBases_v2();
   JsonNode jsonNode=null;   
   String outHtml,error_code,capcha;
   outHtml=""; 
   int i=0; 
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
         
         sumDolg=sumDolg.add(subject.getSum());                  
         outHtml+="<tr><td><b>" + subject.getName()+"</b></td>";
         outHtml+="<td>"+subject.getExe_production()+"</td>";
         outHtml+="<td>"+subject.getDetails()+"</td>";
         outHtml+="<td>"+subject.getSubject()+"</td>";
         outHtml+="<td>"+subject.getDepartment()+"</td>";
         outHtml+="<td>"+subject.getBailiff()+"</td>";                                          
         i++;
         }          
       if(i!=0)
        {
         outHtml+="<img Class=\"image_org\" src=\"resources/Image/icons8-good-3.png\" alt=\"Резутат\">Итого: "+i+" задолженностей. Общая сумма долга: "
                  + sumDolg+" руб<br><thead><tr>"+
                  "<th>Должник</th><th>Исполнительное производство</th>"+
                  "<th>Реквизиты исполнительного документа</th>"+
                  "<th>Предмет исполнения</th>"+
                  "<th>Судебный пристав-исполнитель</th>"+
                  "<th>Отдел судебных приставов</th></tr></thead>";         
         }       
        else 
        outHtml="<img Class=\"image_org\" src=\"resources/Image/icons8-good-3.png\" alt=\"Резутат\">Задолженностей не найдено";
        db.insertResult("Success_org",i,sumDolg);       
       break;
       case ("5"):
       if(param==0)
        {       
         JsonNode nodeCaptcha=jsonNode.at("/data/captcha/url");                           
         capcha=nodeCaptcha.asText();                  
         }
       else capcha=db.checkCapcha();
       outHtml+="<img Class=\"image\" src=\"resources/Image/icons8-medium.png\">ВВЕДИТЕ КАПЧУ !!!</br><p><img src="+capcha+" alt=\"Картинка\"></p>";
       db.insertResult("Capcha_org",-1,null);
       db.insertCapcha(capcha);
       PrimeFaces.current().executeScript("openCapcha();");       
       break;
       default:     
       outHtml="<span>Ошибка, повторите запрос<span>";
       db.insertResult("Error_org",-1,null);       
       break;
       }     
     }
    catch(Exception e)
     {
      outHtml="<span>Ошибка, повторите запрос "+e.getMessage()+"<span>";
      db.insertResult("Error_catch_org",-1,null);
      System.out.println(json);
      } 
    finally
     {
      PrimeFaces.current().executeScript("PF('loader').hide();"); 
      PrimeFaces.current().ajax().update("@widgetVar(result)");    
      }
   return outHtml;
   }  
}
