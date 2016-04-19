package neu.informationretrieval.project.evaluation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import neu.informationretrieval.project.run1.bm25.IO_Operations;

public class Evaluator {
	File cacmRelevanceFile;
	IO_Operations io;
	HashMap<Integer, ArrayList<String>> relevanceJudgements = new HashMap<Integer, ArrayList<String>>(); 
	Evaluator(){
		io = new IO_Operations();
		cacmRelevanceFile = new File("cacm.rel");
		String content = io.readFile(cacmRelevanceFile);
		String[] relevance = content.split("\\n");

		for(String rel : relevance){

			String[] judgements = rel.split(" ");
			int queryID = Integer.parseInt(judgements[0]);
			if(relevanceJudgements.containsKey(queryID)){
				ArrayList<String> relevantDocs = relevanceJudgements.get(queryID);
				String docID =judgements[2].replaceAll("-", "");
				relevantDocs.add(docID);
				relevanceJudgements.put(queryID, relevantDocs);
			}
			else{
				ArrayList<String> relevantDocs = new ArrayList<String>();
				String docID =judgements[2].replaceAll("-", "");
				relevantDocs.add(docID);
				relevanceJudgements.put(queryID, relevantDocs);
			}
		}
		//printInvertedHashMap();
	}

	public void printInvertedHashMap() {
		// TODO Auto-generated method stub

		Iterator<?> itr = relevanceJudgements.entrySet().iterator();
		while(itr.hasNext()){
			@SuppressWarnings("rawtypes")
			HashMap.Entry pair = (HashMap.Entry)itr.next();
			System.out.print(pair.getKey() + " ");
			@SuppressWarnings("unchecked")
			ArrayList<String> postings = (ArrayList<String>) pair.getValue();
			Iterator<String> postingITR = postings.iterator();
			while(postingITR.hasNext()){
				String p = (String) postingITR.next();
				System.out.print(p + " ");
			}
			System.out.println();
		}
		System.out.println("size: " + relevanceJudgements.size());
	}

	public void evaluate(ArrayList<String> runs) {
		// TODO Auto-generated method stub

		for(String run : runs){
			MAP map = new MAP(run, relevanceJudgements);
			map.calculateMAP();
			PrecisionRecall precisionRecall = new PrecisionRecall(run, relevanceJudgements);
			precisionRecall.calculatePrecisionRecall();
		}
	}
}
