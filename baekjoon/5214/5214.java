import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, K, M;
    static Map<Integer, List<Integer>> graphMap = new HashMap<>();
    static Map<Integer, Integer> costMap = new HashMap<>();
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        for (int i = -M; i <= N; i++) {
            graphMap.put(i, new ArrayList<>());
            costMap.put(i, Integer.MAX_VALUE);
        }

        for (int i = 1; i <= M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());

            for (int j = 0; j < K; j++) {
                int number = Integer.parseInt(tokenizer.nextToken());
                graphMap.get(number).add(-i);
                graphMap.get(-i).add(number);
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        if (N == 1) {
            result = 1;
            return;
        }

        dijkstra();

        if (costMap.get(N) == Integer.MAX_VALUE) {
            result = -1;
        } else {
            result = costMap.get(N) - 1;
        }
    }

    static void dijkstra() {
        PriorityQueue<Node> pq = new PriorityQueue<>() {{
            add(new Node(1, 1));
        }};
        costMap.put(1, 1);
        Set<Integer> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();

            if (visited.contains(currentNode.number)) {
                continue;
            }
            visited.add(currentNode.number);

            for (Integer nextNumber : graphMap.get(currentNode.number)) {
                if (visited.contains(nextNumber)) {
                    continue;
                }

                int newCost;
                // 하이퍼튜브인 경우
                if (nextNumber < 0) {
                    newCost = costMap.get(currentNode.number) + 1;
                }
                // 하이퍼튜브가 아닌 경우
                else {
                    if (nextNumber == N) {
                        newCost = costMap.get(currentNode.number) + 1;
                    } else {
                        newCost = costMap.get(currentNode.number);
                    }
                }

                if (newCost < costMap.get(nextNumber)) {
                    costMap.put(nextNumber, newCost);
                    pq.add(new Node(nextNumber, newCost));
                }
            }
        }
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
