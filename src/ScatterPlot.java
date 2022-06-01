package src;

import java.util.ArrayList;
import java.util.List;

public class ScatterPlot {

    List<Point> points = new ArrayList<Point>();

    /*
     * Used to represent a point for the scatterplot lib
     */
    public static class Point {
        public double[] p;
        public final int dim;
        /*
         * Creates a new point in the dimension equal to the
         * number of values given
         * @param vals X, Y, Z... coords
         */
        public Point(double... vals) {
            p = vals;
            dim = vals.length;
        }
    }

    /*
     * A class holding information for a 2d line, including points and slopes.
     */
    public static class Line {
        public Point[] points = {null,null};
        public double m; // slope
        public double b; // y intercept

        /*
         * Creates a new line. The first two given values 
         * of the points will be used in data
         * @param A Point A
         * @param B Point B
         */
        public Line(Point A, Point B) {set(A, B);}

        /*
         * Sets existing parameters of a line
         * @param A Point A
         * @param B Point B
         * @return self
         */
        public Line set(Point A, Point B) {
            points = ScatterPlot.PointSort(A,B);
            m = (A.p[1] - B.p[1]) / (A.p[0] - B.p[0]);
            b = m * (-A.p[0]) + A.p[1];

            return this;
        }

        /*
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
                    (A.m * (A.points[0].p[0]) + A.points[0].p[1]) != 
                    (B.m * (B.points[0].p[0]) + B.points[0].p[1])
                );
            } else {
                return (
                    (
                        (A.b - B.b) / (B.m - A.m) <= (A.points[0].p[0] + Tol) || 
                        (A.b - B.b) / (B.m - A.m) <= (B.points[0].p[0] + Tol)
                    ) || (
                        (A.b - B.b) / (B.m - A.m) >= (A.points[1].p[0] - Tol) || 
                        (A.b - B.b) / (B.m - A.m) >= (B.points[1].p[0] - Tol)
                    )
                );
            }
        }

        /*
         * Checks if two lines meet at a point
         * @param A Line A
         * @param B Lime B
         * @returns the point at which the
         * lines meet, if the lines do not
         * meet, null is returned.
         */
        public static Point CheckCommonPoint(Line A, Line B) {
            for (Point pa : A.points) {
                for (Point pb : B.points) {
                    if(pa == pb) {return pa;}
                }
            }

            return null;
        }

        /**
         * Checks if a given point is over the line.
         * @return true if point is over line.
         */
        public boolean CheckOver(Point point) {
            return (point.p[1] >= m * point.p[0] + b);
        }
    }

    /*
     * A class holding all information to
     * define a Triangle, including points,
     * lines, and boundaries.
     */
    public static class Triangle {
        //                         A    B    C
        public Point[] points = {null,null,null};
        //                      AB   AC   BC
        public Line[] lines = {null,null,null};

        /*
         * Creates a new triangle with defined points,
         * and assumed lines
         * @param A Point A
         * @param B Point B
         * @param C Point C
         */
        public Triangle(Point A, Point B, Point C) {
            points = ScatterPlot.PointSort(A,B,C);
            lines[0] = new Line(points[0], points[2]);
            lines[1] = new Line(points[0], points[1]);
            lines[2] = new Line(points[2], points[1]);
        }

        /*
         * Checks if a given point is within
         * the boundaries of the triangle 
         * @returns true if the point is inside the triangle
         */
        public boolean CheckBoundaries(Point point) {

            double Ax = points[0].p[0]; double Ay = points[0].p[1]; 
            double Bx = points[2].p[0]; double By = points[2].p[1]; 
            double Cx = points[1].p[0]; double Cy = points[1].p[1]; 
            Line AB = lines[0]; Line AC = lines[1]; Line BC = lines[2];
            boolean Cstate =  Cy > ( (Ay - By) / (Ax - Bx) ) * (Cx - Ax) + Ay; // true if C is over A and B

            // TODO TEST!!!!!

            if (Cstate) {
                return (AB.CheckOver(point) && !AC.CheckOver(point) && !BC.CheckOver(point));
                // Over AB, under AC, BC
            } else {
                // Under AB, Over AC, BC
                return (!AB.CheckOver(point) && AC.CheckOver(point) && BC.CheckOver(point));
            }
        }

        
    }

    /*
     * Creates a scatterplot of any dimension
     * @param Points Any number of points to be initially plotted within
     * the scatterplot
     */
    public ScatterPlot(Point... Points) {
        for (Point p : PointSort(Points)) {points.add(p);}
    }


