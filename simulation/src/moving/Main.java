package moving;

import java.util.Random;

public class Main {

    static int ITERS = 1000;

    public static void main(String[] args) {
        for (int n = 100; n <= 1000; n += 100) {
            System.err.println("n = " + n);
            System.err.println(sim(n, n, 0.5) / n);
        }
    }

    static double sim(int n, int m, double p) {
        // n chips move m places, each step with probability p
        int[] pos = new int[n];
        double average = 0;
        Random rng = new Random();
        for (int I = 0; I < ITERS; I++) {
            for (int i = 0; i < n; i++) {
                pos[i] = i;
            }
            for (int step = 0; ; step++) {
                boolean finish = true;
                for (int i = 0; i < n; i++) {
                    finish &= pos[i] == i + m;
                    if (rng.nextDouble() < p && (i < n - 1 && pos[i] + 1 < pos[i + 1] || i == n - 1 && pos[i] < i + m)) {
                        pos[i]++;
                    }
                }
                if (finish) {
                    average += (double) step / ITERS;
                    break;
                }
            }
        }
        return average;
    }
}
