import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

public class AnagramFinderTest {

    @Test
    void testSortLetters() {
        AnagramFinder anagramFinder = new AnagramFinder();

        // Test sorting a word with unique characters
        String word1 = "hello";
        String expected1 = "ehllo";
        String actual1 = anagramFinder.sortLetters(word1);
        assertEquals(expected1, actual1);

        // Test sorting a word with duplicate characters
        String word2 = "bookkeeper";
        String expected2 = "beeekkoopr";
        String actual2 = anagramFinder.sortLetters(word2);
        assertEquals(expected2, actual2);

        // Test sorting an empty string
        String word3 = "";
        String expected3 = "";
        String actual3 = anagramFinder.sortLetters(word3);
        assertEquals(expected3, actual3);
    }

    @Test
    void testFindAnagrams() {
        AnagramFinder anagramFinder = new AnagramFinder();

        // Test finding anagrams in a set of words
        Set<String> words = Set.of("listen", "silent", "debitcard", "badcredit", "astronomer", "moonstarer");

        List<List<String>> expectedAnagrams = Arrays.asList(
                Arrays.asList("listen", "silent"),
                Arrays.asList("debitcard", "badcredit"),
                Arrays.asList("astronomer", "moonstarer")
        );

        List<List<String>> actualAnagrams = anagramFinder.findAnagrams(words);

        assertEquals(expectedAnagrams.size(), actualAnagrams.size());
        assertEquals(expectedAnagrams.stream().flatMap(List::stream).sorted().collect(Collectors.toList()),
                actualAnagrams.stream().flatMap(List::stream).sorted().collect(Collectors.toList()));
    }

    @Test
    void testReadWordsFromFile1() throws IOException {
        AnagramFinder anagramFinder = new AnagramFinder();

        // Create a temporary file with some test data
        Path tempFile = Files.createTempFile("words", ".txt");
        String testData = "cat\ndog\nrat\n";
        Files.writeString(tempFile, testData);

        // Test reading words from a file
        assertDoesNotThrow(() -> {
            Set<String> words = anagramFinder.readWordsFromFile(tempFile.toString());

            // Check the number of words read
            assertEquals(3, words.size());

            // Check that the words were read correctly
            assertTrue(words.contains("cat"));
            assertTrue(words.contains("dog"));
            assertTrue(words.contains("rat"));
        });

        // Delete the temporary file
        Files.deleteIfExists(tempFile);
    }
}
