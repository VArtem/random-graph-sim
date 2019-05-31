package bipartite;

import utils.RollbackDSU;

public class TreeSimulation {

    RollbackDSU dsu;
    long[] result;
    int n, a, b;

    void go(int i, int j, int edges, int countedEdges) {
        if (edges == n - 1) {
            result[countedEdges]++;
            return;
        }
        if (i >= n) {
            return;
        }
        if (j >= n) {
            go(i + 1, i + 2, edges, countedEdges);
            return;
        }
        go(i, j + 1, edges, countedEdges);
        if (dsu.get(i) != dsu.get(j)) {
            int size = dsu.stackSize;
            dsu.addEdge(i, j);
            go(i, j + 1, edges + 1, countedEdges + (((i < a) != (j < a)) ? 1 : 0));
            dsu.rollback(size);
        }

    }

    void countTrees(int a, int b) {
        this.a = a;
        this.b = b;
        this.n = a + b;
        dsu = new RollbackDSU(n);
        result = new long[n];
        go(0, 1, 0, 0);
        System.out.printf("n = %d, a = %d, b = %d\n", n, a, b);
        for (int i = 1; i < result.length; i++) {
            System.out.printf("%d edges => %d\n", i, result[i]);
        }
    }

    public static void main(String[] args) {
        for (int n = 1; n <= 9; n++) {
            for (int a = 1; a < n; a++) {
                new TreeSimulation().countTrees(a, n - a);
            }
        }
    }
}
