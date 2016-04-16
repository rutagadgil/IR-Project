package neu.ir.index.filters;

import java.util.ArrayList;

import neu.ir.index.TokenFilter;

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
