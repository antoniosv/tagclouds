/************************************
 Class name:    Page
 Author:        Sara E. Garza
 Creation date: Unknown (not documented)
 Description:   Produces an XHTML page.

*************************************/

/**
* @author Sara E. Garza
* @version 1.0
*/

/************************************
 Major methods:
  createHTML() -- Generates the XHTML file (.htm)
  

 For more information, view the corresponding javadoc.
*************************************/


import java.io.*;

/*$Page*/
public class Page
{
 /**
  * XHTML page header
  */
 protected static final String HEADER="<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n" +
		"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"" +
		" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
		"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n <head>\n" +
		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\n";
 
 /**
  * XHTML page footer
  */	
 protected static final String FOOTER="</body>\n </html>\n";
 
 /**
  * Buffer where the content is to be appended.
  */
 protected StringBuffer content;
 
 /**
  * Page's title.
  */
 private String title;
 
 /**
  * Page's stylesheet
  */
 private String css;
 
 /**
  * Name for the page's file.
  */
 private String fileName;
 
 /**
  * Constructor.
  * @param title page's title
  * @param css page's style sheet
  * @param fileName page's name
  */
 public Page(String title, String css, String fileName)
 {
  this.title=title;
  this.css=css;
  this.fileName=fileName;
  
  content=new StringBuffer();
  
  content.append(HEADER);
  content.append("<title>" + title + "</title>\n");
  content.append("<link rel=stylesheet href=\"" + css + "\" type=\"text/css\" />\n");
  content.append("</head>\n\n <body>\n");
  
 }
 
 /**
  * Creates the XHTML file (.htm)
  * @exception IOException
  */
 
 /*#createHTML()*/
 protected void createHTML()
 {
  content.append(FOOTER);
  
  try{
  		File f=new File(fileName + ".htm");
  		FileOutputStream fs=new FileOutputStream(f);
  		PrintWriter pw=new PrintWriter(fs);
  		pw.println(content.toString());
  		pw.close();
  }catch(Exception e){System.out.println("Error: " + e.getMessage());}
  
 }

 
}
