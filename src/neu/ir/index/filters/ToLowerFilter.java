package neu.ir.index.filters;

import java.util.ArrayList;

import neu.ir.index.TokenFilter;

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
