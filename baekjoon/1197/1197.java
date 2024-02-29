import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int V, E;
    static PriorityQueue<Edge> edges = new PriorityQueue<>();
    static int[] uf;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        V = Integer.parseInt(tokenizer.nextToken());
        E = Integer.parseInt(tokenizer.nextToken());

        uf = new int[V + 1];
        for (int i = 1; i <= V; i++) {
            uf[i] = i;
        }

        for (int i = 0; i < E; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int A = Integer.parseInt(tokenizer.nextToken());
            int B = Integer.parseInt(tokenizer.nextToken());
            int C = Integer.parseInt(tokenizer.nextToken());
            edges.add(new Edge(A, B, C));
        }

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        for (int i = 0; i < V - 1;) {
            Edge edge = edges.poll();

            if (find(edge.a) == find(edge.b)) {
                continue;
            }

            result += edge.weight;
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
    int a, b, weight;
    Edge(int a, int b, int weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }
    public int compareTo(Edge edge) {
        return weight - edge.weight;
    }
}
