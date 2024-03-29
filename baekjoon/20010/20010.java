import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, K;
    static List<Edge> edges = new ArrayList<>();
    static long result1, result2;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        for (int i = 0; i < K; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int cost = Integer.parseInt(tokenizer.nextToken());
            edges.add(new Edge(a, b, cost));
        }

        solve();

        writer.write(result1 + "\n");
        writer.write(result2 + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        if (N == 1) {
            result1 = 0;
            result2 = 0;
            return;
        }

        int[] uf = new int[N];
        for (int i = 0; i < N; i++) {
            uf[i] = i;
        }

        Collections.sort(edges);
        int count = 0;
        List<Node>[] graph = new List[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (Edge edge : edges) {
            if (count == N) {
                break;
            }

            if (find(edge.a, uf) != find(edge.b, uf)) {
                union(edge.a, edge.b, uf);
                count++;
                result1 += edge.cost;
                graph[edge.a].add(new Node(edge.b, edge.cost));
                graph[edge.b].add(new Node(edge.a, edge.cost));
            }
        }

        long[][] cost = new long[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cost[i][j] = Long.MAX_VALUE;
            }
        }
        for (int number = 0; number < N; number++) {
            dijkstra(number, cost[number], graph);
        }

        result2 = Long.MIN_VALUE;
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                result2 = Math.max(result2, cost[i][j]);
            }
        }
    }

    static void union(int a, int b, int[] uf) {
        int aRoot = find(a, uf);
        int bRoot = find(b, uf);

        uf[aRoot] = bRoot;
    }

    static int find(int number, int[] uf) {
        if (uf[number] == number) {
            return number;
        }

        return uf[number] = find(uf[number], uf);
    }

    static void dijkstra(int startNumber, long[] cost, List<Node>[] graph) {
        boolean[] visited = new boolean[N];
        PriorityQueue<Node> pq = new PriorityQueue<>() {{
            add(new Node(startNumber, 0));
        }};
        cost[startNumber] = 0;

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;

            for (Node nextNode : graph[currentNode.number]) {
                if (visited[nextNode.number]) {
                    continue;
                }

                long newCost = cost[currentNode.number] + nextNode.cost;
                if (newCost < cost[nextNode.number]) {
                    cost[nextNode.number] = newCost;
                    pq.add(new Node(nextNode.number, newCost));
                }
            }
        }
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

class Node implements Comparable<Node> {
    int number;
    long cost;
    Node(int number, long cost) {
        this.number = number;
        this.cost = cost;
    }
    public int compareTo(Node node) {
        return Long.compare(cost, node.cost);
    }
}
