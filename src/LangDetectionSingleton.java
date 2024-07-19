/**
 * @author Jesus Antonio Soto
 * @version 1.0
 * @since 2011-September
 */

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.Language;
import com.cybozu.labs.langdetect.LangDetectException;
import java.io.*;
import java.util.*;

public class LangDetectionSingleton {

    private static LangDetection detector;
    private static boolean instance;

    public static LangDetection getInstance(String profileDirectory) throws LangDetectException {
	if (instance == false) {
	    detector.init(profileDirectory);
	    detector = new LangDetection();
	    instance = true;
	}
	return detector;
    }

    /**
     * Inicializa el sistema de deteccion de idiomas.
     * @param profileDirectory que especifica la ubicacion de las profiles de cada idioma.
     * @throws LangDetectException
     */
    private void init(String profileDirectory) throws LangDetectException {
        DetectorFactory.loadProfile(profileDirectory);
    }
    /**
     * Detecta el idioma del texto proporcionado.
     * @param text que se va a analizar para detectar el idioma.
     * @return String que represente el idioma detectado.
     * @throws LangDetectException
     */
    public String detect(String text) throws LangDetectException {
        Detector detector = DetectorFactory.create();
        detector.append(text);
        return detector.detect();
    }
    public ArrayList<Language> detectLangs(String text) throws LangDetectException {
        Detector detector = DetectorFactory.create();
        detector.append(text);
        return detector.getProbabilities();
    }
    /**
     * Detecta el idioma de cada palabra del vector, y valida si equivale al idioma buscado.
     * @param tokens del vector con las palabras.
     * @param lang con el que se busca comparar.
     * @return sack de palabras filtradas.
     * @throws LangDetectException
     */
  public Vector detectTokens(Vector tokens, String lang) 
  {
    int i;
	String word;
	Vector sack = new Vector(3,1);
	
	for(i=0; i<tokens.size(); i++) {
	  word = (String)tokens.get(i);
	  try {
	    if(detect(word).equals(lang)) {
	      sack.add(word);
	    }
	  } catch(LangDetectException e){
	    System.out.println("Lang exception: " + e.getMessage());
            }
	}
	return sack;
  }

}
