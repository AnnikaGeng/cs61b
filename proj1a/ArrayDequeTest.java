public class ArrayDequeTest {

    /* Prints a nice message based on whether a test passed.
     * The \n means newline. */
    public static void printTestStatus(boolean passed) {
        if (passed) {
            System.out.println("Test passed!\n");
        } else {
            System.out.println("Test failed!\n");
        }
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    public static void addTest() {
        System.out.println("add first and last test.");
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();

        ad.addFirst(0);
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addLast(4);
        ad.addLast(5);
        ad.removeFirst();
        ad.removeLast();
        ad.addFirst(8);
        ad.addLast(9);
        ad.addFirst(10);
        ad.addLast(11);
        ad.addFirst(12);
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeFirst();
        ad.removeFirst();
        boolean passed = checkSize(10, ad.removeFirst());
        printTestStatus(passed);
    }

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        addTest();
    }
}
