import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static Pair[] xArr, yArr, zArr;
    static PriorityQueue<Edge> edges = new PriorityQueue<>();
    static int[] uf;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        xArr = new Pair[N];
        yArr = new Pair[N];
        zArr = new Pair[N];
        uf = new int[N + 1];

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());
            int z = Integer.parseInt(tokenizer.nextToken());

            xArr[i] = new Pair(i + 1, x);
            yArr[i] = new Pair(i + 1, y);
            zArr[i] = new Pair(i + 1, z);
        }

        if (N == 1) {
            writer.write("0\n");
            return;
        }

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        makeEdges();
        kruskal();
    }

    static void makeEdges() {
        Arrays.sort(xArr);
        Arrays.sort(yArr);
        Arrays.sort(zArr);

        for (int i = 0; i < xArr.length - 1; i++) {
            Pair left = xArr[i];
            Pair right = xArr[i + 1];
            edges.add(new Edge(left.planetNumber, right.planetNumber, right.value - left.value));
        }
        for (int i = 0; i < yArr.length - 1; i++) {
            Pair left = yArr[i];
            Pair right = yArr[i + 1];
            edges.add(new Edge(left.planetNumber, right.planetNumber, right.value - left.value));
        }
        for (int i = 0; i < zArr.length - 1; i++) {
            Pair left = zArr[i];
            Pair right = zArr[i + 1];
            edges.add(new Edge(left.planetNumber, right.planetNumber, right.value - left.value));
        }
    }

    static void kruskal() {
        for (int i = 1; i <= N; i++) {
            uf[i] = i;
        }

        for (int i = 0; i < N - 1;) {
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

        uf[aRoot] = bRoot;
    }

    static int find(int number) {
        if (uf[number] == number) {
            return number;
        }

        return uf[number] = find(uf[number]);
    }
}

class Pair implements Comparable<Pair> {
    int planetNumber, value;
    Pair(int planetNumber, int value) {
        this.planetNumber = planetNumber;
        this.value = value;
    }
    public int compareTo(Pair pair) {
        return value - pair.value;
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
