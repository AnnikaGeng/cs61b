package hw2;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestPercolation {

    @Test
    public void testPercolation() {
        Percolation percolation = new Percolation(1);
        //percolation.open(0,0);
        assertEquals(false, percolation.percolates());
    }
}
