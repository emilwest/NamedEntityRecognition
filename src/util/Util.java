package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/** Utility class
 * @author Emil
 *
 */
public class Util {

	/** Shows the current directory.
	 * @return current directory
	 * @throws IOException Exception
	 */
	public static String showCurrentDirectory() throws IOException{
		File currentDirectory = new File(new File(".").getAbsolutePath());
		return currentDirectory.getCanonicalPath();
	}
	
	/** Writes the corpus to a new file
	 * @param corpus String corpus
	 * @param newName Name of the new file
	 * @throws FileNotFoundException Exception of file is not found
	 * @throws UnsupportedEncodingException Exception
	 */
	public static void write(String corpus, String newName) throws FileNotFoundException, UnsupportedEncodingException{
		try {
			PrintStream outfile = new PrintStream(newName, "UTF-8");
			outfile.println(corpus);
			outfile.close();
		} catch (Exception e) {
			System.out.println("\n#################################################");
			System.out.println("------------------ ERROR! :'( -------------------");
			System.out.println("#################################################\n");
			System.out.println("An error occured while writing to the file.\n"
					+ "Make sure that the file is not already open\n"
					+ "and try again!");
			System.exit(0);
			
		}
	}

	/**
	 * Title Screen
	 */
	public static void titleScreen() {
		System.out
				.println("#################################################\n"
						+ "-------------------------------------------------\n"
						+ "---- Named Entity Recognition and Classifier ----\n"
						+ "-------------------------------------------------\n"
						+ "#################################################\n"
						+ "-------------------------------------------------\n"
						+ "------- Developed by Emil W and Oscar G ---------\n"
						+ "------ at Uppsala University, March 2015. -------\n"
						+ "-------------------------------------------------\n"
						+ "#################################################\n");
	}

	/**
	 * Prints out a help screen
	 */
	public static void printhelp() {
		titleScreen();
		System.out
				.print("To use the program, you need a text file as input.\n\n"
						+ "For example:\n"
						+ "java EntityRecognition.java myText.txt\n\n"
						+ "You can use the following flags:\n"
						+ "-keepShapes : keeps the shape feature tags.\n"
						+ "-onlyTags : only shows the words that was tagged as\n"
						+ "a location, person or a company.\n\n"
						+ "For example:\n"
						+ "java EntityRecognition.java myText.txt -onlyTags -keepShapes\n");
	}
	
	/**
	 * Short help screen
	 */
	public static void shortHelp() {
		System.out
				.println("Please enter a text file as an argument to get it annotated.");
		System.out.println("Use -help to get help.");
		System.out
				.println("For example: java EntityRecognition.java myText.txt\n"
						+ "or:\n" + "java EntityRecognition.java -help");
	}
	
	

}
