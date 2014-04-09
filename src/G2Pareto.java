import java.io.*;
import java.util.*;

/**
 * @author adkozlov
 */
public class G2Pareto implements Runnable {

    final private static String FILENAME = "pareto";

    public static void main(String[] args) throws IOException {
        new Thread(new G2Pareto()).run();
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

    private boolean check(int[][] a, int[][] b, int i, int j) {
        for (int k = 0; k < a.length; k++) {
            for (int l = 0; l < a[k].length; l++) {
                if ((a[k][l] >= a[i][j] && b[k][l] > b[i][j]) || (a[k][l] > a[i][j] && b[k][l] >= b[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    private void solve() throws IOException {
        int m = nextInt();
        int n = nextInt();

        int[][] a = readMatrix(m, n);
        int[][] b = readMatrix(m, n);

        List<Pair> result = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (check(a, b, i, j)) {
                    result.add(new Pair(i + 1, j + 1));
                }
            }
        }

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

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
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