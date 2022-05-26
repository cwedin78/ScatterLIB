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
        public double b; // y intercept

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
            points = ScatterPlot.PointSort(A,B);
            m = (A.pos[1] - B.pos[1]) / (A.pos[0] - B.pos[0]);
            b = m * (-A.pos[0]) + A.pos[1];

            return this;
        }

        /**
         * Returns if line B collides with line A 
         * within line As point boundaries
         * @param A Line A
         * @param B Line B
         * @param Tol Tolerance
         * @returns True if lines do not collide 
         * within line As boundaries
         */
        public static boolean CheckCollision(Line A, Line B, double Tol) {
            if (A.m == B.m) { // Divide by 0 catch
                return ( 
                    (A.m * (A.points[0].pos[0]) + A.points[0].pos[1]) != 
                    (B.m * (B.points[0].pos[0]) + B.points[0].pos[1])
                );
            } else {
                return (
                    (
                        (A.b - B.b) / (B.m - A.m) <= (A.points[0].pos[0] + Tol) || 
                        (A.b - B.b) / (B.m - A.m) <= (B.points[0].pos[0] + Tol)
                    ) || (
                        (A.b - B.b) / (B.m - A.m) >= (A.points[1].pos[0] - Tol) || 
                        (A.b - B.b) / (B.m - A.m) >= (B.points[1].pos[0] - Tol)
                    )
                );
            }
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
    public static Point[] PointSort(Point... pointArr) {
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

        switch (inp.length) {
            case 1:
                return seq2d(inp[0]);
            
            case 2:
                return seq3d(inp[0], inp[1]);
        
            default:
                return 0;
        }
    }

    double seq3d(double x, double y) {
        List<Line> lines = new ArrayList<Line>();
        Point[] par = points.toArray(new Point[0]);

        /**
         * Term 2, parsing through all points 
         * from lowest x to highest x, and creating
         * lines in between
         */
        for (int i = 0; i < par.length - 1; i++) {
            lines.add(new Line(par[i], par[i+1]));
        }

        /** 
         * Term 2, parsing through all points from
         * each point from lowest x to highest x, and attempting
         * to create a line. If the created line crosses an existing
         * line it will be removed.
         */
        for (Point n : par) {
            System.out.println("n - (" + n.pos[0] + "," + n.pos[1] + ")");
            for (Point n2 : par) {
                System.out.println("n2 - (" + n2.pos[0] + "," + n2.pos[1] + ")");
                if (n != n2) {
                    Line temp = new Line(n, n2);
                    boolean allow = true;

                    for (Line l : lines.toArray(new Line[0])) {
                        if (!Line.CheckCollision(l, temp, 0.001)) {
                            allow = false;
                            System.out.println("y = " + temp.m + "x + " + temp.b + " - failed");
                        }
                    }

                    if (allow) {
                        lines.add(temp);
                        System.out.println("y = " + temp.m + "x + " + temp.b + " - success");
                    }
                }
            }
        }

        int i = 0;
        for (Line l : lines.toArray(new Line[0])) {
            System.out.println("y = " + l.m + "x + " + l.b);
            i += 1;
        }


        return lines.toArray(new Line[0])[1].m;
    }

    double seq2d(double x) {
        List<Line> lines = new ArrayList<Line>();
        Point[] par = (Point[]) points.toArray(new Point[0]);

        // Term 1, parsing through points from lowest 
        // x to highest x and drawing lines between
        for (int i = 0; i < par.length - 1; i++) {
            lines.add(new Line(par[i], par[i+1]));
        }

        double totSlope = 0;
        for (Line l : lines.toArray(new Line[0])) {totSlope += l.m;}
        double avSlope = (totSlope / lines.toArray().length);

        Line[] ls = lines.toArray(new Line[0]);
        double baseReturn = avSlope * (x - ls[0].points[0].pos[0]) + ls[0].points[0].pos[1];

        for (Line l : lines.toArray(new Line[0])) {
            if (x >= l.points[0].pos[0] && (x < l.points[1].pos[0])) {
                return l.m * (x - l.points[0].pos[0]) + l.points[0].pos[1];
            }
        }

        return baseReturn;
    }
}