package neu.informationretrieval.project.run3.tfidf;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopwordFilter implements TokenFilter {
	public static final Logger log = LoggerFactory.getLogger(StopwordFilter.class);
	
	public StopwordFilter(String stopwordFilename) {
		this.stopwordFilename = stopwordFilename;
	}
	
	@Override
	public ArrayList<String> getFilteredTokens(ArrayList<String> tokens) {
		ArrayList<String> newTokens = new ArrayList<String>();
		Set<String> stopwords = getStopWords();
		
		for(String token : tokens){
			if(!stopwords.contains(token)){
				newTokens.add(token);
			}
		}
		return newTokens;
	}
	
	private Set<String> getStopWords() {
		Set<String> stopwords = new HashSet<String>();
		Scanner in = null;
		try{
			in = new Scanner( new FileInputStream(stopwordFilename));
			while(in.hasNext()){
				stopwords.add(in.next());
			}
		} catch( Exception exp ) {
			// Ignore
			log.error(exp.toString());
		} finally {
			if( in != null ){
				in.close();
			}
		}
		return stopwords;
	}
	
	private String stopwordFilename;
}
