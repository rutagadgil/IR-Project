package neu.ir.index;

import java.util.Map;

import neu.ir.common.Keyword;

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

	private int maxDocId;
	private int ngram;
	private Map<Keyword,Map<String,Integer>> indexes;
}
