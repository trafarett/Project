package fssp;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Subjects_v2
{
  public Subjects_v2()
   {
    name="";
    exe_production="";
    details="";
    subject="";
    department="";
    bailiff="";
    ip_end="";
    sum="";
    }
    
  private String name;
  public void setName(String name){this.name=name;}
  public String getName()
   {
    name=name.substring(1,name.length()-1);
    //name=name.replaceAll("\\","");
    return name;
    }    
  
  private String exe_production;
  public void setExe_production(String exe_production){this.exe_production=exe_production;}
  public String getExe_production()
   {
    exe_production=exe_production.substring(1,exe_production.length()-1);
    return exe_production;
    }    
  
  private String details;
  public void setDetails(String details){this.details = details;}
  public String getDetails()
   {
    details=details.substring(1,details.length()-1);
    return details;
    }    
  
  private String subject;
  public void setSubject(String subject){this.subject=subject;}
  public String getSubject()
   {
    String loanMask="кредит";    
    subject=subject.substring(1,subject.length() - 1);
    if(subject.contains(loanMask))
     {
      subject="<font color=\"red\">"+subject+"</font>";
      }
    return subject;
    }  
  
  private String department;
  public void setDepartment(String department){this.department=department;}
  public String getDepartment()
   {
    if(department.charAt(0) == '"')   
    department=department.substring(1,department.length()-1);
    return department;
    }    
    
  private String bailiff;
  public void setBailiff(String bailiff){this.bailiff=bailiff;}
  public String getBailiff()
   {
    bailiff=bailiff.substring(1, bailiff.length()- 1);
    bailiff=bailiff.replaceAll("<b>","").replaceAll("</b>","");
    return bailiff;
    }    
  
  private String ip_end;
  public void setIp_end(String ip_end){this.ip_end=ip_end;}
  public String getIp_end()
   {
    ip_end=ip_end.substring(1,ip_end.length()-1);      
    return ip_end;
    }
  
  private String sum;
  public void setSum(String sum){this.sum=sum;} 
  public BigDecimal getSum()
   {
    Pattern pat=Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
    Matcher matcher=pat.matcher(sum);
    BigDecimal sum = new BigDecimal(0);      
    while (matcher.find()) 
     {       
      sum=sum.add(new BigDecimal(matcher.group().replaceAll(",", ".")));              
      }       
    return sum;
    }
}