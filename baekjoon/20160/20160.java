import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int V, E;
    static List<Node>[] graph;
    static int[] numbers = new int[10];
    static Map<Integer, long[]> madamCostMap = new HashMap<>();
    static long[] meCost;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        V = Integer.parseInt(tokenizer.nextToken());
        E = Integer.parseInt(tokenizer.nextToken());

        graph = new List[V + 1];
        for (int i = 1; i <= V; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());
            int w = Integer.parseInt(tokenizer.nextToken());

            graph[u].add(new Node(v, w));
            graph[v].add(new Node(u, w));
        }

        Set<Integer> set = new HashSet<>();
        tokenizer = new StringTokenizer(reader.readLine());
        int meStartNumber = Integer.parseInt(reader.readLine());

        for (int i = 0; i < 10; i++) {
            int number = Integer.parseInt(tokenizer.nextToken());
            numbers[i] = number;

            if (set.contains(number)) {
                continue;
            }
            set.add(number);
            dijkstraForMadam(number);
        }

        long[] madamCost = new long[10];
        Arrays.fill(madamCost, Long.MAX_VALUE);
        madamCost[0] = 0;
        for (int i = 0; i < 9;) {
            int from = numbers[i];
            long[] cost = madamCostMap.get(from);
            boolean enable = false;

            for (int j = i + 1; j < 10; j++) {
                int to = numbers[j];

                if (cost[to] == Long.MAX_VALUE) {
                    continue;
                }

                madamCost[j] = madamCost[i] + cost[to];
                i = j;
                enable = true;
                break;
            }

            if (!enable) {
                i++;
            }
        }

        meCost = new long[V + 1];
        dijkstraForMe(meStartNumber);

        Queue<Integer> resultCandidate = new PriorityQueue<>();
        for (int i = 0; i < 10; i++) {
            int to = numbers[i];
            long madamArriveCost = madamCost[i];
            long meArriveCost = meCost[to];

            if (madamArriveCost == Long.MAX_VALUE || meArriveCost == Long.MAX_VALUE) {
                continue;
            }

            if (meArriveCost <= madamArriveCost) {
                resultCandidate.add(to);
            }
        }

        if (resultCandidate.isEmpty()) {
            writer.write("-1\n");
        } else {
            writer.write(resultCandidate.poll() + "\n");
        }

        writer.flush();
        writer.close();
    }

    static void dijkstraForMadam(int startNumber) {
        long[] cost = new long[V + 1];
        boolean[] visited = new boolean[V + 1];
        Arrays.fill(cost, Long.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>() {{
            add(new Node(startNumber, 0));
        }};
        cost[startNumber] = 0;

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

                long newWeight = cost[currentNode.number] + nextNode.cost;
                if (newWeight < cost[nextNode.number]) {
                    cost[nextNode.number] = newWeight;
                    pq.add(new Node(nextNode.number, newWeight));
                }
            }
        }

        madamCostMap.put(startNumber, cost);
    }

    static void dijkstraForMe(int startNumber) {
        boolean[] visited = new boolean[V + 1];
        Arrays.fill(meCost, Long.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>() {{
            add(new Node(startNumber, 0));
        }};
        meCost[startNumber] = 0;

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

                long newWeight = meCost[currentNode.number] + nextNode.cost;
                if (newWeight < meCost[nextNode.number]) {
                    meCost[nextNode.number] = newWeight;
                    pq.add(new Node(nextNode.number, newWeight));
                }
            }
        }
    }
}

class Node implements Comparable<Node> {
    int number;
    long cost;
    Node(int number, long cost) {
        this.number = number;
        this.cost = cost;
    }
    public int compareTo(Node node) {
        return Long.compare(cost, node.cost);
    }
    public String toString() {
        return String.format("%d %d", number, cost);
    }
}
