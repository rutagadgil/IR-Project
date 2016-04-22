package neu.informationretrieval.project.evaluation;

import java.util.ArrayList;

public class EvaluationRunner {

	static String run1 = "BM25Output/";
	static String run2 = "LuceneOutput/";
	static String run3 = "Run4Output/";
	static String run4 = "Run5Output/";
	static String run5 = "BM25Output_stemming/";
	
	static ArrayList<String> runs = new ArrayList<String>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		runs.add(run1);
		runs.add(run2);
		runs.add(run3);
		runs.add(run4);
		runs.add(run5);
		Evaluator evaluator = new Evaluator();
		evaluator.evaluate(runs);
	}

}
