import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, R, Q;
    static List<Integer>[] graph, tree;
    static int[] memo;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        R = Integer.parseInt(tokenizer.nextToken());
        Q = Integer.parseInt(tokenizer.nextToken());

        graph = new List[N + 1];
        tree = new List[N + 1];
        memo = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());

            graph[u].add(v);
            graph[v].add(u);
        }

        makeTree();

        for (int i = 0; i < Q; i++) {
            int number = Integer.parseInt(reader.readLine());
            writer.write(dfs(number) + "\n");
        }

        writer.flush();
        writer.close();
    }

    static void makeTree() {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(R, -1));
        }};

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            for (int nextNumber : graph[current.number]) {
                if (nextNumber == current.prevNumber) {
                    continue;
                }

                queue.add(new Bfs(nextNumber, current.number));
                tree[current.number].add(nextNumber);
            }
        }
    }

    static int dfs(int currentNumber) {
        if (memo[currentNumber] != 0) {
            return memo[currentNumber];
        }
        if (tree[currentNumber].isEmpty()) {
            return memo[currentNumber] = 1;
        }

        memo[currentNumber] = 1;
        for (int nextNumber : tree[currentNumber]) {
            memo[currentNumber] += dfs(nextNumber);
        }

        return memo[currentNumber];
    }
}

class Bfs {
    int number, prevNumber;
    Bfs(int number, int prevNumber) {
        this.number = number;
        this.prevNumber = prevNumber;
    }
}
