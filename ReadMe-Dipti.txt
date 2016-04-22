Following are the steps to run the Tf-idf run for IR Project

1) Change the Constants.java file ( can be found under neu.informationretrieval.project.run3.tfidf package) by making USE_STOPWORDS = false and USE_STEMMING = false and QUERIES = "queries.txt"
2)Clear the OutputCACMFiles, IndexerOutput  directories under the root folder
3) Recreate the OutputCACMFiles, IndexerOuput directories under the same root folder.
4) Run GenerateCorpusRunner.java ( can be found under neu.informationretrieval.project.parser) -> by right clicking -> run as -> java application. 
5) Run InvertedIndexGeneratorRunner.java (can be found under neu.informationretrieval.project.indexer) -> by right clicking -> run as -> java application. 
6) Run GenericEngine.java ( can be found under neu.informationretrieval.project.run3.tfidf package)
7) The ouput as per the tf-idf ranking function can be found in the directory TFIDF_engine.

Following are the steps to generate the stopping run for IR Project

1) Change the Constants.java file ( can be found under neu.informationretrieval.project.run3.tfidf package) by making USE_STOPWORDS = true and USE_STEMMING = false and QUERIES to "queries.txt"
2) Clear the OutputCACMFiles, IndexerOutput  directories under the root folder
3) Recreate the OutputCACMFiles, IndexerOuput directories and BM25Output_stopping under the same root folder.
4) Run GenerateCorpusRunner.java ( can be found under neu.informationretrieval.project.parser) -> by right clicking -> run as -> java application. 
5) Run InvertedIndexGeneratorRunner.java (can be found under neu.informationretrieval.project.indexer) -> by right clicking -> run as -> java application. 
6) Open the BM25Runner.java ( can be found under neu.informationretrieval.project.run1.bm25package) and change the cacmQuery to "QueriesInput/cacm.query", 
outputFolderPath to "BM25Output_stopping/" and query input file name as  fileName = "QueriesInput/queries.txt"
   and save and then run by right clicking -> run as -> java application. 
7) The output for the stopping run gets stored in the directory BM25Output_stopping.

Following are the steps to generate the stemming run for IR Project

1) First please ensure that the cacm_stem.query.txt file is present in the QueriesInput directory . If this file is not present, please paste it inside the QueriesInput directory .
We have appended the query number to the original cacm_stem.query.txt file and then pasted it into the QueriesInput directory.
2) Change the Constants.java file ( can be found under neu.informationretrieval.project.run3.tfidf package) by making USE_STOPWORDS = false and USE_STEMMING = true, and
     also change the constant QUERIES to "cacm_stem.query.txt"
3)  Clear the OutputCACMFiles, IndexerOutput  directories under the root folder
4) Recreate the OutputCACMFiles, IndexerOuput directories and BM25Output_stemming under the same root folder.
5) Run GenerateStemmedCorpus.java ( can be found under neu.informationretrieval.project.parser) -> by right clicking -> run as -> java application. 
5) Run InvertedIndexGeneratorRunner.java (can be found under neu.informationretrieval.project.indexer) -> by right clicking -> run as -> java application.
5) Open the BM25Runner.java ( can be found under neu.informationretrieval.project.run1.bm25package) and change the outputFolderPath to "BM25Output_stemming/"
    and set the cacmQuery string to "QueriesInput/cacm_stem.query.txt" and query input file name as  fileName = "QueriesInput/cacm_stem_parsed.query.txt" and save and then run by right clicking -> run as -> java application. 
6) The output for the stemming run can be found under the BM25Output_stemming directory 

Following are the steps to perform the seventh run( Lucene with stopping)

1) First clear the directories OutputCACMFiles, LuceneIndexOutput
2) Recreate fresh directories named OutputCACMFiles, LuceneIndexOutput and LuceneOutput_stopping
3) Run GenerateCorpusRunner.java ( can be found under neu.informationretrieval.project.parser) -> by right clicking -> run as -> java application. 
4) Click Refresh 
5) Change the output directory name to LuceneOutput_stopping at line no. 67 in LuceneQueryProcessor.java( can be found under neu.informationretrieval.project.run2.lucene package) and save the changes made.
6) Run LuceneWithStopwords.java ( can be found under neu.informationretrieval.project.run7.lucenewithstopwords)-> by right clicking -> run as -> java application. 
7) Click Refresh 
8) The output for the lucene with stopping run can be found under the LuceneOutput_stopping directory.

