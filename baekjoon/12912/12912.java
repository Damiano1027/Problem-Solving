import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static Set<Node>[] tree;
    static List<Edge> edges = new ArrayList<>();
    static long result = Long.MIN_VALUE;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        tree = new Set[N];
        for (int i = 0; i < N; i++) {
            tree[i] = new HashSet<>();
        }

        for (int i = 0; i < N - 1; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(tokenizer.nextToken());
            int to = Integer.parseInt(tokenizer.nextToken());
            int cost = Integer.parseInt(tokenizer.nextToken());

            tree[from].add(new Node(to, cost));
            tree[to].add(new Node(from, cost));
            edges.add(new Edge(from, to, cost));
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        for (Edge edge : edges) {
            tree[edge.a].remove(new Node(edge.b));
            tree[edge.b].remove(new Node(edge.a));

            long tree1Length = getTreeLength(edge.a);
            long tree2Length = getTreeLength(edge.b);

            result = Math.max(result, tree1Length + tree2Length + edge.cost);

            tree[edge.a].add(new Node(edge.b, edge.cost));
            tree[edge.b].add(new Node(edge.a, edge.cost));
        }
    }

    static long getTreeLength(int startNumber) {
        int node1 = -1;
        long maxCost = Long.MIN_VALUE;

        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(startNumber, -1, 0));
        }};
        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.totalCost > maxCost) {
                maxCost = current.totalCost;
                node1 = current.number;
            }

            for (Node nextNode : tree[current.number]) {
                if (nextNode.number == current.prevNumber) {
                    continue;
                }

                queue.add(new Bfs(nextNode.number, current.number, current.totalCost + nextNode.cost));
            }
        }

        maxCost = Long.MIN_VALUE;
        queue.add(new Bfs(node1, -1, 0));
        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.totalCost > maxCost) {
                maxCost = current.totalCost;
            }

            for (Node nextNode : tree[current.number]) {
                if (nextNode.number == current.prevNumber) {
                    continue;
                }

                queue.add(new Bfs(nextNode.number, current.number, current.totalCost + nextNode.cost));
            }
        }

        return maxCost;
    }
}

class Node {
    int number;
    long cost;
    Node(int number) {
        this.number = number;
    }
    Node(int number, long cost) {
        this.number = number;
        this.cost = cost;
    }
    public boolean equals(Object o) {
        Node node = (Node) o;
        return number == node.number;
    }
    public int hashCode() {
        return Objects.hash(number);
    }
}

class Edge {
    int a, b;
    long cost;
    Edge(int a, int b, long cost) {
        this.a = a;
        this.b = b;
        this.cost = cost;
    }
}

class Bfs {
    int number, prevNumber;
    long totalCost;
    Bfs(int number, int prevNumber, long totalCost) {
        this.number = number;
        this.prevNumber = prevNumber;
        this.totalCost = totalCost;
    }
}
