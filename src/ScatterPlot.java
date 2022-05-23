package src;

import java.util.ArrayList;
import java.util.List;

public class ScatterPlot {

    List<Point> points = new ArrayList<Point>();

    /**
     * Used to represent a point for the scatterplot lib
     */
    public static class Point {
        public double[] pos;
        public final int dim;
        /**
         * Creates a new point in the dimension equal to the
         * number of values given
         * @param vals X, Y, Z... coords
         */
        public Point(double... vals) {
            pos = vals;
            dim = vals.length;
        }
    }

    /**
     * A class holding information for a 2d line, including points and slopes.
     */
    public static class Line {
        public Point[] points = {null,null};
        public double m; // slope

        /**
         * Creates a new line. The first two given values 
         * of the points will be used in data
         * @param A Point A
         * @param B Point B
         */
        public Line(Point A, Point B) {set(A, B);}

        /**
         * Sets existing parameters of a line
         * @param A Point A
         * @param B Point B
         * @return self
         */
        public Line set(Point A, Point B) {
            points[0] = A; points[1] = B;
            m = (A.pos[1] - B.pos[1]) / (A.pos[0] - B.pos[0]);
            return this;
        }
    }

    /**
     * Creates a scatterplot of any dimension
     * @param Points Any number of points to be initially plotted within
     * the scatterplot
     */
    public ScatterPlot(Point... Points) {
        for (Point p : PointSort(Points)) {points.add(p);}
    }


    /**
     * Adds points to the existing scatterplot
     * @param Points Any number of points to be plotted 
     * within the scatterplot
     */
    public void AddPoints(Point... Points) {
        for (Point p : Points) {points.add(p);}
        Point[] par = PointSort(points.toArray(new Point[0]));
        points.clear();
        for (Point p : par) {points.add(p);}
    }

    /**
     * Sorts given points from lowest X to highest X. (N^2)
     * @param pointArr Array of given points
     * @return pointArr of sorted points
     * @see hehe stolen from stackoverflow
     */
    public Point[] PointSort(Point[] pointArr) {
        for(double j=0; j<pointArr.length-1; j++){
            for(int i=0; i<pointArr.length-1; i++){
                if (pointArr[i].pos[0] > pointArr[i+1].pos[0]){
                    Point temp = pointArr[i+1];
                    pointArr[i+1] = pointArr[i];
                    pointArr[i] = temp;
                }
            }
        }

        return pointArr;
    }

    /**
     * Assumes an output based upon given data
     * at the given position
     * @param inp Input coordinated to assume an output at,
     * if too many are given, only needed items are taken.
     * @return double of assumed calculation
     */
    public double Calculate(double... inp) {

        switch ( ((Point) points.toArray()[0]) .pos. length) {
            case 2:
                break;
            
            case 3:
                return seq3d(inp[0], inp[1]);
        
            default:
                return 0;
        }

        return 0;
    }

    double seq3d(double x, double y) {
        List<Line> lines = new ArrayList<Line>();
        Point[] par = (Point[]) points.toArray(new Point[0]);

        // Term 1, parsing through points from lowest 
        // x to highest x and drawing lines between
        for (int i = 0; i < par.length - 1; i++) {
            lines.add(new Line(par[i], par[i+1]));
        }

        return lines.toArray(new Line[0])[1].m;
    }
}