import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws Exception {
        while (true) {
            String line = reader.readLine();

            if (line.charAt(0) == '#') {
                break;
            }

            int count = 0;
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                if (isVowel(ch)) {
                    count++;
                }
            }

            writer.write(count + "\n");
        }

        writer.flush();
        writer.close();
    }

    static boolean isVowel(char ch) {
        ch = Character.toLowerCase(ch);
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }
}
