import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public final class Board {
	private int [][] board;
	private int hamming = 0;
	private int manhattan = 0;
	private Board twin;
	private int dimension;
	
	public Board(int[][] blocks) {
		this.dimension = blocks.length;
		int[][] goal = new int[this.dimension][this.dimension]; 
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				if (i == this.dimension - 1 && j == this.dimension - 1) {
					goal[i][j] = 0;
				}
				else {
					goal[i][j] = (i*this.dimension) + (j + 1);
				}
			}
		}
		
		this.board = blocks.clone();
		this.twin = null;
		
		int ham = 0;
		
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				if (this.board[i][j] != 0) {
					if (this.board[i][j] != goal[i][j]) {
						ham++;
					}
				}
			}
		}
		
		this.hamming = ham;
		
		int man = 0;
		int currentVal = 0;
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				currentVal++;
				int boardVal = this.board[i][j];
				
				if (currentVal != boardVal && boardVal != 0) {
					int sum = Math.abs(i - ((boardVal-1) / this.dimension)) 
							+ Math.abs(j - ((boardVal-1) % this.dimension));
					man += sum;
				}
			}
		}
		
		this.manhattan = man;
	}

	public int dimension() {
		int ret = this.dimension;
		return ret;
	}
	
	public int hamming() {
		int ret = this.hamming;
		return ret;
	}
	
	public int manhattan() {
		int man = this.manhattan;
		return man;
	}
	
	public boolean isGoal() {
		if (this.manhattan() == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	private int[][] copyArray() {
		int [][] cp = new int[this.dimension][this.dimension];
		
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				cp[i][j] = this.board[i][j];
			}
		}
		return cp;
	}
	public Board twin() {
		if (this.twin == null) {
			int [][] twinArr = copyArray();

			int val = 0;

			while (true) {
				int i = StdRandom.uniform(this.dimension);
				int j = StdRandom.uniform(this.dimension);

				if (this.board[i][j] != 0) {
					if (i < (this.dimension -1)) {
						if (this.board[i+1][j] != 0) {
							val = twinArr[i][j];
							twinArr[i][j] = twinArr[i+1][j];
							twinArr[i+1][j] = val;
							break;
						}
					}
					else {
						if (this.board[i-1][j] != 0) {
							val = twinArr[i][j];
							twinArr[i][j] = twinArr[i-1][j];
							twinArr[i-1][j] = val;
							break;
						}
					}
				}
			}
			
			this.twin = new Board(twinArr);
		}
		return this.twin;
	}
	
	public boolean equals(Object y) {
		if (this == y) {
			return true;
		}
		if (y == null) {
			return false;
		}
		if (y.getClass() == this.getClass()) {
			Board test = (Board) y;
			return Arrays.deepEquals(this.board, test.board);
		}
		else {
			return false;
		}
	}
	
	public Iterable<Board> neighbors() {
		ArrayList<Board> neighbors = new ArrayList<Board>();
		int i = 0;
		int j = 0;
		boolean found = false;
		
		for (i = 0; i < this.dimension; i++) {
			for (j = 0; j < this.dimension; j++) {
				if (this.board[i][j] == 0) {
					found = true;
					break;
				}
			}
			
			if (found) {
				break;
			}
		}
		
		if (i > 0) {
			int[][] neighbor = copyArray();
			int val = neighbor[i][j];
			neighbor [i][j] = neighbor[i-1][j];
			neighbor[i-1][j] = val;
			neighbors.add(new Board(neighbor));
		}
		if (i < (dimension - 1)) {
			int[][] neighbor = copyArray();
			int val = neighbor[i][j];
			neighbor [i][j] = neighbor[i+1][j];
			neighbor[i+1][j] = val;
			neighbors.add(new Board(neighbor));
		}
		if (j > 0) {
			int[][] neighbor = copyArray();
			int val = neighbor[i][j];
			neighbor [i][j] = neighbor[i][j-1];
			neighbor[i][j-1] = val;
			neighbors.add(new Board(neighbor));
		}
		if (j < (dimension - 1)) {
			int[][] neighbor = copyArray();
			int val = neighbor[i][j];
			neighbor [i][j] = neighbor[i][j+1];
			neighbor[i][j+1] = val;
			neighbors.add(new Board(neighbor));
		}
		
		return neighbors;
	}
	
	public String toString() {
		StringBuilder toPrint = new StringBuilder("" + this.dimension + "\n");
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				toPrint = toPrint.append(this.board[i][j] + " ");
			}
			toPrint.append("\n");
		}
		return toPrint.toString();
	}
	
	public static void main(String[] args) {		
		In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board b0 = new Board(blocks);
		
		StdOut.println(b0.dimension);
		StdOut.println(b0.manhattan());
		StdOut.println(b0.hamming());
		StdOut.println(b0.isGoal());
		StdOut.println(b0.twin());
		StdOut.println(b0.neighbors());
	}
}
