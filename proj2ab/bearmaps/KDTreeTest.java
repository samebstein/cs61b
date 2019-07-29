package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sam Ebstein.
 */

public class KDTreeTest {
    private static Random r = new Random(500);


    private static KDTree buildLectureTree() {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree nn = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        return nn;
    }

    private static void buildTreeWithDoubles() {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(2, 3);

        KDTree nn = new KDTree(List.of(p1, p2));
    }


    @Test
    /** Tests code by using example from nearest demo slides. */
    public void testNearestDemoSlides() {
        KDTree kd = buildLectureTree();

        Point actual = kd.nearest(0, 7);
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }

    private Point randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);

    }

    /**
     * Return N random points.
     */
    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            points.add(randomPoint());
        }
        return points;
    }


    private void testWithNPointsQQueries(int N, int Q) {
        List<Point> points = randomPoints(N);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = randomPoints(Q);

        for (Point p : queries) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    private void timeWithNPointsAndQQueries(int N, int Q) {
        List<Point> points = randomPoints(N);
        KDTree kd = new KDTree(points);

        List<Point> queries = randomPoints(Q);

        Stopwatch sw = new Stopwatch();
        for (Point p : queries) {
            Point actual = kd.nearest(p.getX(), p.getY());
        }
        System.out.println("Time elapsed with " + Q + " queries on " + N + " points: "
                            + sw.elapsedTime() + " seconds.");
    }

    @Test
    public void testWith1000PointsAnd200Queries() {
        int N = 1000;
        int Q = 200;
        testWithNPointsQQueries(N, Q);
    }

    @Test
    public void testWith10000PointsAnd2000Queries() {
        int N = 10000;
        int Q = 2000;
        testWithNPointsQQueries(N, Q);
    }

    @Test
    public void timeWith10000PointsAnd2000Queries() {
        timeWithNPointsAndQQueries(10000, 2000);
    }

    @Test
    public void timeWithVariousNumbersOfPoints() {
        List<Integer> pointCounts = List.of(1000, 10000);
        for (int N : pointCounts) {
            timeWithNPointsAndQQueries(N, 10000);
        }
    }

    @Test
    public void compareTimeOfNaiveVsKDLikeSpec() {
        List<Point> randomPoints = randomPoints(100000);

        KDTree kd = new KDTree(randomPoints);
        NaivePointSet nps = new NaivePointSet(randomPoints);

        List<Point> queryPoint = randomPoints(10000);

        Stopwatch sw = new Stopwatch();
        for (Point p : queryPoint) {
            nps.nearest(p.getX(), p.getY());
        }
        double time = sw.elapsedTime();
        System.out.println("Naive 10,000 queries on 100,000 points = " + time + " seconds.");

        Stopwatch swkd = new Stopwatch();
        for (Point p : queryPoint) {
            kd.nearest(p.getX(), p.getY());
        }
        double kdtime = swkd.elapsedTime();
        System.out.println("KD 10,000 queries on 100,000 points = " + kdtime + " seconds.");

    }

}