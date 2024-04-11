import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static String game;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tokenizer.nextToken());
        game = tokenizer.nextToken();

        Set<String> set = new HashSet<>();
        for (int i = 0; i < N; i++) {
            String name = reader.readLine();
            set.add(name);
        }

        if (game.equals("Y")) {
            writer.write(set.size() + "\n");
        } else if (game.equals("F")) {
            writer.write((set.size() / 2) + "\n");
        } else {
            writer.write((set.size() / 3) + "\n");
        }

        writer.flush();
        writer.close();
    }
}
