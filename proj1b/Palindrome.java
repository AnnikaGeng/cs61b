import java.util.Collections;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordArray = new ArrayDeque<>();
        for (int i=0; i<word.length();i++) {
            char c = word.charAt(i);
            wordArray.addLast(c);
        }
        return wordArray;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return true;
        }
        Deque<Character> wordArray = wordToDeque(word);
        return isPalindromeRecursive(wordArray);
    }

    private boolean isPalindromeRecursive(Deque<Character> d) {
        if (d.size() <= 1) {
            return true;
        }
        if (d.removeFirst() != d.removeLast()) {
            return false;
        }
        return isPalindromeRecursive(d);
    }

    public boolean isPalindrome(CharacterComparator cc, String word) {
        if (word == null) {
            return true;
        }
        Deque<Character> d = wordToDeque(word);
        while (d.size() > 1) {
            if (!cc.equalChars(d.removeFirst(),d.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
