package neu.informationretrieval.project.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import neu.informationretrieval.project.run1.bm25.IO_Operations;

public class PrecisionAtK {
	String outputPath5 = "EvaluationOutput/P@5/";
	String outputPath20 = "EvaluationOutput/P@20/";
	String outputFileName;
	int K5 = 5;
	int K20 = 20;
	double pAt5 = 0;
	double pAt20 = 0;
	
	IO_Operations io = new IO_Operations();
	HashMap<Integer, ArrayList<String>> relevanceJudgements = new HashMap<Integer, ArrayList<String>>();
	String runDirectory;
	ArrayList<Double> precisionAt5 = new ArrayList<Double>();
	ArrayList<Double> precisionAt20 = new ArrayList<Double>();
	
	public PrecisionAtK(String runDirectory, HashMap<Integer, ArrayList<String>> relevanceJudgements) {
		// TODO Auto-generated constructor stub
		this.relevanceJudgements = relevanceJudgements;
		this.runDirectory = runDirectory;
		this.outputFileName = runDirectory.replace("/", "");
	}

	public void calculatePK(HashMap<Integer, ArrayList<PR>> precisionRecallValues) {
		// TODO Auto-generated method stub
		StringBuilder sbPrecisionAt5 = new StringBuilder();
		StringBuilder sbPrecisionAt20 = new StringBuilder();
		
		Iterator<?> itr = precisionRecallValues.entrySet().iterator();
		while(itr.hasNext()){
			@SuppressWarnings("rawtypes")
			HashMap.Entry pair = (HashMap.Entry)itr.next();
			//System.out.print("run: " + runDirectory + " " + pair.getKey() + " ");
			//sb.append("run: " + runDirectory + " " + pair.getKey() + " ");
			@SuppressWarnings("unchecked")
			
			ArrayList<PR> prs = (ArrayList<PR>) pair.getValue();
			Iterator<PR> prsITR = prs.iterator();					
			int i = 0;
			while(prsITR.hasNext()){
				i++;
				PR pr = (PR) prsITR.next();
				if(i == K5){
					//System.out.println("QueryID: " + pair.getKey() + " Precision at 5: " + pr.getPrecision() + "|");
					sbPrecisionAt5.append(pair.getKey() + " " + pr.getPrecision());
					sbPrecisionAt5.append(System.getProperty("line.separator"));
					//precisionAt5.add(pr.getPrecision());
				}
				if(i == K20){
					//System.out.println("QueryID: " + pair.getKey() + " Precision at 20: " + pr.getPrecision() + "|");
					//precisionAt20.add(pr.getPrecision());
					sbPrecisionAt20.append(pair.getKey() + " " + pr.getPrecision());
					sbPrecisionAt20.append(System.getProperty("line.separator"));
				}
			}
		}
		io.writeToFile(outputPath5, outputFileName, sbPrecisionAt5.toString());
		io.writeToFile(outputPath20, outputFileName, sbPrecisionAt20.toString());
	}
	
}