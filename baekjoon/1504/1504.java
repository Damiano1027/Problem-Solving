import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, E, v1, v2;
    static List<Node>[] graph;
    static int[] timesFrom1, timesFromV1, timesFromV2;
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        E = Integer.parseInt(tokenizer.nextToken());

        graph = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        timesFrom1 = new int[N + 1];
        timesFromV1 = new int[N + 1];
        timesFromV2 = new int[N + 1];

        for (int i = 0; i < E; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int c = Integer.parseInt(tokenizer.nextToken());
            graph[a].add(new Node(b, c));
            graph[b].add(new Node(a, c));
        }

        tokenizer = new StringTokenizer(reader.readLine());
        v1 = Integer.parseInt(tokenizer.nextToken());
        v2 = Integer.parseInt(tokenizer.nextToken());

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        dijkstraFrom1();
        dijkstraFromV1();
        dijkstraFromV2();

        if ((timesFrom1[v1] == Integer.MAX_VALUE || timesFromV1[v2] == Integer.MAX_VALUE || timesFromV2[N] == Integer.MAX_VALUE)
                && (timesFrom1[v2] == Integer.MAX_VALUE || timesFromV2[v1] == Integer.MAX_VALUE || timesFromV1[N] == Integer.MAX_VALUE)) {
            result = -1;
        } else {
            result = Math.min(timesFrom1[v1] + timesFromV1[v2] + timesFromV2[N], timesFrom1[v2] + timesFromV2[v1] + timesFromV1[N]);
        }
    }

    static void dijkstraFrom1() {
        boolean[] visited = new boolean[N + 1];

        Arrays.fill(timesFrom1, Integer.MAX_VALUE);
        PriorityQueue<Node> queue = new PriorityQueue<>() {{
            add(new Node(1, 0));
        }};
        timesFrom1[1] = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;

            for (Node nextNode : graph[currentNode.number]) {
                int newDistance = timesFrom1[currentNode.number] + nextNode.value;
                if (newDistance < timesFrom1[nextNode.number]) {
                    timesFrom1[nextNode.number] = newDistance;
                    queue.add(new Node(nextNode.number, newDistance));
                }
            }
        }
    }

    static void dijkstraFromV1() {
        boolean[] visited = new boolean[N + 1];

        Arrays.fill(timesFromV1, Integer.MAX_VALUE);
        PriorityQueue<Node> queue = new PriorityQueue<>() {{
            add(new Node(v1, 0));
        }};
        timesFromV1[v1] = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;

            for (Node nextNode : graph[currentNode.number]) {
                int newDistance = timesFromV1[currentNode.number] + nextNode.value;
                if (newDistance < timesFromV1[nextNode.number]) {
                    timesFromV1[nextNode.number] = newDistance;
                    queue.add(new Node(nextNode.number, newDistance));
                }
            }
        }
    }

    static void dijkstraFromV2() {
        boolean[] visited = new boolean[N + 1];

        Arrays.fill(timesFromV2, Integer.MAX_VALUE);
        PriorityQueue<Node> queue = new PriorityQueue<>() {{
            add(new Node(v2, 0));
        }};
        timesFromV2[v2] = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;

            for (Node nextNode : graph[currentNode.number]) {
                int newDistance = timesFromV2[currentNode.number] + nextNode.value;
                if (newDistance < timesFromV2[nextNode.number]) {
                    timesFromV2[nextNode.number] = newDistance;
                    queue.add(new Node(nextNode.number, newDistance));
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
    public int compareTo(Node o) {
        return value - o.value;
    }
}
