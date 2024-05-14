import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static List<String> list = new ArrayList<>();
    static Set<String> set = new HashSet<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        for (int i = 0; i < N; i++) {
            String word = reader.readLine();
            list.add(word);
        }

        list.sort((o1, o2) -> {
            if (o1.length() < o2.length()) {
                return -1;
            } else if (o1.length() > o2.length()) {
                return 1;
            } else {
                return o1.compareTo(o2);
            }
        });

        for (String word : list) {
            if (set.contains(word)) {
                continue;
            }
            writer.write(word + "\n");
            set.add(word);
        }
        writer.flush();
        writer.close();
    }
}
