package neu.informationretrieval.project.queryexpansion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class InflectionalVariants {

		private Map<String, Set<String>> inflectionalVariants;
		private List<String> expandedQuery;
		private List<String> expandedQueries;
		private StanfordCoreNLP stanfordCoreNLP;
		private List<String> corpusImpWords;
		
		InflectionalVariants(List<String> corpusImpWords){
			inflectionalVariants = new HashMap<String, Set<String>>();
			expandedQuery = new ArrayList<String>();
			expandedQueries = new ArrayList<String>();
			this.corpusImpWords = corpusImpWords;
			stanfordCoreNLP = new StanfordCoreNLP(new Properties() {
				{
					setProperty("annotators", "tokenize,ssplit,pos,lemma");
				}
			});
		}
		
		public void expandQueries(String url){
			populateinverseInflectionalVariants();
			
			String line = null;
			FileReader fileReader;
			try {
				fileReader = new FileReader(url);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while ((line = bufferedReader.readLine()) != null) {
					expandQuery(line);
				}
				bufferedReader.close();
				writeExpandedQueryFile();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void writeExpandedQueryFile(){
			PrintWriter writer;
			try {
				writer = new PrintWriter("QueriesInput/inflectionalVariantsExpandedQueries.txt", "UTF-8");
				for(String expandedQueryWords : expandedQueries){
					writer.println(expandedQueryWords);
				}
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void expandQuery(String query) {
			query = query.trim();
			query = query.replaceAll("\\s+", " ");

			String queryWords[] = query.split(" ");
			int queryNum = Integer.parseInt(queryWords[0]);

			queryWords = filterQueryWords(queryWords);
			for (String queryWord : queryWords) {
				if(corpusImpWords.contains(queryWord)){
					findInflectionalVariants(queryWord);
				}else{
					expandedQuery.add(queryWord);
				}
			}
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(queryNum + " ");
			for(String word : expandedQuery){
				stringBuilder.append(word);
				stringBuilder.append(" ");
			}
			expandedQueries.add(stringBuilder.toString());
			expandedQuery.clear();	
			
		}
		
		private void findInflectionalVariants(String word){
			Annotation tokenAnnotation = new Annotation(word);
			stanfordCoreNLP.annotate(tokenAnnotation);
			List<CoreMap> list = tokenAnnotation.get(SentencesAnnotation.class);
			String tokenLemma = list.get(0).get(TokensAnnotation.class).get(0)
					.get(LemmaAnnotation.class);
			if(inflectionalVariants.containsKey(tokenLemma)){
				for(String variant : inflectionalVariants.get(tokenLemma)){
					expandedQuery.add(variant);
				}
			}else{
				expandedQuery.add(word);
			}
			
			/*for (Map.Entry<String, Set<String>> entry : inflectionalVariants.entrySet()) {
				if(entry.getKey().equals(tokenLemma)){
					//System.out.println("Found variants!");
					for(String variant : entry.getValue()){
						expandedQuery.add(variant);
					}
				}
			}*/
		}
		
		// filtering out number from the query words
		private String[] filterQueryWords(String queryWords[]) {
			String temp[] = new String[queryWords.length - 1];
			int count = 0;
			for (int i = 1; i < queryWords.length; i++) {
				temp[count] = queryWords[i];
				count++;
			}
			return temp;
		}
		
		private void populateinverseInflectionalVariants(){
			GenerateInflectionalVariantsFromCorpus generateInflectionalVariantsFromCorpus = new GenerateInflectionalVariantsFromCorpus();
			inflectionalVariants = generateInflectionalVariantsFromCorpus
					.getInflectionalVariantsFromCorpus();
			/*for (Map.Entry<String, Set<String>> entry : temp.entrySet()) {
			System.out.println("Root : " + entry.getKey());
			for (String variant : entry.getValue()) {
				System.out.println(variant);
			}
		}*/
		}
	
}
