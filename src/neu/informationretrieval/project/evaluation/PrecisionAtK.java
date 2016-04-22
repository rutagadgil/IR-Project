package neu.informationretrieval.project.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import neu.informationretrieval.project.run1.bm25.IO_Operations;

public class PrecisionAtK {
	String outputPath = "EvaluationOutput/P@K/";
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
				if(i==K5){
					//System.out.println("QueryID: " + pair.getKey() + " Precision at 5: " + pr.getPrecision() + "|");
					precisionAt5.add(pr.getPrecision());
				}
				if(i==K20){
					//System.out.println("QueryID: " + pair.getKey() + " Precision at 20: " + pr.getPrecision() + "|");
					precisionAt20.add(pr.getPrecision());
				}
			}
		}
		writePrecisionAtKOutput();
	}
	
	private void writePrecisionAtKOutput() {
		// TODO Auto-generated method stub
		for(double precision : precisionAt5){
			pAt5 = pAt5 + precision;
		}
		for(double precision : precisionAt20){
			pAt20 = pAt20 + precision;
		}
		pAt5 = pAt5 / precisionAt5.size();
		pAt20 = pAt20 / precisionAt20.size();
		
		StringBuilder pAtK = new StringBuilder();
		pAtK.append("Precision at rank K = 5: " + Double.toString(pAt5));
		pAtK.append(System.getProperty("line.separator"));
		pAtK.append("Precision at rank K = 20: " + Double.toString(pAt20));
		
		io.writeToFile(outputPath, outputFileName, pAtK.toString());
		//System.out.println("run: " + runDirectory + " MAP: " + meanAveragePrecision);
	}

}
