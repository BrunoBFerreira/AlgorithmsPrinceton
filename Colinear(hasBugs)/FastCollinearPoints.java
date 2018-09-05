import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
	private LineSegment[] collinear = new LineSegment[0];
	private int numberCol = 0;

	public FastCollinearPoints(Point[] points) {
		// finds all line segments containing 4 or more points
		if (points == null) {
			throw new java.lang.IllegalArgumentException();
		}

		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) {
				throw new java.lang.IllegalArgumentException();
			}
		}
		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				if (points[i].compareTo(points[j]) == 0) {
					throw new java.lang.IllegalArgumentException();
				}
			}
		}

		LineSegment[] cols = new LineSegment[points.length];
		
		Point[] sorted = points.clone();
		
		for(int i = 0; i < points.length; i++){
			Comparator<Point> cp = points[i].slopeOrder();
			
			Arrays.sort(sorted, cp);
			int begin = 1;
			int end = 2;
			boolean col = true;
			
			while (end < sorted.length) {
				Point p0 = sorted[0];
				Point p1 = sorted[begin];
				Point p2 = sorted[end];
				if(p0.compareTo(p1) > 0){
					col = false;
				}
				if (Double.compare(p0.slopeTo(p1), p0.slopeTo(p2)) == 0) {
					if(p0.compareTo(p2) > 0){
						col = false;
					}
				}
				else{
					if((end-begin) >= 3 && col){
						Arrays.sort(sorted, begin, end);
						cols[numberCol++] = new LineSegment(sorted[0], sorted[end-1]);
					}
					begin = end;
					end = begin + 1;
					col = true;
				}
				end++;
			}

			
		}
		
		if(numberCol > 0){
			collinear = new LineSegment[numberCol];
			for(int i = 0; i < numberCol; i++){
				collinear[i] = cols[i];
			}
		}
		
	}

	public int numberOfSegments() {
		// the number of line segments
		return numberCol;
	}

	public LineSegment[] segments() {
		// the line segments
		LineSegment[] res = collinear.clone();
		return res;
	}
}