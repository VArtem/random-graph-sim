package probability;

import java.util.Random;

public class Utils {

    public static final Random rng = new Random(123);

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
        return -Math.log(rng.nextDouble());
    }

    public static int getFromDistribution(double[] p) {
        double value = rng.nextDouble();
        for (int i = 0; i < p.length; i++) {
            if (value <= p[i]) {
                return i;
            }
            value -= p[i];
        }
        throw new AssertionError();
    }

    public static class DataPoint {
        public double x, y;

        public DataPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
