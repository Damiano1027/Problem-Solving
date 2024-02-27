import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, X;
    static List<Node>[] graph;
    static Map<Integer, int[]> timeToGoalMap = new HashMap<>();
    static int[] timeToStart;
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        X = Integer.parseInt(tokenizer.nextToken());

        graph = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }
        timeToStart = new int[N + 1];

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());
            int time = Integer.parseInt(tokenizer.nextToken());
            graph[u].add(new Node(v, time));
        }

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        for (int i = 1; i <= N; i++) {
            dijkstraToGoal(i);
        }
        dijkstraToStart();

        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= N; i++) {
            int timeToG = timeToGoalMap.get(i)[X];
            int timeToS = timeToStart[i];
            max = Math.max(max, timeToG + timeToS);
        }

        result = max;
    }

    static void dijkstraToGoal(int start) {
        int[] time = new int[N + 1];
        boolean[] visited = new boolean[N + 1];

        for (int i = 1; i <= N; i++) {
            time[i] = Integer.MAX_VALUE;
        }
        PriorityQueue<Node> queue = new PriorityQueue<>() {{
            add(new Node(start, 0));
        }};
        time[start] = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;

            for (Node nextNode : graph[currentNode.number]) {
                int newTime = time[currentNode.number] + nextNode.value;
                if (newTime < time[nextNode.number]) {
                    time[nextNode.number] = newTime;
                    queue.add(new Node(nextNode.number, newTime));
                }
            }
        }

        timeToGoalMap.put(start, time);
    }

    static void dijkstraToStart() {
        boolean[] visited = new boolean[N + 1];

        for (int i = 1; i <= N; i++) {
            timeToStart[i] = Integer.MAX_VALUE;
        }
        PriorityQueue<Node> queue = new PriorityQueue<>() {{
            add(new Node(X, 0));
        }};
        timeToStart[X] = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;

            for (Node nextNode : graph[currentNode.number]) {
                int newTime = timeToStart[currentNode.number] + nextNode.value;
                if (newTime < timeToStart[nextNode.number]) {
                    timeToStart[nextNode.number] = newTime;
                    queue.add(new Node(nextNode.number, newTime));
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
