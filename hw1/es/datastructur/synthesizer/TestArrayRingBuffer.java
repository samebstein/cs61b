package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Array;
import java.util.Iterator;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        //ArrayRingBuffer arb = new ArrayRingBuffer(10);
    }

    @Test
    public void testArrayRB() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(4);
        assertEquals(4, arb.capacity());
        assertEquals(0, arb.fillCount());

        arb.enqueue("one");
        arb.enqueue("two");
        arb.enqueue("three");
        arb.enqueue("four");
        assertEquals(4, arb.capacity());
        assertEquals(4, arb.fillCount());

        assertEquals("one", arb.dequeue());
        assertEquals("two", arb.dequeue());
        assertEquals("three", arb.dequeue());
        assertEquals("four", arb.peek());
        assertEquals("four", arb.dequeue());
    }

    @Test
    public void testARBIterator() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(4);
        arb.enqueue("one");
        arb.enqueue("two");
        arb.enqueue("three");
        arb.enqueue("four");

        Iterator<String> arbi = arb.iterator();
        assertTrue(arbi.hasNext());
        assertEquals("one", arbi.next());
        assertTrue(arbi.hasNext());
        assertEquals("two", arbi.next());
        assertTrue(arbi.hasNext());
        assertEquals("three", arbi.next());
        assertTrue(arbi.hasNext());
        assertEquals("four", arbi.next());
        assertFalse(arbi.hasNext());
    }

    @Test
    public void testEquals() {
        ArrayRingBuffer<String> arb1 = new ArrayRingBuffer<>(3);
        assertFalse(arb1.equals(null)); //null check
        assertFalse(arb1.equals("hi")); //class check

        ArrayRingBuffer<String> arb2 = new ArrayRingBuffer<>(3);
        ArrayRingBuffer<String> arb3 = new ArrayRingBuffer<>(2);
        assertTrue(arb1.equals(arb2)); //two empty queues equal
        assertFalse(arb1.equals(arb3)); //different capacities check

        ArrayRingBuffer<String> orig1 = arb1;
        ArrayRingBuffer<String> orig2 = arb2;

        arb1.enqueue("one");
        arb2.enqueue("two");
        assertFalse(arb1.equals(arb2)); //general not equals check

    }

}
