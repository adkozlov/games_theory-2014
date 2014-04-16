import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author adkozlov
 */
public class F2Strong implements Runnable {

    final private static String FILENAME = "strong";

    public static void main(String[] args) throws IOException {
        new Thread(new F2Strong()).run();
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

    private List<Pair> get(int[][] a, int[][] b) {
        List<Pair> result = new ArrayList<>();

        final int m = a.length;
        final int n = a[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                boolean isStrong = true;

                for (int k = 0; k < m && isStrong; k++) {
                    if (a[i][j] < a[k][j]) {
                        isStrong = false;
                    }
                }

                for (int k = 0; k < n && isStrong; k++) {
                    if (b[i][j] < b[i][k]) {
                        isStrong = false;
                    }
                }

                for (int k1 = 0; k1 < m && isStrong; k1++) {
                    for (int k2 = 0; k2 < n && isStrong; k2++) {
                        if (a[i][j] + b[i][j] < a[k1][k2] + b[k1][k2]) {
                            isStrong = false;
                        }
                    }
                }

                if (isStrong) {
                    result.add(new Pair(i + 1, j + 1));
                }
            }
        }

        return result;
    }

    private void solve() throws IOException {
        int m = nextInt();
        int n = nextInt();

        int[][] a = readMatrix(m, n);
        int[][] b = readMatrix(m, n);

        List<Pair> result = get(a, b);

        pw.println(result.size());
        for (Pair pair : result) {
            pw.println(pair);
        }
    }

    private static class Pair {
        private final int i, j;

        private Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return i + " " + j;
        }
    }

    private int[][] readMatrix(int m, int n) throws IOException {
        int[][] result = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = nextInt();
            }
        }

        return result;
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