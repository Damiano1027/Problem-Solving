import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static long[] S, R;
    static long result = 0;
    static Map<Long, Integer> frequencyMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        S = new long[N + 1]; // 누적 합 배열
        R = new long[N + 1]; // 누적 합을 M으로 나눈 나머지 배열

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 1; i <= N; i++) {
            S[i] = S[i - 1] + Integer.parseInt(tokenizer.nextToken());
            R[i] = S[i] % M;
        }

        for (int i = 1; i <= N; i++) {
            if (R[i] == 0) {
                result++;
            }

            frequencyMap.put(R[i], frequencyMap.getOrDefault(R[i], 0) + 1);
        }

        for (Long key : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(key);

            if (frequency >= 2) {
                result += combination(frequency);
            }
        }

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static long combination(int n) {
        return ((long) n * (n - 1)) / 2;
    }
}
