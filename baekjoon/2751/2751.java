import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        for (int i = 0; i < N; i++) {
            int value = Integer.parseInt(reader.readLine());
            list.add(value);
        }

        Collections.sort(list);

        for (int value : list) {
            writer.write(value + "\n");
        }
        
        writer.flush();
        writer.close();
    }
}
