package ner;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

/** Class used for corpus import
 * @author Emil
 *
 */
public class CorpusImporter {

	/** A method for importing the corpora.
	 * 
	 * @param filename The name of the file that is going to get imported.
	 * @param newHashMap The name of the HashMap where the words of the corpus are going to be stored in.
	 * @throws IOException Throws an exception is the file doesn't exist.
	 */
	public static void importCorpus(String filename,
			HashMap<String, Integer> newHashMap) throws IOException {

		final Path file = Paths.get(filename);
		Scanner sc = new Scanner(file);

		while (sc.hasNextLine()) {
			String word = sc.nextLine();

			if (newHashMap.get(word) == null) {
				newHashMap.put(word, 0);
			}
		}
		sc.close();
	}

}
