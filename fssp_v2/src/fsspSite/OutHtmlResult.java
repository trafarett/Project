package fsspSite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutHtmlResult
{
 public OutHtmlResult()
  {   
   html="";
   listPages=new HashMap<>();   
   listResultHtml=new ArrayList<String>();
   }
 
  private String html;
  public void setHtml(String html){this.html=html;}
  public String getHtml(){return html;};
  
  private Map<Integer,String> listPages;
  public void setListPages(Map<Integer,String> listPages){this.listPages=listPages;}
  public Map getListPages(){return listPages;}; 
  
  private List listResultHtml;
  public void setListResultHtml(List listResultHtml){this.listResultHtml=listResultHtml;}
  public List getlistResultHtml(){return listResultHtml;}; 
}
