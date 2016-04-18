package neu.informationretrieval.project.run3.tfidf;

public class Location {
	public Location(String docId, int termFrequency) {
		this.docId = docId;
		this.termFrequency = termFrequency;
	}
	
	public String getDocId() {
		return docId;
	}
	
	public void setDocId(String docId) {
		this.docId = docId;
	}
	
	public int getTermFrequency() {
		return termFrequency;
	}
	
	public void setTermFrequency(int termFrequency) {
		this.termFrequency = termFrequency;
	}
	
	@Override
	public String toString() {
		return "Location [docId=" + docId + ", termFrequency=" + termFrequency
				+ "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docId == null) ? 0 : docId.hashCode());
		result = prime * result + termFrequency;
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
		Location other = (Location) obj;
		if (docId == null) {
			if (other.docId != null)
				return false;
		} else if (!docId.equals(other.docId))
			return false;
		if (termFrequency != other.termFrequency)
			return false;
		return true;
	}

	private String docId;
	private int termFrequency;
}
