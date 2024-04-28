import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int t, n;

    public static void main(String[] args) throws Exception {
        t = Integer.parseInt(reader.readLine());

        for (int i = 0; i < t; i++) {
            n = Integer.parseInt(reader.readLine());
            Set<String> set = new HashSet<>();

            for (int j = 0; j < n; j++) {
                String number = reader.readLine();
                set.add(number);
            }

            boolean yes = true;
            label:
            for (String number : set) {
                for (int j = 0; j < number.length(); j++) {
                    String subString = number.substring(0, j);
                    if (set.contains(subString)) {
                        yes = false;
                        writer.write("NO\n");
                        break label;
                    }
                }
            }

            if (yes) {
                writer.write("YES\n");
            }
        }

        writer.flush();
        writer.close();
    }
}
