package fms;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.InputStream;

import java.util.Properties;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class WorkMethods
{
 public String readProperties(String nameProperties)
  {
   Properties properties=new Properties();
   InputStream is=null;
   String out="";
   try 
    {
     is=this.getClass().getResourceAsStream("appLocal.properties");
     properties.load(is);
     out=properties.getProperty(nameProperties);
     }
    catch(Exception e){}
    return out;
   }
 
 public String readXML(String response) throws Exception
  {
   String result="Повторите запрос";
   if(response.contains("<h4 "))
    {
     int indexStart=response.indexOf("<h4 ");
     int indexStartFinish=response.indexOf("</h4>");
     result=response.substring(indexStart+18,indexStartFinish);     
     if(result.contains("Cреди недействительных не значится"))
      result="<span style=\"color:green\"><b>"+result+"</b></span>";
     else
      result="<span style=\"color:red\"><b>"+result+"</b></span>";     
     }
   return result;
   }
 
 public String getPath()
  {
   String path=this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
   path=path.replace("WEB-INF/classes/", "");        
   return path.replace("file:","").replace("%20", " ");
   }  
 
 public String tesseract() throws TesseractException
  {  
   WorkMethods wm=new WorkMethods();
   Log_File_Out log_out=new Log_File_Out();
   String logPath=wm.readProperties("pathLogs");
   String result="";
   String strok1="";
   try
    {
    String a= System.getProperty("java.library.path");
    //System.out.println(a);
     File image=new File(wm.getPath()+"resources/captcha.jpg"); 
    String [] b = a.split(";");
    for(int i=0; i<b.length; i++)
    {
     System.out.println(b[i]);
    }
                                                               
     Tesseract tesseract=new Tesseract();   
     tesseract.setDatapath(wm.readProperties("pathTessData"));        
      
     //tesseract.setTessVariable("user_defied_dpi", "500");
     //tesseract.setLanguage("eng");
     //tesseract.setPageSegMode(1);
     tesseract.setOcrEngineMode(1);   
     result=tesseract.doOCR(image);   
     result=result.substring(result.length()-7,result.length());
     }
   catch(TesseractException e)
    {
     strok1=e.getMessage()+"   "+e.getStackTrace().toString();
     log_out.logFileOut(logPath, strok1);
     }   
   return result;
   }
 
 public synchronized static String action(String passport) throws Exception  
 {
  ParseImage parseImage=new ParseImage(); 
  WorkMethods workMethods=new WorkMethods();   
  ConnectionFMS connFMS=new ConnectionFMS(); 
  OutResult outResut=new OutResult();
  Log_File_Out log_out=new Log_File_Out();
  File fileCaptcha=null;
  
  String logPath=workMethods.readProperties("pathLogs");
  String outHtml="";
  String strok1,numberCaptcha,serial,number,resultResponse;
  try
   {
    //Получаем файл с капчей из запроса
    outResut=connFMS.getCaptchaImage(); 
    if(outResut.getError()==0)
     {
      fileCaptcha=(File) outResut.getObject();
     //Создаем изображение для распознования 
      BufferedImage captcha=parseImage.getReductCaptcha(fileCaptcha);    
      parseImage.getResultImage(captcha); 
      
      //Отправляем изображение для распознование
      numberCaptcha=workMethods.tesseract(); 
      serial=passport.substring(0,4); 
      number=passport.substring(4,10); 
     
      //Отправляем итоговый запрос и получаем результ
      outResut=connFMS.getConnect(numberCaptcha,serial,number);
      if(outResut.getError()==0)
       {
        resultResponse=(String) outResut.getObject();
        outHtml=workMethods.readXML(resultResponse);     
        captcha.flush();
        }
       else
        outHtml="Нет соединения с ФМС";
      }
     else
      outHtml="Нет соединения с ФМС";
    }
   catch(Exception e)
   {
    strok1="action  -"+e.getMessage()+"   "+e.getStackTrace().toString();
    log_out.logFileOut(logPath, strok1);
    outResut.setError(1); 
    outHtml="Ошибка";
    }  
  return "<tr><td>"+outHtml+"</tr></tr>";
 } 
}
