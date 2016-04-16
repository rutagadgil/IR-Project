package neu.ir.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import neu.ir.common.Keyword;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvertedIndexGenerator {
	public static final Logger log = LoggerFactory.getLogger(InvertedIndexGenerator.class);
	
	public InvertedIndexGenerator( String docDirectory , int ngram ) {
		this.docDirectory = docDirectory;
		this.ngram = ngram;
	}
	public InvertedIndex GenerateIndex() {
		log.info("DocDirectory: " + docDirectory);
		Collection<File> files = FileUtils.listFiles(new File(docDirectory), new String[]{"txt"}, true);
		InvertedIndex ii = new InvertedIndex(new HashMap<Keyword,Map<String,Integer>>());
		for(File file: files){
			try{
				log.info("Generating Index for " + file.getAbsolutePath());
				AddFile(file,ii);
			} catch (Exception exp) {
				log.warn("Ignoring the file " + file.getAbsolutePath());
			}
		}
		return ii;
	}
	public void AddFile(File docFileName, InvertedIndex ii) throws FileNotFoundException {
		Scanner in = new Scanner(new FileInputStream(docFileName));
		ArrayList<String> tokens = new ArrayList<String>();
		
		log.info("Collecting all tokens for " + docFileName.getAbsolutePath());
		// Collect all tokens/lexemes
		while(in.hasNext()){
			String next = in.next();
			tokens.add(next);
		}
		
		log.info("Generating tokens for " + docFileName.getAbsolutePath());
		// parse them
		for(TokenFilter filter : filters){
			tokens = filter.getFilteredTokens(tokens);
		}
		String docId = docFileName.getName();
		AddTokens(docId,ii,tokens);
		in.close();
	}
	public void AddTokens(String docId, InvertedIndex ii, ArrayList<String> tokens) {
		log.info("Adding tokens to docId:" + docId);
		int i=0;
		Map<Keyword,Map<String,Integer>> index = ii.getIndexes();
		String[] tkns = tokens.toArray(new String[tokens.size()]);
		
		while( (i+ngram) < tokens.size() ) {
			Keyword word = new Keyword(Arrays.copyOfRange(tkns, i, i+ngram));
			if(!index.containsKey(word)){
				index.put(word, new HashMap<String,Integer>());
			}
			if(!index.get(word).containsKey(docId)){
				index.get(word).put(docId, 1);
			} else {
				index.get(word).put(docId,index.get(word).get(docId)+1);
			}
			i++;
		}
		ii.getDocLengths().put(docId, i); // Document Length = total number of keywords found
		log.info("Completed adding tokens to " + docId);
	}
	public String getDocDirectory() {
		return docDirectory;
	}
	public void setDocDirectory(String docDirectory) {
		this.docDirectory = docDirectory;
	}
	public void addTokenFilter( TokenFilter filter ) {
		filters.add(filter);
	}
	public void removeTokenFilter( TokenFilter filter ) {
		filters.remove(filter);
	}

	private ArrayList<TokenFilter> filters = new ArrayList<TokenFilter>();
	private String docDirectory;
	private int ngram;
}
