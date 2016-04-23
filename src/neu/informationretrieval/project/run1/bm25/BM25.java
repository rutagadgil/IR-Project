/**
 * 
 */
package neu.informationretrieval.project.run1.bm25;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import neu.informationretrieval.project.run3.tfidf.Constants;
import neu.informationretrieval.project.run3.tfidf.StemmerFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shruti
 *
 */
public class BM25 {

	private double k1;
	private int k2;
	private double b;
	private Map<Integer, String> hasCodeDocIds;
	private Map<Integer, Integer> numOfTokensPerDoc;
	private Map<String, List<Index>> invertedIndex;
	private double AVDL;
	private Map<String, Integer> queryFrequency;
	private Map<Integer, Double> bm25Scores;
	final static Logger logger = LoggerFactory.getLogger(BM25.class);
	private String outputFolderName;
	private boolean stem;
	private boolean stop;

	private IO_Operations io;
	private HashMap<Integer, ArrayList<String>> relevanceJudgements = new HashMap<Integer, ArrayList<String>>(); 


	BM25(String outputFolderName, boolean stop, boolean stem) {
		k1 = 1.2;
		b = 0.75;
		k2 = 100;
		hasCodeDocIds = new HashMap<Integer, String>();
		numOfTokensPerDoc = new HashMap<Integer, Integer>();
		invertedIndex = new HashMap<String, List<Index>>();
		queryFrequency = new HashMap<String, Integer>();
		AVDL = 0;
		bm25Scores = new HashMap<Integer, Double>();
		this.outputFolderName = outputFolderName;
		io = new IO_Operations();
		this.stop = stop;
		this.stem = stem;

	}

	public void rankDocuments(String query) {
		System.out.println("In rank docs");
		if(!stem)
			readRelevantJudgements();
		readAllFilesInHashMaps();
		calculateAVDL();
		calculateBM25PageRanksForQuery(query);
	}

	private void readRelevantJudgements() {
		// TODO Auto-generated method stub

		File cacmRelevanceFile = new File("cacm.rel");
		String content = io.readFile(cacmRelevanceFile);
		String[] relevance = content.split("\\n");

		for(String rel : relevance){

			String[] judgements = rel.split(" ");
			int queryID = Integer.parseInt(judgements[0]);
			if(relevanceJudgements.containsKey(queryID)){
				ArrayList<String> relevantDocs = relevanceJudgements.get(queryID);
				String docID =judgements[2].replaceAll("-", "");
				docID = docID + ".txt";
				relevantDocs.add(docID);
				relevanceJudgements.put(queryID, relevantDocs);
			}
			else{
				ArrayList<String> relevantDocs = new ArrayList<String>();
				String docID =judgements[2].replaceAll("-", "");
				docID = docID + ".txt";
				relevantDocs.add(docID);
				relevanceJudgements.put(queryID, relevantDocs);
			}
		}
	}
	private void calculateBM25PageRanksForQuery(String query) {
		// remove extra white spaces in the query
		query = query.trim();
		query = query.replaceAll("\\s+", " ");

		String queryWords[] = query.split(" ");

		int queryNumber = Integer.parseInt(queryWords[0]);

		queryWords = filterQueryWords(queryWords);
		calculateQueryFrequency(queryWords);

		for (int i = 0; i < queryWords.length; i++) {
			calculateBM25ScorePerTerm(queryNumber, queryWords[i]);
		}
		//System.out.println(bm25Scores.size());
		queryFrequency.clear();
		bm25Scores = sortByValue(bm25Scores);
		printbm25Scores(queryNumber, queryWords);
		bm25Scores.clear();
	}

