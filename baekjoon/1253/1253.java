import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[] arr;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        arr = new int[N];

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(tokenizer.nextToken());
        }

        Arrays.sort(arr);

        for (int i = 0; i < N; i++) {
            int targetSum = arr[i];

            int leftIndex = 0, rightIndex = N - 1;

            while (leftIndex < rightIndex) {
                if (leftIndex == i) {
                    leftIndex++;
                    continue;
                }
                if (rightIndex == i) {
                    rightIndex--;
                    continue;
                }

                int sum = arr[leftIndex] + arr[rightIndex];

                if (sum > targetSum) {
                    rightIndex--;
                } else if (sum < targetSum) {
                    leftIndex++;
                } else {
                    result++;
                    break;
                }
            }
        }

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }
}
