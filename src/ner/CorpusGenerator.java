package ner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class which generates the corpus by putting the words in different HashMaps
 * 
 * @author Emil
 *
 */
public class CorpusGenerator {

	// HashMaps for storing the words in the corpora
	HashMap<String, Integer> places = new HashMap<String, Integer>();
	HashMap<String, Integer> names = new HashMap<String, Integer>();
	HashMap<String, Integer> companies = new HashMap<String, Integer>();

	// An arraylist which is used for storing the words that are going to get
	// annotated
	private ArrayList<String> text = new ArrayList<String>();

	/**
	 * Constructor
	 */
	public CorpusGenerator() {
	};

	/**
	 * Returns an ArrayList of all the words in the text
	 * 
	 * @return ArrayList text
	 */
	public ArrayList<String> getText() {
		return text;
	}

	/**
	 * Puts the words from each corpus in different HashMaps
	 * 
	 * @throws IOException
	 *             Exception if a file is not found
	 */
	public void generateCorpus() throws IOException {
		CorpusImporter.importCorpus("countries.txt", places);
		CorpusImporter.importCorpus("capitals.txt", places);
		CorpusImporter.importCorpus("cities.txt", places);
		CorpusImporter.importCorpus("allnames.txt", names);
		CorpusImporter.importCorpus("worldsurnames.txt", names);
		CorpusImporter.importCorpus("companies.txt", companies);
	}
}
