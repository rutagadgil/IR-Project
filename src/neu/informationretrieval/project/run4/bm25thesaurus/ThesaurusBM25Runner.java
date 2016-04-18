package neu.informationretrieval.project.run4.bm25thesaurus;

import neu.informationretrieval.project.queryexpansion.ThesaurusLookUpRunner;
import neu.informationretrieval.project.run1.bm25.BM25Runner;

public class ThesaurusBM25Runner {
	
	public static void main(String args[]){
		// Generate an Expanded Query file with Thesaurus Lookup
		ThesaurusLookUpRunner.main(args);
		
		//Call BM25 With Expanded Queries File
		String[] arguments = {"Run4Output/","QueriesInput/thesaurusExpandedQueries.txt"};
		BM25Runner.main(arguments);
	}
}
