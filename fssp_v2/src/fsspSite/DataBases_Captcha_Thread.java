package fsspSite;

public class DataBases_Captcha_Thread implements Runnable
{ 
 private String captcha,urlCaptcha;
 
 public DataBases_Captcha_Thread (String captcha,String urlCaptcha)
 {
  this.captcha=captcha;
  this.urlCaptcha=urlCaptcha;
 }
 @Override
 public void run() 
 {
  try
  {
   DataBases_fsspSite db=new DataBases_fsspSite();
   db.setCaptchaImage("",captcha,urlCaptcha);
  }
  catch(Exception e)
  {
   System.out.println(e.getMessage());
  }
 }
}
