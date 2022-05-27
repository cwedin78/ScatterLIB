import src.ScatterPlot;
import src.ScatterPlot.Point;

public class Main {
    public static void main(String[] args) {
        ScatterPlot plot = new ScatterPlot();
        plot.AddPoints(
             new Point(3, 5, 5)
            ,new Point(1, 6, 5)
            ,new Point(2, 3, 5)
            ,new Point(4, 8, 5)
            ,new Point(2.25, 5, 5)
        );
        System.out.println(plot.Calculate(3.3,1));
    }
}