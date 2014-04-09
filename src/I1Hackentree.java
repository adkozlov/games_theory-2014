import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * @author adkozlov
 */
public class I1Hackentree implements Runnable {

    final private static String FILENAME = "hackentree";

    public static void main(String[] args) throws IOException {
        new Thread(new I1Hackentree()).run();
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

    private static class Rational {
        private final BigInteger numerator, denominator;

        public Rational(BigInteger numerator, BigInteger denominator) {
            if (denominator.equals(BigInteger.ZERO)) {
                throw new IllegalArgumentException("dividing by zero");
            }

            BigInteger gcd = numerator.gcd(denominator);

            BigInteger[] rational = { numerator, denominator };
            for (int i = 0; i < rational.length; i++) {
                rational[i] = rational[i].divide(gcd);
            }

            if (denominator.compareTo(BigInteger.ZERO) < 0) {
                for (int i = 0; i < rational.length; i++) {
                    rational[i] = rational[i].negate();
                }
            }

            this.numerator = rational[0];
            this.denominator = rational[1];
        }

        public Rational(BigInteger numerator) {
            this(numerator, BigInteger.ONE);
        }

        public Rational() {
            this(BigInteger.ZERO);
        }

        public BigInteger getNumerator() {
            return numerator;
        }

        public BigInteger getDenominator() {
            return denominator;
        }

        public BigInteger floor() {
            return numerator.divide(denominator);
        }

        public BigInteger ceil() {
            BigInteger floor = floor();

            return numerator.remainder(denominator).equals(BigInteger.ZERO) ? floor : floor.add(BigInteger.ONE);
        }

        @Override
        public String toString() {
            return numerator + " " + denominator;
        }

        public Rational add(Rational rational) {
            BigInteger numerator = this.numerator.multiply(rational.denominator).add(this.denominator.multiply(rational.numerator));
            BigInteger denominator = this.denominator.multiply(rational.denominator);

            return new Rational(numerator, denominator);
        }
    }

    private static class Graph {

        private static class Edge {
            private final int vertex;
            private final boolean isRed;

            private Edge(int vertex, boolean isRed) {
                this.vertex = vertex;
                this.isRed = isRed;
            }
        }

        private Graph(int n) {
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<Edge>());
                used.add(false);
                values.add(new Rational());
            }
        }

        private List<List<Edge>> graph = new ArrayList<>();
        private List<Boolean> used = new ArrayList<>();
        private List<Rational> values = new ArrayList<>();

        public void addEdge(int u, int v, boolean isRed) {
            addOrientedEdge(u, v, isRed);
            addOrientedEdge(v, u, isRed);
        }

        private void addOrientedEdge(int u, int v, boolean isRed) {
            graph.get(u).add(new Edge(v, isRed));
        }

        public Rational getValue() {
            dfs(0);

            return values.get(0);
        }

        private void dfs(int v) {
            used.set(v, true);

            for (Edge edge : graph.get(v)) {
                if (!used.get(edge.vertex)) {
                    dfs(edge.vertex);

                    Rational value = values.get(edge.vertex);
                    Rational result = edge.isRed ? getRed(value) : getBlue(value);

                    values.set(v, values.get(v).add(result));
                    values.set(edge.vertex, result);
                }
            }
        }

        private Rational getRed(Rational value) {
            return getColored(value, true);
        }

        private Rational getBlue(Rational value) {
            return getColored(value, false);
        }

        private Rational getColored(Rational value, boolean isRed) {
            BigInteger coefficient = BigInteger.ONE;
            if (!isRed) {
                coefficient = coefficient.negate();
            }

            BigInteger numerator = value.getNumerator();
            BigInteger denominator = value.getDenominator();

            Rational temp = new Rational(denominator.add(numerator.multiply(coefficient)), denominator);

            BigInteger ceil = temp.ceil();
            ceil = ceil.compareTo(BigInteger.ZERO) > 0 ? ceil : BigInteger.ONE;

            return new Rational(numerator.subtract(ceil.multiply(denominator.multiply(coefficient))), denominator.multiply(BigInteger.ONE.shiftLeft(ceil.intValue() - 1)));
        }

    }

    private void solve() throws IOException {
        int n = nextInt();

        Graph graph = new Graph(n);
        for (int i = 0; i < n - 1; i++) {
            graph.addEdge(nextInt() - 1, nextInt() - 1, nextInt() == 1);
        }

        pw.println(graph.getValue());
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
