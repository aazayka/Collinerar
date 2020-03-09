/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] segments;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Null argument");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("One of array elements is null");
        }
        checkShortArray(points);

        List<LineSegment> tempSegments = new LinkedList<>();
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                if (points[j].compareTo(points[i]) == 0) throw new IllegalArgumentException("equal elements in array");
                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[k].compareTo(points[i]) == 0) throw new IllegalArgumentException("equal elements in array");
                    if (points[k].compareTo(points[j]) == 0) throw new IllegalArgumentException("equal elements in array");
                    for (int l = k + 1; l < points.length; l++) {
                        if (points[l].compareTo(points[i]) == 0) throw new IllegalArgumentException("equal elements in array");
                        if (points[l].compareTo(points[j]) == 0) throw new IllegalArgumentException("equal elements in array");
                        if (points[l].compareTo(points[k]) == 0) throw new IllegalArgumentException("equal elements in array");
                        if (check(points[i], points[j], points[k], points[l])) {
                            tempSegments.add(buildSegment(points[i], points[j], points[k],
                                                          points[l]));
                        }
                    }
                }
            }
        }
        segments = tempSegments.toArray(new LineSegment[0]);
    }

    private void checkShortArray(Point[] points) {
        if ((points.length == 2 || points.length == 3) && points[0].compareTo(points[1]) == 0) throw new IllegalArgumentException("equal elements in array");
        if (points.length == 3 &&
                (points[0].compareTo(points[2]) == 0 || points[1].compareTo(points[2]) == 0)) throw new IllegalArgumentException("equal elements in array");
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    private boolean check(Point p0, Point p1, Point p2, Point p3){
        double slope1 = p0.slopeTo(p1);
        double slope2 = p0.slopeTo(p2);
        double slope3 = p0.slopeTo(p3);
        return Double.compare(slope1, slope2) == 0
                && Double.compare(slope1, slope3) == 0;
    }

    private LineSegment buildSegment(Point p0, Point p1, Point p2, Point p3){
        Point[] arr  = new Point[]{p0, p1, p2, p3};
        Arrays.sort(arr);
        return new LineSegment(arr[0], arr[3]);
    }
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segments.length];
        System.arraycopy(segments, 0, result, 0, segments.length);
        return result;
    }
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In("notpassed.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        //System.out.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
