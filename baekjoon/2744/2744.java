import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static String str;

    public static void main(String[] args) throws Exception {
        str = reader.readLine();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            char addedCh;

            if (Character.isLowerCase(ch)) {
                addedCh = Character.toUpperCase(ch);
            } else {
                addedCh = Character.toLowerCase(ch);
            }

            stringBuilder.append(addedCh);
        }

        writer.write(stringBuilder.toString() + "\n");
        writer.flush();
        writer.close();
    }
}
