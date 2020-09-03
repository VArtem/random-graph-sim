package bipartite;

public class Utils {
    public static String factorize(long x) {
        StringBuilder result = new StringBuilder();
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) {
                int exp = 0;
                while (x % i == 0) {
                    x /= i;
                    exp++;
                }
                appendPrimePower(result, i, exp);
            }
        }
        if (x > 1) {
            appendPrimePower(result, x, 1);
        }
        return result.toString();
    }

    private static void appendPrimePower(StringBuilder result, long prime, int exp) {
        if (result.length() > 0) {
            result.append(" * ");
        }
        result.append(prime).append("^").append(exp);
    }
}
