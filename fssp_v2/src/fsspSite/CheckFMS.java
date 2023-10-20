package fsspSite;

import fms.DataBases;
import fms.WorkMethods;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;

import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.context.PrimeFacesContextFactory;

public class CheckFMS implements Runnable  
{
 private String passport;
 private PrimeFaces pf;
 private FacesContext context;

 
 public CheckFMS(String passport,PrimeFaces pf, FacesContext context)
 {
  this.passport=passport;
  this.pf=pf;
  this.context=context;
  
  
 }
 
  public void run() 
  { 
   //FacesContext context = FacesContext.getCurrentInstance();
   String passport2 = context.getApplication().evaluateExpressionGet(context, "#{headMethod_fsspSite.passport}", String.class);
    //FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("resultFms");
   
    
   String outHtmlFms="";
   try
    {     
     if(passport2.length()==10)
      {
      /*
       WorkMethods workMethods=new WorkMethods();
       int i=0;
        while(i<5)
         {
          outHtmlFms=workMethods.action(passport);
          if(!outHtmlFms.equals("<tr><td>Повторите запрос</tr></tr>"))
           break;
          i++;
          } */
      Thread.sleep(2000);
      outHtmlFms="Проверка фмс"; 
      pf.executeScript("updateFMS(outHtmlFms);");
     // pf.current().ajax().update("@widgetVar(loader)");   
        
       }
      else 
       outHtmlFms="Введите 10 цифр";
    }
   catch(Exception e)
    {
     System.out.println(e.getMessage()+" "+e.getStackTrace().toString());
     outHtmlFms="Неизвестная ошибка";
     }
   finally
    {
      //PrimeFaces.current().executeScript("fmsLoader_stop();"); 
     //PrimeFaces.current().ajax().update("@widgetVar(result)");     
     }
   }
}
