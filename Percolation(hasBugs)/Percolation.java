import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private int open;
	private boolean[][] board;
	private final WeightedQuickUnionUF searchProblem;

	public Percolation(int n) {
		// create n-by-n grid, with all sites blocked
		if (n <= 0) {
			throw new java.lang.IllegalArgumentException();
		} else {
			this.size = n;
			this.open = 0;
			this.board = new boolean[n][];
			for (int i = 0; i < n; i++) {
				this.board[i] = new boolean[n];
			}
		}
		searchProblem = new WeightedQuickUnionUF(n * n + 2);
		for(int i = 0; i<n; i++){
			searchProblem.union(i, n*n);
			searchProblem.union(n*(n-1)+i, n*n+1);
		}

	}

	public void open(int row, int col) {
		// open site (row, col) if it is not open already
		if (1 <= row && row <= this.size && 1 <= col && col <= this.size) {
			if (isOpen(row, col)) {
				return;
			} else {
				board[row - 1][col - 1] = true;
				if (col > 1 && board[row - 1][col - 2]) {
					searchProblem.union(this.size * (row - 1) + (col - 1), (this.size * (row - 1) + (col - 1)) - 1);
				}
				if (col < size && board[row - 1][col]) {
					searchProblem.union(this.size * (row - 1) + (col - 1), (this.size * (row - 1) + (col - 1)) + 1);
				}
				if (row > 1 && board[row - 2][col - 1]) {
					searchProblem.union(this.size * (row - 1) + (col - 1), (this.size * (row - 1) + (col - 1)) - size);
				}
				if (row < size && board[row][col - 1]) {
					searchProblem.union(this.size * (row - 1) + (col - 1), (this.size * (row - 1) + (col - 1)) + size);
				}
				this.open++;
			}
		} else {
			throw new java.lang.IllegalArgumentException();
		}
	}

	public boolean isOpen(int row, int col) {
		// is site (row, col) open?
		if (1 <= row && row <= this.size && 1 <= col && col <= this.size) {
			return board[row - 1][col - 1];
		} else {
			throw new java.lang.IllegalArgumentException();
		}
	}

	public boolean isFull(int row, int col) {
		// is site (row, col) full?
		if (1 <= row && row <= this.size && 1 <= col && col <= this.size) {
			if (!isOpen(row, col)) {
				return false;
			} else {
				return searchProblem.connected(this.size * (row - 1) + (col - 1), this.size*this.size);
			}
		} else {
			throw new java.lang.IllegalArgumentException();
		}
	}

	public int numberOfOpenSites() {
		// number of open sites
		return this.open;
	}

	public boolean percolates() {
		return searchProblem.connected(this.size*this.size, this.size*this.size+1);
	}

}
