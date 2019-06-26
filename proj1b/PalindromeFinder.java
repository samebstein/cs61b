/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("../library-sp19/data/words.txt");
        Palindrome palindrome = new Palindrome();
        CharacterComparator NewerOffByOne = new OffByOne();
        CharacterComparator offBy2 = new OffByN(2);

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word, offBy2)) {
                System.out.println(word);
            }
        }
    }
}