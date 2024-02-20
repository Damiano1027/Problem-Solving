import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int A, B;
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        A = Integer.parseInt(tokenizer.nextToken());
        B = Integer.parseInt(tokenizer.nextToken());

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(B, 1));
        }};
        Set<Integer> visited = new HashSet<>();
        visited.add(B);

        boolean enable = false;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.number == A) {
                result = current.count;
                enable = true;
                break;
            }

            if (current.number % 2 == 0 && !visited.contains(current.number / 2)) {
                int newNumber = current.number / 2;
                queue.add(new Bfs(newNumber, current.count + 1));
                visited.add(newNumber);
            }

            String strNumber = String.valueOf(current.number);
            if (strNumber.length() > 1 && strNumber.charAt(strNumber.length() - 1) == '1'
                    &&!visited.contains(strNumber.substring(0, strNumber.length() - 1))) {
                int newNumber = Integer.parseInt(strNumber.substring(0, strNumber.length() - 1));
                queue.add(new Bfs(newNumber, current.count + 1));
                visited.add(newNumber);
            }
        }

        if (!enable) {
            result = -1;
        }
    }
}

class Bfs {
    int number, count;
    Bfs(int number, int count) {
        this.number = number;
        this.count = count;
    }
}
