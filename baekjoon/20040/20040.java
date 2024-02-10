import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n, m;
    static int[] arr;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());

        arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }

        boolean cycleDetected = false;
        for (int i = 1; i <= m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());

            int aParent = getParent(a);
            int bParent = getParent(b);

            if (!cycleDetected && aParent == bParent) {
                result = i;
                cycleDetected = true;
            }

            if (aParent != bParent) {
                if (aParent < bParent) {
                    arr[bParent] = aParent;
                    arr[b] = aParent;
                } else {
                    arr[aParent] = bParent;
                    arr[a] = bParent;
                }
            }
        }

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static int getParent(int number) {
        if (arr[number] == number) {
            return number;
        }

        return arr[number] = getParent(arr[number]);
    }
}
