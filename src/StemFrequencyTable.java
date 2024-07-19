import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import org.tartarus.snowball.*;
import org.tartarus.snowball.ext.*;

public class StemFrequencyTable {

/*  public static final int en = 1;
  public static final int es = 2;
  public static final int fr = 3; */


    public enum Language { 
    /** Idioma ingles */ en, 
    /** Idioma espa&ntilde;ol*/ es, 
    /** Idioma frances */ fr }

    /**
     * Lista de llaves de cada idioma.
     */
    Enumeration stemList;
    /**
     * Tabla de stems-palabras.
     */
    private Hashtable<String, Vector> stems;
    /**
     * Tabla de palabra-frecuencia.
     */
    private Hashtable<String, Integer> freq;
    /**
     * Enumeration del idioma actual.
     */
    private Language currentLang;
    /**
     * Constructor
     * @param idioma de las palabras manejadas en la tabla.
     */
    public StemFrequencyTable(String idioma) {
	stems = new Hashtable(15);
	freq = new Hashtable(30);
	currentLang = Language.valueOf(idioma);
  }
    /**
     * Obtiene el stem de la palabra, si existe.
     * @param word de la que se encontrara su stem.
     * @return String del stem encontrado.
     */
    public String getStem(String word) {
	String key;
	SnowballStemmer stemmer = null;
	// Ya esta	Language currentLang = Language.valueOf(lang);
      switch(currentLang) { 
	case en: {
	 	  stemmer = new englishStemmer();
	  	  break;
		}
	case es: {
		  stemmer = new spanishStemmer();
		  break;
		}
	case fr: {
		  stemmer = new frenchStemmer();
		  break;
		}
      default:
	  {
	      System.out.println("Idioma no identificado");
	  }

    } 
    stemmer.setCurrent(word);
    stemmer.stem();
    key = stemmer.getCurrent();
    //System.out.println(word + " -> " + key);
    return key;
    } 

    /**
     * Obtiene los stems de un vectors de palabras.
     * @param palabras de las que se van a encontrar el stem.
     */
  public void findStems(Vector palabras) {
    int i, count;
    String word, key;
    Vector variant = null;
    count = 0;       
    for(i=0; i<palabras.size(); i++) {
     variant = new Vector();
      word = (String)palabras.get(i);
      key = getStem((String)palabras.get(i));
      if(stems.containsKey(key)) {
	if(stems.get(key).contains(word)) {
//	if(freq.containsKey(word)) {
	  count = freq.get(word) + 1;
	  freq.put(word, count);
	}
	else {
	  variant = stems.get(key);
//	  stems.get(key).add(word);
//	  System.out.print("Nueva palabra: " + word + ": " + variant + "("+key +") -> ");
//	  stems.get(key).add(word);
	  variant.add(word);
  //        System.out.println(variant +" D:");
	  stems.put(key, variant);
	  freq.put(word, 1);
	}
      }
      else { 
	variant.add(word); 
//	System.out.println(variant + " :D");
        stems.put(key, variant); 
	freq.put(word, 1);
//      stems.put( getStem((String)palabras.get(i)), (String)palabras.get(i) );
      }
    }
    return;
  }

    /**
     * Obtiene las palabras de mayor frecuencia del mismo stem.
     * @return Vector con las palabras de mayor frecuencia encontradas, que pertenecen al mismo stem.
     */
  public Vector retrieveWords() {
    int i, j, major, freqMayor, newValue, total;
    major = freqMayor = newValue = total = 0;
    String actualWord, newWord;
    stemList = stems.keys();
    String root = null;
    Vector current = null;
    Vector common = new Vector();
    while(stemList.hasMoreElements()) {
      current = new Vector();
      root = (String)stemList.nextElement();
      current = (Vector)stems.get(root);
      actualWord = (String)current.get(0);
      total = major = freq.get(actualWord);
//      System.out.println(actualWord + " -> " + major + " (" + current.size() + ")");
      for(i=1; i<current.size(); i++) {
		newWord = (String)current.get(i);
		newValue = freq.get(newWord);
		total = total + newValue;
		if(newValue > major) {
		  major = newValue;
		  actualWord = newWord;
         	}
      }
      j=0;
      do {
      common.add(actualWord);
      j++;
//      System.out.println(j + " < " + total);     
      } while(j<total);
    }
//    System.out.println(common);
    return common;
  }

    /**
     * Imprime las tablas de stem-palabras y palabra-frecuencia.
     */
  public void printTable() {
    System.out.println("Tabla stem-palabras: \n" + stems.toString());
    System.out.println("Tabla palabra-frecuencia: \n" + freq.toString());
    return;
    } 
}
