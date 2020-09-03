package bipartite;

import bipartite.graph.Edge;
import bipartite.ds.RollbackDSU;
import bipartite.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static bipartite.Utils.factorize;

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

    List<DandelionCode> codes = new ArrayList<>();

    void go(int i, int j, List<Edge> edges, int inFirstCluster, int inSecondCluster) {
        if (edges.size() == n - 1) {
            if (inFirstCluster == checkI && inSecondCluster == checkJ) {
                DandelionCode code = new DandelionCode(new Graph(n, edges));
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
                if (a1.get(i) != a2.get(i)){
                    return Integer.compare(a1.get(i), a2.get(i));
                }
            }
            return 0;
        });
        checkCodeValidity(a);
    }

    private void checkCodeValidity(int leftSize) {
        for (DandelionCode code : codes) {
            boolean ok = code.checkValidity(leftSize, checkI, checkJ);
            if (!ok) {
                System.err.println(code);
            }
        }
    }

    public static void main(String[] args) {
        runTSVFormat();
    }

    static void runTSVFormat() {
        System.out.println("n\ta\tb\tfst\tsnd\tcnt\tformula");
        for (int n = 9; n <= 9; n++) {
            for (int a = 3; a <= 3; a++) {
                new TreeSimulation().countTrees(a, n - a);
            }
        }
    }
}
