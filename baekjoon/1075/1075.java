import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N, F;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        F = Integer.parseInt(reader.readLine());

        String strN = String.valueOf(N);
        strN = strN.substring(0, strN.length() - 2);

        label:
        for (char ch1 = '0'; ch1 <= '9'; ch1++) {
            for (char ch2 = '0'; ch2 <= '9'; ch2++) {
                String str = String.valueOf(ch1) + String.valueOf(ch2);
                String newStr = strN + str;
                int value = Integer.parseInt(newStr);

                if (value % F == 0) {
                    writer.write(str + "\n");
                    break label;
                }
            }
        }

        writer.flush();
        writer.close();
    }
}
