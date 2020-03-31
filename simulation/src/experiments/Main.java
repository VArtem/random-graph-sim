package experiments;

import utils.ds.DSU;
import utils.Models;
import utils.Utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static final int SIZE = 2;

    public static final int ITERS = 10000;

    public static void main(String[] args) {
        DSU dsu = new DSU(3000);

        List<Utils.DataPoint> result1 = new ArrayList<>();
        List<Utils.DataPoint> result2 = new ArrayList<>();

        for (int ITER = 1; ITER <= ITERS; ITER++) {
            System.err.println("Iteration " + ITER);
            int n = 500 + Utils.rng.nextInt(3000 - 500 + 1);
            int k = Utils.rng.nextInt(n + 1);
            Models.firstModel(dsu, n, k);
            double c1 = 0, c2 = 0;
            for (int i = 0; i < n; i++) {
                if (dsu.get(i) == i && dsu.isTree[i] && dsu.size[i] == SIZE) {
                    c1 += 1.0 * SIZE / n;
                }
            }

            Models.secondModel(dsu, n, k);
            for (int i = 0; i < n; i++) {
                if (dsu.get(i) == i && dsu.isTree[i] && dsu.size[i] == SIZE) {
                    c2 += 1.0 * SIZE / n;
                }
            }

            result1.add(new Utils.DataPoint(1.0 * k / n, c1));
            result2.add(new Utils.DataPoint(1.0 * k / n, c2));
        }
        try (PrintWriter out = new PrintWriter(SIZE + ".1.txt")) {
//            out.println("Model 1");
            Collections.sort(result1, Comparator.comparingDouble(o -> o.x));
            for (Utils.DataPoint dp : result1) {
                out.println(dp.x + " " + dp.y);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (PrintWriter out = new PrintWriter(SIZE + ".2.txt")) {
//            out.println("Model 2");
            Collections.sort(result2, Comparator.comparingDouble(o -> o.x));
            for (Utils.DataPoint dp : result2) {
                out.println(dp.x + " " + dp.y);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
