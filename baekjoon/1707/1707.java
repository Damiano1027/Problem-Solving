import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int K;

    public static void main(String[] args) throws Exception {
        K = Integer.parseInt(reader.readLine());

        for (int i = 0; i < K; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int V = Integer.parseInt(tokenizer.nextToken());
            int E = Integer.parseInt(tokenizer.nextToken());

            List<Integer>[] graph = new List[V + 1];
            for (int j = 1; j <= V; j++) {
                graph[j] = new ArrayList<>();
            }

            for (int j = 0; j < E; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int u = Integer.parseInt(tokenizer.nextToken());
                int v = Integer.parseInt(tokenizer.nextToken());

                graph[u].add(v);
                graph[v].add(u);
            }

            int[] colors = new int[V + 1];
            for (int j = 1; j <= V; j++) {
                if (colors[j] == 0) {
                    bfs(j, graph, colors);
                }
            }

            boolean bipartite = true;
            label:
            for (int j = 1; j <= V; j++) {
                for (int aNode : graph[j]) {
                    if (colors[aNode] == colors[j]) {
                        bipartite = false;
                        break label;
                    }
                }
            }

            if (bipartite) {
                writer.write("YES\n");
            } else {
                writer.write("NO\n");
            }
        }

        writer.flush();

        writer.close();
    }

    static void bfs(int startNode, List<Integer>[] graph, int[] colors) {
        Queue<Integer> queue = new LinkedList<>() {{
            add(startNode);
        }};
        colors[startNode] = 1;

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            for (int nextNode : graph[currentNode]) {
                if (colors[nextNode] == 0) {
                    queue.add(nextNode);
                    colors[nextNode] = (colors[currentNode] == 1) ? 2 : 1;
                }
            }
        }
    }
}
