package fssp;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBases_v2
 {
 private String urlVds="jdbc:mysql://45.142.36.30:3306/testDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; 
 //jdbc:mysql://localhost:3306
 private String userVds="user";
 private String passVds="qbasic";
 
 public void insertResult(String result,int count_debt,BigDecimal sum_debt) throws SQLException
  {
   Statement stmt;
   Connection conn=null;
   try 
    {
     Class.forName("com.mysql.cj.jdbc.Driver");
     conn=DriverManager.getConnection(urlVds,userVds,passVds);    
     stmt=conn.createStatement();      
     stmt.executeUpdate("INSERT INTO statistic (TIME,result,count_debt,sum_debt) VALUES (NOW(),'"+result+"',"+count_debt+","+sum_debt+")");
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
  
  public void insertCapcha(String capcha) throws SQLException
   { 
    Statement stmt; 
    Connection conn=null;
    try 
     {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn=DriverManager.getConnection(urlVds,userVds,passVds);    
      stmt=conn.createStatement();       
      stmt.executeUpdate("INSERT INTO Capcha (capcha,time) VALUES('"+capcha+"',NOW())");
      stmt.close();
      conn.close(); 
      }
     catch (Exception e) 
      {
      if(conn!=null)
       conn.close();
      System.out.println(e.toString());       
      }    
    }
  
  public String checkCapcha() throws SQLException
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
      rs=stmt.executeQuery("SELECT capcha FROM Capcha ORDER BY TIME DESC LIMIT 1");
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
  
  public void deleteCapcha() throws SQLException
   {
    Statement stmt;        
    
    Connection conn=null;
    try 
     {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn=DriverManager.getConnection(urlVds,userVds,passVds);    
      stmt=conn.createStatement();     
      stmt.executeUpdate("DELETE FROM Capcha");       
      stmt.close();
      conn.close(); 
     }
     catch(Exception e) 
      {
       if(conn!=null)
        conn.close();
       System.out.println(e.toString());       
       }     
   }
}
