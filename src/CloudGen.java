/**
 * @author Jesus Antonio Soto
 * @version 1.0
 * @since 2011-August
 */
import org.mcavallo.opencloud.*;
import java.io.*;
import java.util.*;
import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.Language;
import com.cybozu.labs.langdetect.LangDetectException;

public class CloudGen {
    /**
     * Lee un archivo de texto plano.
     * @param filePath Ubicacion del archivo.
     * @return String enorme que contiene todo el contenido del archivo.
     * @throws IOException
     */
  public static String read(String filePath)
  {
    String fullString = new String();
    fullString = "";
    try	{
	  Vector v = GeneralUtils.readFileContents(filePath);
	  fullString = v.toString();
	} catch(IOException e) {
	    System.out.println("IO Exception: " + e.getMessage());
	  }
    return fullString;
  }

    /**
     * Limpia caracteres basura de la string, deja solo palabras.
     * @param stringsota que se va a procesar.
     * @return Vector que contiene una ubicacion por cada palabra
     */  
  public static Vector clean(String stringsota)
  {
    String dummy = new String();
    Vector<String> tagVector = new Vector<String>(20, 10);
    StringTokenizer st = new StringTokenizer(stringsota,".,;:*~!(){}[]|?><'\" ");
    dummy = "";
    while(st.hasMoreTokens()) {
	dummy = st.nextToken();
	  if(dummy.length() > 1) { // tokens de tan solo 1 letra no son tomadas en cuenta :)
	    tagVector.add(dummy);
	  }
	  dummy = "";
	} 
	return tagVector;
  }	

    /**
     * Limpia caracteres basura de la string, deja solo palabras.
     * @param stringsota que se va a procesar.
     * @return String solo palabras, sin signos.
     */
  public static String cleanStr(String stringsota)
  {
    String tagString = new String();
    String dummy = new String();
    tagString = dummy = "";
    StringTokenizer str = new StringTokenizer(stringsota,".,;:*~!(){}[]|?><'\"\n ");
    while(str.hasMoreTokens()) {
      tagString = tagString + " " + str.nextToken();  // crea una stringsota con las tokens separadas con espacios
    }
    return tagString;
  }	
  
    /**
     * Filtra stopwords de un texto, dada una lista de stopwords.
     * @param bag en forma de Vector con las palabras que se van a analizar.
     * @param swPath Ubicacion del archivo que contiene la lista de stopwords.
     * @return Vector que contiene los enunciados de solo palabras, sin stopwords.
     */
  public static Vector filterSw(Vector bag, String swPath) {
	Vector<String> sw = new Vector<String>();
	Vector<String> impWord = new Vector<String>();
        String stopwords = new String();
	stopwords = read(swPath);
	sw = clean(stopwords);
	int count = 0;
	int i, j;
	String slimString = "";
	String temp = new String();
	String temp2 = new String();
	for(i=0; i<bag.size(); i++) {
		for(j=0; j<sw.size(); j++) {
		  temp = (String)bag.get(i);
		  temp = temp.toLowerCase();
		  temp2 = (String)sw.get(j);
		  //System.out.println(temp + " vs " + temp2);
		  if(temp.equals(temp2)) {
		    count++;
		    break;
		  }			
		  /*if( bag.get(i).equals(sw.get(j)) ) { // Es la misma cosa que la anterior, pero este fue un intento con vectores
			count++;
			break;
		  } */
		}
		if(count==0) {
		  impWord.add(temp);
		}
		count = 0;
	}
	 System.out.println(impWord);
    return impWord;
  }

    /**
     * Crea una tag cloud a partir de un documento. 
     * @param doc Documento a procesar.
     * @param readPath Ubicacion de donde se lee el documento.
     * @param savePath Ubicacion de donde se guardara el html de la tag cloud.
     * @param swPath Ubicacion de la lista de stopwords
     * @param maxTags que contendra la tag cloud.
     * @param maxWeight tama&ntilde;o de cada tag.
     * @param detector del idioma.
     * @throws LangDetectException
     */
  public static void buildTagCloud(File doc, String readPath, String savePath, String swPath, int maxTags, int maxWeight, LangDetection detector) {
    String grande, path, language, titleName;
    int i, j, cap;
    cap = 0;  
    Cloud cloud;
    Visual html;
    Vector limpio, filtrado, cloudTags;
    String important;
    grande = language = titleName = null;
    titleName = doc.getName(); 		
    cloud = new Cloud();
    html = new Visual("", savePath, titleName);
    limpio = new Vector();
    filtrado = new Vector();
    cloudTags = new Vector();
    important = new String();
    language = null;
    
    cloud.setMaxWeight(maxWeight);
    cloud.setMaxTagsToDisplay(maxTags);
    cloud.setDefaultLink("http://lmgtfy.com/?q=%s");
    grande = read(readPath+titleName);
    limpio = clean(grande);
    // aqui se debe procesar para detectar idioma por palabra, filtrar stopwords, y hacer stemming, despues se hace la tag cloud :) 
    // System.out.println("Texto original: \n"+grande +"\nPalabras solas: \n"+limpio);
    try{		
//      LangDetection detector = LangDetection.getInstance("other_resources/langdetect-05-09-2011/profiles");
    // Patron singleton necesario para evitar una excepcion al usarse dos veces LoadProfile (usado en el metodo LangDetection.init)
      System.out.println("Deteccion del idioma: "+detector.detect(grande));
      language = detector.detect(grande);
    }catch(LangDetectException e){
	System.out.println("Lang exception: " + e.getMessage());
   	}
    // se filtran stopwords
    filtrado = filterSw(limpio, swPath);
    // System.out.println(filtrado + "->" + language);
    // se hace stemming :o
    StemFrequencyTable stemsPalabra = new StemFrequencyTable(language);
    stemsPalabra.findStems(filtrado);
    // stemsPalabra.printTable();
    // se encuentran palabras con mayor frecuencia que tengan el mismo stem
    cloudTags = stemsPalabra.retrieveWords();
    // System.out.println(cloudTags);
    // System.out.println("tags finales a ser usadas para la tag cloud: \n"+cloudTags);
    important = cloudTags.toString();
    cloud.addText(important);
    html.displayCloud(cloud);
  }

  public static void main(String[] args) {
    String path, savePath, swPath;
    int i, j, cap;
    int maxTags = Integer.parseInt(args[3]);
    int maxWeight = Integer.parseInt(args[4]);
    path = args[0];
    savePath = args[1];
    swPath = args[2];
    LangDetection detector = null;
    try{		
      detector=new LangDetection();
      detector.init("other_resources/langdetect-05-09-2011/profiles");
    }catch(LangDetectException e){
	System.out.println("Lang exception: " + e.getMessage());
    	}
    if(args.length>5) if(args[6].equals("single")) {
      File doc = new File(args[0]+args[5]);	
      buildTagCloud(doc, path, savePath, swPath, maxTags, maxWeight, detector);
      return;
    }
    File directorio = new File(args[0]);
    File[] files = directorio.listFiles();
    File actual = null;
    for(j=0; j<files.length; j++)
	{
		actual = files[j];
		System.out.println(actual.getName());
    		buildTagCloud(actual, path, savePath, swPath, maxTags, maxWeight, detector);
  	}
  }
}
