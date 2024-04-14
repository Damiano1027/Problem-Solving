import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;

    public static void main(String[] args) throws Exception {
        Set<Integer> set = new HashSet<>();

        while (true) {
            tokenizer = new StringTokenizer(reader.readLine());
            int[] arr = new int[3];
            int i = 0;
            while (tokenizer.hasMoreTokens()) {
                arr[i] = Integer.parseInt(tokenizer.nextToken());
                i++;
            }

            if (arr[0] == 0 && arr[1] == 0 && arr[2] == 0) {
                break;
            }

            Arrays.sort(arr);
            if (arr[2] >= arr[0] + arr[1]) {
                writer.write("Invalid\n");
                continue;
            }

            for (int j = 0; j < 3; j++) {
                set.add(arr[j]);
            }

            if (set.size() == 1) {
                writer.write("Equilateral\n");
            } else if (set.size() == 2) {
                writer.write("Isosceles\n");
            } else {
                writer.write("Scalene\n");
            }
            set = new HashSet<>();
        }

        writer.flush();
        writer.close();
    }
}
