import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome(){

        assertTrue(palindrome.isPalindrome("b"));
        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("racecar"));
    }

    @Test
    public void testNewIsPalindrome(){
        CharacterComparator NewOffByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", NewOffByOne));
        assertTrue(palindrome.isPalindrome("abb", NewOffByOne));
        assertTrue(palindrome.isPalindrome("ab", NewOffByOne));
        assertTrue(palindrome.isPalindrome("a", NewOffByOne));
        assertFalse(palindrome.isPalindrome("noon", NewOffByOne));
    }

    @Test
    public void testisPalindrome5(){
        CharacterComparator offBy5 = new OffByN(5);
        assertFalse(palindrome.isPalindrome("noon", offBy5));
        assertTrue(palindrome.isPalindrome("af", offBy5));

    }
}