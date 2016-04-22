package neu.informationretrieval.project.queryexpansion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.util.StringUtils;

/**
 * 
 * @author shruti This class compares the top 200 high frequent of the corpus
 *         with the stop word list. It finds out the words which are not present
 *         in the stop word list but are highly frequent in corpus and generates
 *         a list of such words.
 * 
 *         These words are considered important for the search engine and act as
 *         an input to query expansion.
 *
 */
public class CorpusSpecificWordFinder {

	private List<String> corpusImportantWordList;
	private List<String> stopWordList;
	private List<String> highFrquentWordList;
	private List<String> months;
	private int impWordSize;

	CorpusSpecificWordFinder(int impWordSize) {
		corpusImportantWordList = new ArrayList<String>();
		stopWordList = new ArrayList<String>();
		highFrquentWordList = new ArrayList<String>();
		months = new ArrayList<String>();
		this.impWordSize = impWordSize;
		months.add("january");
		months.add("february");
		months.add("march");
		months.add("april");
		months.add("may");
		months.add("june");
		months.add("july");
		months.add("august");
		months.add("september");
		months.add("october");
		months.add("november");
		months.add("december");
	}

	public List<String> getCorpusSpecificWords() {
		// read stop word list
		// read high frequent word list
		// take high frequent word list and compare each word in
		// stop word list. If the word is nt in stop word list,
		// add it to the corpusImportantWordList
		readStopWordList();
		readHighFrequencyWordList();
		// filterImportantWords();
		corpusImportantWordList = highFrquentWordList;
		return corpusImportantWordList;
	}

	private void filterImportantWords() {
		for (String highFreqWord : highFrquentWordList) {
			if (!stopWordList.contains(highFreqWord)) {
				corpusImportantWordList.add(highFreqWord);
			}
		}
	}

	private void readStopWordList() {
		String fileName = "common_words";
		String line = null;
		FileReader fileReader;

		try {
			fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();
				stopWordList.add(line);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readHighFrequencyWordList() {
		String fileName = "IndexerOutput/termFrequencyOneGram.txt";
		String line = null;
		FileReader fileReader;
		try {
			fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();
				String tokens[] = line.split(" ");
				tokens[0] = tokens[0].trim();
				if ((!stopWordList.contains(tokens[0]))
						&& (!StringUtils.isNumeric(tokens[0]))
						&& (!months.contains(tokens[0]))) {
					highFrquentWordList.add(tokens[0]);
				}
				if (highFrquentWordList.size() >= impWordSize) {
					break;
				}
			}
			bufferedReader.close();
			for (String term : highFrquentWordList) {
				System.out.println(term);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
