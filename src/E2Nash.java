import java.io.*;
import java.util.*;

/**
 * @author adkozlov
 */
public class E2Nash implements Runnable {

    final private static String FILENAME = "nash";

    public static void main(String[] args) throws IOException {
        new Thread(new E2Nash()).run();
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

    private Set<Pair> get(int[][] m, boolean swap) {
        Set<Pair> result = new HashSet<>();

        for (int i = 0; i < m.length; i++) {
            int temp = m[i][0];
            List<Pair> tempList = new ArrayList<>();
            tempList.add(new Pair(i + 1, 1, swap));

            for (int j = 0; j < m[i].length; j++) {
                Pair tempPair = new Pair(i + 1, j + 1, swap);

                if (m[i][j] > temp) {
                    temp = m[i][j];
                    tempList = new ArrayList<>();
                    tempList.add(tempPair);
                } else if (m[i][j] == temp) {
                    tempList.add(tempPair);
                }
            }

            result.addAll(tempList);
        }

        return result;
    }

    private void solve() throws IOException {
        int m = nextInt();
        int n = nextInt();

        int[][] a = transpose(readMatrix(m, n));
        int[][] b = readMatrix(m, n);

        Set<Pair> result = get(a, true);
        result.retainAll(get(b, false));

        pw.println(result.size());
        for (Pair pair : result) {
            pw.println(pair);
        }
    }

    private static class Pair {
        private final int i, j;

        private Pair(int i, int j, boolean swap) {
            this.i = swap ? j : i;
            this.j = swap ? i : j;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;

            Pair pair = (Pair) o;

            if (i != pair.i) return false;
            if (j != pair.j) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = i;
            result = 31 * result + j;
            return result;
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

    private static int[][] transpose(int[][] matrix) {
        int[][] result = new int[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[j][i] = matrix[i][j];
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