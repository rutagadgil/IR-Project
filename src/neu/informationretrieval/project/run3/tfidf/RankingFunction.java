package neu.informationretrieval.project.run3.tfidf;


public interface RankingFunction {
	String getName();
	double getScore( String docId , Keyword[] query );
}
