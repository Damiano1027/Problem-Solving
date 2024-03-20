import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, K;
    static int[] cost = new int[100001], prev = new int[100001];
    static List<Integer> path = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        solve();

        writer.write(cost[K] + "\n");
        for (int number : path) {
            writer.write(String.format("%d ", number));
        }
        writer.newLine();
        writer.flush();
        writer.close();
    }

    static void solve() {
        dijkstra();

        int currentNumber = K;
        while (true) {
            path.add(0, currentNumber);

            if (currentNumber == N) {
                break;
            }
            currentNumber = prev[currentNumber];
        }
    }

    static void dijkstra() {
        Arrays.fill(cost, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        boolean[] visited = new boolean[100001];

        PriorityQueue<Node> pq = new PriorityQueue<>() {{
            add(new Node(N, 0));
        }};
        cost[N] = 0;

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();

            if (visited[currentNode.number]) {
                continue;
            }
            visited[currentNode.number] = true;

            for (int nextNumber : nextNumbers(currentNode.number)) {
                if (!isInRange(nextNumber) || visited[nextNumber]) {
                    continue;
                }

                int newCost = cost[currentNode.number] + 1;
                if (newCost < cost[nextNumber]) {
                    cost[nextNumber] = newCost;
                    pq.add(new Node(nextNumber, newCost));
                    prev[nextNumber] = currentNode.number;
                }
            }
        }
    }

    static int[] nextNumbers(int number) {
        int[] numbers = new int[3];
        numbers[0] = number - 1;
        numbers[1] = number + 1;
        numbers[2] = number * 2;
        return numbers;
    }

    static boolean isInRange(int number) {
        return number >= 0 && number <= 100000;
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
