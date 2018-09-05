import java.util.ArrayList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	
	private final TreeSet<Point2D> points;
	private int size;
	
	public PointSET() {
		this.points = new TreeSet<Point2D>();
		this.size = 0;
	}
	
	public boolean isEmpty() {
		return this.points.isEmpty();
	}
	
	public void insert(Point2D p) {
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		else  {
			if (!points.contains(p)) {
				this.points.add(p);
				this.size++;
			}
		}
	}
	
	public int size() {
		return this.size;
	}
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		else {
			return this.points.contains(p);
		}
	}
	
	public void draw() {
		if (!this.isEmpty()) {
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(0.01);
			for (Point2D p : this.points) {
				StdDraw.point(p.x(), p.y());
			}
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new java.lang.IllegalArgumentException();
		}
		else {
			ArrayList<Point2D> prange = new ArrayList<Point2D>();

			for (Point2D p : this.points) {
				if (rect.contains(p)) {
					prange.add(p);
				}
			}

			return prange;
		}
	}
	
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		else {
			double dist = Double.POSITIVE_INFINITY;
			double currentDist = 0;
			Point2D ret = null;
			if (this.isEmpty()) {
				return null;
			}
			else {
				for (Point2D neighbour : this.points) {
					currentDist = neighbour.distanceSquaredTo(p);
					if (currentDist < dist) {
						dist = currentDist;
						ret = neighbour;
					}
				}
				
				return ret;
			}
		}
	}
}
