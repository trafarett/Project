package fms;

public class OutResult
{
 public OutResult()
  {
   object=null;
   error=0;
   }
 
 private Object object;
 public void setObject(Object object){this.object=object;}
 public Object getObject(){return object;};
 
 private int error;
 public void setError(int error){this.error=error;}
 public int getError(){return error;} 
}
