import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int testCase;

    public static void main(String[] args) throws Exception {
        testCase = Integer.parseInt(reader.readLine());

        for (int i = 0; i < testCase; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int n = Integer.parseInt(tokenizer.nextToken());
            int d = Integer.parseInt(tokenizer.nextToken());
            int c = Integer.parseInt(tokenizer.nextToken());

            List<Node>[] graph = new List[n + 1];
            for (int j = 1; j <= n; j++) {
                graph[j] = new ArrayList<>();
            }

            for (int j = 0; j < d; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int a = Integer.parseInt(tokenizer.nextToken());
                int b = Integer.parseInt(tokenizer.nextToken());
                int s = Integer.parseInt(tokenizer.nextToken());

                graph[b].add(new Node(a, s));
            }

            Result result = dijkstra(c, n, graph);
            writer.write(String.format("%d %d\n", result.infectedComputerCount, result.time));
        }

        writer.flush();
        writer.close();
    }

    static Result dijkstra(int startNumber, int n, List<Node>[] graph) {
        int[] cost = new int[n + 1];
        Arrays.fill(cost, Integer.MAX_VALUE);
        boolean[] visited = new boolean[n + 1];

        PriorityQueue<Node> pq = new PriorityQueue<>() {{
            add(new Node(startNumber, 0));
        }};
        cost[startNumber] = 0;

        int count = 0;
        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;
            count++;

            for (Node nextNode : graph[currentNode.number]) {
                if (visited[nextNode.number]) {
                    continue;
                }

                int newTime = cost[currentNode.number] + nextNode.time;
                if (newTime < cost[nextNode.number]) {
                    cost[nextNode.number] = newTime;
                    pq.add(new Node(nextNode.number, newTime));
                }
            }
        }

        int timeMax = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            if (cost[i] == Integer.MAX_VALUE) {
                continue;
            }

            timeMax = Math.max(timeMax, cost[i]);
        }

        return new Result(count, timeMax);
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

class Result {
    int infectedComputerCount, time;
    Result(int infectedComputerCount, int time) {
        this.infectedComputerCount = infectedComputerCount;
        this.time = time;
    }
}
