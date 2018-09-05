import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public final class Solver {
	private int moves = 0;
	private ArrayList<Board> sol = null;
	private boolean isSolv = false;

	private class Node {
		private int manhattan = 0;
		private int moves = 0;
		private Board currentBoard = null;
		private Node predec = null;
		
		
		private Node(Board b, int m, Node pre) {
			this.currentBoard = b;
			this.moves = m;
			this.manhattan = this.currentBoard.manhattan();
			this.predec = pre;
		}
		
		public int priority() {
			return this.moves + this.manhattan;
		}
		
		public int getMoves() {
			return this.moves;
		}
		
		public Board getCurrentBoard() {
			return this.currentBoard;
		}
		
		public Node getPredec() {
			return this.predec;
		}
	}
	
	private static class NodeComparator implements Comparator<Node> {
		public int compare(Node n0, Node n1) {
    		int pr0 = n0.priority();
    		int pr1 = n1.priority();
    		if (pr0 > pr1) {
    			return +1;
    		}
    		else if (pr0 < pr1) {
    			return -1;
    		}
    		else {
    			return 0;
    		}
    	}
	}
	public Solver(Board initial) {
		if (initial == null) {
			throw new java.lang.IllegalArgumentException();
		}
		MinPQ<Node> problem = new MinPQ<Node>(new NodeComparator());
		MinPQ<Node> problemTwin = new MinPQ<Node>(new NodeComparator());
		this.sol = new ArrayList<Board>();
		problem.insert(new Node(initial, 0, null));
		problemTwin.insert(new Node(initial.twin(), 0, null));
		
		boolean valid = true;
		
		while (true) {
			if (valid) {
				Node current = problem.delMin();
				Board cb = current.getCurrentBoard();
				if (cb.isGoal()) {
					this.moves = current.getMoves();
					this.isSolv = true;
					while(current != null){
						this.sol.add(current.getCurrentBoard());
						current = current.predec;
					}
					Collections.reverse(this.sol);
					break;
				}
				else {
					Iterable<Board> next = cb.neighbors();
					
					for (Board b : next) {
						if (current.getPredec() == null || !current.getPredec().getCurrentBoard().equals(b)) {
							problem.insert(new Node(b, current.getMoves() + 1, current));
						}
					}
				}
				valid = false;
			}
			else {
				Node current = problemTwin.delMin();
				Board cb = current.getCurrentBoard();
				if (cb.isGoal()) {
					this.moves = -1;
					this.isSolv = false;
					this.sol = null;
					break;
				}
				else {
					Iterable<Board> next = cb.neighbors();
					
					for (Board b : next) {
						if (current.getPredec() == null || !current.getPredec().getCurrentBoard().equals(b)) {
							problemTwin.insert(new Node(b, current.getMoves() + 1, current));
						}
					}
				}
				
				valid = true;
			}
		}	
	}
	
	public boolean isSolvable() {
		boolean ret = this.isSolv;
		return ret;
	}
	
	public int moves() {
		int ret = this.moves;
		return ret;
	}
	
	public Iterable<Board> solution() {
		if (this.sol == null) { 
			return null;
		}
		else {
			return this.sol;
		}
	}
	public static void main(String[] args) {
		// create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        StdOut.print("\n");
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }

	}

}
