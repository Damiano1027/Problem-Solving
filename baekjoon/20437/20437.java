import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int T;

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(reader.readLine());

        for (int i = 0; i < T; i++) {
            String str = reader.readLine();
            int K = Integer.parseInt(reader.readLine());

            int value1 = solve1(str, K);
            int value2 = solve2(str, K);

            if (value1 == -1 || value2 == -1) {
                writer.write("-1\n");
            } else {
                writer.write(String.format("%d %d\n", value1, value2));
            }
        }

        writer.flush();
        writer.close();
    }

    static int solve1(String str, int K) {
        Map<Character, List<Integer>> map = new HashMap<>();
        for (char ch = 'a'; ch <= 'z'; ch++) {
            map.put(ch, new ArrayList<>());
        }

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            map.get(ch).add(i);
        }

        int result = -1;
        for (Character ch: map.keySet()) {
            List<Integer> indexes = map.get(ch);

            int minLength = -1;
            for (int i = 0; i + K - 1 < indexes.size(); i++) {
                int length = indexes.get(i + K - 1) - indexes.get(i) + 1;
                minLength = (minLength == -1) ? length : Math.min(minLength, length);
            }

            if (minLength == -1) {
                continue;
            }

            result = (result == -1) ? minLength : Math.min(result, minLength);
        }

        return result;
    }

    static int solve2(String str, int K) {
        Map<Character, List<Integer>> map = new HashMap<>();
        for (char ch = 'a'; ch <= 'z'; ch++) {
            map.put(ch, new ArrayList<>());
        }

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            map.get(ch).add(i);
        }

        int result = -1;
        for (Character ch: map.keySet()) {
            List<Integer> indexes = map.get(ch);

            int maxLength = -1;
            for (int i = 0; i + K - 1 < indexes.size(); i++) {
                int length = indexes.get(i + K - 1) - indexes.get(i) + 1;
                maxLength = (maxLength == -1) ? length : Math.max(maxLength, length);
            }

            if (maxLength == -1) {
                continue;
            }

            result = (result == -1) ? maxLength : Math.max(result, maxLength);
        }

        return result;
    }
}
