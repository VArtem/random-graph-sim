package utils;

import java.util.Arrays;

public class DSU {
    public int[] parent;
    public int[] rank;
    public boolean[] isTree;
    public int[] size;
    public double[] prob;
    public int sets;
    public int maxId;

    public DSU(int n) {

        parent = new int[n];
        rank = new int[n];
        size = new int[n];
        isTree = new boolean[n];
        prob = new double[n];
        Arrays.fill(isTree, true);
        Arrays.fill(size, 1);
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        this.sets = n;
        this.maxId = 0;
    }

    public int get(int v) {
        if (parent[v] == v) {
            return v;
        }
        return parent[v] = get(parent[v]);
    }

    public boolean addEdge(int u, int v) {
        u = get(u);
        v = get(v);
        if (u == v) {
            isTree[u] = false;
            return false;
        }
        sets--;
        if (rank[u] == rank[v]) {
            rank[u]++;
        }
        if (rank[u] < rank[v]) {
            int tmp = u;
            u = v;
            v = tmp;
        }
        parent[v] = u;
        size[u] += size[v];
        prob[u] += prob[v];
        isTree[u] &= isTree[v];
        if (size[maxId] < size[u]) {
            maxId = u;
        }
        return true;
    }

    public void reset() {
        Arrays.fill(rank, 0);
        Arrays.fill(size, 1);
        Arrays.fill(isTree, true);
        Arrays.fill(prob, 0);
        maxId = 0;
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
        this.sets = parent.length;
    }
}
