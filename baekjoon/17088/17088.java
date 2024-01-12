import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[] numbers;
    static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        tokenizer = new StringTokenizer(reader.readLine());

        numbers = new int[N];
        for (int i = 0; i < N; i++) {
            numbers[i] = Integer.parseInt(tokenizer.nextToken());
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        if (N == 1 || N == 2) {
            result = 0;
            return;
        }

        int number1 = numbers[0];
        int number2 = numbers[1];

        for (int i = number1 - 1; i <= number1 + 1; i++) {
            for (int j = number2 - 1; j <= number2 + 1; j++) {
                int operationCount = 0;
                if (i != number1) {
                    operationCount++;
                }
                if (j != number2) {
                    operationCount++;
                }

                dfs(2, j - i, j, operationCount);
            }
        }

        if (result == Integer.MAX_VALUE) {
            result = -1;
        }
    }

    static void dfs(int index, int d, int prevNumber, int operationCount) {
        if (operationCount >= result) {
            return;
        }
        if (index >= numbers.length) {
            result = Math.min(result, operationCount);
            return;
        }

        int currNumber = numbers[index];

        for (int i = currNumber - 1; i <= currNumber + 1; i++) {
            if (i - prevNumber == d) {
                if (i == currNumber) {
                    dfs(index + 1, d, i, operationCount);
                } else {
                    dfs(index + 1, d, i, operationCount + 1);
                }
            }
        }
    }
}
