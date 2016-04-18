package neu.informationretrieval.project.run3.tfidf;

import java.util.Map;
import java.util.PriorityQueue;

public class Corpus {
	
	public int getTotalDocuments() {
		return documentLengths.size();
	}
	public int getTotalKeywords() {
		return totalKeywords;
	}
	public void setTotalKeywords(int totalKeywords) {
		this.totalKeywords = totalKeywords;
	}
	public double getAverageDocumentLength() {
		return averageDocumentLength;
	}
	public void setAverageDocumentLength(double averageDocumentLength) {
		this.averageDocumentLength = averageDocumentLength;
	}
	
	public PriorityQueue<DocumentRank> getRank( Keyword[] query , RankingFunction rfun ) {
		PriorityQueue<DocumentRank> queue = new PriorityQueue<DocumentRank>();
		
		for(String docId : documentLengths.keySet()) {
			double rank = rfun.getScore(docId, query);
			queue.add(new DocumentRank(docId,rank));
		}
		
		return queue;
	}
	
	@Override
	public String toString() {
		return "Corpus [documentLengths=" + new PrettyPrintingMap<String,Integer>(documentLengths).toString()
				+ ", averageDocumentLength=" + averageDocumentLength
				+ ", totalDocuments=" + getTotalDocuments() + ", totalKeywords="
				+ totalKeywords + "]";
	}
	
	public Map<String, Integer> getDocumentLengths() {
		return documentLengths;
	}
	public void setDocumentLengths(Map<String, Integer> documentLengths) {
		this.documentLengths = documentLengths;
	}

	private Map<String,Integer> documentLengths;
	private double averageDocumentLength = 1;
	private int totalKeywords;
}
