package utils;

import java.util.Random;

public class Dirichlet {
    public double[] p;
    public double[] prefix;

    public Dirichlet(int n, Random rng) {
        p = new double[n];
        prefix = new double[n];
        double sum = 0;
        for (int i = 0; i < n; i++) {
            p[i] = -Math.log(rng.nextDouble());
            sum += p[i];
        }
        for (int i = 0; i < n; i++) {
            p[i] /= sum;
            prefix[i] = p[i];
            if (i > 0) {
                prefix[i] += prefix[i - 1];
            }
        }
    }

    public int sampleVertex(Random rng) {
        return findPos(rng.nextDouble());
    }

    private int findPos(double value) {
        int left = -1, right = p.length;
        while (left < right - 1) {
            int mid = (left + right) >> 1;
            if (prefix[mid] >= value) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    public void makeUniform() {
        for (int i = 0; i < p.length; i++) {
            p[i] = 1.0 / p.length;
            prefix[i] = (i + 1.0) / p.length;
        }
    }
}
