package neu.ir.rankingfunctions;

import java.util.Arrays;
import java.util.Map;

import neu.ir.common.Keyword;
import neu.ir.corpus.Corpus;
import neu.ir.index.InvertedIndex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TFIDF implements RankingFunction {
	private static Logger log = LoggerFactory.getLogger(TFIDF.class);
	
	public TFIDF( Corpus corpus , InvertedIndex invertedIndex ) {
		this.corpus = corpus;
		this.invertedIndex = invertedIndex;
	}
	
	@Override
	public double getScore(String docId , Keyword[] query) {
		double score = 0.0;
		// log.info(Arrays.toString(query));
		for( Keyword keyword : query ) {
			double tf = termFrequency(docId,keyword);
			double idf = IDF(keyword);
			score += (idf * tf);
			// log.debug(String.format("For Keyword=%s, DocId=%s (td,idf) = (%s,%s), tf*idf = %s, score = %s", keyword, docId,tf,idf, tf*idf, score));
		}
		return score;
	}
	
	/**
	 * Raw Term Frequency
	 * @param docId
	 * @param keyword
	 * @return
	 */
	private double termFrequency(String docId, Keyword keyword) {
		if(invertedIndex.getIndexes().containsKey(keyword)){
			Map<String,Integer> frequencies = invertedIndex.getIndexes().get(keyword);
			if(frequencies.containsKey(docId)){
				return frequencies.get(docId);
			}
			return 0.0;
		}
		return 0.0;
	}
	
	private double IDF( Keyword k ) {
		double N = corpus.getTotalDocuments();
		double qi = 0.0;
		if( invertedIndex.getIndexes().containsKey(k)){
			qi = invertedIndex.getIndexes().get(k).size();
		}
		return Math.log((N)/(qi));
	}
	
	@Override
	public String getName() {
		return "TFIDF_engine";
	}
	
	private InvertedIndex invertedIndex;
	private Corpus corpus;
}
