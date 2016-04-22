package neu.informationretrieval.project.run3.tfidf;

public class Constants {
	public static final String INDEX_OUTPUT_DIR = "IndexerOutput";
	public static final String INDEXES = "invertedIndexOneGram.txt";
	public static final String QUERIES = "cacm_stem.query.txt";
	public static final String DOCMAP_FILENAME = "hashCodeToDocIds.txt";
	public static final String DOCLENGTHS_FILENAME = "numOfTokensPerDoc.txt";
	public static final String INPUT_DIR = "QueriesInput";
	public static final String OUTPUT_DIR = "TFIDFOutput";
	public static final String DOCDIR = "OutputCACMFiles";
	public static final int TOPDOC_NUMBER = 100;
	public static final boolean USE_STEMMING = false;
	public static final boolean USE_STOPWORDS = false;
	public static final String STEMMED_CORPUS_FILENAME = "cacm_stem.txt";
}
