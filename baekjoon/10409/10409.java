import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n, T;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        T = Integer.parseInt(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());
        int sum = 0;
        int count = 0;
        while (tokenizer.hasMoreTokens()) {
            int value = Integer.parseInt(tokenizer.nextToken());
            if (sum + value > T) {
                break;
            }

            sum += value;
            count++;
        }

        writer.write(count + "\n");
        writer.flush();
        writer.close();
    }
}