	private String[] filterQueryWords(String queryWords[]) {
		String temp[] = new String[queryWords.length-1];
		int count = 0;
		for (int i = 1; i < queryWords.length ; i++) {
			temp[count] = queryWords[i];
			count++;
		}
		// Do stemming
		if(stem) {
			ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(temp));
			StemmerFilter filter = new StemmerFilter();
			tokens = filter.getFilteredTokens(tokens);
			temp = tokens.toArray(new String[tokens.size()]);
		}
		return temp;
	}

	private void calculateBM25ScorePerTerm(int queryNumber, String term) {
		//logger.info("Processing term "+term);
		List<Index> termInvertedIndex = new ArrayList<Index>();
		termInvertedIndex = invertedIndex.get(term);
		if(termInvertedIndex!=null){
			int ni = termInvertedIndex.size();
			int N = numOfTokensPerDoc.size();
			int qfi = queryFrequency.get(term);
			int ri = 0;
			int R = 0;

			if(!stem){
				ArrayList<String> relevantDocs = relevanceJudgements.get(queryNumber);

				if(relevantDocs!=null){
					R = relevantDocs.size();
				}

				ri = calculateRi(term, relevantDocs);
			}
			for (Index index : termInvertedIndex) {
				//logger.info("Processing doc: "
				//	+ hasCodeDocIds.get(index.getDocId()));
				int docLength = numOfTokensPerDoc.get(index.getDocId());
				int fi = index.getTermFrequency();
				populatebm25Scores(index.getDocId(),
						calculateScore(ri, R, ni, N, qfi, fi, calculateK(docLength)));
			}
		}
	}

	private int calculateRi(String term, ArrayList<String> relevantDocs) {
		// TODO Auto-generated method stub
		int ri = 0;
		List<Index> termInvertedIndex = new ArrayList<Index>();
		termInvertedIndex = invertedIndex.get(term);
		for (Index index : termInvertedIndex) {
			int docID = index.getDocId();
			String docName = hasCodeDocIds.get(docID);
			if(relevantDocs != null){
				if(relevantDocs.contains(docName)){
					ri++;
				}
			}
		}
		//System.out.println(term + " " + ri);
		return ri;
	}

	private void populatebm25Scores(int docId, double score) {
		if (bm25Scores.containsKey(docId)) {
			bm25Scores.put(docId, bm25Scores.get(docId) + score);
			//logger.info("bm25Score: " + bm25Scores.get(docId));
		} else {
			bm25Scores.put(docId, score);
			//logger.info("bm25Score: " + bm25Scores.get(docId));
		}

	}

	private double calculateK(int docLength) {
		//logger.info("Doc length = " + docLength);
		double K = k1
				* ((double) ((double) (1 - b) + (b * (double) (docLength / AVDL))));
		return K;
	}

	private double calculateScore(int ri, int R, int ni, int N, int qfi, int fi, double K) {
		double term1 = (double) ((ri + 0.5)/(R - ri + 0.5))/((ni - ri + 0.5)/(N - ni - R + ri + 0.5));
		double term2 = (double) (((k1 + 1) * fi) / (K + fi));
		double term3 = (double) (((k2 + 1) * qfi) / (k2 + qfi));
		double score = (Math.log(term1)) * term2 * term3;
		return score;
	}

	private void calculateQueryFrequency(String queryWords[]) {
		for (int i = 0; i < queryWords.length; i++) {
			if (queryFrequency.containsKey(queryWords[i])) {
				queryFrequency.put(queryWords[i],
						queryFrequency.get(queryWords[i]) + 1);
			} else {
				queryFrequency.put(queryWords[i], 1);
			}
		}
	}

	private String createOutputFileName(int queryNum) {
		String fileName = "bm25";
		fileName = fileName + "_" + queryNum + ".txt";
		return fileName;
	}

	private void printbm25Scores(int queryNum, String queryWords[]) {
		// Write to file
		PrintWriter writer;
		File file = new File(outputFolderName + createOutputFileName(queryNum));
		try {
			writer = new PrintWriter(file, "UTF-8");
			int count = 0;
			for (Map.Entry<Integer, Double> entry : bm25Scores.entrySet()) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(queryNum + " ");
				stringBuilder.append("Q0 ");
				stringBuilder.append(hasCodeDocIds.get(entry.getKey()) + " ");
				stringBuilder.append((count + 1) + " ");
				stringBuilder.append(entry.getValue() + " " + "BM25Run");
				writer.println(stringBuilder);
				count++;
				if (count == 100) {
					break;
				}
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	private void calculateAVDL() {
		int countOfAllTokensInAllDocuments = 0;
		for (Map.Entry<Integer, Integer> entry : numOfTokensPerDoc.entrySet()) {
			countOfAllTokensInAllDocuments = countOfAllTokensInAllDocuments
					+ entry.getValue();
		}

		AVDL = ((double) countOfAllTokensInAllDocuments)
				/ ((double) numOfTokensPerDoc.size());

		logger.info("AVDL = " + AVDL);
	}

	private void readAllFilesInHashMaps() {
		String fileName;
		String line;
		FileReader fileReader;

		try {
			// read numOfTokens file;
			fileName = "IndexerOutput/numOfTokensPerDoc.txt";
			line = null;
			fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				String split[] = line.split(" ");
				numOfTokensPerDoc.put(Integer.parseInt(split[0]),
						Integer.parseInt(split[1]));
			}
			bufferedReader.close();
			// System.out.println(numOfTokensPerDoc.size());

			// read hashCodeToDocIds
			fileName = "IndexerOutput/hashCodeToDocIds.txt";
			line = null;
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				String split[] = line.split(" ");
				hasCodeDocIds.put(Integer.parseInt(split[1]), split[0]);
			}
			bufferedReader.close();
			// System.out.println(hasCodeDocIds.size());

			// read invertedIndex
			fileName = "IndexerOutput/invertedIndexOneGram.txt";
			line = null;
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();
				String split[] = line.split(" ");

				String key = split[0];

				List<Index> indexList = new ArrayList<Index>();
				for (int i = 1; i < split.length - 2; i = i + 2) {
					Index termIndex = new Index();
					termIndex.setDocId(Integer.parseInt(split[i]));
					termIndex.setTermFrequency(Integer.parseInt(split[i + 1]));
					indexList.add(termIndex);
				}
				invertedIndex.put(key, indexList);
			}
			bufferedReader.close();
			// System.out.println(invertedIndex.size());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
