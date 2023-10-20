package frsb;

import fssp.DataBases_v2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Request_FRSB
 {
  String path = "https://bankrot.fedresurs.ru/backend/cmpbankrupts?searchString=";
  String path2 = "https://bankrot.fedresurs.ru/bankrupts?searchString=";
  String pathEnd = "&limit=15&offset=0";
  
  public String createRequestFRSB(String fio) throws Exception
  {
   String fio2="";
   fio2=URLEncoder.encode(fio,"utf-8"); 
   String stringRequest = path+fio+pathEnd;  
   String inputLine;
   String outResponse="";
   String outHtml="";
   
    HttpURLConnection con=null;   
    try
     {      
      URL obj=new URL(stringRequest);
      con=(HttpURLConnection) obj.openConnection();  
      //optional default is GET
      con.setRequestMethod("GET");           
      //add request header
      con.setRequestProperty("User-Agent","Mozilla/5.0");
      con.setRequestProperty("accept-encoding","utf-8");
      con.setRequestProperty("connection","Keep-Alive");
      con.setRequestProperty("referer",path2+fio);   
                
      //int responseCode = con.getResponseCode(); 
      BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream()));
      StringBuffer response=new StringBuffer();  
      while((inputLine=in.readLine())!=null) 
       {
        response.append(inputLine);
        }
      in.close();
      con.disconnect();  
      outHtml=response.toString();
      /* String ENCODING_TO_BE_APPLIED="windows-1251";
      String MARKER="__MARKER_I__";      
      String CYRILLIC_I=new String("È".getBytes("UTF-8"), ENCODING_TO_BE_APPLIED);  
      outHtml=outHtml.replace(CYRILLIC_I, MARKER);
      outHtml=new String(outHtml.getBytes(ENCODING_TO_BE_APPLIED),"UTF-8");
      outHtml=outHtml.replace(MARKER,"È");   */
      //outHtml=new String(outHtml.getBytes("cp1251"), "UTF-8");
     
      //System.out.println(outHtml);
      //Thread.sleep(3000);     
      }
     catch(Exception e)
      {
       outHtml="error";      
       if(con!=null)
        con.disconnect();
       } 
    return outHtml;
    }
}