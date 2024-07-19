import java.io.*;
import java.util.*;

public class GeneralUtils
{
 public static void writeFile(String path, boolean append, StringBuffer contents) throws IOException
 {
  String outFile=path;
  File f=new File(outFile);
  FileOutputStream fs=new FileOutputStream(f, append);
  PrintWriter pw=new PrintWriter(fs);
  
  pw.println(contents.toString());
  pw.close();
 }

 public static Vector readFileContents(String filePath) throws IOException
 {
	  String line;
	  Vector lines;
	  File entrada;
	  FileInputStream is;
	  BufferedReader bf;
	  boolean eof;
	  int id;
		 
		 entrada=new File(filePath);
		 is=new FileInputStream(entrada);
		 bf=new BufferedReader(new InputStreamReader(is));
		 lines=new Vector();
		 eof=false;
		
		 while(!eof)
		 {			
		 	line=bf.readLine();
			 
			if(line==null) 
			 eof=true;
			else
			{
			 if(!line.equals("")) //else ignore...
			  lines.add(line);
			}			
		 }
		 
								
		 bf.close();
		 is.close();
		 
		 return lines;
 }
 
 //Igual que read file contents, pero regresa un vector de enteros...
 public static Vector getIntegerVectorFromFile(String filePath) throws IOException
 {
	  int element, i;
	  String line;
	  Vector integers=new Vector();
	  Vector lines=readFileContents(filePath);
	  
	  for(i=0;i<lines.size();i++)
	  {
	   line=((String)lines.get(i)).trim();
	   element=Integer.parseInt(line);
	   integers.add(element);
	  }
	  
	  return integers;
 }
 
 public static int readIntegerValueFromFile(String filePath) throws IOException
 {
  int value;
  Vector lines=GeneralUtils.readFileContents(filePath);
  value=Integer.parseInt((String)lines.get(0));
  
  return value;
 }
 
 public static void writeVectorFile(Vector values, String fileName, String screenName) throws IOException
 {
  int i;
  System.out.println("==========Writing " + screenName + " file=========");
  StringBuffer bf=new StringBuffer();
 
  for(i=0;i<values.size();i++)  
   bf.append(values.get(i) + "\r\n");
  
  GeneralUtils.writeFile(fileName, false, bf);

 }
 
 //Esta podria ser igual a escribir un vector, pero de un solo valor...
 public static void writeValueFile(String value, String fileName, String screenName) throws IOException
 {
  StringBuffer bf=new StringBuffer();
  bf.append(value);
  
  System.out.println("==========Writing " + screenName + " file=========");
  GeneralUtils.writeFile(fileName, false, bf);
 }

 
 
}