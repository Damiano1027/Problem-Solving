import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, k;
    static int[] cost, uf;
    static Map<Integer, List<Integer>> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        k = Integer.parseInt(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());
        cost = new int[N + 1];
        uf = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            cost[i] = Integer.parseInt(tokenizer.nextToken());
            uf[i] = i;
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            union(a, b);
        }

        for (int i = 1; i <= N; i++) {
            int root = find(i);

            if (map.get(root) == null) {
                map.put(root, new ArrayList<>());
            }
            map.get(root).add(cost[i]);
        }

        int cost = 0;
        for (Integer key : map.keySet()) {
            List<Integer> numbers = map.get(key);
            Collections.sort(numbers);
            cost += numbers.get(0);
        }

        if (cost <= k) {
            writer.write(String.format("%d\n", cost));
        } else {
            writer.write("Oh no\n");
        }
        writer.flush();

        writer.close();
    }

    static int find(int number) {
        if (uf[number] == number) {
            return number;
        }

        return uf[number] = find(uf[number]);
    }

    static void union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        uf[aRoot] = bRoot;
    }
}
