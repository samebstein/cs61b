import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

/**
 * Created by Jenny Huang on 3/12/19.
 */
public class TestMyTrieSet {

    // assumes add/contains work
    @Test
    public void sanityClearTest() {
        MyTrieSet t = new MyTrieSet();
        for (int i = 0; i < 455; i++) {
            t.add("hi" + i);
            //make sure put is working via contains
            assertTrue(t.contains("hi" + i));
        }
        t.clear();
        for (int i = 0; i < 455; i++) {
            assertFalse(t.contains("hi" + i));
        }
    }

    // assumes add works
    @Test
    public void sanityContainsTest() {
        MyTrieSet t = new MyTrieSet();
        assertFalse(t.contains("waterYouDoingHere"));
        t.add("waterYouDoingHere");
        assertTrue(t.contains("waterYouDoingHere"));
    }

    // assumes add works
    @Test
    public void sanityPrefixTest() {
        String[] saStrings = new String[]{"same", "sam", "sad", "sap"};
        String[] otherStrings = new String[]{"a", "awls", "hello"};

        MyTrieSet t = new MyTrieSet();
        for (String s: saStrings) {
            t.add(s);
        }
        for (String s: otherStrings) {
            t.add(s);
        }

        List<String> keys = t.keysWithPrefix("sa");
        for (String s: saStrings) {
            assertTrue(keys.contains(s));
        }
        for (String s: otherStrings) {
            assertFalse(keys.contains(s));
        }


        String[] familyJEs = new String[]{"jeremy", "jeff"};
        String[] familyJAs = new String[]{"james", "jared"};
        MyTrieSet family = new MyTrieSet();
        for (String s : familyJEs) {
            family.add(s);
        }
        for (String s : familyJAs) {
            family.add(s);
        }

        List<String> keysJA = family.keysWithPrefix("ja");
        List<String> keysJE = family.keysWithPrefix("je");

        for (String s : familyJAs) {
            assertTrue(keysJA.contains(s));
        }

        for (String s : familyJEs) {
            assertTrue(keysJE.contains(s));
        }
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestMyTrieSet.class);
    }



}
