package neu.informationretrieval.project.queryexpansion;

public class ThesaurusLookUpRunner {
	public static void main(String args[]){
		CorpusSpecificWordFinder corpusSpecificWordFinder = new CorpusSpecificWordFinder(20);
		ThesaurusLookup thesaurusLookup = new ThesaurusLookup(corpusSpecificWordFinder.getCorpusSpecificWords());
		thesaurusLookup.expandQueries("QueriesInput/queries.txt");
	}
}
