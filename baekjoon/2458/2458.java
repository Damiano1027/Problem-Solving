import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        List<Integer>[] originalGraph, reversedGraph;
        originalGraph = new List[N + 1];
        reversedGraph = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            originalGraph[i] = new ArrayList<>();
            reversedGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());

            originalGraph[a].add(b);
            reversedGraph[b].add(a);
        }

        solve(originalGraph, reversedGraph);

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve(List<Integer>[] originalGraph, List<Integer>[] reversedGraph) {
        for (int i = 1; i <= N; i++) {
            int backCount = bfs(i, originalGraph);
            int frontCount = bfs(i, reversedGraph);
            if (backCount + frontCount == N - 1) {
                result++;
            }
        }
    }

    static int bfs(int startNumber, List<Integer>[] graph) {
        Queue<Integer> queue = new LinkedList<>() {{
            add(startNumber);
        }};
        boolean[] visited = new boolean[N + 1];
        visited[startNumber] = true;

        int count = 0;
        while (!queue.isEmpty()) {
            int currentNumber = queue.poll();
            count++;

            for (int nextNumber : graph[currentNumber]) {
                if (!visited[nextNumber]) {
                    queue.add(nextNumber);
                    visited[nextNumber] = true;
                }
            }
        }

        return count - 1;
    }
}
