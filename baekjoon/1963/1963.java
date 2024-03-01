import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static boolean[] isPrimeNumber = new boolean[10000];
    static int T;

    public static void main(String[] args) throws Exception {
        setIsPrimeNumberArray();

        T = Integer.parseInt(reader.readLine());

        for (int i = 0; i < T; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            String original = tokenizer.nextToken();
            String target = tokenizer.nextToken();

            int result = bfs(original, target);
            if (result == -1) {
                writer.write("Impossible\n");
            } else {
                writer.write(result + "\n");
            }
        }

        writer.flush();

        writer.close();
    }

    static void setIsPrimeNumberArray() {
        Arrays.fill(isPrimeNumber, true);

        for (int i = 2; i <= 9999; i++) {
            if (!isPrimeNumber[i]) {
                continue;
            }

            for (int j = i + i; j <= 9999; j += i) {
                isPrimeNumber[j] = false;
            }
        }
    }

    static int bfs(String original, String target) {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(original, 0));
        }};
        boolean[] visited = new boolean[10000];
        visited[Integer.parseInt(original)] = true;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.number.equals(target)) {
                return current.count;
            }

            for (char ch = '1'; ch <= '9'; ch++) {
                String parsedNumber = parse0(current.number, ch);
                int intNumber = Integer.parseInt(parsedNumber);
                if (isPrimeNumber[intNumber] && !visited[intNumber]) {
                    queue.add(new Bfs(parsedNumber, current.count + 1));
                    visited[intNumber] = true;
                }
            }
            for (char ch = '0'; ch <= '9'; ch++) {
                String parsedNumber = parse1(current.number, ch);
                int intNumber = Integer.parseInt(parsedNumber);
                if (isPrimeNumber[intNumber] && !visited[intNumber]) {
                    queue.add(new Bfs(parsedNumber, current.count + 1));
                    visited[intNumber] = true;
                }
            }
            for (char ch = '0'; ch <= '9'; ch++) {
                String parsedNumber = parse2(current.number, ch);
                int intNumber = Integer.parseInt(parsedNumber);
                if (isPrimeNumber[intNumber] && !visited[intNumber]) {
                    queue.add(new Bfs(parsedNumber, current.count + 1));
                    visited[intNumber] = true;
                }
            }
            for (char ch = '0'; ch <= '9'; ch++) {
                String parsedNumber = parse3(current.number, ch);
                int intNumber = Integer.parseInt(parsedNumber);
                if (isPrimeNumber[intNumber] && !visited[intNumber]) {
                    queue.add(new Bfs(parsedNumber, current.count + 1));
                    visited[intNumber] = true;
                }
            }
        }

        return -1;
    }

    static String parse0(String original, char value) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(value);
        stringBuilder.append(original.substring(1));
        return stringBuilder.toString();
    }

    static String parse1(String original, char value) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(original.charAt(0));
        stringBuilder.append(value);
        stringBuilder.append(original.substring(2));
        return stringBuilder.toString();
    }

    static String parse2(String original, char value) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(original.substring(0, 2));
        stringBuilder.append(value);
        stringBuilder.append(original.substring(3));
        return stringBuilder.toString();
    }

    static String parse3(String original, char value) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(original.substring(0, 3));
        stringBuilder.append(value);
        return stringBuilder.toString();
    }
}

class Bfs {
    String number;
    int count;
    Bfs(String number, int count) {
        this.number = number;
        this.count = count;
    }
}
