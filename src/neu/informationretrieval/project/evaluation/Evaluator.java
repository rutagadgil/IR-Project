package neu.informationretrieval.project.evaluation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import neu.informationretrieval.project.run1.bm25.IO_Operations;

public class Evaluator {
	File cacmRelevanceFile;
	IO_Operations io;
	HashMap<Integer, ArrayList<PR>> precisionRecallValues;
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
	}

	public void evaluate(ArrayList<String> runs) {
		// TODO Auto-generated method stub

		for(String run : runs){
			MAP map = new MAP(run, relevanceJudgements);
			map.calculateMAP();
			MRR mrr = new MRR(run, relevanceJudgements);
			mrr.calculateMRR();
			PrecisionRecall precisionRecall = new PrecisionRecall(run, relevanceJudgements);
			precisionRecallValues = precisionRecall.calculatePrecisionRecall();
			System.out.println("Precision at K:");
			PrecisionAtK pk = new PrecisionAtK(run, relevanceJudgements);
			pk.calculatePK(precisionRecallValues);
			
		}
	}
}
