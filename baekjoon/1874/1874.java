import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int n;
    static Stack<Integer> stack = new Stack<>();
    static Queue<Integer> resultQueue = new LinkedList<>();
    static int current = 1;
    static int pushCount = 0, popCount = 0;
    static boolean possible = true;
    static List<Character> result = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(reader.readLine());

        for (int i = 0; i < n; i++) {
            int value = Integer.parseInt(reader.readLine());
            resultQueue.add(value);
        }

        solve();

        if (possible) {
            for (char ch : result) {
                writer.write(ch + "\n");
            }
        } else {
            writer.write("NO\n");
        }

        writer.flush();
        writer.close();
    }

    static void solve() {
        for (int i = 1; i <= n + 1;) {
            if (i == 1) {
                stack.add(i);
                i++;
                result.add('+');
                pushCount++;
                continue;
            }

            if (!stack.isEmpty() && !resultQueue.isEmpty() && stack.peek().equals(resultQueue.peek())) {
                resultQueue.poll();
                stack.pop();
                result.add('-');
                popCount++;
            } else {
                if (i == n + 1) {
                    break;
                }

                stack.add(i);
                i++;
                result.add('+');
                pushCount++;
            }
        }

        if (pushCount != n || popCount != n) {
            possible = false;
        }
    }
}
