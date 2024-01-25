import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[] b;
    static int[] a;
    static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        tokenizer = new StringTokenizer(reader.readLine());

        a = new int[N];
        b = new int[N];
        for (int i = 0; i < N; i++) {
            b[i] = Integer.parseInt(tokenizer.nextToken());
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        int count = 0;

        while (!Arrays.equals(a, b)) {
            if (isAllEven(b)) {
                for (int i = 0; i < N; i++) {
                    b[i] /= 2;
                }
                count++;
            } else {
                for (int i = 0; i < N; i++) {
                    if (b[i] % 2 != 0) {
                        b[i]--;
                        count++;
                    }
                }
            }
        }

        result = count;
    }

    static boolean isAllEven(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % 2 != 0) {
                return false;
            }
        }
        return true;
    }
}
