import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    @Test
    public void testEqualChars() {

        OffByN offBy5 = new OffByN(5);
        assertTrue(offBy5.equalChars('a', 'f'));  // true
        assertTrue(offBy5.equalChars('f', 'a'));
        assertFalse(offBy5.equalChars('f', 'h'));  // false

        OffByN offBy1 = new OffByN(1);
        assertTrue(offBy1.equalChars('a', 'b'));  // true
        assertTrue(offBy1.equalChars('b', 'a'));
        assertFalse(offBy1.equalChars('a', 'c'));

        OffByN offBy0 = new OffByN(0);
        assertTrue(offBy0.equalChars('a', 'a'));

    }
}
