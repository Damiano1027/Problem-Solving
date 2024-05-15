import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N, M;
    static StringTokenizer tokenizer;
    static Map<String, Integer> mapToInteger = new HashMap<>();
    static Map<Integer, String> mapToString = new HashMap<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        for (int i = 1; i <= N; i++) {
            String str = reader.readLine();
            mapToInteger.put(str, i);
            mapToString.put(i, str);
        }

        for (int i = 0; i < M; i++) {
            String str = reader.readLine();
            if (Character.isDigit(str.charAt(0))) {
                int value = Integer.parseInt(str);
                writer.write(mapToString.get(value) + "\n");
            } else {
                writer.write(mapToInteger.get(str) + "\n");
            }
        }

        writer.flush();
        writer.close();
    }
}
