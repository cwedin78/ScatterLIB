import src.ScatterPlot;
import src.ScatterPlot.Point;
import src.ScatterPlot.Triangle;

public class Main {
    public static void main(String[] args) {
        Triangle trigle = new Triangle(
            new Point(0,0), new Point(1, 1), new Point(2, 0)
        );

        System.out.println(ScatterPlot.Triangle.CheckBoundaries(trigle, new Point(0.5, 0.5)));
    }
}