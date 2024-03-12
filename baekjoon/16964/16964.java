import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static Set<Integer>[] graph;
    static Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        graph = new Set[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new HashSet<>();
        }

        for (int i = 0; i < N - 1; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());

            graph[a].add(b);
            graph[b].add(a);
        }

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 1; i <= N; i++) {
            queue.add(Integer.parseInt(tokenizer.nextToken()));
        }

        solve();

        writer.write("1\n");
        writer.flush();
        writer.close();
    }

    static void solve() throws Exception {
        queue.poll();
        dfs(1, -1);
    }

    static void dfs(int currentNode, int prevNode) throws Exception {
        // leaf node
        if (graph[currentNode].size() == 1) {
            for (int node : graph[currentNode]) {
                if (node == prevNode) {
                    return;
                }
            }
        }

        graph[currentNode].remove(prevNode);
        for (int i = 0; i < graph[currentNode].size(); i++) {
            Integer nextNode = queue.poll();

            if (graph[currentNode].contains(nextNode)) {
                dfs(nextNode, currentNode);
            } else {
                writer.write("0\n");
                writer.flush();
                writer.close();
                System.exit(0);
            }
        }
    }
}
