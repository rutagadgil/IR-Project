package neu.informationretrieval.project.queryexpansion;

public class ThesaurusLookUpRunner {
	public static void main(String args[]){
		ThesaurusLookup thesaurusLookup = new ThesaurusLookup();
		thesaurusLookup.expandQueries("QueriesInput/queries.txt");
	}
}
