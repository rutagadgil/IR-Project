package neu.informationretrieval.project.parser;

/**
 * 
 * @author shruti
 * This class simply runs the GenerateCorpus 
 * processRawData method. No arguments need to 
 * be passed to Generate Corpus.
 *
 */
public class GenerateCorpusRunner {
	public static void main(String args[]){
		GenerateCorpus generateCorpus = new GenerateCorpus();
		generateCorpus.processRawData();
	}
}
