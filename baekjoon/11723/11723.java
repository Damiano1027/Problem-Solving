import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int M;
    static Set<Integer> set = new HashSet<>();

    public static void main(String[] args) throws Exception {
        M = Integer.parseInt(reader.readLine());

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            String operation = tokenizer.nextToken();
            Integer value = null;
            if (tokenizer.hasMoreTokens()) {
                value = Integer.parseInt(tokenizer.nextToken());
            }

            if (operation.equals("add")) {
                set.add(value);
            } else if (operation.equals("remove")) {
                set.remove(value);
            } else if (operation.equals("check")) {
                if (set.contains(value)) {
                    writer.write("1\n");
                } else {
                    writer.write("0\n");
                }
            } else if (operation.equals("toggle")) {
                if (set.contains(value)) {
                    set.remove(value);
                } else {
                    set.add(value);
                }
            } else if (operation.equals("all")) {
                for (int j = 1; j <= 20; j++) {
                    set.add(j);
                }
            } else if (operation.equals("empty")) {
                set = new HashSet<>();
            }
        }

        writer.flush();
        writer.close();
    }
}
