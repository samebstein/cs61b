import static org.junit.Assert.*;
import org.junit.Test;


public class TestArrayDequeGold {

    @Test
    public void testArrayDeque(){

        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sol1 = new ArrayDequeSolution<>();

        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                sad1.addLast(i);
                sol1.addLast(i);
            } else {
                sad1.addFirst(i);
                sol1.addLast(i);
            }
        }

        //sad1.printDeque();
        //sol1.printDeque();

        for (int i = 0; i < 10; i++){
            Integer student = sad1.removeLast();
            Integer solution = sol1.removeLast();
            assertEquals(student, solution);
        }
    }
}
