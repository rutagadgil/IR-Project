package neu.informationretrieval.project.evaluation;

import java.util.ArrayList;

public class EvaluationRunner {

	static String run1 = "BM25Output/";
	static String run2 = "LuceneOutput/";
	
	static ArrayList<String> runs = new ArrayList<String>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		runs.add(run1);
		runs.add(run2);
		Evaluator evaluator = new Evaluator();
		evaluator.evaluate(runs);
	}

}
