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

public class FastCollinearPoints {
    private List<LineSegment> segments = new LinkedList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Null argument");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("One of array elements is null");
        }
        checkShortArray(points);
        if (points.length < 4) return;

        //sort by y
        Point[] arr = new Point[points.length];
        System.arraycopy(points, 0, arr, 0, arr.length);
        Arrays.sort(arr);

        for (int i = 0; i < arr.length; i++) {
//            if (points[i] == null) throw new IllegalArgumentException("One of array elements is null");
            if (i > 0 && arr[i - 1].compareTo(arr[i]) == 0) throw new IllegalArgumentException("equal points");
            Point[] workingSet = new Point[arr.length];
            System.arraycopy(arr, 0, workingSet, 0, arr.length);
            mergeSortAndProcessArray(arr[i], workingSet);
        }
    }

    private void checkShortArray(Point[] points) {
        if ((points.length == 2 || points.length == 3) && points[0].compareTo(points[1]) == 0) throw new IllegalArgumentException("equal elements in array");
        if (points.length == 3 &&
                (points[0].compareTo(points[2]) == 0 || points[1].compareTo(points[2]) == 0)) throw new IllegalArgumentException("equal elements in array");
    }

    private void mergeSortAndProcessArray(Point basePoint, Point[] points) {
        //Make own merge-sort to preserve y-sorting
        Arrays.sort(points, basePoint.slopeOrder());
        processArray(basePoint, points);
    }

    private void processArray(Point basePoint, Point[] points) {
        //First point is point itself
        double lastSlope = basePoint.slopeTo(points[1]);
        double currentSlope;
        int startPoint = 1;
        int endPoint = 1;
        for (int i = 2; i < points.length; i++) {
            currentSlope = basePoint.slopeTo(points[i]);
            if (Double.compare(lastSlope, currentSlope) == 0){
                endPoint++;
            } else {
                if (endPoint - startPoint >= 2 && basePoint.compareTo(points[startPoint]) < 0)
                    addSegment(basePoint, points[endPoint]);
                lastSlope = currentSlope;
                startPoint = i;
                endPoint = i;
            }
        }
        if (endPoint - startPoint >= 2 && basePoint.compareTo(points[startPoint]) < 0){
            addSegment(basePoint, points[points.length - 1]);
        }
    }

    private void addSegment(Point p1, Point p2){
        segments.add(new LineSegment(p1, p2));
    }
    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments(){
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In("input1.txt");
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
