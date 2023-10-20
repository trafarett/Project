package fsspSite;

import fssp.WorkClass;

import java.io.Serializable;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import org.junit.Test;

public class WorkClass_fsspSiteTest
{ 
 /**
  * @see WorkClass_fsspSite#getSum(String)
  */
 @Test
 public void testGetSum()
 {
  WorkClass_fsspSite wc=new WorkClass_fsspSite();
  BigDecimal check=wc.getSum("Задолженность: 22233696.86 руб.");
  BigDecimal real=new BigDecimal(22233696.86).setScale(2, BigDecimal.ROUND_HALF_EVEN);
  assertEquals(check,real);  
 }
 
 @Test
 public void getNumberPage()
 {
  WorkClass_fsspSite wc=new WorkClass_fsspSite();
  String link=wc.getNumberPage("https://is-node5.fssp.gov.ru/ajax_search?system=ip&is[extended]=1&nocache=1&is[variant]=1&is[region_id][0]=-1&is[last_name]=%D1%82%D0%B8%D0%BC%D0%BE%D1%84%D0%B5%D0%B5%D0%B2&is[first_name]=%D1%81%D0%B5%D1%80%D0%B3%D0%B5%D0%B9&is[drtr_name]=&is[ip_number]=&is[patronymic]=%D0%B1%D0%BE%D1%80%D0%B8%D1%81%D0%BE%D0%B2%D0%B8%D1%87&is[date]=*&is[address]=&is[id_number]=&is[id_type][0]=&is[id_issuer]=&is[inn]=&_=1686225761563&page=244&t=1");
  String real="page=244";  
  assertEquals(link,real);  
 }
 
}
