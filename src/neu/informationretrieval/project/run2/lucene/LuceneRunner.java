package neu.informationretrieval.project.run2.lucene;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.WordlistLoader;
import org.apache.lucene.util.Version;

public class LuceneRunner {

	public static void main(String args[]) throws IOException{
		
		Analyzer analyzer;
		String outputFolder;
		
		if(args.length == 0){
			 analyzer = new SimpleAnalyzer(Version.LUCENE_47);
			 outputFolder = "Run2Output";
		}else{
			Reader reader = new InputStreamReader(new FileInputStream("common_words"));
			analyzer = new StandardAnalyzer(Version.LUCENE_47, WordlistLoader.getWordSet(reader, Version.LUCENE_47));
			outputFolder = args[0];
		}
	
		System.out.println("Output Folder:" + outputFolder);
		
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
					luceneQueryProcessor.searchDocuments(line, outputFolder);
				}
				bufferedReader.close(); 
		} catch (Exception ex) {
		    ex.printStackTrace();
		    System.exit(-1);
		}
	}
}
