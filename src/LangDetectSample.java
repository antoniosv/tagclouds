import java.util.ArrayList;
import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.Language;
import com.cybozu.labs.langdetect.LangDetectException;


class LangDetectSample {
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
   
    public static void main(String args[])
    {
	try{
          LangDetectSample sample=new LangDetectSample();
          sample.init(args[0]);
          System.out.println(sample.detect(args[1]));
	}catch(LangDetectException e){
	    System.out.println("Lang exception: " + e.getMessage());
         }
    }

}