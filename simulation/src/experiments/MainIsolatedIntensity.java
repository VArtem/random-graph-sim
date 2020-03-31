package experiments;

import utils.ds.DSU;
import utils.Models;

public class MainIsolatedIntensity {

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

        for (int n : new int[] {250, 500, 1000, 2000, 4000}) {
            double avgIsolatedProbabilty = 0;

            int k = n / 4;
            double c = 1.0 / 3;
            for (int IT = 0; IT < ITERS; IT++) {
                double[] p = Models.firstModel(dsu, n, k);
//                utils.Models.secondModel(dsu, n, k);


                for (int i = 0; i < n; i++) {
                    if (dsu.get(i) == i && dsu.size[i] == 1) {
                        avgIsolatedProbabilty += p[i] / ITERS;
                    }
                }
            }
            System.err.printf("n = %d, k = %d, isolated probability = %f\n", n, k, avgIsolatedProbabilty);

        }
    }

}
