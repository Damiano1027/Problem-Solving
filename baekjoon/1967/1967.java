import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n;
    static List<Edge>[] graph;
    static int result;

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(reader.readLine());
        graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int weight = Integer.parseInt(tokenizer.nextToken());
            graph[a].add(new Edge(b, weight));
            graph[b].add(new Edge(a, weight));
        }

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        int endVertex1 = bfs(1, false);
        int endVertex2 = bfs(endVertex1, true);
        result = endVertex2;
    }

    static int bfs(int startNode, boolean requiredResult) {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(startNode, 0));
        }};
        boolean[] visited = new boolean[n + 1];
        visited[startNode] = true;
        int max = -1;
        int endVertex = -1;

        while (!queue.isEmpty()) {
            Bfs bfs = queue.poll();

            if (max == -1) {
                max = bfs.weightSum;
                endVertex = bfs.node;
            } else {
                if (bfs.weightSum > max) {
                    max = bfs.weightSum;
                    endVertex = bfs.node;
                }
            }

            for (Edge edge: graph[bfs.node]) {
                if (!visited[edge.nextNode]) {
                    queue.add(new Bfs(edge.nextNode, bfs.weightSum + edge.weight));
                    visited[edge.nextNode] = true;
                }
            }
        }

        return requiredResult ? max : endVertex;
    }


}

class Edge {
    int nextNode, weight;
    Edge(int nextNode, int weight) {
        this.nextNode = nextNode;
        this.weight = weight;
    }
}

class Bfs {
    int node, weightSum;
    Bfs(int node, int weightSum) {
        this.node = node;
        this.weightSum = weightSum;
    }
}
