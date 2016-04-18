package neu.informationretrieval.project.run3.tfidf;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvertedIndexReader {
	// private Logger log = LoggerFactory.getLogger(InvertedIndexReader.class);
	
	public InvertedIndexReader(int ngram) {
		this.ngram = ngram;
	}
	
	public InvertedIndex ReadInvertedIndex( InputStream inStream , Map<Integer,String> docMap ) {
		Scanner in = new Scanner(inStream);
		Map<Keyword,Map<String,Integer>> indexes = new HashMap<Keyword,Map<String,Integer>>();
		
		while( in.hasNextLine() ){
			String line = in.nextLine();
			if( "".equals(line.trim()) ) continue;
			// log.debug(String.format("Line => %s", line));
			
			Scanner lineScanner = new Scanner(line);
			String[] words = new String[ngram];
			for(int i=0 ; i<ngram ; i++){
				words[i] = lineScanner.next();
			}
			
			Map<String,Integer> locations = new HashMap<String,Integer>();
			while( lineScanner.hasNext() ) {
				Location loc = getLocation(lineScanner.next(),lineScanner.next(), docMap);
				if( loc.getTermFrequency() < 0 )
					System.out.println(loc);
				locations.put(loc.getDocId(),loc.getTermFrequency());
			}
			lineScanner.close();
			indexes.put(new Keyword(words), locations);
		}
		in.close();
		InvertedIndex ii = new InvertedIndex(indexes);
		ii.setNgram(ngram);
		return ii;
	}
	
	public Location getLocation(String docIdHashCode , String freq, Map<Integer,String> docMap){
		String docId = docMap.get(Integer.parseInt(docIdHashCode));
		return new Location(docId,Integer.parseInt(freq));
	}
	
	private int ngram;
}
