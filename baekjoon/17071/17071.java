import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, K;
    static int result = -1;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        if (N == K) {
            result = 0;
            return;
        }

        int[][] visited = new int[2][500001];
        Arrays.fill(visited[0], -1);
        Arrays.fill(visited[1], -1);
        bfs(visited);

        int time = 0;
        int timeOffset = 1;
        for (int i = K; i <= 500000;) {
            if (visited[time % 2][i] != -1 && visited[time % 2][i] <= time) {
                result = time;
                break;
            }

            time++;
            i += timeOffset;
            timeOffset++;
        }
    }

    static void bfs(int[][] visited) {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(N, 0));
        }};
        visited[0][N] = 0;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            for (int nextNumber : nextNumbers(current.number)) {
                if (isInRange(nextNumber) && visited[(current.time + 1) % 2][nextNumber] == -1) {
                    queue.add(new Bfs(nextNumber, current.time + 1));
                    visited[(current.time + 1) % 2][nextNumber] = current.time + 1;
                }
            }
        }
    }

    static int[] nextNumbers(int number) {
        int[] arr = new int[3];
        arr[0] = number - 1;
        arr[1] = number + 1;
        arr[2] = number * 2;
        return arr;
    }

    static boolean isInRange(int number) {
        return number >= 0 && number <= 500000;
    }
}

class Bfs {
    int number, time;
    Bfs(int number, int time) {
        this.number = number;
        this.time = time;
    }
}
