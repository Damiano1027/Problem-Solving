import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, L;
    static int[] h;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        L = Integer.parseInt(tokenizer.nextToken());

        h = new int[N];

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        tokenizer = new StringTokenizer(reader.readLine());
        while (tokenizer.hasMoreTokens()) {
            int value = Integer.parseInt(tokenizer.nextToken());
            pq.add(value);
        }

        while (!pq.isEmpty()) {
            int value = pq.poll();
            if (value <= L) {
                L++;
            } else {
                break;
            }
        }

        writer.write(L + "\n");
        writer.flush();
        writer.close();
    }
}
