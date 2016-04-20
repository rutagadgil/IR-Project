package neu.informationretrieval.project.evaluation;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

import neu.informationretrieval.project.run1.bm25.IO_Operations;

public class MRR {
	String outputPath = "EvaluationOutput/MRR/";
	String outputFileName;
	
	IO_Operations io = new IO_Operations();
	HashMap<Integer, ArrayList<String>> relevanceJudgements = new HashMap<Integer, ArrayList<String>>();
	ArrayList<Double> reciprocalRanks = new ArrayList<Double>();
	double meanReciprocalRank = 0;
	String runDirectory;
	public MRR(String runDirectory, HashMap<Integer, ArrayList<String>> relevanceJudgements) {
		// TODO Auto-generated constructor stub
		this.relevanceJudgements = relevanceJudgements;
		this.runDirectory = runDirectory;
		this.outputFileName = runDirectory.replace("/", "");
	}

	public void calculateMRR() {
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
				String fileContent = io.readFile(file);
				calculateReciprocalRankPerQuery(fileContent);
			}
			writeMRROutput();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void calculateReciprocalRankPerQuery(String queryRankContent) {
		// TODO Auto-generated method stub
		String[] rankedQueries = queryRankContent.split("\\n");
		int queryID = 0;
		double i = 0;
		double reciprocalRank = 0;
		
		for(String rank : rankedQueries){
			String[] line = rank.split(" ");
			queryID = Integer.parseInt(line[0]);
			String retrieved = line[2].substring(0, line[2].length() - 4);
			i++;
			if(relevanceJudgements.containsKey(queryID)){
				ArrayList<String> relevantSet = relevanceJudgements.get(queryID);
				if(relevantSet.contains(retrieved)){
					reciprocalRank = 1/i;
					break;
				}
			}
			else{
				reciprocalRank = 0;
			}
			
		}
		reciprocalRanks.add(reciprocalRank);
		//System.out.println("Run: " + runDirectory + " queryID: " + queryID + " Reciprocal Rank: " + reciprocalRank);
	}

	private void writeMRROutput() {
		// TODO Auto-generated method stub
		for(double mrr : reciprocalRanks){
			meanReciprocalRank = meanReciprocalRank + mrr;
		}
		meanReciprocalRank = meanReciprocalRank / reciprocalRanks.size();
		String MRR = Double.toString(meanReciprocalRank);
		io.writeToFile(outputPath, outputFileName, MRR);
		//System.out.println("run: " + runDirectory + " MAP: " + meanAveragePrecision);
	}
}
