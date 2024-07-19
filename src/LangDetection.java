import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.Language;
import com.cybozu.labs.langdetect.LangDetectException;
import java.io.*;
import java.util.*;

public class LangDetection {

    public void init(String profileDirectory) throws LangDetectException {
        DetectorFactory.loadProfile(profileDirectory);
    }
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
  // Aqui regresa solo 1 dato, o puede almacenarse en la clase con main?
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
