import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[] arr, sum;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        arr = new int[N + 1];
        sum = new int[N + 1];

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(tokenizer.nextToken());

            if (i == 1) {
                sum[i] = arr[i];
                continue;
            }
            sum[i] = sum[i - 1] + arr[i];
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(tokenizer.nextToken());
            int to = Integer.parseInt(tokenizer.nextToken());

            if (from == 1) {
                writer.write(sum[to] + "\n");
                continue;
            }
            writer.write((sum[to] - sum[from - 1]) + "\n");
        }

        writer.flush();

        writer.close();
    }
}
