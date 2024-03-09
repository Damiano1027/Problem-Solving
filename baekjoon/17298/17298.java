import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[] numbers, result;
    static Stack<Integer> stack = new Stack<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        tokenizer = new StringTokenizer(reader.readLine());

        numbers = new int[N];
        result = new int[N];

        for (int i = 0; i < N; i++) {
            numbers[i] = Integer.parseInt(tokenizer.nextToken());
            result[i] = -1;
        }

        solve();

        // StringBuilder를 사용하지 않으면 시간초과 발생
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < N; i++) {
            stringBuilder.append(result[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");

        writer.write(stringBuilder.toString());
        writer.flush();
        writer.close();
    }

    static void solve() {
        stack.add(0);

        for (int i = 1; i < N; i++) {
            while (!stack.isEmpty() && numbers[i] > numbers[stack.peek()]) {
                result[stack.pop()] = numbers[i];
            }
            stack.add(i);
        }
    }
}
