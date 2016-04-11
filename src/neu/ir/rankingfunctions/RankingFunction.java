package neu.ir.rankingfunctions;

import neu.ir.common.Keyword;

public interface RankingFunction {
	String getName();
	double getScore( String docId , Keyword[] query );
}
