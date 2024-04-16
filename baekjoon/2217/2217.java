import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static int[] arr;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        arr = new int[N];

        for (int i = 0; i < N; i++) {
            int value = Integer.parseInt(reader.readLine());
            arr[i] = value;
        }
        
        Arrays.sort(arr);

        int j = 1;
        int max = Integer.MIN_VALUE;
        for (int i = N - 1; i >= 0; i--) {
            int sum = arr[i] * j++;
            max = Math.max(max, sum);
        }

        writer.write(max + "\n");
        writer.flush();
        writer.close();
    }
}
