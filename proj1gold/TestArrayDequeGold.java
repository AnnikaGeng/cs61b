import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    StudentArrayDeque<Integer> testArray = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> stdArray = new ArrayDequeSolution<>();

    @Test
    public void testDeque() {
        String log = "";
        for (int i = 0; i < 1000; i++) {
            if (stdArray.size() == 0) {
                int addNumber = StdRandom.uniform(1000);
                int headOrBack = StdRandom.uniform(2);
                if (headOrBack == 0) {
                    log = log + "addFirst(" + addNumber + ")\n";
                    stdArray.addFirst(addNumber);
                    testArray.addFirst(addNumber);
                } else {
                    log = log + "addLast(" + addNumber + ")\n";
                    stdArray.addLast(addNumber);
                    testArray.addLast(addNumber);
                }
            } else {
                int x = StdRandom.uniform(4);
                int addNumber = StdRandom.uniform(1000);
                Integer testRemoveNumber = 1;
                Integer stdRemoveNumber = 1;
                switch (x) {
                    case 0:
                        log = log + "addFirst(" + addNumber + ")\n";
                        stdArray.addFirst(addNumber);
                        testArray.addFirst(addNumber);
                        break;
                    case 1:
                        log = log + "addLast(" + addNumber + ")\n";
                        stdArray.addLast(addNumber);
                        testArray.addLast(addNumber);
                        break;
                    case 2:
                        log = log + "removeFirst()\n";
                        testRemoveNumber = testArray.removeFirst();
                        stdRemoveNumber = stdArray.removeFirst();
                        break;
                    case 3:
                        log = log + "removeLast()\n";
                        testRemoveNumber = testArray.removeLast();
                        stdRemoveNumber = stdArray.removeLast();
                        break;
                    default:
                }
                assertEquals(log, testRemoveNumber, stdRemoveNumber);
            }
        }
    }
}
