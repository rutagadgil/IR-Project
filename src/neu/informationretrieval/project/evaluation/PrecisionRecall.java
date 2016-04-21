package neu.informationretrieval.project.evaluation;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import neu.informationretrieval.project.run1.bm25.IO_Operations;

public class PrecisionRecall {

	String outputPath = "EvaluationOutput/PrecisionRecall/";
	String outputFileName;
	
	HashMap<Integer, ArrayList<PR>> precisionRecall = new HashMap<Integer, ArrayList<PR>>();
	IO_Operations io = new IO_Operations();
	HashMap<Integer, ArrayList<String>> relevanceJudgements = new HashMap<Integer, ArrayList<String>>();
	String runDirectory;

	/*
	 * constructor to initialize relevance judgments, output directories and files
	 */
	public PrecisionRecall(String runDirectory, HashMap<Integer, ArrayList<String>> relevanceJudgements) {
		// TODO Auto-generated constructor stub
		this.relevanceJudgements = relevanceJudgements;
		this.runDirectory = runDirectory;
		this.outputFileName = runDirectory.replace("/", "");
	}	

	/*
	 * parses every file to generate precision recall values 
	 */
	public HashMap<Integer, ArrayList<PR>> calculatePrecisionRecall() {
		// TODO Auto-generated method stub
		try{
			File dir = new File(runDirectory);
			FileFilter filter = new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return !pathname.isHidden();
				}
			};

			File[] dirListing = dir.listFiles(filter);
			for(File file : dirListing){
				//System.out.println("Directory: " + runDirectory);
				String fileContent = io.readFile(file);
				calculatePrecisionRecallPerQuery(fileContent);
			}
			writePrecisionRecallOutput();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return precisionRecall;
	}
	
	/*
	 * calculate precision recall values for every query 
	 */
	public void calculatePrecisionRecallPerQuery(String queryRankContent){
		// TODO Auto-generated constructor stub
		String[] rankedQueries = queryRankContent.split("\\n");
		int queryID = 0;
		double i = 1;
		double count = 0;
		ArrayList<PR> prs = new ArrayList<PR>();
		for(String rank : rankedQueries){
			String[] line = rank.split(" ");
			queryID = Integer.parseInt(line[0]);
			String retrieved = line[2].substring(0, line[2].length() - 4);
			i++;
			if(relevanceJudgements.containsKey(queryID)){
				ArrayList<String> relevantSet = relevanceJudgements.get(queryID);
				if(relevantSet.contains(retrieved)){
					count++;
				}
				int relevanceJudgementCount = relevanceJudgements.get(queryID).size();
				double precision = count/i;
				double recall = count/relevanceJudgementCount;
				PR pr = new PR();
				pr.setPrecision(precision);
				pr.setRecall(recall);
				prs.add(pr);
			}
		}
		precisionRecall.put(queryID, prs);
	}

	public void writePrecisionRecallOutput() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		Iterator<?> itr = precisionRecall.entrySet().iterator();
		while(itr.hasNext()){
			@SuppressWarnings("rawtypes")
			HashMap.Entry pair = (HashMap.Entry)itr.next();
			//System.out.print("run: " + runDirectory + " " + pair.getKey() + " ");
			//sb.append("run: " + runDirectory + " " + pair.getKey() + " ");
			@SuppressWarnings("unchecked")
			ArrayList<PR> prs = (ArrayList<PR>) pair.getValue();
			Iterator<PR> postingITR = prs.iterator();
			while(postingITR.hasNext()){
				PR pr = (PR) postingITR.next();
				sb.append(pair.getKey() + " " + pr.getPrecision() + " " + pr.getRecall());
				sb.append(System.getProperty("line.separator"));
			}
			//System.out.println();
			sb.append(System.getProperty("line.separator"));
		}
		io.writeToFile(outputPath, outputFileName, sb.toString());
		//System.out.println("Printing sb: " + sb);
		//System.out.println("size: " + relevanceJudgements.size());
	}
}
