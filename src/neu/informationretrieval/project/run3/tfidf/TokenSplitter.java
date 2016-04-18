package neu.informationretrieval.project.run3.tfidf;

import java.util.ArrayList;
import java.util.Arrays;

public class TokenSplitter implements TokenFilter {
	public TokenSplitter(String regex) {
		this.regex = regex;
	}
	
	@Override
	public ArrayList<String> getFilteredTokens(ArrayList<String> tokens) {
		ArrayList<String> newTokens = new ArrayList<String>();
		for(String token : tokens){
			newTokens.addAll(Arrays.asList(token.split(regex)));
		}
		return newTokens;
	}
	
	private String regex;
}
