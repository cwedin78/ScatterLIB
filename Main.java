import src.ScatterPlot;
import src.ScatterPlot.Point;

public class Main {
    public static void main(String[] args) {
        ScatterPlot plot = new ScatterPlot();
        plot.AddPoints(
             new Point(3, 5, 5)
            ,new Point(1, 6, 5)
            ,new Point(2, 4, 5)
            ,new Point(2.2, 7, 5)
            ,new Point(2.5, 3, 5)
            ,new Point(4,4, 5)
        );
        System.out.println(plot.Calculate(3.3,1));
    }
}