package probability;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Stat {
    public double sum;
    public double sumSquares;
    public double min, max;
    public int count;

    public Stat() {
        sum = 0;
        sumSquares = 0;
        min = Double.POSITIVE_INFINITY;
        max = Double.NEGATIVE_INFINITY;
        count = 0;
    }

    public static void printStats(Stat[] array, String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            for (int i = 0; i < array.length; i++) {
                out.println((i + 1) + " " + array[i].mean());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addValue(double value) {
        count++;
        sum += value;
        sumSquares += value * value;
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    public double mean() {
        return sum / count;
    }

    public double dispersion() {
        double m = mean();
        return sumSquares / count - m * m;
    }

    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public double count() {
        return count;
    }
}
