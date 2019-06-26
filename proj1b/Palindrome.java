import java.util.LinkedList;

public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> d = new LinkedListDeque<Character>();

        for (int i = 0; i < word.length(); i++) {
            d.addLast(word.charAt(i));
        }
        return d;
    }
    /** Function returns true if word is a palindrome.
     * If word is length 0 or 1, returns true. */
    /**public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        }
        Palindrome palindrome = new Palindrome();
        Deque d = palindrome.wordToDeque(word);

        String myFinal = "";
        for (int i = 0; i < word.length(); i++){
            myFinal += d.removeLast();
        }
        return myFinal.equals(word);
    }*/

    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        }
        Palindrome palindrome = new Palindrome();
        Deque d = palindrome.wordToDeque(word);

        boolean answer = isPalindromeHelper(d);
        return answer;
    }

    public boolean isPalindromeHelper(Deque d){
        if (d == null || d.size() == 0 || d.size() == 1) {
            return true;
        } else if (d.removeFirst() == d.removeLast()){
            return isPalindromeHelper(d);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Palindrome palindrome = new Palindrome();
        Deque d = palindrome.wordToDeque(word);

        return isPalindromeHelperTwo(d, cc);
    }

    public boolean isPalindromeHelperTwo(Deque d, CharacterComparator cc){
        if (d == null || d.size() == 0 || d.size() == 1) {
            return true;
        }
        char a = (char) d.removeFirst();
        char b = (char) d.removeLast();

        if (cc.equalChars(a, b)){
            return isPalindromeHelperTwo(d, cc);
        }

        return false;
    }
}
