import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n;
    static List<Long> numbers = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        int i = 1;
        int cnt = 0;

        while (true) {
            tokenizer = new StringTokenizer(reader.readLine());

            if (i == 1) {
                n = Integer.parseInt(tokenizer.nextToken());
                while (tokenizer.hasMoreTokens()) {
                    String number = tokenizer.nextToken();
                    number = new StringBuilder().append(number).reverse().toString();
                    numbers.add(Long.parseLong(number));
                    cnt++;
                }
            } else {
                while (tokenizer.hasMoreTokens()) {
                    String number = tokenizer.nextToken();
                    number = new StringBuilder().append(number).reverse().toString();
                    numbers.add(Long.parseLong(number));
                    cnt++;
                }
            }

            if (cnt >= n) {
                break;
            }

            i++;
        }

        Collections.sort(numbers);
        for (long number : numbers) {
            writer.write(number + "\n");
        }
        writer.flush();
        writer.close();
    }
}
