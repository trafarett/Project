package frsb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.databind.node.ObjectNode;

import fssp.Subjects_v2;

import java.io.Serializable;

import java.net.URLEncoder;

public class WorkClass_frsb implements Serializable
{
 /* private String name; 
 public String getName(){return name;}
 public void setName(String name){this.name=name;}
 
 private String outHtml;
 public String getOutHtml(){return outHtml;}
 public void setOutHtml (String outHtml){this.outHtml=outHtml;} */
 
 String path = "https://bankrot.fedresurs.ru/backend/cmpbankrupts?searchString=";
 String path2 = "https://bankrot.fedresurs.ru/bankrupts?searchString=";
 String pathEnd = "&limit=15&offset=0";
 
 public String responseFRSB(String fio) throws Exception
  {
   String outHtml="";
   String json="";
   String total="";
   String guid="";
   Request_FRSB request_FRSB=new Request_FRSB();
   json=request_FRSB.createRequestFRSB(fio);
   JsonNode jsonNode=null;      
   
   ObjectMapper objectMapper=new ObjectMapper();               
   jsonNode=objectMapper.readTree(json);
   total=jsonNode.get("total").asText();
   if(!total.equals("0"))
    {
     JsonNode node=jsonNode.at("/pageData");        
     ArrayNode arrayNode=(ArrayNode) node; 
     for (JsonNode jsonnode : arrayNode)
      {
       ObjectNode objectNode=(ObjectNode)jsonnode;          
       Subjects_frsb subject=new Subjects_frsb();
       subject.setName(objectNode.get("name").toString());
       subject.setINN(objectNode.get("inn").toString()); 
       subject.setAdress(objectNode.get("address").toString());
       subject.setStatus(objectNode.get("status").toString());
       subject.setLink(objectNode.get("guid").toString());
      
       guid=subject.getLink();
       //guid=URLEncoder.encode(guid,"utf-8");        
       
       outHtml+="<tr><td>"+subject.getName()+"</td>";
       outHtml+="<td>"+subject.getINN()+"</td>";
       outHtml+="<td>"+subject.getAdress()+"</td>";
       outHtml+="<td>"+subject.getStatus()+" <a target=\"_blank\" href=https://fedresurs.ru/company/"+guid+">Сайт ФРСБ</a></td></tr>";      
       }
     outHtml="<h5>Запрос ФРСБ<h5><tabel><table class=\"table\"><thead><tr>"+ 
                       "<th>Должник</th><th>ИНН</th>"+
                       "<th>Адрес</th>"+
                       "<th>Статус</th></tr></thead>"+outHtml+"</tabel>";
    }
    else outHtml="<h5>ФРСБ: НЕ НАЙДЕНО<h5>";
   return outHtml;
   } 
}
