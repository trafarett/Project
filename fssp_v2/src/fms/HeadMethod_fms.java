package fms;

import org.primefaces.PrimeFaces;

public class HeadMethod_fms
{
 private String passport; 
 public String getPassport(){return passport;}
 public void setPassport(String passport){this.passport=passport;}
 
 private String outHtml;
 public String getOutHtml(){return outHtml;}
 public void setOutHtml (String outHtml){this.outHtml=outHtml;}
 
  public void responsePassport() throws Exception
  { 
   DataBases db=new DataBases();
   try
    { 
     //System.out.println(System.getProperty("java.classpath"));
     //System.out.println(ClassLoader.getSystemClassLoader());
   
     if(passport.length()==10)
      {
       WorkMethods workMethods=new WorkMethods();
       int i=0;
        while(i<5)
         {
          outHtml=workMethods.action(passport);
          if(!outHtml.equals("<tr><td>Повторите запрос</tr></tr>"))
           break;
          i++;
          }
        db.inserResultResponse("NULL",i);
       }
      else 
       outHtml="Введите 10 цифр";
    }
   catch(Exception e)
    {
     outHtml="Неизвестная ошибка";
     }
   finally
    {
     PrimeFaces.current().executeScript("PF('loader').hide();");
     PrimeFaces.current().ajax().update("@widgetVar(result)");
     }
   }
  
  public static void main(String [] args) throws Exception   
  {
   WorkMethods workMethods=new WorkMethods();
   System.out.println(workMethods.action("4510123691"));
  }
}
