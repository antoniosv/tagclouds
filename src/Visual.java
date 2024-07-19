import org.mcavallo.opencloud.*;
import java.io.*;
import java.util.*;

public class Visual extends Page{
    /**
     * Constructor
     * @param cssPath Ubicacion de la hoja de estilos.
     * @param htmlPath Ubicacion deseada del documento html.
     * @param fileName para nombrar el archivo html.
     */
  public Visual(String cssPath, String htmlPath, String fileName) {
    super("Generaci&oacute;n de nube de etiquetas", cssPath+"style.css", htmlPath+fileName+"_tagcloud");
  }
    /**
     * Desplega la nube de tags en un documento html.
     * @param cloud de la que se construira la nube en html.
     */
  public void displayCloud(Cloud cloud) {
    String name;
    int weight = 0;
    String link;
    for(Tag tag : cloud.tags()) {
      name = tag.getName();
	//System.out.println(weight);
      link = tag.getLink();
//	if(name.equals("wait"))  tag.multiply((Double) 0.1);
	tag.setWeight(tag.getWeight() - 10);
      weight = tag.getWeightInt();
      content.append("<A HREF=\"" + link + "\" style=\"font-size: "+ weight + "px\">" + name + "</A> ");
	System.out.println(name + "  weight-> " + weight + "  score->  " + tag.getScore());
      //content.append("<FONT SIZE="+weight+"px>"+name+"</FONT>   ");
    }
  super.createHTML();
  }
}
