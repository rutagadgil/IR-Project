package neu.ir.index;

import java.io.PrintWriter;
import java.util.Map;

import neu.ir.common.Keyword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvertedIndexWriter {
	public static final Logger log = LoggerFactory.getLogger(InvertedIndexWriter.class);
	
	public static void writeIndexTo( PrintWriter out , InvertedIndex ii ) {
		try{
			log.info("Writing records");
			for(Map.Entry<Keyword,Map<String,Integer>> entry : ii.getIndexes().entrySet()){
				out.print(String.join(" ", entry.getKey().getWords()) + " ");
				for(Map.Entry<String, Integer> docEntry : entry.getValue().entrySet()){
					out.print(docEntry.getKey() + " " + docEntry.getValue() + " ");
				}
				out.println();
			}
			log.info("Finished");
		} catch (Exception exp) {
			log.error(exp.toString());
		} finally {
			if(out != null){
				out.close();
			}
		}
	}
	
	public static void writeDocLengthsTo( PrintWriter out , InvertedIndex ii ){
		try{
			log.info("Writing Lengths");
			for(Map.Entry<String,Integer> entry : ii.getDocLengths().entrySet()){
				out.println(entry.getKey() + " " + entry.getValue());
			}
			log.info("Finished");
		} catch (Exception exp) {
			log.error(exp.toString());
		} finally {
			if(out != null){
				out.close();
			}
		}
	}
}
