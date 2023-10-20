package fssp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

public class Http_request
 {
  //private String url="https://api.fssprus.ru/api/v2/search?"; 
 
  public static synchronized String fssp_connection(String params) throws Exception 
   {
    DataBases_v2 db=new DataBases_v2();
    HttpURLConnection con=null;
    String inputLine;
    String outResponse="";
    String url="https://api.fssp.gov.ru/api/v2/search?";
    String URL_params=url+params;
    try
     {      
      URL obj=new URL(URL_params);
      con=(HttpURLConnection) obj.openConnection();  
      //optional default is GET
      con.setRequestMethod("GET");           
      //add request header
      con.setRequestProperty("User-Agent","android");
      con.setRequestProperty("accept-encoding","utf-8");
      con.setRequestProperty("connection","Keep-Alive");
      con.setRequestProperty("host","api.fssprus.ru");   
                
      int responseCode=con.getResponseCode(); 
      BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream()));
      StringBuffer response=new StringBuffer();  
      while((inputLine=in.readLine())!=null) 
       {
        response.append(inputLine);
        }
      in.close();
      con.disconnect();      
      outResponse=response.toString();      
      System.out.println(outResponse);
      //db.insertResult("CheckCode "+responseCode,-1,null); 
      Thread.sleep(3000);
      }
     catch(Exception e)
      {
       outResponse="error";
       System.out.println(e.getMessage());
       //db.insertResult("Error_catch_fssp_connection "+e.getMessage(),-1,null);     
       if(con!=null)
        con.disconnect();
       }   
    return outResponse;     
    }
  
  public static synchronized String capcha(String capcha) throws Exception 
   {
    String response2="";
    String URL="https://api.fssprus.ru/api/v2/captcha/check?udid=wy58963a-5o77-7p33-8523-31a32d87523y&ver=44&code="+capcha; 
    try
     {  
      DataBases_v2 db=new DataBases_v2();
      if(!db.checkCapcha().equals(""))
       {
        URL obj=new URL(URL);
        HttpURLConnection con=(HttpURLConnection) obj.openConnection();
        
        con.setDoOutput(true);
        con.setRequestMethod("POST");      
        //add request header
        con.setRequestProperty("User-Agent","Dalvik/2.1.0 (Linux; U; Android 7.1.2; SM-G977N Build/LMY48Z)");
        con.setRequestProperty("accept-encoding","utf-8");
        con.setRequestProperty("connection","Keep-Alive");
        con.setRequestProperty("host","api.fssprus.ru");
        con.setRequestProperty("content-length","0");
                  
        //int responseCode = con.getResponseCode();    
        //System.out.println("Response Code : " + responseCode);
        BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response=new StringBuffer();
    
        while((inputLine=in.readLine()) != null) 
         {
          response.append(inputLine);
          }
        in.close();
        con.disconnect();
            
        response2=response.toString();       
        Thread.sleep(3000);
        }
      else response2="{\"is_dev\":1,\"error_code\":0,\"error_name\":\"\u0423\u0441\u043f\u0435\u0448\u043d\u043e\",\"data\":{\"captcha\":{\"session_id\":\"\"}}}";
      }
     catch(Exception e){}
    return response2;    
    }
 }