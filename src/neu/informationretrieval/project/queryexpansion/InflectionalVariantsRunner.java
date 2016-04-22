package neu.informationretrieval.project.queryexpansion;

public class InflectionalVariantsRunner {

	public static void main(String args[]){
		CorpusSpecificWordFinder corpusSpecificWordFinder = new CorpusSpecificWordFinder(20);
		InflectionalVariants in  = new InflectionalVariants(corpusSpecificWordFinder.getCorpusSpecificWords());
		in.expandQueries("QueriesInput/queries.txt");
	}
}
