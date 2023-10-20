package fms;

import java.io.File;

import java.io.FileOutputStream;

import okhttp3.*;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class ConnectionFMS
{
 public OutResult getCaptchaImage() throws Exception
  {    
   WorkMethods workMethods=new WorkMethods();
   DataBases db=new DataBases();
   OutResult outResut=new OutResult();
   Log_File_Out log_out=new Log_File_Out();
   String logPath=workMethods.readProperties("pathLogs");
   String[] cookie=db.readDataBase();
   String url="http://services.fms.gov.ru/services/captcha.jpg";
   String query="";
   String strok1="";
   URL obj=new URL(url);    
   HttpURLConnection con=null;
   File file=null;
   
   try
    {
     con=(HttpURLConnection) obj.openConnection();  
   
     con.setDoOutput(true);
     con.setRequestMethod("GET");
     //add request header
     con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
     con.setRequestProperty("Accept-Language","ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
     con.setRequestProperty("Cache-Control","no-cache");
     con.setRequestProperty("Connection","keep-alive");
     con.setRequestProperty("Cookie", "uid="+cookie[1]+"; JSESSIONID="+cookie[0]);
     con.setRequestProperty("Host", "services.fms.gov.ru");
     con.setRequestProperty("Pragma","no-cache");
     con.setRequestProperty("Upgrade-Insecure-Requests","1");
     con.setRequestProperty("Connection","keep-alive");
     con.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36");
   
     List list=new ArrayList<String>();
     Map map=new HashMap<String, List <String>>();
     map=con.getHeaderFields();
     list=(List)map.get("Set-Cookie");  
     if(list != null) 
      {
       for(int i = 0; list.size()>i; i++) 
        {
         String result=(String) list.get(i);
         if(result.contains("JSESSIONID=")) 
          {
           strok1=result.substring(11, 39);
           query="UPDATE CookieFMS SET SESSIONID='"+strok1+"' WHERE id=1";
           db.writeDataBase(query);         
           }
         if(result.contains("uid=")) 
          {
           strok1=result.substring(4, 28);
           query="UPDATE CookieFMS SET uid='"+strok1+"' WHERE id=1"; 
           db.writeDataBase(query);        
           }
         }
       }
     file=new File(workMethods.getPath()+"resources/tempCaptcha.jpg");
     OutputStream outputStream=new FileOutputStream(file);
     IOUtils.copy(con.getInputStream(),outputStream);     
     con.disconnect(); 
     outResut.setObject(file);
     }
    catch(Exception e)
     {
      if(con!=null)
       con.disconnect();
      strok1="getCaptchaImage  -"+e.getMessage()+"   "+e.getStackTrace().toString();
      log_out.logFileOut(logPath,strok1);
      outResut.setError(1);
      }
   return outResut;    
   } 
 
  public OutResult getConnect(String captcha,String serial,String number) throws Exception
   {    
    WorkMethods workMethods=new WorkMethods();
    DataBases db=new DataBases();
    String cookie[]=db.readDataBase();
    Log_File_Out log_out=new Log_File_Out();
    OutResult outResut=new OutResult();
    String result="", strok1="";     
    String URL="http://services.fms.gov.ru/info-service.htm";
    String logPath=workMethods.readProperties("pathLogs");
    try
     {
      OkHttpClient client = new OkHttpClient().newBuilder().build();
      MediaType mediaType = MediaType.parse("text/plain");
      RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
              .addFormDataPart("sid","2000")
              .addFormDataPart("form_name","form")
              .addFormDataPart("DOC_SERIE",serial)
              .addFormDataPart("DOC_NUMBER",number)
              .addFormDataPart("captcha-input",captcha)
              .build();
      Request request = new Request.Builder()
              .url(URL)
              .method("POST", body)
              .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
              //.addHeader("Accept-Encoding", "gzip, deflate")
              .addHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
              .addHeader("Cache-Control", "no-cache")
              .addHeader("Connection", "keep-alive")
              .addHeader("Cookie", "uid="+cookie[1]+"; JSESSIONID="+cookie[0])
              .addHeader("Host", "services.fms.gov.ru")
              .addHeader("Pragma", "no-cache")
              .addHeader("Referer", "http://services.fms.gov.ru/info-service.htm?sid=2000")
              .addHeader("Upgrade-Insecure-Requests", "1")
              .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36")
              .build();
      Response response=client.newCall(request).execute();       
      result=response.body().string();  
      outResut.setObject(result);
      response.body().close();
      client.connectionPool().evictAll();
      }
     catch (Exception e)
     {
       strok1="getConnect  -"+e.getMessage()+"   "+e.getStackTrace().toString();
       log_out.logFileOut(logPath, strok1);
       outResut.setError(1);
      }    
    return outResut;
   }   
}
