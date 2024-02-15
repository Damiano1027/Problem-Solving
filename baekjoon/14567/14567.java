import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static List<Integer>[] graph;
    static int[] indegree, minTerm;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        indegree = new int[N + 1];
        minTerm = new int[N + 1];
        graph = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());

            graph[a].add(b);
            indegree[b]++;
        }

        solve();

        for (int i = 1; i <= N; i++) {
            writer.write(String.format("%d ", minTerm[i]));
        }
        writer.newLine();
        writer.flush();

        writer.close();
    }

    static void solve() {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
                minTerm[i] = 1;
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (int nextNode: graph[node]) {
                indegree[nextNode]--;
                if (indegree[nextNode] == 0) {
                    queue.add(nextNode);
                    minTerm[nextNode] = minTerm[node] + 1;
                }
            }
        }
    }
}
