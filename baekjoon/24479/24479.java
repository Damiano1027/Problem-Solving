import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, R;
    static List<Integer>[] graph;
    static int[] result;
    static int count = 1;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        R = Integer.parseInt(tokenizer.nextToken());

        graph = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }
        result = new int[N + 1];

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());
            graph[u].add(v);
            graph[v].add(u);
        }

        for (int i = 1; i <= N; i++) {
            Collections.sort(graph[i]);
        }

        solve();

        for (int i = 1; i <= N; i++) {
            writer.write(result[i] + "\n");
        }
        writer.flush();
        writer.close();
    }

    static void solve() {
        dfs(R, new boolean[N + 1]);
    }

    static void dfs(int currentNode, boolean[] visited) {
        result[currentNode] = count++;

        visited[currentNode] = true;
        for (int nextNode : graph[currentNode]) {
            if (visited[nextNode]) {
                continue;
            }

            dfs(nextNode, visited);
        }
    }
}
