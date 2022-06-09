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
        int sample = 1;
        int[] speeds = new int[sample];
        for (int s = 0; s < sample; s++) {
            List<Point> toAdd = new ArrayList<Point>();

            int N = 450;
            int a = 0;
            int b = N * 3;

            Random random = new Random();
            List<Integer> randInt = random.ints(a, b).distinct().limit(N * 3).boxed().collect(Collectors.toList());

            for (int i = 0; i <= (N*3)-3 ; i += 3 ) {
                toAdd.add(new Point(randInt.toArray(new Integer[0])[i], randInt.toArray(new Integer[0])[i + 1], randInt.toArray(new Integer[0])[i + 2]));
            }
             
            ScatterPlot plot = new ScatterPlot(toAdd.toArray(new Point[0]));
            speeds[s] = plot.TrimeshSpeed;
        }

        Arrays.sort(speeds);

        int total = 0;
        for (int i : speeds) {
            System.out.print(i + ", ");
            total += i;
        }

        System.out.println("Mean = " + total/sample);
    }
}