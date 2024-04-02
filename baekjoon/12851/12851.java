import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, K;
    static int result1, result2 = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        solve();

        writer.write(result1 + "\n");
        writer.write(result2 + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(N, 0));
        }};
        int[] visited = new int[100001];
        Arrays.fill(visited, -1);
        visited[N] = 0;

        Bfs bfs = null;
        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.number == K) {
                bfs = current;
                break;
            }

            for (int nextNumber : nextNumbers(current.number)) {
                if (isInRange(nextNumber) && (visited[nextNumber] == -1 || current.time + 1 == visited[nextNumber])) {
                    queue.add(new Bfs(nextNumber, current.time + 1));
                    if (nextNumber != K) {
                        visited[nextNumber] = current.time + 1;
                    }
                }
            }
        }

        result1 = bfs.time;
        result2++;
        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (bfs.time > current.time) {
                break;
            }

            if (bfs.time == current.time && current.number == K) {
                result2++;
            }
        }
    }

    static int[] nextNumbers(int number) {
        int[] numbers = new int[3];
        numbers[0] = number - 1;
        numbers[1] = number + 1;
        numbers[2] = number * 2;
        return numbers;
    }

    static boolean isInRange(int number) {
        return number >= 0 && number <= 100000;
    }
}

class Bfs {
    int number, time;
    Bfs(int number, int time) {
        this.number = number;
        this.time = time;
    }
}
