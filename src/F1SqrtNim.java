import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author adkozlov
 */
public class F1SqrtNim implements Runnable {

    final private static String FILENAME = "sqrtnim";

    public static void main(String[] args) throws IOException {
        new Thread(new F1SqrtNim()).run();
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

    private void solve() throws IOException {
        int n = nextInt();

        int result = 0;
        for (int i = 0; i < n; i++) {
            long v = nextLong();
            long sqrt = (long) Math.sqrt(v);
            long sqr = sqrt * sqrt;

            while (sqr != v) {
                v -= sqrt + 1;

                if (sqr > v) {
                    sqrt--;
                    sqr = sqrt * sqrt;
                }
            }

            result ^= (int) sqrt;
        }

        pw.println(result != 0 ? "First" : "Second");
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

    private long nextLong() throws IOException {
        return Long.parseLong(next());
    }
}
