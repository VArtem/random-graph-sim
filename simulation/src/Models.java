public class Models {
    static void secondModel(DSU dsu, int n, int k) {
        dsu.reset();
        double[] p = dirichlet(n);
        for (int i = 0; i < n;i++) {
            for (int j = i; j < n; j++) {
                double prob;
                if (i != j) {
                    prob = 2 * p[i] * p[j] * k;
                } else { // i == j
                    prob = p[i] * p[i] * k;
                }
                if (Main.rng.nextDouble() < prob) {
                    dsu.addEdge(i, j);
                }
            }
        }
    }

    static void firstModel(DSU dsu, int n, int k) {
        dsu.reset();
        double[] p = dirichlet(n);
        for (int i = 0; i < k; i++) {
            int u = getFromDistribution(p);
            int v = getFromDistribution(p);
            dsu.addEdge(u, v);
        }
    }

    public static double[] dirichlet(int n) {
        double[] result = new double[n];
        double sum = 0;
        for (int i = 0; i < n; i++) {
            result[i] = exp();
            sum += result[i];
        }
        for (int i = 0; i < n; i++) {
            result[i] /= sum;
        }
        return result;
    }

    private static double exp() {
        return -Math.log(Main.rng.nextDouble());
    }

    private static int getFromDistribution(double[] p) {
        double value = Main.rng.nextDouble();
        for (int i = 0; i < p.length; i++) {
            if (value <= p[i]) {
                return i;
            }
            value -= p[i];
        }
        throw new AssertionError();
    }

    static class DataPoint {
        double x, y;

        public DataPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
