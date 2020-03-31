package bipartite;

import utils.ds.RollbackDSU;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeSimulation {

    final int checkI = 1;
    final int checkJ = 1;


    RollbackDSU dsu;
    long[][] result;
    long[][] choose;
    int n, a, b;

    public TreeSimulation() {
        int MAX = 50;
        choose = new long[MAX][MAX];
        for (int i = 0; i < MAX; i++) {
            choose[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                choose[i][j] = choose[i - 1][j - 1] + choose[i - 1][j];
            }
        }
    }

    List<int[]> codes = new ArrayList<>();

    void go(int i, int j, List<Edge> edges, int inFirstCluster, int inSecondCluster) {
        if (edges.size() == n - 1) {
            if (inFirstCluster == checkI && inSecondCluster == checkJ) {
                int[] code = DandelionCode.fromEdges(n, edges);
                codes.add(code);
            }
            result[inFirstCluster][inSecondCluster]++;
            return;
        }
        if (i >= n) {
            return;
        }
        if (j >= n) {
            go(i + 1, i + 2, edges, inFirstCluster, inSecondCluster);
            return;
        }
        go(i, j + 1, edges, inFirstCluster, inSecondCluster);
        if (dsu.get(i) != dsu.get(j)) {
            int size = dsu.stackSize;
            dsu.addEdge(i, j);
            edges.add(new Edge(i, j));
            go(i, j + 1, edges,
                    inFirstCluster + ((i < a && j < a) ? 1 : 0),
                    inSecondCluster + ((i >= a && j >= a) ? 1 : 0));
            dsu.rollback(size);
            edges.remove(edges.size() - 1);
        }
    }

    void countTrees(int a, int b) {
        this.a = a;
        this.b = b;
        this.n = a + b;
        dsu = new RollbackDSU(n);
        result = new long[n][n];
        go(0, 1, new ArrayList<>(), 0, 0);
        for (int i = 0; i <= a - 1; i++) {
            for (int j = 0; j <= b - 1; j++) {
                if (i == checkI && j == checkJ)
                System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\t%s\n", n, a, b, i, j, result[i][j], factorize(result[i][j]));
//                System.out.printf("%d = %d^%d * %d^%d * C(%d, %d) * C(%d, %d)\n", result[i][j],  a, b - 1 + i - j, b, a - 1 + j - i, a - 1, i, b - 1, j);
            }
        }
        Collections.sort(codes, (a1, a2) -> {
            for (int i = 0; i < n; i++) {
                if (a1[i] != a2[i] ){
                    return Integer.compare(a1[i], a2[i]);
                }
            }
            return 0;
        });
        checkCodeValidity(a);
    }

    private void checkCodeValidity(int leftSize) {
        for (int[] code : codes) {
            boolean ok = true;
            ok &= code[0] == 0;
            ok &= code[n - 1] == n - 1;

            int cnt = 0;
            for (int i = 1; i < leftSize; i++) {
                if (code[i] < leftSize) {
                    cnt++;
                }
            }
            ok &= cnt == checkI;
            cnt = 0;
            for (int i = leftSize; i < n - 1; i++) {
                if (code[i] >= leftSize) {
                    cnt++;
                }
            }
            ok &= cnt == checkJ;
            if (!ok) {
                System.err.println(Arrays.toString(code));
            }
        }
    }

    String factorize(long x) {
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

    private void appendPrimePower(StringBuilder result, long prime, int exp) {
        if (result.length() > 0) {
            result.append(" * ");
        }
        result.append(prime).append("^").append(exp);
    }

    public static void main(String[] args) {
        System.out.println("n\ta\tb\tfst\tsnd\tcnt\tformula");
        for (int n = 9; n <= 9; n++) {
            for (int a = 3; a <= 3; a++) {
                new TreeSimulation().countTrees(a, n - a);
            }
        }
    }
}
