package ner;

import java.io.IOException;

import util.MyPrintStream;
import util.Util;

/**
 * A simple Named Entity Recognition application that can detect and classify
 * locations, persons and companies in a text. We have manually created the
 * corpora by collecting data from various sources on the web, such as
 * Wikipedia and other databases.
 * 
 * Could easily be updated with more corpora, such as a list of institutions, or
 * increasing the list of common names.
 * 
 * @version 0.1
 * 
 * @author Emil W
 * @author Óscar G
 *
 */

public class EntityRecognition {

	/**
	 * The main metohd of the Named Entity Recognition Classifier
	 * 
	 * @param args
	 *            You can use a text file as an argument, which will be the
	 *            input text that is going to get annotated.
	 * @throws IOException
	 *             Throws an exception if the file is not found.
	 */
	public static void main(String[] args) throws IOException {
		
		boolean keepShapeFeatures = false;
		boolean tagsOnly = false;

		// adding a slight effect to the System.out so each character at a time
		// is printed out.
		MyPrintStream sleep = new MyPrintStream(System.out, true);
		CorpusGenerator corp = new CorpusGenerator();
		System.setOut(sleep);
		// sleep for 2 milliseconds between each character outprint
		sleep.setSleep(2);

		if (args.length < 1) {
			Util.shortHelp();
			System.exit(0);
		} else {
			if (args[0].equals("-help")) {
				Util.printhelp();
				System.exit(0);
			} else if (!args[0].substring(args[0].length()-4, args[0].length()).equals(".txt")){
				Util.shortHelp();
				System.exit(0);
			}
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-keepShapes")) {
					keepShapeFeatures = true;
				}

				if (args[i].equals("-onlyTags")) {
					tagsOnly = true;
				}
			}
		}

		// counting how long time it takes to execute and finish the program
		long tStart = System.currentTimeMillis();
		
		sleep.setSleep(0);
		// title screen
		Util.titleScreen();
		sleep.setSleep(2);
		
		System.out.println("Use the -help flag for more instructions\n");
		
		System.out.println("#################################################");
		System.out.println("Importing Corpora...");
		corp.generateCorpus();
		System.out.println("Done.\n");

		System.out.println("#################################################");
		System.out.println("Extracting Shape Features...");
		FeatureExtraction.shapeFeatureExtractor(args[0], corp.getText());
		System.out.println("Shape features successfully extracted.\n");

		System.out.println("#################################################");
		System.out
				.println("Starting the Named Entity Classification Algorithm:\n");
		System.out.println("Annotation in progress...");
		
		if (tagsOnly == true){
			System.out.println("Retrieving annotated words only.");
		}
		if (keepShapeFeatures == true){
			System.out.println("Keeping the shape feature tags...");
		}
		
		String corpus = "";
		int wordCounter = 0;

		try {
			// going trough every word in the text with the shape features annotated
			for (int i=0; i<corp.getText().size(); i++){
				// if the current word is a shape feature tag
				if (corp.getText().get(i).matches("<.+>")){
					
					// look at the word after the tag
					String s = corp.getText().get(i+1);
					
					// we replace that tag with our own tags according to if its a location, person or a company
					Annotator.replaceTag(i, s, corp, corp.places, "<LOCATION ");
					// apparently, 'New' can also be the name of a person. So we only classify New York as a location
					// because the probability that New York being a location is much higher than it is being a name of a person
					if (!corp.getText().get(i).equals("<LOCATION ")){
						Annotator.replaceTag(i, s, corp, corp.names, "<PERSON ");
						Annotator.replaceTag(i, s, corp, corp.companies, "<COMPANY ");
					}
					
					// removes shape feature tags by default, unless the -keepShapes flag is given as an argument
					if (!keepShapeFeatures && corp.getText().get(i).matches("(<AllCaps>|<MixedCase>|<Initials>|<EndsInDigit>|<ContainsHyphen>|<Capitalized>)")){
						corp.getText().remove(i);
					}
				} 
				// else, if the current word is not a shape feature tag:
				else {
					String s = corp.getText().get(i);
					Annotator.annotateWord(i, s, corp, corp.places, "<LOCATION ");
					if (i > 1 && !corp.getText().get(i-1).equals("<LOCATION ")){
						Annotator.annotateWord(i, s, corp, corp.names, "<PERSON ");
						Annotator.annotateWord(i, s, corp, corp.companies, "<COMPANY ");
					}
				}

				// if the -tagsOnly flag is given, we only add the named entity tags
				if (tagsOnly){
					corpus = Annotator.search(i, corp, corpus, "<LOCATION ");
					corpus = Annotator.search(i, corp, corpus, "<PERSON ");
					corpus = Annotator.search(i, corp, corpus, "<COMPANY ");
					wordCounter = Annotator.getCounter();
				} else {
					corpus = corpus + " " + corp.getText().get(i);
					wordCounter++;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		

		
		System.out.println("Annotation completed.\n");
		System.out.println("#################################################");

		String newName = "annotated.txt";
		System.out.printf("Writing to new file %s...\n", newName);
		
		System.setOut(System.out);
		Util.write(corpus, newName);
	
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - tStart;
		double elapsedSeconds = tDelta / 1000.0;
		
		System.out.println("Done.\n");
		
		if (tagsOnly){
			System.out.printf("%d tags were written to %s%n", wordCounter, newName);
		} else {
			System.out.printf("%d words were written to %s%n", wordCounter, newName);
		}
		
		System.out.printf("Time elapsed: %.2f seconds\n\n", elapsedSeconds);
		System.out.printf("%s was saved to:\n%s\n", newName,
				Util.showCurrentDirectory());
	}
}
