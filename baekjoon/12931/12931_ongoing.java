import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[] b;
    static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        tokenizer = new StringTokenizer(reader.readLine());

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
        int[] a = new int[N];
        int count = fillRestTo1(a);
        dfs(a, count);
    }

    static void dfs(int[] a, int count) {
        System.out.println(Arrays.toString(a));

        if (Arrays.equals(a, b)) {
            result = Math.min(result, count);
            return;
        }
        if (isExceed(a)) {
            return;
        }

        for (int i = 0; i < N; i++) {
            a[i] *= 2;
        }
        dfs(a, count + 1);
        for (int i = 0; i < N; i++) {
            a[i] /= 2;
        }

        int restCount = fillRest(a);
        dfs(a, count + restCount);
    }

    static boolean isExceed(int[] a) {
        for (int i = 0; i < N; i++) {
            if (a[i] > b[i]) {
                return true;
            }
        }
        return false;
    }
    
    static int fillRestTo1(int[] a) {
        int count = 0;

        for (int i = 0; i < N; i++) {
            if (b[i] > 0) {
                a[i] = 1;
                count++;
            }
        }

        return count;
    }

    static int fillRest(int[] a) {
        int count = 0;

        for (int i = 0; i < N; i++) {
            count += b[i] - a[i];
            a[i] = b[i];
        }

        return count;
    }
}

