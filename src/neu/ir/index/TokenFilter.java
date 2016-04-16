package neu.ir.index;

import java.util.ArrayList;

public interface TokenFilter {
	ArrayList<String> getFilteredTokens( ArrayList<String> tokens );
}
