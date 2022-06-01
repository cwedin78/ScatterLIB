import src.ScatterPlot;
import src.ScatterPlot.Point;
import src.ScatterPlot.Triangle;

public class Main {
    public static void main(String[] args) {
        Triangle trigle = new Triangle(
            new Point(0,0), new Point(1, 1), new Point(2, 0)
        );

        double y = 0.8;
        for (int i = 0; i < 50; i++) {
            System.out.println("Testing point at - (" + 0.1 * i + "," + y + ") - " + trigle.CheckBoundaries(new Point(0.1 * i, y)));
        }
    }
}