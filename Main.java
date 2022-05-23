import src.ScatterPlot;
import src.ScatterPlot.Point;

public class Main {
    public static void main(String[] args) {
        ScatterPlot plot = new ScatterPlot();
        plot.AddPoints(new Point(3, 5, 5));
        plot.AddPoints(new Point(0.25, 8, 5));
        plot.AddPoints(new Point(1, 6, 5));
        plot.AddPoints(new Point(.5, 1, 5));
        System.out.println(plot.Calculate(1, 3));
    }
}