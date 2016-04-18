package neu.informationretrieval.project.run3.tfidf;

import java.util.Arrays;

public class Keyword {
	public Keyword(String[] words) {
		this.words = words;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(words);
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
		Keyword other = (Keyword) obj;
		if (!Arrays.equals(words, other.words))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Keyword [words=" + Arrays.toString(words) + "]";
	}
	
	private String[] words;
}
