package frsb;

public class Subjects_frsb
{
  public Subjects_frsb()
   {
    name="";
    INN="";
    adress="";
    status="";    
    }
    
  private String name;
  public void setName(String name){this.name=name;}
  public String getName()
   {
    name=name.substring(1,name.length()-1);
    //name=name.replaceAll("\\","");
    return name;
    }    
  
  private String INN;
  public void setINN(String INN){this.INN=INN;}
  public String getINN()
   {
    //exe_production=exe_production.substring(1,exe_production.length()-1);
    return INN;
    }    
  
  private String adress;
  public void setAdress(String adress){this.adress = adress;}
  public String getAdress()
   {
    //details=details.substring(1,details.length()-1);
    return adress;
    }  
  
  private String status;
  public void setStatus(String status){this.status = status;}
  public String getStatus()
   {
    //details=details.substring(1,details.length()-1);
    return status;
    }  
  
  private String link;
  public void setLink(String link){this.link = link;}
  public String getLink()
   {
    link=link.substring(1,link.length()-1);
    return link;
    }  
}
