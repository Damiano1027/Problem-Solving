import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static Set<Integer>[] graph;
    static int[] memo;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        graph = new Set[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new HashSet<>();
        }
        memo = new int[N + 1];
        Arrays.fill(memo, -1);

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());

            graph[u].add(v);
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        boolean[] visited = new boolean[N + 1];
        for (int number = 1; number <= N; number++) {
            if (memo[number] == -1) {
                dfs(number, visited);
            }
        }

        for (int number = 1; number <= N; number++) {
            if (memo[number] == 1) {
                result++;
            }
        }
    }

    static int dfs(int currentNumber, boolean[] visited) {
        if (visited[currentNumber]) {
            return 0;
        }
        if (memo[currentNumber] != -1) {
            return memo[currentNumber];
        }

        boolean cycleDetected = false;

        visited[currentNumber] = true;
        for (int nextNumber : graph[currentNumber]) {
            if (dfs(nextNumber, visited) == 0) {
                cycleDetected = true;
            }
        }
        visited[currentNumber] = false;

        return memo[currentNumber] = cycleDetected ? 0 : 1;
    }
}
