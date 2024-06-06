import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int[] arr = new int[3];

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        arr[0] = Integer.parseInt(tokenizer.nextToken());
        arr[1] = Integer.parseInt(tokenizer.nextToken());
        arr[2] = Integer.parseInt(tokenizer.nextToken());

        Arrays.sort(arr);

        writer.write(String.format("%d %d %d\n", arr[0], arr[1], arr[2]));
        writer.flush();
        writer.close();
    }
}
