package ner;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import util.Util;

/** The FeatureExtractor class is used for extracting shape features in words, 
 * according to how they are structured orthographically.
 * 
 * @author Emil
 * @author Oscar
 */
public class FeatureExtraction {

	/** A method which annotates words according to their shape features.
	 * 
	 * @param argument Input file (.txt) as an argument.
	 * @param text The arraylist text is used for storing the annotations and the words
	 */
	public static void shapeFeatureExtractor(String argument,
			ArrayList<String> text) {

		try {
			// create a scanner which reads every file f:
			Scanner lineReader = new Scanner(new File(argument));

			while (lineReader.hasNextLine()) {

				Scanner rowReader = new Scanner(lineReader.nextLine());
				if (!rowReader.hasNext()) {
					//adds new lines between paragraphs
					text.add("\n");
					text.add("\n");
				}
				while (rowReader.hasNext()) {
					String word = rowReader.next();

					if (word.matches("[A-Z]+,?\\.?")) {
						text.add("<AllCaps>");
					}

					if (word.matches("\\b([a-z]{1,}[A-Z]{1,}[a-z]*){1,}\\b")) {
						text.add("<MixedCase>");
					}
					if (word.matches("\\b([A-Z]\\.)+\\b")) {
						text.add("<Initials>");
					}
					if (word.matches("([A-Z]+[0-9]+)+")) {
						text.add("<EndsInDigit>");
					}

					if (word.matches("([A-Z][a-z]+-[A-Z][a-z])|([A-Z]+-[A-Z]+)")) {
						text.add("<ContainsHyphen>");
					}

					if (word.matches("([A-Z]{1}[a-z]+)|([A-Z][a-z]+,)")) {
						text.add("<Capitalized>");
					}
					text.add(word);
				}
				rowReader.close();
			}
			lineReader.close();
		} catch (Exception e) {
			// if any error occurs
			System.out.println("\nSorry, no file found!");
			Util.shortHelp();
			e.printStackTrace();
			System.exit(0);
		}
	}

}
