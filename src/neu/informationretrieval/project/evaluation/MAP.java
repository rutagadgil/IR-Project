package neu.informationretrieval.project.evaluation;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

import neu.informationretrieval.project.run1.bm25.IO_Operations;

public class MAP {

	String outputPath = "EvaluationOutput/MAP/";
	String outputFileName;
	
	IO_Operations io = new IO_Operations();
	HashMap<Integer, ArrayList<String>> relevanceJudgements = new HashMap<Integer, ArrayList<String>>();
	ArrayList<Double> averagePrecision = new ArrayList<Double>();
	double meanAveragePrecision = 0;
	String runDirectory;
	
	public MAP(String runDirectory, HashMap<Integer, ArrayList<String>> relevanceJudgements) {
		// TODO Auto-generated constructor stub
		this.relevanceJudgements = relevanceJudgements;
		this.runDirectory = runDirectory;
		this.outputFileName = runDirectory.replace("/", "");
	}

	public void calculateMAP() {
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
				calculateAveragePrecisionPerQuery(fileContent);
			}
			writeMAPOutput();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void calculateAveragePrecisionPerQuery(String queryRankContent) {
		// TODO Auto-generated method stub
		ArrayList<Double> averagePrecisionValues = new ArrayList<Double>();
		
		String[] rankedQueries = queryRankContent.split("\\n");
		int queryID = 0;
		double i = 1;
		double count = 0;
		//ArrayList<PR> prs = new ArrayList<PR>();
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
				double precision = count/i;
				averagePrecisionValues.add(precision);
			}
			else{
				double precision = 0;
				averagePrecisionValues.add(precision);
			}
		}
		double ap = 0;
		for(double averagePrecision : averagePrecisionValues){
			ap = ap + averagePrecision;
		}
		ap = ap/averagePrecisionValues.size();
		averagePrecision.add(ap);
		//System.out.println("Average Precision: " + ap);
	}

	private void writeMAPOutput() {
		// TODO Auto-generated method stub
		for(double ap : averagePrecision){
			meanAveragePrecision = meanAveragePrecision + ap;
		}
		meanAveragePrecision = meanAveragePrecision / averagePrecision.size();
		String MAP = Double.toString(meanAveragePrecision);
		io.writeToFile(outputPath, outputFileName, MAP);
		//System.out.println("run: " + runDirectory + " MAP: " + meanAveragePrecision);
	}

}
