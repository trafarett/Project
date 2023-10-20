package fssp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class PostRegions
{
 public HashMap regions() throws Exception
 {
  String pathRegions=this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
  pathRegions=pathRegions.replace("WEB-INF/classes/", "");
  pathRegions=pathRegions.replace("file:","");  
  String key,region;
  
  File file=new File(pathRegions+"/resources/PostRegions.csv");
  HashMap<String,String> mapRegions = new HashMap<>();
  FileInputStream fis=new FileInputStream(file);                                         
  BufferedReader reader=new BufferedReader(new InputStreamReader(fis, "UTF-8"));           
  String line=reader.readLine();    
  while (line != null) 
   {
    key="";
    region="";    
    for(int i=0; line.length()>i;i++)
     {     
      if(line.charAt(i)!=';')
       key+=line.charAt(i);
      else
       {
        ++i;
        region=line.substring(i,line.length());
        break;
        }
      }
    mapRegions.put(key,region);       
    line=reader.readLine();
    }
  return mapRegions;
  }
}
