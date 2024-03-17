import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static List<Node>[] graph;
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        graph = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int c = Integer.parseInt(tokenizer.nextToken());

            graph[a].add(new Node(b, c));
            graph[b].add(new Node(a, c));
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        dijkstra();
    }

    static void dijkstra() {
        int[] cost = new int[N + 1];
        Arrays.fill(cost, Integer.MAX_VALUE);
        boolean[] visited = new boolean[N + 1];

        PriorityQueue<Node> pq = new PriorityQueue<>() {{
            add(new Node(1, 0));
        }};
        cost[1] = 0;

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

                int newCost = cost[currentNode.number] + nextNode.cost;
                if (newCost < cost[nextNode.number]) {
                    cost[nextNode.number] = newCost;
                    pq.add(new Node(nextNode.number, newCost));
                }
            }
        }

        result = cost[N];
    }
}

class Node implements Comparable<Node> {
    int number, cost;
    Node(int number, int cost) {
        this.number = number;
        this.cost = cost;
    }
    public int compareTo(Node node) {
        return Integer.compare(cost, node.cost);
    }
}
