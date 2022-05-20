public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordArray = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            wordArray.addLast(c);
        }
        return wordArray;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordArray = wordToDeque(word);
        while (wordArray.size() > 1) {
            if (wordArray.removeFirst() != wordArray.removeLast()) {
                return false;
            }
        }
        return true;
    }

//    private boolean isPalindromeRecursive(Deque<Character> d) {
//        if (d.size() <= 1) {
//            return true;
//        }
//        if (d.removeFirst() != d.removeLast()) {
//            return false;
//        }
//        return isPalindromeRecursive(d);
//    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = wordToDeque(word);
        while (d.size() > 1) {
            if (!cc.equalChars(d.removeFirst(), d.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
