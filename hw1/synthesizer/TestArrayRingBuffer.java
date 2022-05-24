package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    ArrayRingBuffer arb = new ArrayRingBuffer(4);
    @Test
    public void testEnque() {

        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        assertEquals(1, arb.dequeue());
        assertEquals(2, arb.dequeue());
        assertEquals(3, arb.dequeue());
        arb.enqueue(5);
        assertEquals(4, arb.dequeue());

    }

//    @Test
//    public void testGetNextIndex() {
//        assertEquals(1, arb.getNextIndex(0,10));
//        assertEquals(0, arb.getNextIndex(9,10));
//    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
