import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int V;
    static List<Vertex>[] graph;
    static class Vertex {
        int number, distance;

        Vertex(int number, int distance) {
            this.number = number;
            this.distance = distance;
        }
    }
    static int result = -1;

    public static void main(String[] args) throws Exception {
        V = Integer.parseInt(reader.readLine());

        graph = new List[V + 1];

        for (int i = 1; i <= V; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < V; i++) {
            tokenizer = new StringTokenizer(reader.readLine());

            int vertex = Integer.parseInt(tokenizer.nextToken());
            while (true) {
                int connectedVertex = Integer.parseInt(tokenizer.nextToken());
                if (connectedVertex == -1) {
                    break;
                }

                int distance = Integer.parseInt(tokenizer.nextToken());

                graph[vertex].add(new Vertex(connectedVertex, distance));
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        Vertex endVertex1 = dfs(1, 0, new boolean[V + 1]);
        Vertex endVertex2 = dfs(endVertex1.number, 0, new boolean[V + 1]);
        result = endVertex2.distance;
    }

    static Vertex dfs(int vertex, int totalDistance, boolean[] visited) {
        Vertex maxDistanceVertex = null;
        visited[vertex] = true;

        for (Vertex connectedVertex: graph[vertex]) {
            if (visited[connectedVertex.number]) {
                continue;
            }

            Vertex dfsResult = dfs(connectedVertex.number, totalDistance + connectedVertex.distance, visited);

            if (maxDistanceVertex == null) {
                maxDistanceVertex = dfsResult;
            } else {
                if (maxDistanceVertex.distance < dfsResult.distance) {
                    maxDistanceVertex = dfsResult;
                }
            }
        }

        visited[vertex] = false;

        return (maxDistanceVertex == null) ? new Vertex(vertex, totalDistance) : maxDistanceVertex;
    }
}
