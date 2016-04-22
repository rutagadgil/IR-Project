package neu.informationretrieval.project.run1.bm25;
import neu.informationretrieval.project.run3.tfidf.Constants;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class StemmedQueryParser {
	 	
	 	public void parseQueries(String cacmQuery) {
	 		try {
	 		Scanner in = new Scanner(new FileInputStream(cacmQuery));
	 		PrintWriter f0;
	 		
	 			f0 = new PrintWriter(new FileWriter(Constants.INPUT_DIR + "/cacm_stem_parsed.query.txt"));
	 		 
	 		while( in.hasNextLine()){
	 			for ( int i = 1; i <= 7; i++){
	 			String line = in.nextLine();
	 			StringBuilder sb = new StringBuilder();
	 			sb.append(i);
	 			sb.append(" ");
	 			sb.append(line);
	 			String query = sb.toString ();
	 		//System.out.println(newStr);
	 			f0.println(query);
	 		
	 		}
	 			f0.close();
	 	}
	 	}
	 	catch (IOException e) {
	 		// TODO Auto-generated catch block
	 		e.printStackTrace();
	 	}
	 }
	 }