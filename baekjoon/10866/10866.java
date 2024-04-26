import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static ArrayDeque<Integer> deque = new ArrayDeque<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            String command = tokenizer.nextToken();

            if (command.equals("push_back")) {
                int value = Integer.parseInt(tokenizer.nextToken());
                deque.addLast(value);
            } else if (command.equals("push_front")) {
                int value = Integer.parseInt(tokenizer.nextToken());
                deque.addFirst(value);
            } else if (command.equals("pop_front")) {
                if (deque.isEmpty()) {
                    writer.write("-1\n");
                } else {
                    writer.write(deque.pollFirst() + "\n");
                }
            } else if (command.equals("pop_back")) {
                if (deque.isEmpty()) {
                    writer.write("-1\n");
                } else {
                    writer.write(deque.pollLast() + "\n");
                }
            } else if (command.equals("size")) {
                writer.write(deque.size() + "\n");
            } else if (command.equals("empty")) {
                if (deque.isEmpty()) {
                    writer.write("1\n");
                } else {
                    writer.write("0\n");
                }
            } else if (command.equals("front")) {
                if (deque.isEmpty()) {
                    writer.write("-1\n");
                } else {
                    writer.write(deque.peekFirst() + "\n");
                }
            } else if (command.equals("back")) {
                if (deque.isEmpty()) {
                    writer.write("-1\n");
                } else {
                    writer.write(deque.peekLast() + "\n");
                }
            }
        }

        writer.flush();
        writer.close();
    }
}
