import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int V, E, K;
    static List<Node>[] graph;
    static boolean[] visited;
    static int[] distance;
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        V = Integer.parseInt(tokenizer.nextToken());
        E = Integer.parseInt(tokenizer.nextToken());

        K = Integer.parseInt(reader.readLine());

        graph = new List[V + 1];
        visited = new boolean[V + 1];
        distance = new int[V + 1];
        for (int i = 1; i <= V; i++) {
            graph[i] = new ArrayList<>();
        }
        Arrays.fill(distance, Integer.MAX_VALUE);

        for (int i = 0; i < E; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());
            int w = Integer.parseInt(tokenizer.nextToken());

            graph[u].add(new Node(v, w));
        }

        dijkstra();

        for (int i = 1; i <= V; i++) {
            if (distance[i] == Integer.MAX_VALUE) {
                writer.write("INF\n");
            } else {
                writer.write(distance[i] + "\n");
            }
        }
        writer.flush();

        writer.close();
    }

    static void dijkstra() {
        distance[K] = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>() {{
            add(new Node(K, 0));
        }};

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;

            for (Node nextNode: graph[currentNode.number]) {
                int newDistance = distance[currentNode.number] + nextNode.value;
                if (newDistance < distance[nextNode.number]) {
                    distance[nextNode.number] = newDistance;
                    queue.add(new Node(nextNode.number, distance[nextNode.number]));
                }
            }
        }
    }
}

class Node implements Comparable<Node> {
    int number, value;
    Node(int number, int value) {
        this.number = number;
        this.value = value;
    }
    public int compareTo(Node node) {
        return value - node.value;
    }
}
