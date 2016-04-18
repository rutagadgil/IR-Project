package neu.informationretrieval.project.evaluation;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;

import neu.informationretrieval.project.run1.bm25.IO_Operations;

public class MAP {
	
	HashMap<Integer, PR> precisionRecall = new HashMap<Integer, PR>();
	IO_Operations io = new IO_Operations();
	
	public MAP(String runDirectory, String relevanceJudgements) {
		// TODO Auto-generated constructor stub
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
				calculateAveragePrecision(fileContent);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void calculateAveragePrecision(String queryRankContent){
		// TODO Auto-generated constructor stub
		String[] rankedQueries = queryRankContent.split("\\n");
		for()
	}
}
