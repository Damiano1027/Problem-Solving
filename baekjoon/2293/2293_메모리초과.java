import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n, k;
    static List<Integer> coins = new ArrayList<>();
    static Set<Map<Integer, Integer>> mapSet = new HashSet<>();
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        k = Integer.parseInt(tokenizer.nextToken());

        for (int i = 0; i < n; i++) {
            coins.add(Integer.parseInt(reader.readLine()));
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int coin: coins) {
            map.put(coin, 0);
        }

        dfs(k, map);
    }

    static void dfs(int amount, Map<Integer, Integer> map) {
        Map<Integer, Integer> newMap = new HashMap<>(map);

        if (mapSet.contains(newMap)) {
            return;
        }

        if (amount == 0) {
            if (!mapSet.contains(newMap)) {
                mapSet.add(newMap);
                result++;
            }
            return;
        } else if (amount < 0) {
            return;
        }

        for (int coin: coins) {
            newMap.put(coin, newMap.get(coin) + 1);
            dfs(amount - coin, newMap);
            newMap.put(coin, newMap.get(coin) - 1);
        }

        mapSet.add(newMap);
    }
}
