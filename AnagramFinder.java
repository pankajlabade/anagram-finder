import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * AnagramFinder is a utility class for finding anagrams in a set of words.
 * An anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
 * typically using all the original letters exactly once.
 */
public class AnagramFinder {

    private static final String DEFAULT_WORDS_FILE_PATH = "words-utf8.txt";

    /**
     * Prints a list of anagram groups to the console.
     * An anagram group is a list of two or more words that contain the same letters in a different order.
     *
     * @param anagrams a list of anagram groups to print
     */
    private void printAnagrams(List<List<String>> anagrams) {
        anagrams.parallelStream().forEach(anagramList -> {
            if (anagramList.size() > 1) {
                System.out.println(String.join(" ", anagramList));
            }
        });
    }

    /**
     * Sorts the letters of a word in alphabetical order.
     *
     * @param word the word to sort
     * @return the sorted letters of the word
     */
    public String sortLetters(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    /**
     * Finds anagram groups in a set of words.
     * An anagram group is a list of two or more words that contain the same letters in a different order.
     *
     * @param words the set of words to search for anagram groups
     * @return a list of anagram groups
     */
    public List<List<String>> findAnagrams(Set<String> words) {
        Map<String, List<String>> anagrams = words.parallelStream()
                .collect(Collectors.groupingBy(this::sortLetters));

        return anagrams.values().parallelStream()
                .filter(list -> list.size() > 1)
                .collect(Collectors.toList());
    }

    /**
     * Reads a set of words from a text file.
     *
     * @param fileName the path to the text file containing the words
     * @return a set of words read from the file
     * @throws IOException if there is an error reading the file
     * @throws FileNotFoundException if the specified file cannot be found
     */
    public Set<String> readWordsFromFile(String fileName) throws IOException {
        Path path = Path.of(fileName);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
        try (var stream = Files.lines(path)) {
            return stream.map(String::trim)
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.toSet());
        }
    }

    /**
     * Entry point for the AnagramFinder program.
     * Reads a set of words from a text file specified in the first command-line argument or
     * the default file path "words-utf8.txt" if no argument is provided.
     * Finds all anagram groups in the set of words and prints them to the console.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        AnagramFinder anagramFinder = new AnagramFinder();

        // Read words from file
        String fileName = args.length > 0 ? args[0] : DEFAULT_WORDS_FILE_PATH;
        Set<String> words = null;

        try {
            words = anagramFinder.readWordsFromFile(fileName);
        } catch (IOException e) {
            System.err.println("Error reading words from file");
            e.printStackTrace();
            System.exit(1);
        }

        // Find anagrams
        List<List<String>> anagrams = anagramFinder.findAnagrams(words);

        // Print anagrams
        anagramFinder.printAnagrams(anagrams);
    }
}
