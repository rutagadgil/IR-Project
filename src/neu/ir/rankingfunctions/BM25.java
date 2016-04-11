package neu.ir.rankingfunctions;

import java.util.Map;

import neu.ir.common.Keyword;
import neu.ir.corpus.Corpus;
import neu.ir.index.InvertedIndex;

public class BM25 implements RankingFunction {
	public BM25( Corpus corpus , InvertedIndex invertedIndex , double parameterK , double parameterK1 , double parameterB ) {
		this.corpus = corpus;
		this.invertedIndex = invertedIndex;
		this.parameterK = parameterK;
		this.parameterK1 = parameterK1;
		this.parameterB = parameterB;
	}
	
	@Override
	public double getScore(String docId , Keyword[] query) {
		double score = 0.0;
		for( Keyword keyword : query ) {
			double tf = termFrequency(docId,keyword);
			double idf = IDF(keyword);
			double K = parameterK * (1-parameterB+parameterB * corpus.getDocumentLengths().get(docId)/corpus.getAverageDocumentLength());
			// this equation is specifically for unigram index
			// TODO: change the equation for ngram index (qfi).
			double qfi = 1;
			score += (idf * tf * (parameterK + 1))/(tf + K) * (parameterK1+1)*qfi/(parameterK1 + qfi);
		}
		return score;
	}
	
	private double termFrequency(String docId, Keyword keyword) {
		if(invertedIndex.getIndexes().containsKey(keyword)){
			Map<String,Integer> docMap = invertedIndex.getIndexes().get(keyword);
			if(docMap.containsKey(docId)){
				return docMap.get(docId);
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
		return Math.log((N-qi+0.5)/(qi+0.5));
	}
	@Override
	public String getName() {
		return "BM25_engine";
	}
	
	private InvertedIndex invertedIndex;
	private double parameterK;
	private double parameterB;
	private Corpus corpus;
	private double parameterK1;
}
