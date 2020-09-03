package experiments;

import bipartite.ds.DSU;
import probability.Models;

public class MainMaxComponent {

    public static final int ITERS = 10000;
    public static final int MAX_N = 4000;
    private static final int MAX_SIZE = 4;

    public static void main(String[] args) {
        DSU dsu = new DSU(MAX_N);
        double[] fact = new double[MAX_SIZE  + 1];
        fact[0] = 1;
        for (int i = 1; i <= MAX_SIZE; i++) {
            fact[i] = fact[i - 1] * i;
        }

        for (int n : new int[] {10, 20, 40, 80, 250, 500, 1000, 2000, 4000}) {
            double avgMaxComponent = 0;
            double avgMaxComponentProb = 0;


            int k = (int) (n / 3.9);
            double c = 1.0 / 3;
            for (int IT = 0; IT < ITERS; IT++) {
                double[] p = Models.firstModel(dsu, n, k);

//                utils.Models.secondModel(dsu, n, k);

                int maxCompId = -1;
                for (int i = 0; i < n; i++) {
                    if (dsu.get(i) == i) {
                        if (maxCompId == -1 || dsu.size[i] > dsu.size[maxCompId]) {
                            maxCompId = i;
                        }
                    }
                }
                avgMaxComponent += (double) dsu.size[maxCompId] / ITERS;
                for (int i = 0; i < n; i++) {
                    if (dsu.get(i) == maxCompId) {
                        avgMaxComponentProb += p[i] / ITERS;
                    }
                }

            }
            System.err.printf("n = %d, k = %d\nmax component size = %f\nmax component probability = %f\n", n, k, avgMaxComponent, avgMaxComponentProb);

        }
    }

}
