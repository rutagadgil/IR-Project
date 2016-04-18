package neu.informationretrieval.project.run3.tfidf;

import java.util.ArrayList;

public class TokenTrimmerAndEmptyDropper implements TokenFilter {

	@Override
	public ArrayList<String> getFilteredTokens(ArrayList<String> tokens) {
		ArrayList<String> newTokens = new ArrayList<String>();
		for(String token : tokens){
			String nt = token.trim();
			if( !(nt == null || "".equals(nt)) ){
				newTokens.add(nt);
			}
		}
		return newTokens;
	}

}
