/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        List<LineSegment> segments = new LinkedList<>();
        LineSegment[] x = segments.toArray(new LineSegment[0]);
        for (LineSegment segment : x) {
            StdOut.println(segment);
            segment.draw();
        }

    }
}
