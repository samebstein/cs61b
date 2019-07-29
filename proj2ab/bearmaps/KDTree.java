package bearmaps;

import java.util.List;

/**
 * Created by Sam Ebstein.
 */

public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;

    private class Node {
        private Point p;
        private boolean orientation;
        private Node leftChild; // leftChild also refers to down child.
        private Node rightChild; // rightChild also refers to up child.

        public Node(Point p, boolean orientation) {
            this.p = p;
            this.orientation = orientation;
        }

        public Point getP() {
            return p;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, HORIZONTAL);
        }
    }

    private Node add(Point p, Node n, boolean orientation) {
        if (n == null) {
            return new Node(p, orientation);
        }
        if (p.equals(n.p)) {
            return n;
        }
        int cmp = comparePoints(p, n.p, orientation);
        if (cmp < 0) {
            n.leftChild = add(p, n.leftChild, !orientation);
        } else if (cmp >= 0) {
            n.rightChild = add(p, n.rightChild, !orientation);
        }

        return n;
    }

    private int comparePoints(Point a, Point b, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(a.getX(), b.getX());
        } else {
            return Double.compare(a.getY(), b.getY());
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node best = nearestHelper(root, goal, root);
        return best.getP();
    }


    /** Will return the Node in the KD tree that is closest to the
     * inputted "goal" point. Will prune off bad sides of KD tree that
     * shouldn't be considered. */
    private Node nearestHelper(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        double bestDistance = Point.distance(goal, best.getP());
        double nodeDistance = Point.distance(goal, n.getP());
        if (nodeDistance < bestDistance) {
            best = n;
        }

        int cmp = comparePoints(goal, n.getP(), n.orientation);
        Node goodSide;
        Node badSide;

        if (cmp > 0) {
            goodSide = n.rightChild;
            badSide = n.leftChild;
        } else {
            goodSide = n.leftChild;
            badSide = n.rightChild;
        }
        best = nearestHelper(goodSide, goal, best);

        double cmpBadSide;
        if (n.orientation == HORIZONTAL) {
            cmpBadSide = n.getP().getX() - goal.getX();
        } else {
            cmpBadSide = n.getP().getY() - goal.getY();
        }
        cmpBadSide *= cmpBadSide;


        if (cmpBadSide < Point.distance(goal, best.getP())) {
            best = nearestHelper(badSide, goal, best);
        }

        return best;
    }
}
