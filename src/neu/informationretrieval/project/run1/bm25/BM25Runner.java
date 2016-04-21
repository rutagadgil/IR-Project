/**
 * 
 */
package neu.informationretrieval.project.run1.bm25;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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
			
			String cacmQuery = "QueriesInput/cacm.query";
			QueryParser queryParser = new QueryParser();
			queryParser.parseQueries(cacmQuery);
			
			// Set output Folder Path
			String outputFolderPath = null;
			if (args.length == 0) {
				outputFolderPath = "BM25Output/";
			} else {
				outputFolderPath = args[0];
			}

			// Set inputFile
			String fileName = null;
			if (args.length == 0) {
				fileName = "QueriesInput/queries.txt";
			} else {
				fileName = args[1];
			}

			String line = null;
			FileReader fileReader;
			fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int counter = 1;
			while ((line = bufferedReader.readLine()) != null) {
				BM25 bm25 = new BM25(outputFolderPath);
				bm25.rankDocuments(line);
				counter++;
			}
			bufferedReader.close();
			if( Constants.USE_STEMMING ){ 
				// Add extra queries from cacm_stem.query.txt if used stemming
				FileInputStream stream = new FileInputStream("cacm_stem.query.txt");
				Scanner in = new Scanner(stream);
				while( in.hasNext() ) {
					String stemQuery = in.nextLine();
					BM25 bm25 = new BM25(outputFolderPath);
					bm25.rankDocuments("" + counter + " " + stemQuery);
					counter++;
				}
				in.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
