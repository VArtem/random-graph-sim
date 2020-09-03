import bipartite.DandelionCode;
import bipartite.graph.Edge;
import bipartite.graph.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class CodeConverter {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Provide a graphviz file with tree edges and an output file");
            return;
        }
        List<Edge> edges = new ArrayList<>();
        int n = 0;
        try (Scanner in = new Scanner(new File(args[0]))) {
            in.nextLine();
            while (true) {
                String s = in.nextLine().trim();
                if (s.equals("}")) {
                    break;
                }
                StringTokenizer st = new StringTokenizer(s);
                int from = Integer.parseInt(st.nextToken());
                st.nextToken();
                int to = Integer.parseInt(st.nextToken());
                edges.add(new Edge(from - 1, to - 1));
                n = Math.max(n, Math.max(from, to));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DandelionCode code = new DandelionCode(new Graph(n,edges));
        try (PrintWriter out = new PrintWriter(new File(args[1]))) {
            out.println("digraph {");
            for (int i = 0; i < n; i++) {
                out.printf("\t%d -> %d\n", i + 1, code.get(i) + 1);
            }
            out.println("}");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully printed a digraph into " + args[1]);
    }
}
