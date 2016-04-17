package neu.informationretrieval.project.parser;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import neu.ir.config.Constants;

public class GenerateStemmedCorpus {
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(new FileInputStream(Constants.STEMMED_CORPUS_FILENAME));
		PrintWriter out = null;
		while( in.hasNextLine()){
			String line = in.nextLine();
			if( line.startsWith("#") ){
				if( out != null ) out.close();
				out = new PrintWriter(Constants.DOCDIR + "/cacm_stemmed_" + line.split("\\s+")[1] );
			} else if( out != null ){
				out.println(line);
			}
		}
		in.close();
	}
}
