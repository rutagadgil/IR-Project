package neu.informationretrieval.project.evaluation;

import java.io.File;
import java.util.ArrayList;

import neu.informationretrieval.project.run1.bm25.IO_Operations;

public class Evaluation {
	File cacmRelevanceFile;
	IO_Operations io;
	String relevanceJudgements;
	
	Evaluation(){
		io = new IO_Operations();
		cacmRelevanceFile = new File("cacm.rel");
		relevanceJudgements = io.readFile(cacmRelevanceFile);
	}
	public void evaluate(ArrayList<String> runs) {
		// TODO Auto-generated method stub
		
		for(String run : runs){
			MAP map = new MAP(run,);
			
		}
	}
}
