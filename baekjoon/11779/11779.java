import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static List<Node>[] graph;
    static int[] cost, prevNumber;
    static boolean[] visited;
    static int n, m;
    static int start, end;
    static int result1, result2;
    static List<Integer> result3;

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(reader.readLine());
        m = Integer.parseInt(reader.readLine());

        graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(tokenizer.nextToken());
            int to = Integer.parseInt(tokenizer.nextToken());
            int cost = Integer.parseInt(tokenizer.nextToken());
            graph[from].add(new Node(to, cost));
        }

        tokenizer = new StringTokenizer(reader.readLine());
        start = Integer.parseInt(tokenizer.nextToken());
        end = Integer.parseInt(tokenizer.nextToken());

        cost = new int[n + 1];
        prevNumber = new int[n + 1];
        visited = new boolean[n + 1];

        solve();

        writer.write(result1 + "\n");
        writer.write(result2 + "\n");
        for (int r : result3) {
            writer.write(String.format("%d ", r));
        }
        writer.newLine();
        writer.flush();

        writer.close();
    }

    static void solve() {
        if (start == end) {
            result1 = 0;
            result2 = 1;
            result3.add(start);
            return;
        }

        dijkstra();
        result1 = cost[end];
        List<Integer> numbers = getMinCostNumbers();
        result2 = numbers.size();
        result3 = numbers;
    }

    static void dijkstra() {
        Arrays.fill(cost, Integer.MAX_VALUE);
        PriorityQueue<Node> queue = new PriorityQueue<>() {{
            add(new Node(start, 0));
        }};
        cost[start] = 0;
        prevNumber[start] = -1;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;

            for (Node nextNode : graph[currentNode.number]) {
                int newCost = cost[currentNode.number] + nextNode.cost;
                if (newCost < cost[nextNode.number]) {
                    cost[nextNode.number] = newCost;
                    queue.add(new Node(nextNode.number, newCost));
                    prevNumber[nextNode.number] = currentNode.number;
                }
            }
        }
    }

    static List<Integer> getMinCostNumbers() {
        List<Integer> numbers = new LinkedList<>();
        numbers.add(end);

        int currentNumber = end;
        while (true) {
            int prev = prevNumber[currentNumber];
            numbers.add(0, prev);

            if (prev == start) {
                break;
            }
            currentNumber = prev;
        }

        return numbers;
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
