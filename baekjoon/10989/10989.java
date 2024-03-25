import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static int[] numbers;

    // List 사용시 메모리 초과 발생
    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        numbers = new int[N];
        for (int i = 0; i < N; i++) {
            numbers[i] = Integer.parseInt(reader.readLine());
        }

        Arrays.sort(numbers);

        for (int number : numbers) {
            writer.write(number + "\n");
        }
        writer.flush();
        writer.close();
    }
}
