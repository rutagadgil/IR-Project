package neu.informationretrieval.project.evaluation;

import java.util.ArrayList;

public class EvaluationRunner {

	static String run1 = "BM25Output/";
	static String run2 = "Run2Output/";
	static String run4 = "Run4Output/";
	static String run5 = "Run5Output/";
	static String run3 = "TFIDF_engine/";
	static String run7 = "Run7Output/";
	
	static ArrayList<String> runs = new ArrayList<String>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		runs.add(run1);
		runs.add(run2);
		runs.add(run3);
		runs.add(run4);
		runs.add(run5);
		runs.add(run7);
		Evaluator evaluator = new Evaluator();
		evaluator.evaluate(runs);
	}

}
