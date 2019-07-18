package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    Node[][] grid;
    int openSites;
    int N;
    WeightedQuickUnionUF wuf;
    WeightedQuickUnionUF wufNoBottom;

    private class Node {
        private boolean b;
        private int coordinate;
        private boolean full;

        public Node(int row, int col) {
            this.coordinate = xyToCoordinate(row, col);
            this.b = false;
            if (coordinate < N) {
                this.full = true;
            } else {
                this.full = false;
            }
        }
    }
    /** Returns an integer value given integer coordinates in a two dimensional array. */
    public int xyToCoordinate(int row, int col) {
       return row * N + col;
    }

    public int valueToRow(int number) {
        return number / N;
    }

    public int valueToCol(int number) {
        return number % N;
    }

    /** Create N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.N = N;
        this.openSites = 0;
        this.wuf = new WeightedQuickUnionUF(N*N + 2);
        this.wufNoBottom = new WeightedQuickUnionUF(N*N + 1);
        grid = new Node[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = new Node(i, j);
            }
        }
    }

    /** open the site (row, col) if it is not open already.
     * And also connect via union find with any other adjacent nodes. */
    public void open(int row, int col) {
        if (row >= N || col >= N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (grid[row][col].b != true) {
            grid[row][col].b = true;
            openSites += 1;
        }
        int value = xyToCoordinate(row, col);
        if (value < N) {
            wuf.union(value, N*N);
            wufNoBottom.union(value, N*N);
        }
        if (value < N*N && value >= N*N - N) {
            wuf.union(value, N*N + 1);
        }
        if (legitimatePlusMinusN(value + N)) {
            int r = valueToRow(value + N);
            int c = valueToCol(value + N);
            if (grid[r][c].b) {
                wuf.union(value, value + N);
                wufNoBottom.union(value, value + N);
            }
        }
        if (legitimatePlusMinusN(value - N)) {
            int r = valueToRow(value - N);
            int c = valueToCol(value - N);
            if (grid[r][c].b) {
                wuf.union(value, value - N);
                wufNoBottom.union(value, value - N);
            }
        }
        if (legitimatePlus1(value + 1)) {
            int r = valueToRow(value + 1);
            int c = valueToCol(value + 1);
            if (grid[r][c].b) {
                wuf.union(value, value + 1);
                wufNoBottom.union(value, value + 1);
            }

        }
        if (legitimateMinus1(value - 1)) {
            int r = valueToRow(value - 1);
            int c = valueToCol(value - 1);
            if (grid[r][c].b) {
                wuf.union(value, value - 1);
                wufNoBottom.union(value, value - 1);
            }
        }
    }
    /** I want to define some function that takes the four possible adjacent coordinates and
     * returns a boolean if they are legitimate adjacents. So it should take the coordinate of the node
     * that we are looking at and another coordinate of the adjacent node. I will figure out the
     * plus/minus N and the plus/minus 1 with if statements and then find statements in the open
     * function.
     */
    public boolean legitimatePlusMinusN(int adjacent) {
        return adjacent < N*N && adjacent >= 0;
    }

    public boolean legitimatePlus1(int adjacent) {
        return adjacent % N != 0;
    }

    public boolean legitimateMinus1(int adjacent) {
        if (adjacent < 0) {
            return false;
        }
        return adjacent % N != N - 1;
    }

    /** I also want another function that determines if the adjacent is full, and if so makes the node
     * we're looking at become full as well.
     * This function not necessary.
     */
    public boolean adjacentFull(int adjacent) {
        int row = valueToRow(adjacent);
        int col = valueToCol(adjacent);

        return grid[row][col].full;
    }

    /** and then I also want something else that changes every connected node to full if that
     * node is now full.
     */

    /** return true if the site (row, col) is open */
    public boolean isOpen(int row, int col) {
        if (row >= N || col >= N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return grid[row][col].b;
    }

    /** return true if the site (row, col) is full */
    // isFull is complicated. I need to figure out how to tell if this node is connected
    // to one of the top rows' nodes and you can only connect if you connect through open nodes
    // and to an open node on the top row.
    public boolean isFull(int row, int col) {
        if (row >= N || col >= N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int value = xyToCoordinate(row, col);
        return wuf.connected(value, N*N) && wufNoBottom.connected(value, N*N);
    }

    /** return int number of open sites */
    public int numberOfOpenSites() {
        return openSites;
    }

    /** does the system percolate? */
    public boolean percolates() {
        return wuf.connected(N * N, N * N + 1);
    }

    /** use for unit testing (not required, but keep this here for the autograder) */
    public static void main(String[] args) {
    }
}