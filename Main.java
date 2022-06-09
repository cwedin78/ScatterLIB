import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import src.ScatterPlot;
import src.ScatterPlot.Point;
import src.ScatterPlot.Triangle;

public class Main {
    public static void main(String[] args) {
        List<Point> toAdd = new ArrayList<Point>();

        int N = 1000;
        int a = 0;
        int b = N * 3;

        Random random = new Random();
        List<Integer> randInt = random.ints(a, b).distinct().limit(N * 3).boxed().collect(Collectors.toList());

        for (int i = 0; i <= (N*3)-3 ; i += 3 ) {
            toAdd.add(new Point(randInt.toArray(new Integer[0])[i], randInt.toArray(new Integer[0])[i + 1], randInt.toArray(new Integer[0])[i + 2]));
        }
        

        System.out.println("Using " + toAdd.toArray().length + " points");

        ScatterPlot plot = new ScatterPlot(toAdd.toArray(new Point[0]));

        System.out.println(plot.Calculate(50,50));
    }
}