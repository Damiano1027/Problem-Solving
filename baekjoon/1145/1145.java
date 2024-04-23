import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int[] arr = new int[5];

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());

        for (int i = 0; i < 5; i++) {
            arr[i] = Integer.parseInt(tokenizer.nextToken());
        }

        int number = 1;
        while (true) {
            int count = 0;
            for (int value : arr) {
                if (number % value == 0) {
                    count++;
                }
            }
            if (count >= 3) {
                break;
            }

            number++;
        }

        writer.write(number + "\n");
        writer.flush();
        writer.close();
    }
}
