package fms;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.stream.ImageInputStream;

public class ParseImage
{
    public BufferedImage getReductCaptcha(File inFileCaptchaTemp) throws Exception
    {     
     Log_File_Out log_out=new Log_File_Out();
     WorkMethods workMethods=new WorkMethods();
     String logPath=workMethods.readProperties("pathLogs");
     String strok1="";
     
      BufferedImage image=ImageIO.read(inFileCaptchaTemp);    //(inCapcthaImage);
      BufferedImage result=new BufferedImage(121, image.getHeight(), image.getType());
      try
      {
        for(int i=25; i<146; i++)
        {
            for(int j=0; j<11; j++)
            {
                Color color = new Color(image.getRGB(i, j));

                int blue = 212;
                int red = 212;
                int green = 212;

                Color newColor = new Color(red, green, blue);
                result.setRGB(i-25, j, newColor.getRGB());
            }
         
            for(int j=11; j<44; j++)
            {
                Color color = new Color(image.getRGB(i, j));

                int blue = color.getBlue();
                int red = color.getRed();
                int green = color.getGreen();

                Color newColor = new Color(red, green, blue);
                result.setRGB(i-25, j, newColor.getRGB());
            }
         
            for(int j=44; j<60; j++)
            {
                Color color = new Color(image.getRGB(i, j));

                int blue = 212;
                int red = 212;
                int green = 212;

                Color newColor = new Color(red, green, blue);
                result.setRGB(i-25, j, newColor.getRGB());
            }
        }
     }
     catch(Exception e)
     {
       strok1=e.getMessage()+"   "+e.getStackTrace().toString();
      log_out.logFileOut(logPath, strok1);
     }
        image.flush();
        result.flush();     
        return result;
    }
    
    public void getResultImage(BufferedImage inImageCaptchaTemp) throws Exception
     {      
      WorkMethods workMethods=new WorkMethods();
      File fileTest=new File(workMethods.getPath()+"resources/test.jpg");        
      BufferedImage imageTest=ImageIO.read(fileTest);
      String strok1="";
      Log_File_Out log_out=new Log_File_Out();
      String logPath=workMethods.readProperties("pathLogs");
       
      BufferedImage result=new BufferedImage(imageTest.getWidth()+inImageCaptchaTemp.getWidth(), imageTest.getHeight(), imageTest.getType());
      try
       {      
        for(int i=0; i<imageTest.getWidth(); i++)
        {
            for (int j = 0; j < imageTest.getHeight(); j++)
            {
                Color color = new Color(imageTest.getRGB(i, j));
   
                int blue = color.getBlue();
                int red = color.getRed();
                int green = color.getGreen();
   
                Color newColor = new Color(red, green, blue);
                result.setRGB(i , j, newColor.getRGB());
            }
        }
         
        for(int i=0; i<inImageCaptchaTemp.getWidth(); i++)
        {
            for (int j = 0; j < inImageCaptchaTemp.getHeight(); j++)
            {
                Color color = new Color(inImageCaptchaTemp.getRGB(i, j));
   
                int blue = color.getBlue();
                int red = color.getRed();
                int green = color.getGreen();
   
                Color newColor = new Color(red, green, blue);
                result.setRGB(i+imageTest.getWidth() , j, newColor.getRGB());
            }
        }      
       File output=new File(workMethods.getPath()+"resources/captcha.jpg");
       ImageIO.write(result, "jpg", output);
       }
       catch(Exception e)
       {
         strok1=e.getMessage()+"   "+e.getStackTrace().toString();
        log_out.logFileOut(logPath, strok1);
       }
      imageTest.flush();
      result.flush();      
      }
}
