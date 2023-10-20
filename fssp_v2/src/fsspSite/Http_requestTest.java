package fsspSite;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class Http_requestTest
{ 

 /**
  * @see Http_request#fssp_connection(String,int,String)
  */
 @Test
 public void testFssp_connection() throws Exception
 {  
  Http_request request=new Http_request();
  OutHttpRequest outRequest=new OutHttpRequest();
  String params="https://is-node7.fssp.gov.ru/ajax_search?callback=jQuery34002699358782772554_1685987942583&system=ip&is[extended]=1&nocache=1&is[variant]=1&is[region_id][0]=-1&is[last_name]=%D0%A1%D0%B5%D1%80%D0%B3%D0%B5%D0%B5%D0%B2&is[first_name]=%D0%A2%D0%B8%D0%BC%D0%BE%D1%84%D0%B5%D0%B9&is[drtr_name]=&is[ip_number]=&is[patronymic]=&is[date]=01.01.1980&is[address]=&is[id_number]=&is[id_type][0]=&is[id_issuer]=&is[inn]=&_=1685987942584";

  outRequest=request.fssp_connection(params,0,"0");
  assertTrue(outRequest.getHtml().contains("captcha"));
 }
}
