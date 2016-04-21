package neu.informationretrieval.project.queryexpansion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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

public class GenerateInflectionalVariantsFromCorpus {

	private Map<String, Set<String>> inverseLemma;
	private File corpusFolderPath;
	private StanfordCoreNLP stanfordCoreNLP;

	public GenerateInflectionalVariantsFromCorpus() {
		inverseLemma = new HashMap<String, Set<String>>();
		corpusFolderPath = new File("OutputCACMFiles");
		stanfordCoreNLP = new StanfordCoreNLP(new Properties() {
			{
				setProperty("annotators", "tokenize,ssplit,pos,lemma");
			}
		});
	}

	public Map<String, Set<String>> getInflectionalVariantsFromCorpus() {
		iterateThroughCorpus();
		return inverseLemma;
	}

	private void iterateThroughCorpus() {
		File[] listOfFiles = corpusFolderPath.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				processFileForWordLemma(corpusFolderPath + "/" + file.getName());
			}
		}
	}

	private void processFileForWordLemma(String url) {
		String fileName = url;
		String line = null;
		FileReader fileReader;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder = stringBuilder.append(" " + line);
			}
			bufferedReader.close();
			tokenizeAndFindLemma(stringBuilder.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void tokenizeAndFindLemma(String text) {

		/*
		 * Annotation document = new Annotation(text);
		 * this.stanfordCoreNLP.annotate(document); List<CoreMap> sentences =
		 * document.get(SentencesAnnotation.class); for (CoreMap sentence :
		 * sentences) { for (CoreLabel token :
		 * sentence.get(TokensAnnotation.class)) { System.out.println("Token = "
		 * + token); System.out.println(token.get(LemmaAnnotation.class)); } }
		 */
		text = text.replaceAll("\\s+", " ");
		text = text.trim();
		String tokens[] = text.split(" ");
		for (String token : tokens) {
			Annotation tokenAnnotation = new Annotation(token);
			stanfordCoreNLP.annotate(tokenAnnotation);
			List<CoreMap> list = tokenAnnotation.get(SentencesAnnotation.class);
			String tokenLemma = list.get(0).get(TokensAnnotation.class).get(0)
					.get(LemmaAnnotation.class);
			if(inverseLemma.containsKey(tokenLemma)){
				Set<String> temp = inverseLemma.get(tokenLemma);
				temp.add(token);
				inverseLemma.put(tokenLemma, temp);
			}else{
				Set<String> temp = new HashSet<String>();
				temp.add(token);
				inverseLemma.put(tokenLemma,temp);
			}
		}

	}
}
