import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int result1 = 0, result2 = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 7; i++) {
            int value = Integer.parseInt(reader.readLine());

            if (value % 2 != 0) {
                result1 += value;
                result2 = Math.min(result2, value);
            }
        }

        if (result1 > 0) {
            writer.write(result1 + "\n");
            writer.write(result2 + "\n");
        } else {
            writer.write("-1\n");
        }

        writer.flush();
        writer.close();
    }
}
