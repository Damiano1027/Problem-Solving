import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N, M;
    static StringTokenizer tokenizer;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        solve();

        writer.flush();
        writer.close();
    }

    static void solve() throws Exception {
        boolean[] visited = new boolean[N + 1];
        List<Integer> numbers = new ArrayList<>();

        for (int number = 1; number <= N; number++) {
            numbers.add(number);
            dfs(numbers, visited);
            numbers.remove(numbers.size() - 1);
        }
    }

    static void dfs(List<Integer> numbers, boolean[] visited) throws Exception {
        if (numbers.size() >= M) {
            for (int number : numbers) {
                writer.write(number + " ");
            }
            writer.newLine();
            return;
        }

        int currentNumber = numbers.get(numbers.size() - 1);

        visited[currentNumber] = true;
        for (int number = 1; number <= N; number++) {
            if (!visited[number]) {
                numbers.add(number);
                dfs(numbers, visited);
                numbers.remove(numbers.size() - 1);
            }
        }
        visited[currentNumber] = false;
    }
}
