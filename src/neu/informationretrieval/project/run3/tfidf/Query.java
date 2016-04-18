package neu.informationretrieval.project.run3.tfidf;

import java.util.ArrayList;
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
	private static ArrayList<String> getFilteredTokens(ArrayList<String> tokens , ArrayList<TokenFilter> filters){
		for(TokenFilter filter : filters){
			tokens = filter.getFilteredTokens(tokens);
		}
		return tokens;
	}
	public static Query getQuery( String[] lineParts , int ngram , ArrayList<TokenFilter> filters) {
		String[] parts = Arrays.copyOfRange(lineParts, 1, lineParts.length);
		ArrayList<String> tokens = new ArrayList<String>();
		tokens.addAll(Arrays.asList(parts));
		tokens = getFilteredTokens(tokens,filters);
		parts = tokens.toArray(new String[1]);
		Query query = new Query();
		query.keywords = new Keyword[parts.length-ngram+1];
		for( int i=0 ; i<=parts.length-ngram ; i++ ) { // Skip the id integer
			query.keywords[i] = new Keyword(Arrays.copyOfRange(parts, i,i+ngram));
		}
		return query;
	}

	private Keyword[] keywords;
}
