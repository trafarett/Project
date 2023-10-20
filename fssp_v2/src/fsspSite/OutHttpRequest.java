package fsspSite;

import java.util.ArrayList;

public class OutHttpRequest
{
 public OutHttpRequest()
  {   
   html="";
   listHtml=new ArrayList<>();
   result="";
   sid="";
   error=0;
   }
 
 private String html;
 public void setHtml(String html){this.html=html;}
 public String getHtml(){return html;};
 
 private ArrayList<String> listHtml;
 public void setListHtml(ArrayList<String> listHtml){this.listHtml=listHtml;}
 public ArrayList getListHtml(){return listHtml;};
 
 private String result;
 public void setResult(String result){this.result=result;}
 public String getResul(){return result;};
 
 private String sid;
 public void setSid(String sid){this.sid=sid;}
 public String getSid(){return sid;};
 
 private int error;
 public void setError(int error){this.error=error;}
 public int getError(){return error;} 
}
