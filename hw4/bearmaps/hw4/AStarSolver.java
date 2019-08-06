package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private int verticesExplored;

    private ArrayHeapMinPQ<Vertex> pq;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private HashSet<Vertex> set;


    private AStarGraph<Vertex> input;
    private Vertex start;
    private Vertex end;
    private double timeout;




    /** Constructor which finds the solution, computing everything necessary for all
     * other methods to return their results in constant time. Note that timeout
     * passed in is in seconds. */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        pq = new ArrayHeapMinPQ<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        set = new HashSet<>();
        this.input = input;
        this.start = start;
        this.end = end;
        this.timeout = timeout;
        this.verticesExplored = 0;
        this.start = start;
        this.timeout = timeout;
        this.solution = new LinkedList<>();


        pq.add(start, 0);
        distTo.put(start, 0.0);
        edgeTo.put(start, start);

        if (start.equals(end)) {
            outcome = SolverOutcome.SOLVED;
            solutionWeight = 0;
            solution = List.of(start);
            timeSpent = sw.elapsedTime();
            return;
        }

        while (pq.size() != 0) {
            Vertex p = pq.removeSmallest();
            verticesExplored++;
            set.add(p);
            if (explorationTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                return;
            }
            timeSpent = sw.elapsedTime();
            for (WeightedEdge<Vertex> neighbor : input.neighbors(p)) {
                relax(neighbor);
                if (neighbor.to().equals(end)) {
                    finished(neighbor);
                    return;
                }
            }
        }
        outcome = SolverOutcome.UNSOLVABLE;
    }

    /** Iterates through the hashmap of edges to determine list of edges in the shortest path
     * from start to end, the distance to the end and to mark the outcome as solved.
     * Only called once we hit the goal vertex.
     * */
    private void finished(WeightedEdge<Vertex> e) {
        LinkedList<Vertex> list = new LinkedList<>();
        Vertex q = e.to();
        solutionWeight = distTo.get(q);
        while (edgeTo.get(q) != q) {
            list.addFirst(q);
            q = edgeTo.get(q);
        }
        list.addFirst(q);
        solution = list;
        outcome = SolverOutcome.SOLVED;
    }

    private void relax(WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();

        if (!distTo.containsKey(q)) {
            distTo.put(q, Double.POSITIVE_INFINITY);
        }

        if (distTo.get(p) + w < distTo.get(q)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(q, p);
        }

        if (pq.contains(q)) {
            pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
        } else if (set.contains(q)) {
            return;
        } else {
            pq.add(q,distTo.get(q) + input.estimatedDistanceToGoal(q, end));
        }
    }

    @Override
    public SolverOutcome outcome() {return outcome;}

    @Override
    public List<Vertex> solution() {
        if (outcome == SolverOutcome.UNSOLVABLE || outcome == SolverOutcome.TIMEOUT) {
            solutionWeight = 0;
            return solution;
        }
        return solution;
    }

    @Override
    public double solutionWeight() {return solutionWeight;}

    @Override
    public int numStatesExplored() {return verticesExplored;}

    @Override
    public double explorationTime() {return timeSpent;}

}

