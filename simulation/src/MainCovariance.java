import utils.DSU;
import utils.Models;

public class MainCovariance {

    public static final int ITERS = 1;
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
            double[][] expprod = new double[5][5];
            double[] exp = new double[5];

            int k = n / 6;
            double c = 1.0 / 3;
            for (int IT = 0; IT < ITERS; IT++) {
//                utils.Models.firstModel(dsu, n, k);
                Models.secondModel(dsu, n, k);
//                utils.Models.uniformModel(dsu, n, c / n);


                int[] count = new int[5];
                for (int i = 0; i < n; i++) {
                    if (dsu.get(i) == i && dsu.isTree[i] && dsu.size[i] <= 4) {
                        count[dsu.size[i]]++;
                    }
                }
                for (int i = 1; i <= 4; i++) {
                    for (int j = i; j <= 4; j++) {
                        expprod[i][j] += count[i] * count[j] * 1.0 / ITERS;
                    }
                    exp[i] += count[i] * 1.0 / ITERS;
                }
            }

            for (int i = 1; i <= MAX_SIZE; i++) {
                for (int j = i; j <= MAX_SIZE; j++) {
                    if (i != j) {
                        continue;
                    }
                    double disp = expprod[i][j] - exp[i] * exp[j];
                    System.err.printf("n = %d, i = %d, j = %d\nCov(T_i, T_j) = %.10f\n", n, i, j, disp);
                    double lambdaN = n * Math.pow(i, i - 2) * Math.pow(c, i - 1) * Math.exp(-i * c) / fact[i];
                    double varWN = lambdaN * (1 + (c - 1) * Math.pow(i * c, i - 1) * Math.exp(-i * c) / fact[i]);
                    System.err.println((varWN / lambdaN) - 1);
                    System.err.println("Estimated variance: " + varWN);
                }
            }
        }
    }

}
