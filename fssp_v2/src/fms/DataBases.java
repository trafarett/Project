package fms;

import java.sql.*;

public class DataBases
{
  String urlVds="jdbc:mysql://45.142.36.30:3306/testDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
  String userVds="user";
  String passVds="qbasic";
  
   /* DataBases()
    {
     WorkMethods workMethods=new WorkMethods();
     this.urlVds=workMethods.readProperties("urlDataBaseVds");
     this.userVds=workMethods.readProperties("userDataBaseVds");
     this.passVds=workMethods.readProperties("passDataBaseVds");
     } */
   
    public String[] readDataBase() throws Exception
    {        
        String[] outResult=new String[2];
        Statement stmt;
        Connection conn=null;
        ResultSet rs;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn=DriverManager.getConnection(urlVds,userVds,passVds);
            stmt=conn.createStatement();
            rs=stmt.executeQuery("SELECT SESSIONID, uid FROM CookieFMS");
            while (rs.next())
            {
                outResult[0]=rs.getString(1);
                outResult[1]=rs.getString(2);
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception e)
        {
            if(conn!=null)
                conn.close();
            System.out.println(e.toString());
        }
        return outResult;
    }

    public void writeDataBase(String query) throws Exception
    {
        Statement stmt;
        Connection conn=null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn=DriverManager.getConnection(urlVds,userVds,passVds);
            stmt=conn.createStatement();
            stmt.executeUpdate(query);
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
    
 public void inserResultResponse (String result,int amountTry) throws Exception
 {
     Statement stmt;
     Connection conn=null;
     String query="";
     try
     {
         Class.forName("com.mysql.cj.jdbc.Driver");
         conn=DriverManager.getConnection(urlVds,userVds,passVds);
         stmt=conn.createStatement();
         query="INSERT INTO StatisticFMS (time,result,AmountTry) VALUES (NOW(),NULL,"+amountTry+")";
         stmt.executeUpdate(query);
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
}
