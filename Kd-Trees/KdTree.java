import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
	private Node tree;
	private int size;
	
	private static class Node {
		private final Point2D point;
		private final RectHV rect;
		private final boolean vertical;
		private Node left;
		private Node right;
		
		public Node(Point2D p, double xmin, double ymin, double xmax, double ymax, boolean vert) {
			this.point = p;
			this.rect = new RectHV(xmin, ymin, xmax, ymax);
			this.vertical = vert;
			this.left = null;
			this.right = null;
		}
		
		public Point2D getPoint() {
			return this.point;
		}
		
		public RectHV getRect() {
			return this.rect;
		}
		
		public boolean isVertical() {
			return this.vertical;
		}
		
		public Node getLeft() {
			return this.left;
		}
		
		public Node getRight() {
			return this.right;
		}
		
		public void setLeft(Node le) {
			this.left = le;
		}
		
		public void setRight(Node r) {
			this.right = r;
		}
	}
	
	public KdTree() {
		this.tree = null;
		this.size = 0;
	}
	
	public boolean isEmpty() {
		return this.tree == null;
	}
	
	public int size() {
		return this.size;
	}
	
	public void insert(Point2D p) {
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		else {
			if (this.isEmpty()) {
				Node root = new Node(p, 0.0, 0.0, 1.0, 1.0, true);
				this.tree = root;
				this.size++;
			}
			else {
				put(p, this.tree);
			}
		}
	}
	
	private void put(Point2D p, Node n) {
		if (!n.getPoint().equals(p)) {
			RectHV r = n.getRect();
			if (n.isVertical()) {
				if (p.x() >= n.getPoint().x()) {
					if (n.getRight() != null) {
						put(p, n.getRight());
					}
					else {
						Node nn = new Node(p, n.getPoint().x(), r.ymin(), r.xmax(), r.ymax(), false);
						n.setRight(nn);
						this.size++;
					}
				}
				else {
					if (n.getLeft() != null) {
						put(p, n.getLeft());
					}
					else {
						Node nn = new Node(p, r.xmin(), r.ymin(), n.getPoint().x(), r.ymax(), false);
						n.setLeft(nn);
						this.size++;
					}
				}
			}
			else {
				if (p.y() >= n.getPoint().y()) {
					if (n.getRight() != null) {
						put(p, n.getRight());
					}
					else {
						Node nn = new Node(p, r.xmin(), n.getPoint().y(), r.xmax(), r.ymax(), true);
						n.setRight(nn);
						this.size++;
					}
				}
				else {
					if (n.getLeft() != null) {
						put(p, n.getLeft());
					}
					else {
						Node nn = new Node(p, r.xmin(), r.ymin(), r.xmax(), n.getPoint().y(), true);
						n.setLeft(nn);
						this.size++;
					}
				}
			}
		}
	}
	
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		else {
			if (this.isEmpty()) {
				return false;
			}
			else {
				return get(p, this.tree);
			}
		}
	}
	
	private boolean get(Point2D p, Node n) {
		if (n == null) {
			return false;
		}
		else if (n.getPoint().equals(p)) {
			return true;
		}
		else {
			if (n.isVertical()) {
				if (p.x() >= n.getPoint().x()) {
					return get(p, n.getRight());
				}
				else {
					return get(p, n.getLeft());
				}
			}
			else {
				if (p.y() >= n.getPoint().y()) {
					return get(p, n.getRight());
				}
				else {
					return get(p, n.getLeft());
				}
			}
		}
	}
	
	public void draw() {
		if (this.tree != null) {
			drawMe(this.tree);
		}
	}
	
	private void drawMe(Node n) {
		if (n != null) {
			if (n.isVertical()) {
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.setPenRadius(0.01);
				StdDraw.point(n.getPoint().x(), n.getPoint().y());
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.setPenRadius();
				StdDraw.line(n.getPoint().x(), n.getRect().ymin(), n.getPoint().x(), n.getRect().ymax());
				
				drawMe(n.getLeft());

				drawMe(n.getRight());

			}
			else {
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.setPenRadius(0.01);
				StdDraw.point(n.getPoint().x(), n.getPoint().y());
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.setPenRadius();
				StdDraw.line(n.getRect().xmin(), n.getPoint().y(), n.getRect().xmax(), n.getPoint().y());
				
				drawMe(n.getLeft());
				drawMe(n.getRight());
			}
		}
	}
	
	public Iterable<Point2D> range (RectHV rect) {
		if (rect == null) {
			throw new java.lang.IllegalArgumentException();
		}
		else {	
			ArrayList<Point2D> prange = new ArrayList<Point2D>();
			
			if (this.tree == null) {
				return null;
			}
			else {
				return checkRange(rect, this.tree, prange);
			}
		}
	}
	
	private ArrayList<Point2D> checkRange(RectHV rect, Node n, ArrayList<Point2D> li){
		if (n == null) {
			return li;
		}
		else {
			ArrayList<Point2D> laux = li;
			if (rect.contains(n.getPoint())) {
				laux.add(n.getPoint());
			}
			if (n.getLeft() != null && rect.intersects(n.getLeft().getRect())) {
				laux = checkRange(rect, n.getLeft(), laux);
			}
			if (n.getRight() != null && rect.intersects(n.getRight().getRect())) {
				laux = checkRange(rect, n.getRight(), laux);
			}
			
			return laux;
		}
	}
	
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		else {
			if (this.isEmpty()) {
				return null;
			}
			else {
				return checkNearest(p, this.tree, Double.POSITIVE_INFINITY, null);
			}
		}
	}
	
	private Point2D checkNearest(Point2D p, Node n, double dist, Point2D nearest) {
		double currentDist = n.getPoint().distanceSquaredTo(p);
		if (currentDist < dist) {
			nearest = n.getPoint();
			dist = currentDist;
		}
		if (n.getLeft() != null && n.getLeft().getRect().distanceSquaredTo(p) < dist) {
			nearest = checkNearest(p, n.getLeft(), dist, nearest);
			dist = nearest.distanceSquaredTo(p);
		}
		if (n.getRight() != null && n.getRight().getRect().distanceSquaredTo(p) < dist) {
			nearest = checkNearest(p, n.getRight(), dist, nearest);
		}
		
		return nearest;

	}
	
	public static void main(String[] args) {
		Point2D p0 = new Point2D(0.5, 0.5);
		Point2D p1 = new Point2D(0.75, 0.5);
		Point2D p2 = new Point2D(0.25, 0.45);
		Point2D p3 = new Point2D(0.8, 0.65);
		
		KdTree k = new KdTree();
		k.insert(p0);
		k.insert(p1);
		k.insert(p2);
		k.insert(p3);
		
		StdOut.println(k.contains(p0));
		StdOut.println(k.contains(p1));
		StdOut.println(k.contains(p2));
		StdOut.println(k.contains(p3));
		StdOut.println(k.contains(new Point2D(0.9, 0.9)));
	}
}
