import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, K;
    static boolean[] visited = new boolean[100001];
    static int[] time = new int[100001];
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        dijkstra();
        result = time[K];
    }

    static void dijkstra() {
        Arrays.fill(time, Integer.MAX_VALUE);
        time[N] = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>() {{
            add(new Node(N, 0));
        }};

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited[currentNode.location]) {
                continue;
            }
            visited[currentNode.location] = true;

            int newTime = time[currentNode.location] + 1;
            if (isInRange(currentNode.location - 1) && newTime < time[currentNode.location - 1]) {
                time[currentNode.location - 1] = newTime;
                queue.add(new Node(currentNode.location - 1, newTime));
            }
            if (isInRange(currentNode.location + 1) && newTime < time[currentNode.location + 1]) {
                time[currentNode.location + 1] = newTime;
                queue.add(new Node(currentNode.location + 1, newTime));
            }

            newTime = time[currentNode.location];
            if (isInRange(currentNode.location * 2) && newTime < time[currentNode.location * 2]) {
                time[currentNode.location * 2] = newTime;
                queue.add(new Node(currentNode.location * 2, newTime));
            }
        }
    }

    static boolean isInRange(int location) {
        return location >= 0 && location <= 100000;
    }
}

class Node implements Comparable<Node> {
    int location, value;
    Node(int location, int value) {
        this.location = location;
        this.value = value;
    }
    public int compareTo(Node node) {
        return value - node.value;
    }
}
