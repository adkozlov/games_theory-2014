import java.io.*;
import java.util.*;

/**
 * @author adkozlov
 */
public class A1GraphGame implements Runnable {

    final private static String FILENAME = "graphgame";

    public static void main(String[] args) throws IOException {
        new Thread(new A1GraphGame()).run();
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
        protected int degree = 0;
        protected boolean isUsed;
        protected boolean win;
        protected boolean loss;
    }

    private List<Vertex> graph = new ArrayList<>();

    private void dfs(Vertex v) {
        v.isUsed = true;

        for (int i : v.edges) {
            Vertex u = graph.get(i);

            if (!u.isUsed) {
                if (v.loss) {
                    u.win = true;
                } else if (--u.degree == 0) {
                    u.loss = true;
                } else {
                    continue;
                }

                dfs(u);
            }
        }
    }

    private void solve() throws IOException {
        int n = nextInt();
        for (int i = 0; i < n; i++) {
            graph.add(new Vertex());
        }

        int m = nextInt();
        for (int j = 0; j < m; j++) {
            int x = nextInt() - 1;
            int y = nextInt() - 1;

            graph.get(y).edges.add(x);
            graph.get(x).degree++;
        }

        for (Vertex v : graph) {
            if (!v.isUsed && v.degree == 0) {
                v.loss = true;
                dfs(v);
            }
        }

        for (Vertex v : graph) {
            pw.println(v.win ? "Win" : (v.loss ? "Loss" : "Draw"));
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
