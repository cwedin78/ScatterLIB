import src.ScatterPlot;
import src.ScatterPlot.Point;
import src.ScatterPlot.Triangle;

public class Main {
    public static void main(String[] args) {
        ScatterPlot plot = new ScatterPlot(
            new Point(0,1),
            new Point(1,2),
            new Point(2,0),
            new Point(3,4),
            new Point(4,3),
            new Point(0.5,4)
        );

        plot.Calculate(0,0);
    }
}