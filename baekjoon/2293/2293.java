import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n, k;
    static List<Integer> coins = new ArrayList<>();
    static int[] dp;
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        k = Integer.parseInt(tokenizer.nextToken());

        dp = new int[k + 1];

        for (int i = 0; i < n; i++) {
            coins.add(Integer.parseInt(reader.readLine()));
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        Collections.sort(coins);
        dp[0] = 1;

        int firstCoin = coins.get(0);
        for (int i = 1; i <= k; i++) {
            if (i % firstCoin == 0) {
                dp[i] = 1;
            }
        }

        for (int i = 1; i < coins.size(); i++) {
            int coin = coins.get(i);

            for (int j = coin; j <= k; j++) {
                dp[j] += dp[j - coin];
            }
        }

        result = dp[k];
    }
}
