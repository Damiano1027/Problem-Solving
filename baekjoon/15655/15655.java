import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N, M;
    static StringTokenizer tokenizer;
    static int[] arr;
    static List<String> result = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        arr = new int[N];
        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(tokenizer.nextToken());
        }

        solve();

        for (String r : result) {
            writer.write(r + "\n");
        }

        writer.flush();
        writer.close();
    }

    static void solve() {
        Arrays.sort(arr);

        List<Integer> numbers = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < N; i++) {
            numbers.add(arr[i]);
            dfs(numbers, set);
            numbers.remove(numbers.size() - 1);
        }
    }

    static void dfs(List<Integer> numbers, Set<Integer> set) {
        if (numbers.size() == M) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int number : numbers) {
                stringBuilder.append(number).append(" ");
            }
            result.add(stringBuilder.toString());
            return;
        }

        int currentNumber = numbers.get(numbers.size() - 1);

        set.add(currentNumber);
        for (int i = 0; i < N; i++) {
            if (set.contains(arr[i]) || arr[i] < currentNumber) {
                continue;
            }

            numbers.add(arr[i]);
            dfs(numbers, set);
            numbers.remove(numbers.size() - 1);
        }
        set.remove(currentNumber);
    }
}
