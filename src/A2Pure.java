import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author adkozlov
 */
public class A2Pure implements Runnable {

    final private static String FILENAME = "pure";

    public static void main(String[] args) throws IOException {
        new Thread(new A2Pure()).run();
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

    private Pair optimize(List<List<Integer>> matrix, Comparator<Integer> comparator) {
        int value = Collections.max(matrix.get(0), comparator);
        List<Integer> arguments = new ArrayList<>();
        arguments.add(0);

        for (int i = 1; i < matrix.size(); i++) {
            int temp = Collections.max(matrix.get(i), comparator);

            int compareTo = comparator.compare(temp, value);
            if (compareTo < 0) {
                value = temp;
                arguments = new ArrayList<>();
                arguments.add(i);
            } else if (compareTo == 0) {
                arguments.add(i);
            }
        }

        return new Pair(value, arguments);

    }

    private void solve() throws IOException {
        List<List<Integer>> matrix = readMatrix(nextInt(), nextInt());

        Pair maximin = optimize(matrix, Comparator.<Integer>reverseOrder());
        Pair minimax = optimize(transpose(matrix), Comparator.<Integer>naturalOrder());

        boolean printZero = maximin.getValue() != minimax.getValue();
        writeListSize(maximin, printZero);
        writeListSize(minimax, printZero);

        if (!printZero) {
            writeList(maximin);
            writeList(minimax);
        }
    }

    private void writeListSize(Pair result, boolean printZero) {
        pw.print((printZero ? 0 : result.getArguments().size()) + " ");
    }

    private void writeList(Pair result) {
        pw.println();

        for (int i : result.getArguments()) {
            pw.print((i + 1) + " ");
        }
    }

    private static class Pair {
        private final int value;
        private final List<Integer> arguments;

        private Pair(int value, List<Integer> arguments) {
            this.value = value;
            this.arguments = arguments;
        }

        public int getValue() {
            return value;
        }

        public List<Integer> getArguments() {
            return arguments;
        }
    }

    private List<List<Integer>> readMatrix(int m, int n) throws IOException {
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            List<Integer> row = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                row.add(nextInt());
            }

            result.add(row);
        }

        return result;
    }

    private static List<List<Integer>> transpose(List<List<Integer>> matrix) {
        List<List<Integer>> result = new ArrayList<>();

        for (int j = 0; j < matrix.get(0).size(); j++) {
            result.add(new ArrayList<Integer>());
        }

        for (List<Integer> row : matrix) {
            for (int j = 0; j < row.size(); j++) {
                result.get(j).add(row.get(j));
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