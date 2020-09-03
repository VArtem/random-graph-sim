package bipartite;

import bipartite.graph.Graph;

import java.util.Arrays;
import java.util.List;

public class DandelionCode {

    int[] code;

    public DandelionCode(int[] code) {
        this.code = code.clone();
    }

    public DandelionCode(Graph graph) {
        int n = graph.size();
        this.code = new int[graph.size()];
        List<Integer> path = graph.findPath(0, n - 1, -1);
        graph.codeForNonCycleVertices(code, path);
        pathIntoCycles(code, path, 0, path.size() - 1);
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

    @Override
    public String toString() {
        return "DandelionCode{" +
                "code=" + Arrays.toString(code) +
                '}';
    }

    public int get(int i) {
        return code[i];
    }

    public boolean checkValidity(int leftSize, int checkI, int checkJ) {
        boolean ok = true;
        int n = code.length;
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
        return ok;
    }

    public int length() {
        return code.length;
    }
}
