import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n, m, r;
    static int[] items;
    static List<Node>[] graph;
    static int result = Integer.MIN_VALUE;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        r = Integer.parseInt(tokenizer.nextToken());

        graph = new List[n + 1];
        items = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 1; i <= n; i++) {
            items[i] = Integer.parseInt(tokenizer.nextToken());
        }

        for (int i = 0; i < r; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int l = Integer.parseInt(tokenizer.nextToken());

            graph[a].add(new Node(b, l));
            graph[b].add(new Node(a, l));
        }

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        for (int start = 1; start <= n; start++) {
            result = Math.max(result, dijkstra(start));
        }
    }

    static int dijkstra(int startNumber) {
        int[] cost = new int[n + 1];
        boolean[] visited = new boolean[n + 1];

        Arrays.fill(cost, Integer.MAX_VALUE);
        PriorityQueue<Node> queue = new PriorityQueue<>() {{
            add(new Node(startNumber, 0));
        }};
        cost[startNumber] = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;

            for (Node nextNode : graph[currentNode.number]) {
                int newCost = cost[currentNode.number] + nextNode.cost;
                if (!visited[nextNode.number] && newCost < cost[nextNode.number]) {
                    cost[nextNode.number] = newCost;
                    queue.add(new Node(nextNode.number, newCost));
                }
            }
        }

        int item = 0;
        for (int i = 1; i <= n; i++) {
            if (cost[i] <= m) {
                item += items[i];
            }
        }

        return item;
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
