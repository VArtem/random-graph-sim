package bipartite;

import java.util.ArrayList;
import java.util.List;

public class DandelionCode {

    public static int[] fromEdges(int n, List<Edge> edges) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (Edge e : edges) {
            graph[e.from].add(e.to);
            graph[e.to].add(e.from);
        }
        List<Integer> path = findPath(graph, 0, n - 1, -1);
        boolean[] used = new boolean[n];
        for (int i : path) {
            used[i] = true;
        }
        int[] code = new int[n];
        for (int i : path) {
            fillNonCycleVertices(graph, used, code, i);
        }

        pathIntoCycles(code, path, 0, path.size() - 1);
        return code;
    }

    private static void pathIntoCycles(int[] code, List<Integer> path, int l, int r) {
        if (l > r) {
            return;
        }
        int minPos = l;
        for (int i = l + 1; i <= r; i++) {
            if (path.get(i) < path.get(minPos)) {
                minPos = i;
            }
        }
        for (int i = l; i < minPos; i++) {
            code[path.get(i)] = path.get(i + 1);
        }
        code[path.get(minPos)] = path.get(l);
        pathIntoCycles(code, path, minPos + 1, r);
    }

    private static List<Integer> findPath(List<Integer>[] graph, int u, int finish, int p) {
        if (u == finish) {
            List<Integer> path = new ArrayList<>();
            path.add(u);
            return path;
        }
        for (int v : graph[u]) {
            if (v != p) {
                List<Integer> ret = findPath(graph, v, finish, u);
                if (ret != null) {
                    ret.add(0, u);
                    return ret;
                }
            }
        }
        return null;
    }

    private static void fillNonCycleVertices(List<Integer>[] graph, boolean[] used, int[] code, int i) {
        used[i] = true;
        for (int j : graph[i]) {
            if (!used[j]) {
                code[j] = i;
                fillNonCycleVertices(graph, used, code, j);
            }
        }
    }


}
