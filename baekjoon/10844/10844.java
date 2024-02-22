import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static long[][] dp;
    static int mod = 1000000000;
    static long result = 0;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        dp = new long[N + 1][10];

        for (int j = 1; j <= 9; j++) {
            dp[1][j] = 1;
        }

        for (int i = 2; i <= N; i++) {
            dp[i][0] = dp[i - 1][1] % mod;
            for (int j = 1; j <= 8; j++) {
                dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j + 1]) % mod;
            }
            dp[i][9] = dp[i - 1][8] % mod;
        }

        for (int j = 0; j <= 9; j++) {
            result += dp[N][j];
        }
        result %= mod;

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }
}
