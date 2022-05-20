import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offByN = new OffByN(1);
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testOffByN() {
        assertTrue(palindrome.isPalindrome("flake", offByN));
    }
}
