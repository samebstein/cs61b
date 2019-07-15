package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

import java.util.Arrays;

public class PercolationStats {
    int m;
    double mean;
    double[] s;
    int T;
    PercolationFactory pf;
    double stdd;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.m = 0;
        this.s = new double[T];
        this.T = T;
        this.pf = pf;

        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                p.open(row, col);
            }
            m += p.numberOfOpenSites();
            s[i] = p.numberOfOpenSites() * 1.0;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        this.mean = (m * 1.0) / T;
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        this.stdd = StdStats.stddev(s);
        return stdd;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean - ((1.96*stdd) / Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean + ((1.96*stdd) / Math.sqrt(T));
    }
}
