import utils.*;

import java.util.Random;

public class MainPhaseTransition {

    public static final int ITERS = 100000;

    private static final Random rng = new Random(123);

    public static void main(String[] args) {
        int n = 2000;
        int maxK = 2 * n;
        DSU dsu = new DSU(n);

        Stat[] maxSize = new Stat[maxK];
        Stat[] maxSizeProb = new Stat[maxK];
        for (int i = 0; i < maxSize.length; i++) {
            maxSize[i] = new Stat();
            maxSizeProb[i] = new Stat();
        }

        for (int IT = 0; IT < ITERS; IT++) {
            System.err.println("IT = " + IT);
            dsu.reset();
            Dirichlet d = new Dirichlet(n, rng);
            d.makeUniform();
            for (int i = 0; i < n; i++) {
                dsu.prob[i] = d.p[i];
            }

            for (int k = 0; k < maxK; k++) {
                int u = d.sampleVertex(rng);
                int v = d.sampleVertex(rng);
                dsu.addEdge(u, v);
                int id = dsu.maxId;
                maxSize[k].addValue(dsu.size[id]);
                maxSizeProb[k].addValue(dsu.prob[id]);
            }
        }

        Stat.printStats(maxSize, String.format("n%d.uniform.size.txt", n));
        Stat.printStats(maxSizeProb, String.format("n%d.uniform.prob.txt", n));
    }

}
