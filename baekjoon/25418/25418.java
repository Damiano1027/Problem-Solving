import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int A, K;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        A = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        writer.write(bfs() + "\n");
        writer.flush();
        writer.close();
    }

    static int bfs() {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(A, 0));
        }};
        boolean[] visited = new boolean[1000001];
        visited[A] = true;

        int result = 0;
        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.number == K) {
                result = current.count;
                break;
            }

            if (isInRange(current.number + 1) && !visited[current.number + 1]) {
                queue.add(new Bfs(current.number + 1, current.count + 1));
                visited[current.number + 1] = true;
            }
            if (isInRange(current.number * 2) && !visited[current.number * 2]) {
                queue.add(new Bfs(current.number * 2, current.count + 1));
                visited[current.number * 2] = true;
            }
        }

        return result;
    }

    static boolean isInRange(int number) {
        return number >= 1 && number <= 1000000;
    }
}

class Bfs {
    int number, count;
    Bfs(int number, int count) {
        this.number = number;
        this.count = count;
    }
}
