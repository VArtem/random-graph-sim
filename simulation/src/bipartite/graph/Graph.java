package bipartite.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Integer>[] graph;

    public Graph(int n) {
        this.graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
    }

    public Graph(int n, List<Edge> edges) {
        this(n);
        for (Edge e : edges) {
            this.addEdge(e);
        }
    }

    public void addEdge(Edge e) {
        this.addEdge(e.from, e.to);
    }

    public void addEdge(int u, int v) {
        graph[u].add(v);
        graph[v].add(u);
    }

    public int size() {
        return graph.length;
    }

    public List<Integer> findPath(int cur, int end, int prev) {
        if (cur == end) {
            List<Integer> path = new ArrayList<>();
            path.add(end);
            return path;
        }

        for (int next : graph[cur]) {
            if (next != prev) {
                List<Integer> path = findPath(next, end, cur);
                if (path != null) {
                    path.add(0, cur);
                    return path;
                }
            }
        }
        return null;
    }

    public void codeForNonCycleVertices(int[] code, List<Integer> path) {
        boolean[] used = new boolean[size()];
        for (int i : path) {
            used[i] = true;
        }
        for (int i : path) {
            for (int j : graph[i]) {
                if (!used[j]) {
                    code[j] = i;
                    fillSubtree(j, i, used, code);
                }
            }
        }
    }

    private void fillSubtree(int cur, int parent, boolean[] used, int[] code) {
        code[cur] = parent;
        used[cur] = true;
        for (int neighbor : graph[cur]) {
            if (!used[cur]) {
                fillSubtree(neighbor, cur, used, code);
            }
        }
    }
}
