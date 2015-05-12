package ner;

import java.util.HashMap;

/** Used for annotating the text with appropiate tags.
 * It's convenient to create methods like this so that we don't have to duplicate the code.
 * 
 * @author Emil
 *
 */
public class Annotator {
	
	private static int wordcounter;
	
	/** Can annotates 3-grams and replaces the shape feature tag it matches
	 * @param i The current index
	 * @param s The current string of the index
	 * @param corp Specifying the corpus object we are using
	 * @param hashmap Specifying the hashmap we want to use
	 * @param tagname The name of the tag 
	 */
	
	public static void replaceTag (Integer i, String s, CorpusGenerator corp, HashMap<String,Integer> hashmap, String tagname){
		// if the next word ends in , or . we delete that character so that it will find the string in the HashMap
		if (s.endsWith(",") || s.endsWith(".")) {
			s = s.substring(0, s.length() - 1);

			// if the word for example matches a location, it will begin with a
			// "location"-tag
			if (hashmap.get(s) != null) {
				corp.getText().set(i, tagname);
				corp.getText().set(i + 1, corp.getText().get(i + 1) + ">");
			
			}
		} else if (hashmap.get(s) != null) {
			corp.getText().set(i, tagname);
			corp.getText().set(i + 1, corp.getText().get(i + 1) + ">");
		}
		
		else {
			// (<AllCaps>|<MixedCase>|<Initials>|<EndsInDigit>|<ContainsHyphen>|<Capitalized>)
			// if the next word also matches a tag
			if (corp.getText().get(i + 2).matches("<.+>")) {
				String v = corp.getText().get(i + 3);

				if (corp.getText().get(i + 3).endsWith(",")
						|| corp.getText().get(i + 3).endsWith(".")) {
					v = v.substring(0, v.length() - 1);
					s = s + " " + v;

					if (hashmap.get(s) != null) {
						corp.getText().set(i, tagname);
						// remove the shape feature tag
						corp.getText().remove(i + 2);
						corp.getText().set(i + 2,
								corp.getText().get(i + 2) + ">");
					}
				} else if (hashmap.get(s + " " + v) != null) {
					corp.getText().set(i, tagname);
					// remove the shape feature tag
					corp.getText().remove(i + 2);
					corp.getText().set(i + 2, corp.getText().get(i + 2) + ">");
				}

				else {
					if (corp.getText().get(i + 3).matches("<.+>")) {
						String four = corp.getText().get(i + 4);

						if (four.endsWith(",") || four.endsWith(".")) {
							four = four.substring(0, four.length() - 1);
							s = s + " " + v + " " + four;

							if (hashmap.get(s) != null) {
								corp.getText().set(i, tagname);
								// remove the shape feature tag
								corp.getText().remove(i + 3);
								corp.getText().set(i + 3, four + ">");
							}
						} else if (hashmap.get(s + " " + v + " " + four) != null) {
							corp.getText().set(i, tagname);
							// remove the shape feature tag
							corp.getText().remove(i + 3);
							corp.getText().set(i + 3,
									corp.getText().get(i + 3) + ">");
						}

					}
				}
			}

		}
	}
	
	/** Annotates a single word
	 * @param i The current index
	 * @param s The current string of the index
	 * @param corp Specifying the corpus object we are using
	 * @param hashmap Specifying the hashmap we want to use
	 * @param tagname The name of the tag 
	 */
	public static void annotateWord(Integer i, String s, CorpusGenerator corp,
			HashMap<String, Integer> hashmap, String tagname) {

		if (s.endsWith(",") || s.endsWith(".")) {
			s = s.substring(0, s.length() - 1);

			// if the word matches a location, it will begin with a
			// "location"-tag
			if (hashmap.get(s) != null) {
				corp.getText().set(i, tagname + s + ">");
			}
		} else if (hashmap.get(s) != null) {
			corp.getText().set(i, tagname + s + ">");
		}

	}
	
	
/** Returns a match of strings, i.e. the tags that we are looking for
 * @param i The current index
 * @param corp Specifies the corpus object
 * @param corpus The String corpus which is where the string where everything is saved to
 * @param searchword The search word, for example a tag.
 * @return A string of the matching annotated word
 */
	public static String search(Integer i, CorpusGenerator corp, String corpus,
			String searchword) {

		if (corp.getText().get(i).matches(searchword + " [a-zA-Z ]+>")){
			corpus = corpus + " "+ corp.getText().get(i);
			wordCounter();
		} 
		if ((i > 1 && corp.getText().get(i-1).matches(searchword) && corp.getText().get(i).matches("[a-zA-Z,\\. ]+>"))){
			corpus = corpus + " "+ corp.getText().get(i-1) + " " + corp.getText().get(i);
			wordCounter();
		}
		if ((i > 2 && corp.getText().get(i-2).matches(searchword ) && corp.getText().get(i).matches("[a-zA-Z,\\. ]+>"))){
			corpus = corpus + " "+ corp.getText().get(i-2) + " " + corp.getText().get(i-1) + corp.getText().get(i);
			wordCounter();
		}

		return corpus;
	}

	/**
	 * Counts tags/words
	 */
	public static void wordCounter() {
		wordcounter++;
	}

	/**
	 * Returns the word count
	 * 
	 * @return wordcounter
	 */
	public static int getCounter() {
		return wordcounter;
	}


}
