import src.ScatterPlot;
import src.ScatterPlot.Point;
import src.ScatterPlot.Triangle;

public class Main {
    public static void main(String[] args) {
        ScatterPlot plot = new ScatterPlot(
            new Point(0,1,3),
            new Point(1,2,7),
            new Point(2,0,9),
            new Point(3,4,1),
            new Point(4,3,3),
            new Point(0.5,4,7)
        );

        System.out.println(plot.Calculate(0.5,1));
    }
}