import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static List<Integer>[] graph;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        graph = new List[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            graph[a].add(b);
            graph[b].add(a);
        }

        solve();

        writer.write("0\n");
        writer.flush();
        writer.close();
    }

    static void solve() throws Exception {
        dfs();
    }

    static void dfs() throws Exception {
        List<Integer> numbers = new ArrayList<>();
        Set<Integer> numberSet = new HashSet<>();
        boolean[] visited = new boolean[N];

        for (int i = 0; i < N; i++) {
            numbers.add(i);
            numberSet.add(i);
            visited[i] = true;

            dfs(numbers, numberSet, visited);

            numbers.remove(numbers.size() - 1);
            numberSet.remove(i);
            visited[i] = false;
        }
    }

    static void dfs(List<Integer> numbers, Set<Integer> numberSet, boolean[] visited) throws Exception {
        if (numbers.size() != numberSet.size()) {
            return;
        }
        if (numberSet.size() == 5) {
            writer.write("1\n");
            writer.flush();
            writer.close();
            System.exit(0);
        }

        int currentNumber = numbers.get(numbers.size() - 1);

        visited[currentNumber] = true;
        for (int aNumber : graph[currentNumber]) {
            if (visited[aNumber]) {
                continue;
            }

            numbers.add(aNumber);
            numberSet.add(aNumber);

            dfs(numbers, numberSet, visited);

            if (numbers.size() == numberSet.size()) {
                numberSet.remove(aNumber);
            }
            numbers.remove(numbers.size() - 1);
        }
        visited[currentNumber] = false;
    }
}
