import java.io.*;
import java.util.*;

/**
 * @author adkozlov
 */
public class D1Karlsson implements Runnable {

    final private static String FILENAME = "karlsson";

    public static void main(String[] args) throws IOException {
        new Thread(new D1Karlsson()).run();
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

    private void  setKthGrundy(int[] grundy, int k) {
        boolean[] used = new boolean[k / 2];

        for (int i = k - k / 2; i < k; i++) {
            if (grundy[i] < k / 2) {
                used[grundy[i]] = true;
            }
        }
        for (int i = 0; i < used.length; i++) {
            if (!used[i]) {
                grundy[k] = i;
                return;
            }
        }

        grundy[k] = k / 2;
    }

    private int[] getGrundy(int n) {
        int[] result = new int[n + 1];

        for (int k = 2; k < result.length; k++) {
            setKthGrundy(result, k);
        }

        return result;
    }

    private void solve() throws IOException {
        int[] dimensions = new int[3];
        for (int i = 0; i < dimensions.length; i++) {
            dimensions[i] = nextInt();
        }

        int[] grundy = getGrundy(max(dimensions));

        boolean answer = getAnswer(dimensions, grundy);
        pw.println(answer ? "YES" : "NO");

        if (!answer) {
            return;
        }

        for (int i = 0; i < dimensions.length; i++) {
            for (int j = 0; j <= dimensions[i] / 2; j++) {
                int xor = 0;
                for (int k = 0; k < dimensions.length; k++) {
                    xor ^= grundy[dimensions[k] - getDelta(i, j, k)];
                }

                if (xor == 0) {
                    for (int k = 0; k < dimensions.length; k++) {
                        pw.print(dimensions[k] - getDelta(i, j, k) + " ");
                    }

                    return;
                }
            }
        }
    }

    private static int max(int[] dimensions) {
        int result = Integer.MIN_VALUE;
        for (int d : dimensions) {
            result = Math.max(d, result);
        }

        return result;
    }

    private static boolean getAnswer(int[] dimensions, int[] grundy) {
        int xor = 0;
        for (int d : dimensions) {
            xor ^= grundy[d];
        }

        return xor != 0;
    }

    private static int getDelta(int i, int j, int k) {
        return k == i ? j : 0;
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
