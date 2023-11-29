import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class EvilHangmanTest {
    private EvilHangman evilHangman;

    @BeforeEach
    public void setup() {
        // Initialize EvilHangman with a predefined set of words for testing
        evilHangman = new EvilHangman("test.txt");
        // test file contains: <"echo", "heal", "belt", "peel", "hazy">
    }

    // The function should find the word pattern that has the most words, making the game hardest for the player.
    @Test
    public void testEvilAlgo0() {
        ArrayList<String> newWordList = evilHangman.evilAlgo('e');
        ArrayList<String> expectedWordList = new ArrayList<>(Arrays.asList("belt", "heal"));
        assertEquals(expectedWordList, newWordList);
    }

    @Test
    public void testEvilAlgo1() {
        ArrayList<String> newWordList = evilHangman.evilAlgo('a');
        ArrayList<String> expectedWordList = new ArrayList<>(Arrays.asList("belt", "echo", "peel"));
        assertEquals(expectedWordList, newWordList);
    }

    @Test
    public void testEvilAlgo2() {
        ArrayList<String> newWordList = evilHangman.evilAlgo('t');
        for (String word : newWordList) {
            System.out.println(word);
        }
        ArrayList<String> expectedWordList = new ArrayList<>(Arrays.asList("echo", "hazy", "heal", "peel"));
        assertEquals(expectedWordList, newWordList);
    }

}
