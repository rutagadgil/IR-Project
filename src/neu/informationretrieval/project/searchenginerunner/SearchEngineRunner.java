package neu.informationretrieval.project.searchenginerunner;

import java.io.File;

import org.apache.commons.io.FileUtils;

import neu.informationretrieval.project.evaluation.EvaluationRunner;
import neu.informationretrieval.project.indexer.InvertedIndexGeneratorRunner;
import neu.informationretrieval.project.parser.GenerateCorpusRunner;
import neu.informationretrieval.project.run1.bm25.BM25Runner;
import neu.informationretrieval.project.run2.lucene.LuceneRunner;
import neu.informationretrieval.project.run3.tfidf.GenericEngine;
import neu.informationretrieval.project.run4.bm25thesaurus.ThesaurusBM25Runner;
import neu.informationretrieval.project.run5.bm25InflectionalVariants.InflectionalVariantsBM25Runner;
import neu.informationretrieval.project.run6.bm25withstopping.BM25WithStoppingRunner;
import neu.informationretrieval.project.run7.lucenewithstopwords.LuceneWithStopwords;
import neu.informationretrieval.project.run8.bm25stemming.BM25WithStemmingRunner;

public class SearchEngineRunner {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		// Delete Files under OutputCACM Folder
		File file = new File("OutputCACMFiles");
		FileUtils.deleteDirectory(file);
		file.mkdir();


		// Delete Files from IndexerOutput Folder
		File file1 = new File("IndexerOutput");
		FileUtils.deleteDirectory(file1);
		file1.mkdir();
		
		//Generate Corpus
		GenerateCorpusRunner.main(args);
		
		//Generate Inverted Index
		InvertedIndexGeneratorRunner.main(args);
		
		//Run1 - BM25
		BM25Runner.main(args);
		
		//Run2 - Lucene
		LuceneRunner.main(args);
		
		//Run3 - TFIDF
		GenericEngine.main(args);
		
		//Run4 - Query Expansion using Synonyms
		ThesaurusBM25Runner.main(args);
		
		//Run5 - Query Expansion using Inflectional variants
		InflectionalVariantsBM25Runner.main(args);
		
		//Run7 - Lucene with StopWords
		LuceneWithStopwords.main(args);
		
		//Run6 - BM25 with stopping
		BM25WithStoppingRunner.main(args);
		
		//Run8 - BM25 with stemming
		BM25WithStemmingRunner.main(args);
		
		//Generate Evaluation
		EvaluationRunner.main(args);
	}

}
