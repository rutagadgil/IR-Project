package neu.informationretrieval.project.run2.lucene;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.util.Version;

public class LuceneRunner {

	public static void main(String args[]){
		Run(new SimpleAnalyzer(Version.LUCENE_47));
	}
	
	public static void Run( Analyzer analyzer ) {
		try {
			File file = new File("LuceneIndexOutput");
			FileUtils.deleteDirectory(file);
		    LueceneIndexer indexer = new LueceneIndexer(analyzer);
		    indexer.indexFiles();
		    
		    FileReader fileReader;
			String fileName = "QueriesInput/queries.txt";
			String line = null;
			LuceneQueryProcessor luceneQueryProcessor = new LuceneQueryProcessor(analyzer);
				fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while ((line = bufferedReader.readLine()) != null) {
					luceneQueryProcessor.searchDocuments(line);
				}
				bufferedReader.close(); 
		} catch (Exception ex) {
		    ex.printStackTrace();
		    System.exit(-1);
		}
	}
}
