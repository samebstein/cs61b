
import bearmaps.ArrayHeapMinPQ;
import bearmaps.NaiveMinPQ;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.Random;

/**
 * Created by hug. Demonstrates how you can use either
 * System.currentTimeMillis or the Princeton Stopwatch
 * class to time code.
 */
public class TimingTest {
    public static void main(String[] args) {
        ArrayHeapMinPQ<String> heapMine = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> heapNaive = new NaiveMinPQ<>();
        for (int i = 1; i < 100000; i += 1) {
            heapMine.add("hi" + i, i);
        }
         for (int i = 1; i < 10000; i += 1) {
            heapNaive.add("hi" + i, i);
        }
        Random random = new Random();
        Stopwatch sw = new Stopwatch();
            for (int j = 1; j < 100000; j += 1) {
                heapMine.changePriority("hi" + j, random.nextDouble());
                heapMine.contains("hi"+ j);
                heapMine.size();
                heapMine.getSmallest();
            }
        System.out.println("Total time elapsed of my implementation: " + sw.elapsedTime() +  " seconds.");

        Stopwatch sw2 = new Stopwatch();
        for (int j = 1; j < 10000; j += 1) {
            heapNaive.changePriority("hi" + j, random.nextDouble());
        }
        System.out.println("Total time elapsed of Naive implementation: " + sw2.elapsedTime() +  " seconds.");

    }
}