import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static String str1, str2;
    static int[][] dp;
    static int result;

    public static void main(String[] args) throws Exception {
        str1 = reader.readLine();
        str2 = reader.readLine();

        dp = new int[str2.length()][str1.length()];

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) == str2.charAt(0)) {
                for (int j = i; j < str1.length(); j++) {
                    dp[0][j] = 1;
                }
                break;
            }
        }
        for (int j = 0; j < str2.length(); j++) {
            if (str2.charAt(j) == str1.charAt(0)) {
                for (int i = j; i < str2.length(); i++) {
                    dp[i][0] = 1;
                }
                break;
            }
        }

        for (int i = 1; i < str2.length(); i++) {
            for (int j = 1; j < str1.length(); j++) {
                if (str1.charAt(j) == str2.charAt(i)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        result = dp[str2.length() - 1][str1.length() - 1];
    }
}
