package neu.ir.common;

import java.util.Arrays;

public class Query {
	public Query() { }
	
	public Query(Keyword[] keywords) {
		super();
		this.keywords = keywords;
	}
	
	public Keyword[] getKeywords() {
		return keywords;
	}

	public void setKeywords(Keyword[] keywords) {
		this.keywords = keywords;
	}
	
	@Override
	public String toString() {
		return "Query [keywords=" + Arrays.toString(keywords) + "]";
	}

	public static Query GetQuery( String[] lineParts , int ngram ) {
		String[] parts = Arrays.copyOfRange(lineParts, 1, lineParts.length);
		Query query = new Query();
		query.keywords = new Keyword[parts.length-ngram+1];
		for( int i=0 ; i<=parts.length-ngram ; i++ ) { // Skip the id integer
			query.keywords[i] = new Keyword(Arrays.copyOfRange(parts, i,i+ngram));
		}
		return query;
	}

	private Keyword[] keywords;
}
