import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[] uf;
    static PriorityQueue<Edge> edges = new PriorityQueue<>();
    static int result = 0;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        M = Integer.parseInt(reader.readLine());

        uf = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            uf[i] = i;
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int c = Integer.parseInt(tokenizer.nextToken());

            edges.add(new Edge(a, b, c));
        }

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        for (int i = 0; i < N - 1;) {
            Edge edge = edges.poll();

            if (find(edge.a) == find(edge.b)) {
                continue;
            }

            result += edge.cost;
            union(edge.a, edge.b);
            i++;
        }
    }

    static void union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        uf[aRoot] = bRoot;
    }

    static int find(int number) {
        if (uf[number] == number) {
            return number;
        }

        return uf[number] = find(uf[number]);
    }
}

class Edge implements Comparable<Edge> {
    int a, b, cost;
    Edge(int a, int b, int cost) {
        this.a = a;
        this.b = b;
        this.cost = cost;
    }
    public int compareTo(Edge edge) {
        return cost - edge.cost;
    }
}
