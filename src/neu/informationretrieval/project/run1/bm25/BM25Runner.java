/**
 * 
 */
package neu.informationretrieval.project.run1.bm25;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import neu.informationretrieval.project.run3.tfidf.Constants;

/**
 * @author shruti
 *
 */
public class BM25Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			FileInputStream in = new FileInputStream("config.properties");
			Properties props = new Properties();
			props.load(in);
			in.close();
			
			// immaterial of the run
			String cacmQuery = "QueriesInput/cacm_stem.query.txt";
	
			// Set output Folder Path
			String outputFolderPath = null;
			String fileName = null;
			boolean stop = Boolean.parseBoolean(props.getProperty("stopping"));
			boolean stem = Boolean.parseBoolean(props.getProperty("stemming"));
			
			if(stem){
				StemmedQueryParser qp = new StemmedQueryParser();
				qp.parseQueries(cacmQuery);
			}else{
				QueryParser queryParser = new QueryParser();
				queryParser.parseQueries("QueriesInput/queries.txt");
			}

			if (args.length == 0) {
				outputFolderPath = "Run1Output/";
				fileName = "QueriesInput/queries.txt";
			} else {
				outputFolderPath = args[0];
				fileName = args[1];
			}

			System.out.println(fileName);
			System.out.println(outputFolderPath);
			String line = null;
			FileReader fileReader;
			fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				BM25 bm25 = new BM25(outputFolderPath, stop, stem);
				bm25.rankDocuments(line);
			}
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
