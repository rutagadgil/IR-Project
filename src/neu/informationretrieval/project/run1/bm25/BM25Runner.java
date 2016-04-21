/**
 * 
 */
package neu.informationretrieval.project.run1.bm25;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
			
			String cacmQuery = "QueriesInput/cacm_stem.query.txt";
			
			if(Constants.USE_STEMMING){
			StemmedQueryParser qp = new StemmedQueryParser();
			qp.parseQueries(cacmQuery);
			} else {
				QueryParser queryParser = new QueryParser();
			    queryParser.parseQueries(cacmQuery);
			}
			// Set output Folder Path
			String outputFolderPath = null;
			if (args.length == 0) {
				outputFolderPath = "BM25Output_stemming/";
			} else {
				outputFolderPath = args[0];
			}

			// Set inputFile
			String fileName = null;
			if (args.length == 0) {
				fileName = "QueriesInput/cacm_stem_parser.query.txt";
			} else {
				fileName = args[1];
			}

			String line = null;
			FileReader fileReader;
			fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				BM25 bm25 = new BM25(outputFolderPath);
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
