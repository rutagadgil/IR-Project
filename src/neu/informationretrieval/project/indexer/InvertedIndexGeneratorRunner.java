package neu.informationretrieval.project.indexer;

/**
 * 
 * @author shruti
 * This class runs the InvertedIndexRunner class
 */
public class InvertedIndexGeneratorRunner {

	public static void main(String[] args) {
		InvertedIndexGenerator indexGenerator = new InvertedIndexGenerator();
		indexGenerator.generateInvertedIndex();
		indexGenerator.generateCorpusStopList();
	}

}
