import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        int cnt = 0;
        for (int i = 2 * N - 1; i >= 1; i -= 2) {
            for (int j = 0; j < cnt; j++) {
                writer.write(" ");
            }
            for (int j = 0; j < i; j++) {
                writer.write("*");
            }
            writer.newLine();
            cnt++;
        }

        writer.flush();
        writer.close();
    }
}
