import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static String str;
    static int aC = 0;
    static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        str = reader.readLine();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == 'a') {
                aC++;
            }
        }

        if (aC == 0) {
            writer.write("0\n");
            writer.flush();
            writer.close();
            return;
        }

        int left = 0, right = aC - 1;
        do {
            int bCount = 0;

            if (left <= right) {
                for (int i = left; i <= right; i++) {
                    if (str.charAt(i) == 'b') {
                        bCount++;
                    }
                }
            } else {
                for (int i = left; i < str.length(); i++) {
                    if (str.charAt(i) == 'b') {
                        bCount++;
                    }
                }
                for (int i = 0; i <= right; i++) {
                    if (str.charAt(i) == 'b') {
                        bCount++;
                    }
                }
            }

            result = Math.min(result, bCount);

            left++;
            right++;
            if (left >= str.length()) {
                left = 0;
            }
            if (right >= str.length()) {
                right = 0;
            }
        } while (left > 0);

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }
}
