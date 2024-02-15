import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n, m;
    static class Edge {
        int to, time;
        Edge(int to, int time) {
            this.to = to;
            this.time = time;
        }
    }
    static List<Edge>[] graph, reversedGraph;
    static int[] cpTime;
    static int[] indegree, reversedIndegree;
    static boolean[] valid;
    static int startNode, endNode;
    static int result1, result2;

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(reader.readLine());
        m = Integer.parseInt(reader.readLine());
        graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        reversedGraph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            reversedGraph[i] = new ArrayList<>();
        }
        cpTime = new int[n + 1];
        indegree = new int[n + 1];
        reversedIndegree = new int[n + 1];
        valid = new boolean[n + 1];

        for (int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(tokenizer.nextToken());
            int to = Integer.parseInt(tokenizer.nextToken());
            int time = Integer.parseInt(tokenizer.nextToken());

            graph[from].add(new Edge(to, time));
            indegree[to]++;
            reversedGraph[to].add(new Edge(from, time));
            reversedIndegree[from]++;
        }

        tokenizer = new StringTokenizer(reader.readLine());
        startNode = Integer.parseInt(tokenizer.nextToken());
        endNode = Integer.parseInt(tokenizer.nextToken());

        solve();

        writer.write(String.format("%d\n%d\n", result1, result2));
        writer.flush();

        writer.close();
    }

    static void solve() {
        topologySort();
        result1 = cpTime[endNode];
        result2 = reversedTopologySort();
    }

    static void topologySort() {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (Edge nextEdge: graph[node]) {
                int nextNodeCpTime = cpTime[node] + nextEdge.time;
                cpTime[nextEdge.to] = Math.max(cpTime[nextEdge.to], nextNodeCpTime);

                indegree[nextEdge.to]--;
                if (indegree[nextEdge.to] == 0) {
                    queue.add(nextEdge.to);
                }
            }
        }
    }

    static int reversedTopologySort() {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(endNode);
        valid[endNode] = true;
        int count = 0;

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (Edge nextEdge: reversedGraph[node]) {
                int nextNodeCpTime = cpTime[node] - nextEdge.time;

                if (valid[node] && nextNodeCpTime == cpTime[nextEdge.to]) {
                    count++;
                    valid[nextEdge.to] = true;
                }

                reversedIndegree[nextEdge.to]--;
                if (reversedIndegree[nextEdge.to] == 0) {
                    queue.add(nextEdge.to);
                }
            }
        }

        return count;
    }
}
