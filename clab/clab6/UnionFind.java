/**
 * Implementation of Weighted Quick Union (UF) with Path Compression.
 * @author Sam Ebstein
 */

public class UnionFind {


    private int sizeOfArray;
    private int[] disjointSet;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {

        sizeOfArray = n;
        disjointSet = new int[sizeOfArray];
        for (int i = 0; i < sizeOfArray; i++) {
            disjointSet[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex >= sizeOfArray) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {

        return disjointSet[find(v1)] * -1;
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {

        return disjointSet[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {

        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a
       vertex with itself or vertices that are already connected should not
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {

        int v1Root = find(v1);
        int v2Root = find(v2);

        //If v1Root has larger (negative) size than v2Root, hence less than operation.
        if (sizeOf(v1) > sizeOf(v2)) {
            int temp = disjointSet[v2Root];
            disjointSet[v2Root] = v1Root;
            disjointSet[v1Root] += temp;
        } else {
            int temp = disjointSet[v1Root];
            disjointSet[v1Root] = v2Root;
            disjointSet[v2Root] += temp;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {

        if (disjointSet[vertex] < 0) {
            return vertex;
        } else {
            int temp = find(disjointSet[vertex]);
            disjointSet[vertex] = temp;
            return temp;
        }
    }
}
