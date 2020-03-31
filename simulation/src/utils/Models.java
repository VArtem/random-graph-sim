package utils;

import utils.ds.DSU;

public class Models {
    public static double[] secondModel(DSU dsu, int n, int k) {
        dsu.reset();
        double[] p = Utils.dirichlet(n);
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                double prob;
                if (i != j) {
                    prob = 2 * p[i] * p[j] * k;
                } else { // i == j
                    prob = p[i] * p[i] * k;
                }
                if (Utils.rng.nextDouble() < prob) {
                    dsu.addEdge(i, j);
                }
            }
        }
        return p;
    }

    public static double[] firstModel(DSU dsu, int n, int k) {
        dsu.reset();
        double[] p = Utils.dirichlet(n);
        for (int i = 0; i < k; i++) {
            int u = Utils.getFromDistribution(p);
            int v = Utils.getFromDistribution(p);
            dsu.addEdge(u, v);
        }
        return p;
    }

    public static void uniformModel(DSU dsu, int n, double v) {
        dsu.reset();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Utils.rng.nextDouble() < v) {
                    dsu.addEdge(i, j);
                }
            }
        }
    }

    public static double[] thirdModel(DSU dsu, int n, int k) {
        dsu.reset();
        double[] p = Utils.dirichlet(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double prob = 2 * p[i] * p[j] * k / (1 + 2 * p[i] * p[j] * k);
                if (Utils.rng.nextDouble() < prob) {
                    dsu.addEdge(i, j);
                }
            }
        }
        return p;
    }
}
