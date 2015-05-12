Named Entity Recognizer and Classifier
Version 0.1

Developed by Emil W. and Óscar G.
at Uppsala University, March 2015.
Course: Semantic Analysis
--------------------------------------

A simple Named Entity Recognition application that can detect and classify
locations, persons and companies in a text. We have manually created the
corpora by collecting data from various sources on the web, such as
Wikipedia and other databases.
 
Could easily be updated with more corpora, such as a list of institutions, or
increasing the list of common names.

---------------------------------------


1. Instructions:

You can run either the main java file EntityRecognition.java in the src-folder, 
or by running the NER-0.1.jar file in the main folder.
The jar file is recommended, for simplicity.

To run:

java -jar NER-0.1.jar "locationToTextfile.txt"


The txt file "bioInformatics-10.txt" is inside the folder "unannotated corpus",
if you want to use that as input, since it's fairly small.

You can enter any file you like, of course.
Just keep in mind that if the txt file is larger than 1 MB (one million bytes, 1 byte = 1 character), 
it can take over 30 minutes to annotate the whole text, due to everything it has to process.

However, normally it only takes a couple of seconds to annotate.


2. Availible flags:

-keepShapes : keeps the shape feature tags.
-onlyTags : only shows the words that was tagged as a location, person or a company.
-help : shows a help screen

For example:

java EntityRecognition.java "bioInformatics-10.txt" -onlyTags -keepShapes

or:

java -jar NER-0.1.jar "bioInformatics-10.txt" -onlyTags -keepShapes

or: 

java -jar NER-0.1.jar -help