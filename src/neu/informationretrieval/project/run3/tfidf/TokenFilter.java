package neu.informationretrieval.project.run3.tfidf;

import java.util.ArrayList;

public interface TokenFilter {
	ArrayList<String> getFilteredTokens( ArrayList<String> tokens );
}
