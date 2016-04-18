package neu.informationretrieval.project.run3.tfidf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoolGenericEngine {
	public static final Logger log = LoggerFactory.getLogger(GenericEngine.class);
	
	public static void main(String[] args) throws Exception {
		log.info("Engine started");
		
		Path docLengthFile = Paths.get(Constants.INDEX_OUTPUT_DIR, Constants.DOCLENGTHS_FILENAME);
		Path indexFilename = Paths.get(Constants.INDEX_OUTPUT_DIR, Constants.INDEXES);
		Path queries = Paths.get(Constants.INPUT_DIR, Constants.QUERIES);
		
		log.info("docLengthFile: " + docLengthFile);
		log.info("index file: " + indexFilename);
		log.info("queries: " + queries);
		
		Map<String,Integer> docLengths = getDocumentLengths(docLengthFile.toString());
		
		// log.info(new PrettyPrintingMap<Integer,String>(docMap).toString());
		log.info(new PrettyPrintingMap<String, Integer>(docLengths).toString());
		
		InvertedIndex index = getIndex(1, indexFilename.toString());
		System.out.println(index.getIndexes().size());
		// Initialize corpus
		Corpus corpus = new Corpus();
		corpus.setDocumentLengths(docLengths);
		corpus.setTotalKeywords(index.getTotalKeywords());
		corpus.setAverageDocumentLength(getAverageOf(docLengths.values()));
		
		readQueriesAndDisplayScore(queries.toString(), corpus, index, new BM25(corpus,index,1.2,100,0.75));
		
		log.info("Ending finished");
	}
	
	public static Map<String,Integer> getDocumentLengths( String filename ) {
		Map<String,Integer> lengths = new HashMap<String,Integer>();
		Scanner in = null;
		try {
			in = new Scanner(new FileInputStream(filename));
			while( in.hasNext() ) {
				String hashCode = in.next();
				Integer length = in.nextInt();
				lengths.put(hashCode, length);
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
	public static InvertedIndex getIndex( int ngram , String indexFilename ) throws FileNotFoundException {
		CoolInvertedIndexReader reader = new CoolInvertedIndexReader(ngram);
		return reader.ReadInvertedIndex(new FileInputStream(indexFilename));
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
		ArrayList<TokenFilter> filters = IndexCreator.getTokenFilters(Constants.USE_STEMMING, Constants.USE_STOPWORDS);
		Scanner in = null;
		try {
			in = new Scanner(new FileInputStream(filename));
			while(in.hasNextLine()){
				String line = in.nextLine();
				log.info("Query Line: " + line);
				if( "".equals(line.trim()) ) continue; // Skip blank lines
				String[] parts = line.trim().split("\\s+");
				Query query = Query.getQuery(parts, ngram, filters);
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

