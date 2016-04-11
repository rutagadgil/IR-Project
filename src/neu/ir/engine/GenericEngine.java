package neu.ir.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

import neu.ir.common.DocumentRank;
import neu.ir.common.Query;
import neu.ir.config.Constants;
import neu.ir.corpus.Corpus;
import neu.ir.index.InvertedIndex;
import neu.ir.index.InvertedIndexReader;
import neu.ir.rankingfunctions.RankingFunction;
import neu.ir.rankingfunctions.TFIDF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericEngine {
	public static final Logger log = LoggerFactory.getLogger(GenericEngine.class);
	
	public static void main(String[] args) throws Exception {
		log.info("Engine started");
		
		Path docMapFile = Paths.get(Constants.INDEX_OUTPUT_DIR, Constants.DOCMAP_FILENAME);
		Path docLengthFile = Paths.get(Constants.INDEX_OUTPUT_DIR, Constants.DOCLENGTHS_FILENAME);
		Path indexFilename = Paths.get(Constants.INDEX_OUTPUT_DIR, Constants.INDEXES);
		Path queries = Paths.get(Constants.INPUT_DIR, Constants.QUERIES);
		
		log.info("docMapFile: " + docMapFile);
		log.info("docLengthFile: " + docLengthFile);
		log.info("index file: " + indexFilename);
		log.info("queries: " + queries);
		
		Map<Integer,String> docMap = getDocMap(docMapFile.toString());
		Map<String,Integer> docLengths = getDocumentLengths(docLengthFile.toString(), docMap);
		
		// log.info(new PrettyPrintingMap<Integer,String>(docMap).toString());
		// log.info(new PrettyPrintingMap<String, Integer>(docLengths).toString());
		
		InvertedIndex index = getIndex(1, indexFilename.toString(), docMap);
		
		// Initialize corpus
		Corpus corpus = new Corpus();
		corpus.setDocumentLengths(docLengths);
		corpus.setTotalKeywords(index.getTotalKeywords());
		corpus.setAverageDocumentLength(getAverageOf(docLengths.values()));
		
		readQueriesAndDisplayScore(queries.toString(), corpus, index, new TFIDF(corpus,index));
		
		log.info("Ending finished");
	}
	
	public static Map<String,Integer> getDocumentLengths( String filename , Map<Integer,String> docMap ) {
		Map<String,Integer> lengths = new HashMap<String,Integer>();
		Scanner in = null;
		try {
			in = new Scanner(new FileInputStream(filename));
			while( in.hasNextInt() ) {
				Integer hashCode = in.nextInt();
				Integer length = in.nextInt();
				lengths.put(docMap.get(hashCode), length);
			}
			in.close();
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			log.error(sw.toString());
		} finally {
			if( in != null ){
				in.close();
			}
		}
		
		return lengths;
	}
	public static Map<Integer,String> getDocMap( String fileName ) {
		Map<Integer,String> docMap = new HashMap<Integer,String>();
		Scanner in = null;
		try {
			in = new Scanner(new FileInputStream(fileName));
			while(in.hasNextLine()){
				String[] parts = in.nextLine().split("\\s+");
				Integer hashCode = Integer.parseInt(parts[1]);
				docMap.put(hashCode, parts[0]);
			}
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			log.error(sw.toString());
		} finally {
			if( in != null ){
				in.close();
			}
		}
		return docMap;
	}
	public static InvertedIndex getIndex( int ngram , String indexFilename , Map<Integer,String> docMap ) throws FileNotFoundException {
		InvertedIndexReader reader = new InvertedIndexReader(ngram);
		return reader.ReadInvertedIndex(new FileInputStream(indexFilename), docMap);
	}
	public static Long getAverageOf(Collection<Integer> nums) {
		Long sum = 0L;
		int n = 0;
		for(Integer i : nums){
			sum += i;
			n++;
		}
		return sum/n;
	}
	public static Map<Integer,Query> getQueries(String filename , int ngram) {
		Map<Integer,Query> queries = new HashMap<Integer,Query>();
		Scanner in = null;
		try {
			in = new Scanner(new FileInputStream(filename));
			while(in.hasNextLine()){
				String line = in.nextLine();
				log.info("Query Line: " + line);
				if( "".equals(line.trim()) ) continue; // Skip blank lines
				String[] parts = line.trim().split("\\s+");
				Query query = Query.GetQuery(parts, ngram);
				Integer id = Integer.parseInt(parts[0]);
				log.info(String.format("%d => %s",id,query));
				queries.put(id, query);
			}
		} catch ( Exception exp ) {
			log.error("Unable to get the queries", exp);
		} finally {
			if( in != null )
				in.close();
		}
		
		return queries;
	}
	public static void checkAndReadyFS(RankingFunction rfun) {
		Path dir = Paths.get(rfun.getName());
		if( !dir.toFile().exists() ) {
			dir.toFile().mkdir();
		}
	}
	public static void readQueriesAndDisplayScore(String filename , Corpus corpus , InvertedIndex index , RankingFunction rfun) throws FileNotFoundException {
		
		checkAndReadyFS(rfun); // Create directory if not exits
		
		Map<Integer,Query> queries = getQueries(filename,index.getNgram());
		for(Map.Entry<Integer, Query> entry : queries.entrySet()) {
			FileOutputStream out = new FileOutputStream(Paths.get(rfun.getName(), rfun.getName() + "_" + entry.getKey().toString()).toString());
			PrintWriter pout = new PrintWriter(out);
			log.info(String.format("Generating score for %s",entry.getKey()));
			PriorityQueue<DocumentRank> ranks = corpus.getRank(entry.getValue().getKeywords(), rfun);
			int i=1;
			while(!ranks.isEmpty()){
				// query_id	Q0	doc_id	rank	BM25_score	system_name
				if( i > Constants.TOPDOC_NUMBER )
					break;
				DocumentRank rank = ranks.poll();
				String line = String.format("%s Q0 %s %s %s %s",
						entry.getKey(),
						rank.getDocId(),
						i++,
						rank.getRelevance(),
						rfun.getName());
				pout.println(line);
				log.info(line);
			}
			pout.close();
		}
	}
}
