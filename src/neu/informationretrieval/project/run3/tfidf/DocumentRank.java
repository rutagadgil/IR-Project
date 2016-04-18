package neu.informationretrieval.project.run3.tfidf;

public class DocumentRank implements Comparable<DocumentRank>{
	public DocumentRank(){ }
	public DocumentRank(String docId, double relevance) {
		super();
		this.docId = docId;
		this.relevance = relevance;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public double getRelevance() {
		return relevance;
	}
	public void setRelevance(double relevance) {
		this.relevance = relevance;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docId == null) ? 0 : docId.hashCode());
		long temp;
		temp = Double.doubleToLongBits(relevance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentRank other = (DocumentRank) obj;
		if (docId == null) {
			if (other.docId != null)
				return false;
		} else if (!docId.equals(other.docId))
			return false;
		if (Double.doubleToLongBits(relevance) != Double
				.doubleToLongBits(other.relevance))
			return false;
		return true;
	}
	@Override
	public int compareTo(DocumentRank arg0) {
		// Greater Wins
		return Double.compare(arg0.relevance, relevance);
	}

	private String docId;
	private double relevance;
}
