package neu.informationretrieval.project.run7.lucenewithstopwords;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import neu.informationretrieval.project.run2.lucene.LuceneRunner;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.WordlistLoader;
import org.apache.lucene.util.Version;

public class LuceneWithStopwords {
	public static void main(String[] args) throws Exception{
		Reader reader = new InputStreamReader(new FileInputStream("common_words"));
		LuceneRunner.Run(new StandardAnalyzer(Version.LUCENE_47, WordlistLoader.getWordSet(reader, Version.LUCENE_47)));
	}
}
