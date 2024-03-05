import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[] arr;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        M = Integer.parseInt(reader.readLine());

        arr = new int[N];

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(tokenizer.nextToken());
        }

        Arrays.sort(arr);

        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                int sum = arr[i] + arr[j];
                if (sum == M) {
                    result++;
                    break;
                } else if (sum > M) {
                    break;
                }
            }
        }

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }
}
