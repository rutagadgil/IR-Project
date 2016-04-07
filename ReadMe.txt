INSTRUCTIONS: HOW TO RUN THIS CODE?

NOTE: neu.informationretrieval.assignment01B.task02 package has been added to this source code
as clarified in Piazza post @175. 
Note that the InvertedIndexRunner class expects that the corpus is cleaned (punctuation removed, 
all letters converetd to lower case etc) and it rests in OutputRawFiels folder in the project.
Should there be a need to generate a corpus from the downloded HTML files, assignments 1B
will have to be run according to the ReadMe.txt for Assignment1B.
 
--------------------------------------------------------------------------------------------------------------------------
Task 01:

1. This is a maven project. Open it in an IDE like Eclipse.
2. Go to package neu.informationretrieval.assignment03.task01.relevencemodels
3. Open BM25Runner.java
4. Click on run
5. Output will be generated in BM25Output folder
6. Output will be 1 text file per query search
	- For example for query "global	warming	potential" the output file containing the query results would be 
	  bm25_global_warming_potential.txt
7. Currently the output generated is for query files given in the problem statement. If you wish to
   run more queries add them to file "Input/queries.txt". Please follow the same format to add more queries
   to this file as exists in the queries.txt file. i.e queryID(unique integer) query terms (separated by space)
--------------------------------------------------------------------------------------------------------------------------
Task 02:

1. This is a maven project. Open it in an IDE like Eclipse.
2. Go to package neu.informationretrieval.assignment03.task02.relevencemodels
3. Open LuceneRunner.java
4. Click on run
5. Output will be generated in LuceneOutput folder
6. Output will be 1 text file per query search
	- For example for query "global	warming	potential" the output file containing the query results would be 
	  bm25_global_warming_potential.txt
7. Currently the output generated is for query files given in the problem statement. If you wish to
   run more queries add them to file "Input/queries.txt". Please follow the same format to add more queries
   to this file as exists in the queries.txt file. i.e queryID(unique integer) query terms (separated by space)
--------------------------------------------------------------------------------------------------------------------------
For running the indexer:

1. Open the project in an IDE like Eclipse
2. Go to package: neu.informationretrieval.assignment01B.task02
3. Make sure that all the corpus consisting of clean files rest in folder OutputCACMFiles
4. Click on run
5. Check folder IndexerOutput for output from the indexer.

Note that this process can be continued for running the BM25Runner as stated above to fetch the
documents in this corpus.
--------------------------------------------------------------------------------------------------------------------------
REFERENCES:

https://lucene.apache.org/core/4_0_0/core/
HW3.JAVA provided in the assignment
https://lucene.apache.org/core/
