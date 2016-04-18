package neu.informationretrieval.project.run3.tfidf;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class IndexCreator {
	public static void main( String[] args ) throws FileNotFoundException {
		InvertedIndexGenerator unigramGenerator = new InvertedIndexGenerator(Constants.DOCDIR,1);
		addHandlers(unigramGenerator);
		InvertedIndex ii = unigramGenerator.GenerateIndex();
		InvertedIndexWriter.writeIndexTo(new PrintWriter(Constants.INDEX_OUTPUT_DIR + "/" + Constants.INDEXES), ii);
		InvertedIndexWriter.writeDocLengthsTo(new PrintWriter(Constants.INDEX_OUTPUT_DIR + "/" + Constants.DOCLENGTHS_FILENAME), ii);
		System.out.println(ii.getIndexes().size());
	}
	
	public static ArrayList<TokenFilter> getTokenFilters( boolean useStemming , boolean useCommonWords ) {
		ArrayList<TokenFilter> filters = new ArrayList<TokenFilter>();
		filters.add(new TokenSplitter("\\s+"));
		filters.add(new TokenSplitter("[^A-Za-z0-9]")); // only text
		filters.add(new ToLowerFilter());
		if( useCommonWords ) {
			filters.add(new StopwordFilter("common_words"));
		}
		if( useStemming ) {
			filters.add(new TokenTrimmerAndEmptyDropper());
			filters.add(new StemmerFilter());
		}
		filters.add(new TokenTrimmerAndEmptyDropper());
		return filters;
	}
	
	public static void addHandlers(InvertedIndexGenerator generator) {
		for(TokenFilter filter : getTokenFilters(false, Constants.USE_STOPWORDS)){ // Doc dir is already stemmed
			generator.addTokenFilter(filter);
		}
	}
}
