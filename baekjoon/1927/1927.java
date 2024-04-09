import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static PriorityQueue<Integer> pq = new PriorityQueue<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        for (int i = 0; i < N; i++) {
            int x = Integer.parseInt(reader.readLine());

            if (x == 0) {
                Integer value = pq.poll();
                if (value == null) {
                    writer.write("0\n");
                } else {
                    writer.write(value + "\n");
                }
            } else {
                pq.add(x);
            }
        }

        writer.flush();
        writer.close();
    }
}
