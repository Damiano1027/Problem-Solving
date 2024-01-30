import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[] arr;
    static int[] dp1, dp2;
    static int result = -1;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        arr = new int[N];
        dp1 = new int[N];
        dp2 = new int[N];
        Arrays.fill(dp1, -1);
        Arrays.fill(dp2, -1);

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(tokenizer.nextToken());
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        dp1();
        dp2();

        for (int i = 0; i < N; i++) {
            result = (result == -1) ? dp1[i] + dp2[i] - 1 : Math.max(result, dp1[i] + dp2[i] - 1);
        }
    }

    static void dp1() {
        dp1[0] = 1;

        for (int i = 1; i < N; i++) {
            dp1[i] = 1;

            int max = -1;
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    max = (max == -1) ? dp1[j] : Math.max(max, dp1[j]);
                }
            }

            if (max != -1) {
                dp1[i] += max;
            }
        }
    }

    static void dp2() {
        dp2[N - 1] = 1;

        for (int i = N - 2; i >= 0; i--) {
            dp2[i] = 1;

            int max = -1;
            for (int j = N - 1; j > i; j--) {
                if (arr[i] > arr[j]) {
                    max = (max == -1) ? dp2[j] : Math.max(max, dp2[j]);
                }
            }

            if (max != -1) {
                dp2[i] += max;
            }
        }
    }
}
