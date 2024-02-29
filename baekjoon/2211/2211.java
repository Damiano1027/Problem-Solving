import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static List<Node>[] graph;
    static int[] cost, prevNumber;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        graph = new List[N + 1];
        cost = new int[N + 1];
        prevNumber = new int[N + 1];
        visited = new boolean[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int A = Integer.parseInt(tokenizer.nextToken());
            int B = Integer.parseInt(tokenizer.nextToken());
            int C = Integer.parseInt(tokenizer.nextToken());

            graph[A].add(new Node(B, C));
            graph[B].add(new Node(A, C));
        }

        solve();

        writer.flush();

        writer.close();
    }

    static void solve() throws Exception {
        dijkstra();
        writer.write(String.format("%d\n", N - 1));
        for (int i = 2; i <= N; i++) {
            writer.write(String.format("%d %d\n", i, prevNumber[i]));
        }
    }

    static void dijkstra() {
        Arrays.fill(cost, Integer.MAX_VALUE);
        PriorityQueue<Node> queue = new PriorityQueue<>() {{
            add(new Node(1, 0));
        }};
        cost[1] = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;

            for (Node nextNode : graph[currentNode.number]) {
                int newTime = cost[currentNode.number] + nextNode.time;
                if (newTime < cost[nextNode.number]) {
                    cost[nextNode.number] = newTime;
                    queue.add(new Node(nextNode.number, newTime));
                    prevNumber[nextNode.number] = currentNode.number;
                }
            }
        }
    }
}

class Node implements Comparable<Node> {
    int number, time;
    Node(int number, int time) {
        this.number = number;
        this.time = time;
    }
    public int compareTo(Node node) {
        return time - node.time;
    }
}
