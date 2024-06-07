import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, v;
    static Map<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        tokenizer = new StringTokenizer(reader.readLine());

        while (tokenizer.hasMoreTokens()) {
            int value = Integer.parseInt(tokenizer.nextToken());
            map.put(value, map.getOrDefault(value, 0) + 1);
        }

        v = Integer.parseInt(reader.readLine());

        if (map.containsKey(v)) {
            writer.write(map.get(v) + "\n");
        } else {
            writer.write("0\n");
        }

        writer.flush();
        writer.close();
    }
}
