import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n;
    static List<Coordinate> coordinates = new ArrayList<>();
    static PriorityQueue<Edge> edges = new PriorityQueue<>();
    static int[] uf;
    static double result = 0.0;

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(reader.readLine());

        uf = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            double x = Double.parseDouble(tokenizer.nextToken());
            double y = Double.parseDouble(tokenizer.nextToken());
            coordinates.add(new Coordinate(i, x, y));
        }

        if (n == 1) {
            writer.write("0.00\n");
            return;
        }

        solve();

        writer.write(String.format("%.2f\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        makeEdges();
        kruskal();
    }

    static void makeEdges() {
        for (int i = 0; i < coordinates.size() - 1; i++) {
            for (int j = i + 1; j < coordinates.size(); j++) {
                Coordinate left = coordinates.get(i);
                Coordinate right = coordinates.get(j);

                edges.add(new Edge(left.number, right.number, Math.sqrt(Math.pow(left.x - right.x, 2) + Math.pow(left.y - right.y, 2))));
            }
        }
    }

    static void kruskal() {
        for (int i = 1; i <= n; i++) {
            uf[i] = i;
        }

        for (int i = 0; i < n - 1;) {
            Edge edge = edges.poll();

            if (find(edge.a) != find(edge.b)) {
                union(edge.a, edge.b);
                result += edge.cost;
                i++;
            }
        }
    }

    static void union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        if (aRoot != bRoot) {
            uf[aRoot] = bRoot;
        }
    }

    static int find(int number) {
        if (uf[number] == number) {
            return number;
        }

        return uf[number] = find(uf[number]);
    }
}

class Coordinate {
    int number;
    double x, y;
    Coordinate(int number, double x, double y) {
        this.number = number;
        this.x = x;
        this.y = y;
    }
}

class Edge implements Comparable<Edge> {
    int a, b;
    double cost;
    Edge(int a, int b, double cost) {
        this.a = a;
        this.b = b;
        this.cost = cost;
    }
    public int compareTo(Edge edge) {
        return Double.compare(cost, edge.cost);
    }
}
