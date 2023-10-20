package fsspSite;

import fssp.DataBases_v2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Http_request
 { 
  public OutHttpRequest fssp_connection(String params,int param, String sid) throws Exception  //String server
   {
    OutHttpRequest out=new OutHttpRequest();     
    HttpURLConnection con=null;   
    String inputLine,outResponse;
    outResponse="";        
    int responseCode=-1;
    try
     {      
      URL obj=new URL(params);
      con=(HttpURLConnection) obj.openConnection();  
      //optional default is GET
      con.setRequestMethod("GET");           
      //add request header
      con.setRequestProperty("Accept","*/*");
      con.setRequestProperty("accept-encoding","utf-8");
      con.setRequestProperty("Accept-Language","ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
      con.setRequestProperty("Cache-Control","no-cache");
      con.setRequestProperty("connection","Keep-Alive");     
      con.setRequestProperty("Host","is-node7.fssp.gov.ru");
      con.setRequestProperty("Pragma","no-cache");     
      con.setRequestProperty("Referer","https://fssp.gov.ru/");     
      con.setRequestProperty("Sec-Ch-Ua","Google Chrome\";v=\"113\", \"Chromium\";v=\"113\", \"Not-A.Brand\";v=\"24");
      con.setRequestProperty("Sec-Ch-Ua-Mobile","?0");     
      con.setRequestProperty("Sec-Ch-Ua-Platform","Windows");     
      con.setRequestProperty("Sec-Fetch-Dest","script");
      con.setRequestProperty("Sec-Fetch-Mode","no-cors");      
      con.setRequestProperty("Sec-Fetch-Site", "same-site");
      con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");
      if(param==2)  // отправка капчи
       {       
        con.setRequestProperty("Cookie","connect.sid="+sid);
        }
      responseCode=con.getResponseCode(); 
      if(responseCode==200)
       {      
        BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        List<String> cookies=new ArrayList<>();
        Map<String,List<String>> responseHeaders=new HashMap<>(); 
        responseHeaders=con.getHeaderFields();
        if(responseHeaders.containsKey("set-cookie")) 
         {
          cookies=responseHeaders.get("set-cookie");
          String connect_sid=cookies.get(0);    
          connect_sid=connect_sid.substring(connect_sid.indexOf("=")+1,connect_sid.indexOf(";"));
          out.setSid(connect_sid);                   
          }
        StringBuffer response=new StringBuffer();  
        while((inputLine=in.readLine())!=null) 
         {
          response.append(inputLine);
          }
        in.close();
        con.disconnect();      
        outResponse=response.toString();    
        out.setHtml(outResponse);
        if(outResponse.contains("captcha-popup"))
         out.setResult("captcha");            
        }
      else out.setHtml("Ошибка запроса_"+responseCode);     
     }
     catch(Exception e)
      {
       out.setHtml("error");        
       if(con!=null)
        con.disconnect();
       }   
    return out;     
    } 
 }
