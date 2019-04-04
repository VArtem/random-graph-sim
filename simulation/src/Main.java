import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static final int SIZE = 2;

    public static final int ITERS = 10000;
    public static Random rng = new Random(123);

    public static void main(String[] args) {
        DSU dsu = new DSU(3000);

        List<Models.DataPoint> result1 = new ArrayList<>();
        List<Models.DataPoint> result2 = new ArrayList<>();

        for (int ITER = 1; ITER <= ITERS; ITER++) {
            System.err.println("Iteration " + ITER);
            int n = rng.nextInt(3000 - 500 + 1) + 500;
            int k = rng.nextInt(n + 1);
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

            result1.add(new Models.DataPoint(1.0 * k / n, c1));
            result2.add(new Models.DataPoint(1.0 * k / n, c2));
        }
        try (PrintWriter out = new PrintWriter(SIZE + ".1.txt")) {
//            out.println("Model 1");
            Collections.sort(result1, Comparator.comparingDouble(o -> o.x));
            for (Models.DataPoint dp : result1) {
                out.println(dp.x + " " + dp.y);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (PrintWriter out = new PrintWriter(SIZE + ".2.txt")) {
//            out.println("Model 2");
            Collections.sort(result2, Comparator.comparingDouble(o -> o.x));
            for (Models.DataPoint dp : result2) {
                out.println(dp.x + " " + dp.y);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
