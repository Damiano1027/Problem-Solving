import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n, m;
    static List<Node>[] graph;
    static int[][] prevNumberMatrix, resultMatrix;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());

        prevNumberMatrix = new int[n + 1][n + 1];
        resultMatrix = new int[n + 1][n + 1];
        graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int time = Integer.parseInt(tokenizer.nextToken());

            graph[a].add(new Node(b, time));
            graph[b].add(new Node(a, time));
        }

        solve();

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (resultMatrix[i][j] == -1) {
                    writer.write("- ");
                } else {
                    writer.write(resultMatrix[i][j] + " ");
                }
            }
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }

    static void solve() {
        for (int i = 1; i <= n; i++) {
            dijkstra(i);
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    resultMatrix[i][j] = -1;
                    continue;
                }

                resultMatrix[i][j] = getSmallestPathFirstNode(i, j);
            }
        }
    }

    static void dijkstra(int startNumber) {
        int[] times = new int[n + 1];
        boolean[] visited = new boolean[n + 1];
        int[] prevNumbers = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            times[i] = Integer.MAX_VALUE;
        }
        PriorityQueue<Node> pq = new PriorityQueue<>() {{
            add(new Node(startNumber, 0));
        }};
        times[startNumber] = 0;
        prevNumbers[startNumber] = -1;

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;

            for (Node nextNode : graph[currentNode.number]) {
                int newTime = times[currentNode.number] + nextNode.time;
                if (!visited[nextNode.number] && newTime < times[nextNode.number]) {
                    times[nextNode.number] = newTime;
                    pq.add(new Node(nextNode.number, newTime));
                    prevNumbers[nextNode.number] = currentNode.number;
                }
            }
        }

        prevNumberMatrix[startNumber] = prevNumbers;
    }

    static int getSmallestPathFirstNode(int startNumber, int targetNumber) {
        int currentNumber = targetNumber;
        while (true) {
            int prev = prevNumberMatrix[startNumber][currentNumber];

            if (prev == startNumber) {
                return currentNumber;
            }
            currentNumber = prev;
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
