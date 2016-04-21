package neu.informationretrieval.project.run5.bm25InflectionalVariants;

import neu.informationretrieval.project.queryexpansion.InflectionalVariantsRunner;
import neu.informationretrieval.project.run1.bm25.BM25Runner;

public class InflectionalVariantsBM25Runner {
	
	public static void main(String args[]){
		// Generate an Expanded Query file with 
		InflectionalVariantsRunner.main(args);
		
		//Call BM25 With Expanded Queries File
		String[] arguments = {"Run5Output/","QueriesInput/inflectionalVariantsExpandedQueries.txt"};
		BM25Runner.main(arguments);
	}
}
