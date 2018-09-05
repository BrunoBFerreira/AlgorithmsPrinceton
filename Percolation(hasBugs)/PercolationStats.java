import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double[] results;
	private static final double per = 1.96;

	public PercolationStats(int n, int trials) {
		// perform trials independent experiments on an n-by-n grid
		this.results = new double[trials];
		for (int i = 0; i < trials; i++) {
			Percolation p = new Percolation(n);
			while (!p.percolates()) {
				p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
			}
			this.results[i] = p.numberOfOpenSites() / (n * n * 1.0);
		}
	}

	public double mean() {
		// sample mean of percolation threshold
		return StdStats.mean(this.results);
	}

	public double stddev() {
		// sample standard deviation of percolation threshold
		return StdStats.stddev(this.results);
	}

	public double confidenceLo() {
		// low endpoint of 95% confidence interval
		return mean() - (per * stddev()) / java.lang.Math.sqrt(results.length);
	}

	public double confidenceHi() {
		// high endpoint of 95% confidence interval
		return mean() + (per * stddev()) / java.lang.Math.sqrt(results.length);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = StdIn.readInt();
		int t = StdIn.readInt();

		PercolationStats ps = new PercolationStats(n, t);

		StdOut.print("mean \t = ");
		StdOut.println(ps.mean());

		StdOut.print("stddev \t = ");
		StdOut.println(ps.stddev());

		StdOut.print("95% confidence interval = [");
		StdOut.print(ps.confidenceLo());
		StdOut.print(", ");
		StdOut.print(ps.confidenceHi());
		StdOut.println("]");

	}

}
