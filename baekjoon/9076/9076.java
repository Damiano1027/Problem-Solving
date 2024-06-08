import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int T;
    static int[] arr = new int[5];

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(reader.readLine());

        for (int i = 0; i < T; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int j = 0;
            while (tokenizer.hasMoreTokens()) {
                int value = Integer.parseInt(tokenizer.nextToken());
                arr[j++] = value;
            }

            Arrays.sort(arr);

            if (arr[3] - arr[1] >= 4) {
                writer.write("KIN\n");
            } else {
                int sum = 0;
                sum = arr[1] + arr[2] + arr[3];
                writer.write(sum + "\n");
            }
        }

        writer.flush();
        writer.close();
    }
}
