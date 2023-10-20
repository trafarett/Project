package fssp;

public class Fio_v2
{
  String surname="";
  String name= "";
  String patronymic="";
  
  public Fio_v2(String fio)
   {
    String Y = fio.trim();    
    int y = 0;
    for (int i = y; i < Y.length() && Y.charAt(i) != ' '; i++)
    {surname += Y.charAt(i); y++;}
    
    while (Y.length() > y && Y.charAt(y) ==' ')
    {y++;}
    
    for (int i = y; i < Y.length() && Y.charAt(i) != ' '; i++)
    {name += Y.charAt(i); y++;}
    
    while (Y.length() > y && Y.charAt(y) ==' ')
    {y++;}
    
    for (int i = y; i < Y.length(); i++)
    {patronymic +=  Y.charAt(i); y++;}
    }
    public String getSurname()
    {return surname;}
    
    public String getName()
    {return name;}
    
    public String getPatronymic()
    {return patronymic;}  
}
