import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static List<Long> numbers = new ArrayList<>();
    static List<Integer> result = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        tokenizer = new StringTokenizer(reader.readLine());

        for (int i = 0; i < N; i++) {
            numbers.add(Long.parseLong(tokenizer.nextToken()));
        }

        solve();
    }

    static void solve() throws Exception {
        for (long number: numbers) {
            dfs(number, new HashSet<>(numbers), new ArrayList<>());
        }
    }

    static void dfs(long currentNumber, Set<Long> set, List<Long> result) throws Exception {
        set.remove(currentNumber);
        result.add(currentNumber);

        if (result.size() >= N) {
            for (int i = result.size() - 1; i >= 0; i--) {
                writer.write(String.format("%d ", result.get(i)));
            }
            writer.newLine();
            writer.flush();
            writer.close();
            System.exit(0);
        }

        if (set.contains(currentNumber * 3)) {
            dfs(currentNumber * 3, set, result);
        }
        if (currentNumber % 2 == 0 && set.contains(currentNumber / 2)) {
            dfs(currentNumber / 2, set, result);
        }

        result.remove(result.size() - 1);
        set.add(currentNumber);
    }
}
