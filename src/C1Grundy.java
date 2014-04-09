import java.io.*;
import java.util.*;

/**
 * @author adkozlov
 */
public class C1Grundy implements Runnable {

    final private static String FILENAME = "grundy";

    public static void main(String[] args) throws IOException {
        new Thread(new C1Grundy()).run();
    }

    @Override
    public void run() {
        try {
            br = new BufferedReader(new FileReader(FILENAME + ".in"));
            pw = new PrintWriter(FILENAME + ".out");

            solve();

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static class Vertex {
        protected List<Integer> edges = new ArrayList<>();
        protected boolean isUsed = false;
        protected int grundy = 0;
    }

    private List<Vertex> graph = new ArrayList<>();

    private void dfs(Vertex v) {
        v.isUsed = true;

        for (int i : v.edges) {
            Vertex u = graph.get(i);

            if (!u.isUsed) {
                dfs(u);
            }
        }

        v.grundy = mex(v.edges);
    }

    private int mex(Collection<Integer> vertices) {
        Set<Integer> set = new HashSet<>();

        for (int i : vertices) {
            set.add(graph.get(i).grundy);
        }

        int i = 0;
        while (set.contains(i)) {
            i++;
        }

        return i;
    }

    private void solve() throws IOException {
        int n = nextInt();
        for (int i = 0; i < n; i++) {
            graph.add(new Vertex());
        }

        int m = nextInt();
        for (int j = 0; j < m; j++) {
            graph.get(nextInt() - 1).edges.add(nextInt() - 1);
        }

        for (Vertex v : graph) {
            if (!v.isUsed) {
                dfs(v);
            }
        }

        for (Vertex v : graph) {
            pw.println(v.grundy);
        }
    }

    private BufferedReader br;
    private StringTokenizer st;
    private PrintWriter pw;

    private String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }

        return st.nextToken();
    }

    private int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
}
