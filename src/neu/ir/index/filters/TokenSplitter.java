package neu.ir.index.filters;

import java.util.ArrayList;
import java.util.Arrays;

import neu.ir.index.TokenFilter;

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
