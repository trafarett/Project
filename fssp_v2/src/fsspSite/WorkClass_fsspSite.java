package fsspSite;

import fssp.DataBases_v2;

import java.io.IOException;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WorkClass_fsspSite
{
 public String parseCaptcha (String response) throws IOException
 {
  Document document=Jsoup.parse(response,"UTF-8");
  Elements image=document.select("img");
  String out=image.outerHtml().replaceAll("\\\\&quot;", "");  
  return out;
 }
 
 public OutHtmlResult parseHTML (String response) throws IOException
  {
   OutHtmlResult outResult=new OutHtmlResult();
   Map<Integer,String> listPage=new HashMap<>();
   DataBases_v2 db=new DataBases_v2();   
   String linkPages="";
   String numberPage="";
   try
    {
     String ENCODING_TO_BE_APPLIED="cp1251";
     String MARKER="__MARKER_I__";      
     String CYRILLIC_I=new String("�".getBytes("UTF-8"), ENCODING_TO_BE_APPLIED);  
     response=response.replace(CYRILLIC_I, MARKER);
     response=new String(response.getBytes(ENCODING_TO_BE_APPLIED),"UTF-8");
     response=response.replace(MARKER,"�"); 
     
     response = response.replaceAll("\\\\\"", "'");  
     
     Document document=Jsoup.parse(response, "UTF-8");
     Elements html=document.getElementsByClass("results");  
     String out=html.outerHtml().replaceAll("\\\\r", "").replaceAll("\\\\n","");
     
     Elements tableResult=document.getElementsByTag("table"); 
     Element isPage=document.getElementsByClass("pagination").first();
     if(tableResult.size()>0)
      {
       out=getTableResult(out);
       outResult.setHtml(out);    
       if(isPage!=null)
        {      
         Elements pages=isPage.select("a");
         for (int i = 0; i < pages.size(); i++) 
          {
           numberPage=getNumberPage(pages.get(i).attr("href")).replace("&page=","");
           /* if(numberPage.equals(""))
            numberPage="N/A_"+i; */
           linkPages=pages.get(i).attr("href");
           listPage.put(Integer.valueOf(numberPage),linkPages);        
           }
         }                    
       outResult.setListPages(listPage); 
       }
     else 
      {
       outResult.setHtml(out);
       db.insertResult("Success", 0, null);
       }
     }
   catch(Exception e)
   {
    
   }
   return outResult;
  }
 
 public String getTableResult(String html) throws SQLException
 {
  String outHtmlTable="";
  String outHtmlRow;
  int count_debt=0; 
  DataBases_v2 db=new DataBases_v2(); 
  try
   {
    Document doc=Jsoup.parse(html);
    Element table=doc.select("table").first();
    Elements rows=table.select("tr");
    BigDecimal sumDolg=new BigDecimal(0);   
    for (int i=1; i<rows.size(); i++) 
     {
      outHtmlRow="";
      Element row=rows.get(i);
      Elements cols=row.select("td");
      if(cols.size()==1)
       outHtmlRow+="<td colspan=\"7\"><b>"+cols.get(0).text()+"</b></td>";
      else
       {   
        for(int j = 0; j < cols.size(); j++)
         { 
          if(j==4) continue;
          outHtmlRow+="<td>"+cols.get(j).text()+"</td>";
          if(j==5)
          sumDolg=sumDolg.add(getSum(cols.get(j).text()));
          }
        count_debt++;
        }
      outHtmlTable+="<tr>"+outHtmlRow+"</tr>";
      }
    outHtmlTable="<table><tr><td><img Class=\"image\" src=\"resources/Image/icons8-good-3.png\" alt=\"�������\"></td>"+ 
                       "<td>�����: "+count_debt+" ��������������. ����� ����� �����: "+sumDolg+" ���. </td></tr></table>"+
                 "<table class=\"table\"><tr>"+        
        "<th>������� <span>(���. ����: ���, ���� � ����� ��������; ��. ����: ������������, ��. �����, ����������� �����, ���)</span></th>"+  
        "<th>�������������� ������������ <span>(�����, ���� �����������)</span></th>"+  
        "<th>��������� ��������������� ��������� <span>(���, ���� �������� �������, �����, ������������ ������, ��������� �������������� ��������, ��� ����������-�����������)</span></th>"+
        "<th>����, ������� ��������� ��� ����������� �� <span>(������, �����, ����� ���������)</span></th>"+
        "<th>������� ����������, ����� ������������ �������������</th>"+  
        "<th>����� �������� ��������� <span>(������������, �����)</span></th>"+  
        "<th>�������� �������-�����������, ������� ��� ��������� ���������� </th>"+ 
        "</tr>"+outHtmlTable+"</table>";
        
    db.insertResult("Success", count_debt, sumDolg);
    }   
   catch(Exception e)
   {
    outHtmlTable="������ � ��������� ����������"+e.getMessage();
    db.insertResult("Error_"+e.getMessage(), 0, null);
    }
  return outHtmlTable;
 } 
 
 public BigDecimal getSum(String subject)
  {
   Pattern pat=Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
   Matcher matcher=pat.matcher(subject);
   BigDecimal sum = new BigDecimal(0);      
   while (matcher.find()) 
    {       
     sum=sum.add(new BigDecimal(matcher.group().replaceAll(",", ".")));              
     }       
   return sum;
   }
 
 public String getNumberPage(String link)
  {
   String outPage="";
   int page=0;
   page=link.indexOf("page=");
   if(page!=-1)
    {
     String out="";
     for(int i=page+5;i<link.length();i++)
      {
       if(Character.isDigit(link.charAt(i)))
        out+=link.charAt(i);
       else break;
       }
      if(!out.equals(""))
      outPage="&page="+out;
     }
   return outPage;
   }
}
