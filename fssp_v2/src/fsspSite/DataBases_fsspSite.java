package fsspSite;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPooled;


public class DataBases_fsspSite
{
 private String urlVds="jdbc:mysql://localhost:3306/testDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
 private String userVds="user";
 private String passVds="qbasic";
 
 public void insertSid(String sid) throws SQLException
  {
   Statement stmt;
   Connection conn=null;
   try 
    {
     Class.forName("com.mysql.cj.jdbc.Driver");
     conn=DriverManager.getConnection(urlVds,userVds,passVds);    
     stmt=conn.createStatement();      
     stmt.executeUpdate("UPDATE CookieFSSP SET sid='"+sid+"' WHERE id=1");
     stmt.close();
     conn.close(); 
     }
    catch(Exception e) 
     {
      if (conn!=null)
       conn.close();
      System.out.println(e.toString());    
      }    
   }
 
 public String getSid() throws SQLException
  {
   Statement stmt; 
   ResultSet rs;
   String out="";   
   Connection conn=null;
   try 
    {
     Class.forName("com.mysql.cj.jdbc.Driver");
     conn=DriverManager.getConnection(urlVds,userVds,passVds);    
     stmt=conn.createStatement();    
     rs=stmt.executeQuery("SELECT sid FROM CookieFSSP WHERE id=1");
     while (rs.next()) 
      {
       out=rs.getString(1);     
       }       
     stmt.close();
     conn.close(); 
     }
    catch(Exception e) 
     {
      if(conn!=null)
       conn.close();
      System.out.println(e.toString());       
      }
   return out;
   }

public void setCaptchaImage(String idImg,String captcha,String img_url) throws SQLException
 {
  Statement stmt;
  Connection conn=null;
  try 
   {
    Class.forName("com.mysql.cj.jdbc.Driver");
    conn=DriverManager.getConnection(urlVds,userVds,passVds);    
    stmt=conn.createStatement();      
    stmt.executeUpdate("INSERT INTO FsspCaptchaImage_test (idImg,captcha,count_use,img_url) VALUES ('"+idImg+"','"+captcha+"',NULL,'"+img_url+"')");
    stmt.close();
    conn.close(); 
    }
   catch(Exception e) 
    {
     if (conn!=null)
      conn.close();
     System.out.println(e.toString());    
     }    
  }
}
