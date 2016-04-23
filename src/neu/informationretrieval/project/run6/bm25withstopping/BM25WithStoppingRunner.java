package neu.informationretrieval.project.run6.bm25withstopping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import neu.informationretrieval.project.indexer.InvertedIndexGeneratorRunner;
import neu.informationretrieval.project.parser.GenerateCorpusRunner;
import neu.informationretrieval.project.run1.bm25.BM25Runner;

public class BM25WithStoppingRunner {
	
	public static void main(String args[]) throws IOException{

		// Delete Files under OutputCACM Folder
		File file = new File("OutputCACMFiles");
		FileUtils.deleteDirectory(file);
		file.mkdir();
		
		
		// Delete Files from IndexerOutput Folder
		File file1 = new File("IndexerOutput");
		FileUtils.deleteDirectory(file1);
		file1.mkdir();
		
		FileInputStream in = new FileInputStream("config.properties");
		Properties props = new Properties();
		props.load(in);
		in.close();

		FileOutputStream out = new FileOutputStream("config.properties");
		props.setProperty("stopping", "true");
		props.store(out, null);
		out.close();
		
		
		try {
			// generate stemmed corpus
			GenerateCorpusRunner.main(args);
			InvertedIndexGeneratorRunner.main(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] arg = {"Run6Output", "QueriesInput/cacm.query"};
		BM25Runner.main(arg);
		
		FileInputStream in1 = new FileInputStream("config.properties");
		props.load(in1);
		in.close();
		
		FileOutputStream out1 = new FileOutputStream("config.properties");
		props.setProperty("stopping", "false");
		props.store(out1, null);
		out1.close(); 
		
		
	}
}
