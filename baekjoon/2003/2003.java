import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static long M;
    static long[] arr, sum;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Long.parseLong(tokenizer.nextToken());

        arr = new long[N];
        sum = new long[N];

        tokenizer = new StringTokenizer(reader.readLine());
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            arr[i] = Long.parseLong(tokenizer.nextToken());

            if (i == 0) {
                sum[i] = arr[i];
            } else {
                sum[i] = sum[i - 1] + arr[i];
            }
            i++;
        }

        int leftIndex = 0, rightIndex = 0;
        while (leftIndex < N && rightIndex < N) {
            long currentSum;
            if (leftIndex == 0) {
                currentSum = sum[rightIndex];
            } else {
                currentSum = sum[rightIndex] - sum[leftIndex - 1];
            }

            if (currentSum < M) {
                rightIndex++;
            } else if (currentSum > M) {
                leftIndex++;
            } else {
                result++;
                //System.out.printf("%d %d\n", leftIndex, rightIndex);
                rightIndex++;
            }
        }

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }
}
