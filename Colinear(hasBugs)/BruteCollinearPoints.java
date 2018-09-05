import java.util.Arrays;

public class BruteCollinearPoints {
	private LineSegment[] collinear = new LineSegment[0];
	private int numberCol = 0;
	
	public BruteCollinearPoints(Point[] points){
		if(points == null){
			throw new java.lang.IllegalArgumentException();
		}
		
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) {
				throw new java.lang.IllegalArgumentException();
			}
		}
		for(int i = 0; i < points.length; i++){
			for(int j = i+1; j < points.length; j++){
				if(points[i].compareTo(points[j]) == 0){
					throw new java.lang.IllegalArgumentException();				}
			}
		}
		Point[] sorted = points.clone();
		Arrays.sort(sorted);
		LineSegment[] cols = new LineSegment[sorted.length*sorted.length];
		// finds all line segments containing 4 points
		for(int i = 0; i < sorted.length; i++){
			Point p0 = sorted[i];
			for(int j = i+1; j < sorted.length; j++){
				Point p1 = sorted[j];
				for(int k = j+1; k < sorted.length; k++){
					Point p2 = sorted[k];
					for (int l = k+1; l< sorted.length; l++){
						Point p3 = sorted[l];
						if(Double.compare(p0.slopeTo(p1), p0.slopeTo(p2)) == 0 
								&& Double.compare(p0.slopeTo(p1), p0.slopeTo(p3)) == 0){
							LineSegment ls = new LineSegment(p0, p3);
							cols[numberCol++] = ls;
						}
					}
				}	
			}
		}
		
		if(numberCol > 0){
			collinear = new LineSegment[numberCol];
			for(int i = 0; i < numberCol; i++){
				collinear[i] = cols[i];
			}
		}
			
		
	}
	public           int numberOfSegments() {
		// the number of line segments
		return numberCol;
	}
	public LineSegment[] segments()  {
		// the line segments
		LineSegment[] res = collinear.clone();
		return res;
	}
}