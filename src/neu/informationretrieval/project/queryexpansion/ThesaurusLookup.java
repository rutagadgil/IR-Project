package neu.informationretrieval.project.queryexpansion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.lucene.wordnet.SynonymMap;

class ThesaurusLookup {

	private SynonymMap synonymMap;
	private File wordNetDBFilePath;
	private InputStream wordNetInputStream;
	private ArrayList<String> expandedQuery;
	private ArrayList<String> expandedQueries;

	public ThesaurusLookup() {
		wordNetDBFilePath = new File("WordNetInputFile/wn_s.pl");
		expandedQuery = new ArrayList<String>();
		expandedQueries = new ArrayList<String>();
		try {
			wordNetInputStream = new FileInputStream(wordNetDBFilePath);
			synonymMap = new SynonymMap(wordNetInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void expandQueries(String filePath){
		String line = null;
		FileReader fileReader;
		try {
			fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				expandQuery(line);
			}
			bufferedReader.close();
			writeExpandedQueryFile();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void expandQuery(String query) {
		query = query.trim();
		query = query.replaceAll("\\s+", " ");

		String queryWords[] = query.split(" ");
		int queryNum = Integer.parseInt(queryWords[0]);

		queryWords = filterQueryWords(queryWords);
		for (String queryWord : queryWords) {
			lookupWord(queryWord);
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(queryNum + " ");
		for(String word : expandedQuery){
			stringBuilder.append(word);
			stringBuilder.append(" ");
		}
		expandedQueries.add(stringBuilder.toString());
		expandedQuery.clear();	
	}

	// write the expanded query to a file
	private void writeExpandedQueryFile() {
		PrintWriter writer;
		try {
			writer = new PrintWriter("QueriesInput/thesaurusExpandedQueries.txt", "UTF-8");
			for(String expandedQueryWords : expandedQueries){
				writer.println(expandedQueryWords);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// filtering out number from the query words
	private String[] filterQueryWords(String queryWords[]) {
		String temp[] = new String[queryWords.length - 1];
		int count = 0;
		for (int i = 1; i < queryWords.length; i++) {
			temp[count] = queryWords[i];
			count++;
		}
		return temp;
	}

	private void lookupWord(String word) {
		String[] synonyms = synonymMap.getSynonyms(word);
		expandedQuery.add(word);
		for (String synonym : synonyms) {
			expandedQuery.add(synonym);
		}
	}
}