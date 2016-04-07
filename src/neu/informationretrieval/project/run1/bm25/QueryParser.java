package neu.informationretrieval.project.run1.bm25;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QueryParser {
	
	IO_Operations io = new IO_Operations();
	String path = "QueriesInput/";
	String filename = "queries";

	public void parseQueries(String cacmQuery) {
		// TODO Auto-generated method stub
		try {
			File input = new File(cacmQuery);
			Document doc = Jsoup.parse(input, "UTF-8");
			StringBuilder sb = new StringBuilder();
			
			Elements contents = doc.getElementsByTag("DOC");
			for (Element content : contents) {
				String parsedQuery = parseQuery(content.text());
				sb.append(parsedQuery);
				sb.append(System.getProperty("line.separator"));
			}
			
			io.writeToFile(path, filename, sb.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String parseQuery(String text){
		// case folding
		text = text.toLowerCase();

		// remove references in square brackets
		text = text.replaceAll("(\\[.*?\\])", "");

		// replace colons with white spaces
		text = text.replaceAll(":", " ");
		text = text.replaceAll("/", " ");
		text = text.replaceAll(" ", " ");
		
		// remove punctuation
		text = removePunctuation(text);

		return text;
	}
	
	/**
	 * @param text : text in the form of String
	 * @return text after removing all the punctuations
	 * The punctuation from characters is removed. The punctuation
	 * in between the numbers is retained
	 */
	public String removePunctuation(String text) {
		String tokens[] = text.split(" ");
		text = "";
		for (String token : tokens) {
			text = text + " " + processToken(token);
		}
		return text;
	}

	/**
	 * 
	 * @param token : String comprising of single word
	 * @return a token after it is processed based on 
	 * whether it is a number string or character string
	 */
	public String processToken(String token) {
		String rawToken = getRawToken(token);
		if (StringUtils.isNumeric(getRawToken(token))) {
			return getProcessedNumericToken(token);
		} else {
			return rawToken;
		}
	}

	/**
	 * 
	 * @param token : number String
	 * @return processed number String
	 * If a ',' or '.' occur in between the digits, it is retained
	 * If a '%' or '$' occur at beginning or end of the String it 
	 * is retained
	 */
	public String getProcessedNumericToken(String token) {
		
		char[] charArray = token.toCharArray();
		char tempArray[] = new char[charArray.length];
		int count = 0;
		for (int i = 0; i < charArray.length; i++) {
			if (Character.isDigit(charArray[i])) {
				tempArray[count] = charArray[i];
				count++;
			} else if (!Character.isDigit(charArray[i]) && i != 0
					&& i != (charArray.length - 1)) {
				if (charArray[i] == '%' || charArray[i] == '$'
						|| charArray[i] == ',' || charArray[i] == '.') {
					tempArray[count] = charArray[i];
					count++;
				}
			} else if (!Character.isDigit(charArray[i])
					&& (i == 0 || i == (charArray.length - 1))) {
				if (charArray[i] == '%' || charArray[i] == '$') {
					tempArray[count] = charArray[i];
					count++;
				} 
			}
		}
		/*System.out.println("String conversion of "
				+ String.copyValueOf(charArray) + " to "
				+ String.valueOf(tempArray));*/
		return String.valueOf(tempArray);
	}

	/**
	 * 
	 * @param token character/number String
	 * @return a chacater/number String after removing:
	 * 1. '.'
	 * 2. ','
	 * 3. '/'
	 * 4. '#'
	 * 5. '!'
	 * 6. '$'
	 * 7. '%'
	 * 8. '^'
	 * 9. '&'
	 * 10. '*'
	 * 11. '"'
	 * 12. '''
	 * 13. ';'
	 * 14. ':'
	 * 15. '{'
	 * 16. '}'
	 * 17. '='
	 * 18. '_'
	 * 19. '-'
	 * 20. '`'
	 * 21. '~'
	 * 22. '('
	 * 23. ')'
	 */
	public String getRawToken(String token) {
		token = token.trim();
		if (token.length() > 1) {
			if (token.length() == 2) {
				if ((token.charAt(0) == '-' || token.charAt(0) == '–')) {
					token = String.valueOf(token.charAt(1));
				}else if((token.charAt(token.length() - 1) == '-'
						|| token.charAt(token.length() - 1) == '–')){
					token = String.valueOf(token.charAt(0));
				}
			} else{
				if(token.charAt(0) == '-' || token.charAt(0) == '–') {
					token = token.substring(1, token.length() - 1);
				} else if (token.charAt(token.length() - 1) == '-'
						|| token.charAt(token.length() - 1) == '–') {
					token = token.substring(0, token.length() - 2);
				}
			}
		}
		
		return token.replaceAll("[.,\\/#!$%\\^&\\*\"“”;:{}=_`~()\'’?ˈ<>‘@]", "");
	}

}
