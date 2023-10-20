package fsspSite;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBases_fsspSite
{
 private String urlVds="jdbc:mysql://45.142.36.30:3306/testDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
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
}
