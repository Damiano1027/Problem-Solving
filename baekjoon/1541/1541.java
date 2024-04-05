import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine(), "-");
        List<String> list = new ArrayList<>();

        while (tokenizer.hasMoreTokens()) {
            list.add(tokenizer.nextToken());
        }

        solve(list);

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve(List<String> list) {
        result = calculate(list.get(0));

        for (int i = 1; i < list.size(); i++) {
            String str = list.get(i);
            int value = calculate(str);
            result -= value;
        }
    }

    static int calculate(String strValue) {
        int sum = 0;
        tokenizer = new StringTokenizer(strValue, "+");

        while (tokenizer.hasMoreTokens()) {
            int value = Integer.parseInt(tokenizer.nextToken());
            sum += value;
        }

        return sum;
    }
}
