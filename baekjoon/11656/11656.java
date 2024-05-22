import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws Exception {
        String str = reader.readLine();
        List<String> list = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            String subStr = str.substring(i);
            list.add(subStr);
        }

        Collections.sort(list);

        for (String s : list) {
            writer.write(s + "\n");
        }
        writer.flush();
        writer.close();
    }
}
