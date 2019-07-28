import java.util.*;

/**
 * Created by Sam Ebstein on July 27, 2019.
 * @author Sam Ebstein
 */


public class MyTrieSet implements TrieSet61B {
    Node root;

    private class Node {
        private boolean isKey;
        private HashMap<Character, Node> map;

        public Node(boolean isKey) {
            this.isKey = isKey;
            map = new HashMap<>();
        }
    }

    public MyTrieSet() {
        root = new Node(false);
    }


    /**
     * Clears all items out of Trie
     */
    @Override
    public void clear() {
        root = new Node(false);

    }


    /**
     * Returns true if the Trie contains KEY, false otherwise
     */
    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            return false;
        }

        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            } else {
                curr = curr.map.get(c);
            }
        }
        return true;
    }

    /**
     * Inserts string KEY into Trie
     */
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    /**
     * Returns a list of all words that start with PREFIX
     */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        Node curr = root;
        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            if (!curr.map.containsKey(c)) {
                throw new NoSuchElementException("Prefix doesn't exist in this Trie");
            } else {
                curr = curr.map.get(c);
            }
        }

        Set<Character> keys = curr.map.keySet();
        return prefixHelper(curr, prefix);
    }
    /** I want to call a helper function that goes through recursively, receives a node,
     * determines whether or not to add that prefix + node to a list of words
     */
    private List<String> prefixHelper(Node n, String prefix) {
        List<String> words = new ArrayList<>();

        if (n.isKey) {
            words.add(prefix);
        }
        if (n.map.keySet().isEmpty()) {
            return words;
        }
        for (char c : n.map.keySet()) {
            words.addAll(prefixHelper(n.map.get(c), prefix + c));
        }
        return words;
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args){
        MyTrieSet t = new MyTrieSet();
        t.add("hello");
        t.add("hi");
        t.add("help");
        t.add("zebra");
    }

}