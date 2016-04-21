package neu.informationretrieval.project.queryexpansion;

public class InflectionalVariantsRunner {

	public static void main(String args[]){
		InflectionalVariants in  = new InflectionalVariants();
		in.expandQueries("QueriesInput/queries.txt");
	}
}
