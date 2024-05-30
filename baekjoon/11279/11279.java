import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static PriorityQueue<Integer> pq = new PriorityQueue<>(((o1, o2) -> o2 - o1));

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        for (int i = 0; i < N; i++) {
            int value = Integer.parseInt(reader.readLine());
            if (value == 0) {
                if (pq.isEmpty()) {
                    writer.write("0\n");
                } else {
                    writer.write(pq.poll() + "\n");
                }
            } else {
                pq.add(value);
            }
        }

        writer.flush();
        writer.close();
    }
}
