import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    private static ArrayList<ArrayList<Integer>> graph;

    public static void main(String[] args) throws IOException {
        StringTokenizer stringTokenizer;

        stringTokenizer = new StringTokenizer(reader.readLine());
        int N = Integer.parseInt(stringTokenizer.nextToken());
        int M = Integer.parseInt(stringTokenizer.nextToken());
        int V = Integer.parseInt(stringTokenizer.nextToken());

        graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            stringTokenizer = new StringTokenizer(reader.readLine());
            int node1 = Integer.parseInt(stringTokenizer.nextToken());
            int node2 = Integer.parseInt(stringTokenizer.nextToken());

            graph.get(node1).add(node2);
            graph.get(node2).add(node1);
        }

        for (int i = 1; i <= N; i++) {
            graph.get(i).sort(Integer::compareTo);
        }

        dfs(V, new boolean[N + 1]);
        writer.write("\n");
        writer.flush();

        bfs(V, new boolean[N + 1]);
        writer.write("\n");
        writer.flush();

        writer.close();
    }

    private static void dfs(int currentNode, boolean[] visited) throws IOException {
        visited[currentNode] = true;
        writer.write(String.format("%d ", currentNode));

        for (int adjoinNode : graph.get(currentNode)) {
            if (!visited[adjoinNode]) {
                dfs(adjoinNode, visited);
            }
        }
    }

    private static void bfs(int startNode, boolean[] visited) throws IOException {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);
        visited[startNode] = true;

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            writer.write(String.format("%d ", currentNode));

            for (int adjoinNode : graph.get(currentNode)) {
                if (!visited[adjoinNode]) {
                    visited[adjoinNode] = true;
                    queue.add(adjoinNode);
                }
            }
        }
    }
}