    /*
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

    /*
     * Sorts given points from lowest X to highest X. (N^2)
     * @param pointArr Array of given points
     * @return pointArr of sorted points
     * @see hehe stolen from stackoverflow
     */
    public static Point[] PointSort(Point... pointArr) {
        for(double j=0; j<pointArr.length-1; j++){
            for(int i=0; i<pointArr.length-1; i++){
                if (pointArr[i].p[0] > pointArr[i+1].p[0]){
                    Point temp = pointArr[i+1];
                    pointArr[i+1] = pointArr[i];
                    pointArr[i] = temp;
                }
            }
        }

        return pointArr;
    }

    /*
     * Assumes an output based upon given data
     * at the given pition
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
        List<Triangle> triangles = new ArrayList<Triangle>();
        Point[] par = points.toArray(new Point[0]);

        /**
         * Term 2, parsing through all points 
         * from lowest x to highest x, and creating
         * lines in between
         */
        for (int i = 0; i < par.length - 1; i++) {
            lines.add(new Line(par[i], par[i+1]));
        }

        /*
         * Term 2, parsing through all points from
         * each point from lowest x to highest x, and attempting
         * to create a line. If the created line crosses an existing
         * line it will be removed.
         */
        for (Point n : par) {
            System.out.println("n - (" + n.p[0] + "," + n.p[1] + ")");
            for (Point n2 : par) {
                System.out.println("n2 - (" + n2.p[0] + "," + n2.p[1] + ")");
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

        for (Line l : lines.toArray(new Line[0])) {
            System.out.println("y = " + l.m + "x + " + l.b);
        }

        /*
         * Triangle identification
         * 
         * Rules are as follows:
         *  line A must share a point with line B, line C must not share
         *  the same point like A and B share, and line C must share a point
         *  with line A and line B.
         * 
         * TODO BUG: a triangle can be created when a point is inside it:
         *         Created new triangle via - (1.0,6.0) (2.0,4.0) (2.2,7.0)
         *         Created new triangle via - (2.0,4.0) (2.2,7.0) (2.5,3.0)
         *         Created new triangle via - (2.2,7.0) (2.5,3.0) (3.0,5.0)
         *         Created new triangle via - (2.2,7.0) (2.5,3.0) (4.0,4.0)
         *         Created new triangle via - (2.5,3.0) (3.0,5.0) (4.0,4.0)
         *         Created new triangle via - (2.2,7.0) (3.0,5.0) (4.0,4.0)
         */
        for (Line A : lines.toArray(new Line[0])) {
            for (Line B : lines.toArray(new Line[0])) {
                if (A != B) {
                    Point AB = Line.CheckCommonPoint(A, B);
                    if (AB != null) {
                        for (Line C : lines.toArray(new Line[0])) {
                            if (C != A && C != B) {
                                Point AC = Line.CheckCommonPoint(A,C);
                                Point BC = Line.CheckCommonPoint(B,C);
                                if (AC != null && BC != null
                                && AC != AB && BC != AB
                                ) {
                                    Triangle t = new Triangle(AB, AC, BC);

                                    boolean allow = true;
                                    for (Triangle tri : triangles.toArray(new Triangle[0])) {
                                        if (
                                            (
                                                t.points[0].p == tri.points[0].p &&
                                                t.points[1].p == tri.points[1].p &&
                                                t.points[2].p == tri.points[2].p
                                            )
                                        ) {
                                            allow = false;
                                        }
                                    }

                                    if (allow) {
                                        triangles.add(t);
                                        System.out.println("Created new triangle via - (" +
                                        t.points[0].p[0] + "," + t.points[0].p[1] + ") (" +
                                        t.points[1].p[0] + "," + t.points[1].p[1] + ") (" +
                                        t.points[2].p[0] + "," + t.points[2].p[1] + ")");
                                    }
                                }
                            }
                        }
                    }
                }
            } 
        }


        return 0;
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
        double baseReturn = avSlope * (x - ls[0].points[0].p[0]) + ls[0].points[0].p[1];

        for (Line l : lines.toArray(new Line[0])) {
            if (x >= l.points[0].p[0] && (x < l.points[1].p[0])) {
                return l.m * (x - l.points[0].p[0]) + l.points[0].p[1];
            }
        }

        return baseReturn;
    }
}
