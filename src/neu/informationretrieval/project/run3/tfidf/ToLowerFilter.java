package neu.informationretrieval.project.run3.tfidf;

import java.util.ArrayList;

public class ToLowerFilter implements TokenFilter {

	@Override
	public ArrayList<String> getFilteredTokens(ArrayList<String> tokens) {
		ArrayList<String> newTokens = new ArrayList<String>();
		
		for(String token : tokens){
			newTokens.add(token.toLowerCase());
		}
		
		return newTokens;
	}

}
