import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static int result = 1;
    static int startIndex = 1, endIndex = 1;
    static int sum = 1;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        while (endIndex < N) {
            if (sum == N) {
                result++;
                endIndex++;
                sum += endIndex;
            } else if (sum < N) {
                endIndex++;
                sum += endIndex;
            } else {
                sum -= startIndex;
                startIndex++;
            }
        }

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }
}
