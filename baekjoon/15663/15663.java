import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N, M;
    static StringTokenizer tokenizer;
    static int[] arr;
    static List<String> result = new ArrayList<>();
    static Set<String> set = new HashSet<>();

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
            if (set.contains(r)) {
                continue;
            }

            writer.write(r + "\n");
            set.add(r);
        }

        writer.flush();
        writer.close();
    }

    static void solve() {
        Arrays.sort(arr);

        List<Integer> numbers = new ArrayList<>();
        Set<Integer> indexSet = new HashSet<>();

        for (int i = 0; i < N; i++) {
            numbers.add(arr[i]);
            indexSet.add(i);
            dfs(numbers, indexSet);
            indexSet.remove(i);
            numbers.remove(numbers.size() - 1);
        }
    }

    static void dfs(List<Integer> numbers, Set<Integer> indexSet) {
        if (numbers.size() == M) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int number : numbers) {
                stringBuilder.append(number).append(" ");
            }
            result.add(stringBuilder.toString());
            return;
        }

        for (int i = 0; i < N; i++) {
            if (indexSet.contains(i)) {
                continue;
            }

            numbers.add(arr[i]);
            indexSet.add(i);
            dfs(numbers, indexSet);
            indexSet.remove(i);
            numbers.remove(numbers.size() - 1);
        }
    }
}
