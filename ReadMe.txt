Anagram Finder

Overview :
This project contains an implementation of an Anagram Finder program in Java, along with a suite of JUnit tests to verify its functionality.

Requirements :
JDK 11 or higher
JUnit 5.7.0 or higher


To run the program, use the following command:
1. 
With dictionary-file-path as argument. dictionary-file-path is the path to a file containing a list of words, one per line.
java -jar AnagramFinder.jar <dictionary-file-path>

For example, to find anagrams from the test.txt file, run the following command:

java -jar AnagramFinder.jar test.txt

OR

2. 
Without argument which takes default file words-utf8.txt

java -jar AnagramFinder.jar


Running the Tests : 
To run the JUnit tests, navigate to the project directory and run the following command:

java -jar junit-platform-console-standalone-1.9.2.jar -cp . --scan-classpath

This will execute all JUnit tests in the project and print the results to the console.