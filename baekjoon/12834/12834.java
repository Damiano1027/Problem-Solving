import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, V, E;
    static int A, B;
    static List<Node>[] graph;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        V = Integer.parseInt(tokenizer.nextToken());
        E = Integer.parseInt(tokenizer.nextToken());

        graph = new List[V + 1];
        for (int i = 1; i <= V; i++) {
            graph[i] = new ArrayList<>();
        }

        tokenizer = new StringTokenizer(reader.readLine());
        A = Integer.parseInt(tokenizer.nextToken());
        B = Integer.parseInt(tokenizer.nextToken());

        StringTokenizer tempTokenizer = new StringTokenizer(reader.readLine());

        for (int i = 0; i < E; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int l = Integer.parseInt(tokenizer.nextToken());

            graph[a].add(new Node(b, l));
            graph[b].add(new Node(a, l));
        }

        while (tempTokenizer.hasMoreTokens()) {
            int start = Integer.parseInt(tempTokenizer.nextToken());
            result += dijkstra(start);
        }

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static int dijkstra(int start) {
        int[] cost = new int[V + 1];
        Arrays.fill(cost, Integer.MAX_VALUE);
        cost[start] = 0;
        boolean[] visited = new boolean[V + 1];
        PriorityQueue<Node> pq = new PriorityQueue<>() {{
            add(new Node(start, 0));
        }};

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

        int costA = cost[A] == Integer.MAX_VALUE ? -1 : cost[A];
        int costB = cost[B] == Integer.MAX_VALUE ? -1 : cost[B];
        return costA + costB;
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
