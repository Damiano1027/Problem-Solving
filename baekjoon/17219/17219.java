import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N, M;
    static StringTokenizer tokenizer;
    static Map<String, String> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            String domain = tokenizer.nextToken();
            String password = tokenizer.nextToken();
            map.put(domain, password);
        }

        for (int i = 0; i < M; i++) {
            String domain = reader.readLine();
            writer.write(map.get(domain) + "\n");
        }
        writer.flush();
        writer.close();
    }
}
