package neu.ir.index.filters;

import java.util.ArrayList;

import neu.ir.index.TokenFilter;
import neu.ir.stemming.Stemmer;

public class StemmerFilter implements TokenFilter {

	@Override
	public ArrayList<String> getFilteredTokens(ArrayList<String> tokens) {
		ArrayList<String> newTokens = new ArrayList<String>();
		
		for(String token: tokens){
			stem.add(token.toCharArray(), token.length());
			stem.stem();
			newTokens.add(stem.toString());
		}
		return newTokens;
	}
	
	private Stemmer stem = new Stemmer();
}
