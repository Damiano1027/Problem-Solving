import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n, m, s, t;
    static List<Node>[] graph;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());

        graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int c = Integer.parseInt(tokenizer.nextToken());

            graph[a].add(new Node(b, c));
            graph[b].add(new Node(a, c));
        }

        tokenizer = new StringTokenizer(reader.readLine());
        s = Integer.parseInt(tokenizer.nextToken());
        t = Integer.parseInt(tokenizer.nextToken());

        writer.write(dijkstra() + "\n");
        writer.flush();
        writer.close();
    }

    static int dijkstra() {
        PriorityQueue<Node> pq = new PriorityQueue<>() {{
            add(new Node(s, 0));
        }};
        boolean[] visited = new boolean[n + 1];
        int[] cost = new int[n + 1];
        Arrays.fill(cost, Integer.MAX_VALUE);
        cost[s] = 0;

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

        return cost[t];
    }
}

class Node implements Comparable<Node> {
    int number, cost;
    Node(int number, int cost) {
        this.number = number;
        this.cost = cost;
    }
    public int compareTo(Node node) {
        return cost - node.cost;
    }
}
