import utils.DSU;
import utils.Models;
import utils.Utils;

import java.util.Arrays;

public class MainConnectedness {

    public static final int ITERS = 10;
    public static final int MAX_N = 4000;
    private static final int MAX_SIZE = 4;

    public static void main(String[] args) {

        for (int n : new int[]{10, 20, 40, 80, 250, 500, 1000, 2000, 4000}) {

            DSU dsu = new DSU(n);
            double avgConnectednessMoment = 0;
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

            for (int IT = 0; IT < ITERS; IT++) {
                dsu.reset();
                double[] p = Utils.dirichlet(n);
                for (int i = 1; ; i++) {
                    int u = Utils.getFromDistribution(p);
                    int v = Utils.getFromDistribution(p);
                    dsu.addEdge(u, v);
                    if (dsu.sets == 1) {
                        avgConnectednessMoment += (double) i / ITERS;
                        min = Math.min(min, i);
                        max = Math.max(max, i);
                        break;
                    }
                }

            }
            System.err.printf("n = %d, edges until connected = %f\n", n, avgConnectednessMoment);
            System.err.printf("min = %d, max = %d\n", min, max);

        }
    }

}
