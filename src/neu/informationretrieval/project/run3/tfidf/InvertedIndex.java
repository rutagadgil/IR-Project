package neu.informationretrieval.project.run3.tfidf;

import java.util.HashMap;
import java.util.Map;

public class InvertedIndex {
	
	public InvertedIndex(Map<Keyword,Map<String,Integer>> indexes) {
		this.indexes = indexes;
	}
	
	public Map<Keyword,Map<String,Integer>> getIndexes() {
		return indexes;
	}
	
	public int getTotalKeywords() {
		return indexes.size();
	}

	public void setIndexes(Map<Keyword,Map<String,Integer>> indexes) {
		this.indexes = indexes;
	}
	public int getMaxDocId() {
		return maxDocId;
	}

	public void setMaxDocId(int maxDocId) {
		this.maxDocId = maxDocId;
	}

	public int getNgram() {
		return ngram;
	}

	public void setNgram(int ngram) {
		this.ngram = ngram;
	}
	
	public Map<String,Integer> getDocLengths() {
		return docLengths;
	}
	public void setDocLengths( Map<String,Integer> docLengths ) {
		this.docLengths = docLengths;
	}

	private int maxDocId;
	private int ngram;
	private Map<String,Integer> docLengths = new HashMap<String,Integer>();
	private Map<Keyword,Map<String,Integer>> indexes;
}
