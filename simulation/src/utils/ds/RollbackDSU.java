package utils.ds;

import java.util.Arrays;

public class RollbackDSU {
    public int[] parent;
    public int[] stack;
    public int stackSize;

    public RollbackDSU(int n) {
        parent = new int[n];
        Arrays.fill(parent, -1);
        stack = new int[n * 4];
        stackSize = 0;
    }

    public int get(int v) {
        if (parent[v] < 0) {
            return v;
        }
        return get(parent[v]);
    }

    public boolean addEdge(int u, int v) {
        u = get(u);
        v = get(v);
        if (u == v) {
            return false;
        }
        if (-parent[u] < -parent[v]) {
            int tmp = u;
            u = v;
            v = tmp;
        }
        set(u, parent[u] + parent[v]);
        set(v, u);
        return true;
    }

    void set(int index, int value) {
        stack[stackSize++] = index;
        stack[stackSize++] = parent[index];
        parent[index] = value;
    }

    public void reset() {
        rollback(0);
    }

    public void rollback(int size) {
        while (stackSize > size) {
            int value = stack[--stackSize];
            int index = stack[--stackSize];
            parent[index] = value;
        }
    }
}
