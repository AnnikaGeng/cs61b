package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestAddHexagon {

    @Test
    public void testHexRowWidth() {
        assertEquals(4, AddHexagon.hexRowWidth(2, 1));
        assertEquals(5, AddHexagon.hexRowWidth(5, 0));
        assertEquals(13, AddHexagon.hexRowWidth(5, 4));
        assertEquals(5, AddHexagon.hexRowWidth(3, 1));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(-1, AddHexagon.hexRowOffset(3, 1));
        assertEquals(-2, AddHexagon.hexRowOffset(3, 3));
        assertEquals(-4, AddHexagon.hexRowOffset(5, 4));
        assertEquals(-3, AddHexagon.hexRowOffset(5, 6));
    }

    @Test
    public void testRowNumber() {
        assertEquals(1, AddHexagon.rowNumber(3, 0));
        assertEquals(3, AddHexagon.rowNumber(4, 2));
        assertEquals(2, AddHexagon.rowNumber(3, 5));
        assertEquals(1, AddHexagon.rowNumber(3, 8));
    }

    @Test
    public void testRowOffSet() {
        assertEquals(0, AddHexagon.rowOffSet(3, 0, 0));
        assertEquals(5, AddHexagon.rowOffSet(3, 1, 1));
        assertEquals(-10, AddHexagon.rowOffSet(3, 2, 0));
        assertEquals(0, AddHexagon.rowOffSet(3, 4, 1));
        assertEquals(0, AddHexagon.rowOffSet(4, 4, 1));
    }
}
