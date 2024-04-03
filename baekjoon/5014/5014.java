import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int F, S, G, U, D;
    static int result = -1;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        F = Integer.parseInt(tokenizer.nextToken());
        S = Integer.parseInt(tokenizer.nextToken());
        G = Integer.parseInt(tokenizer.nextToken());
        U = Integer.parseInt(tokenizer.nextToken());
        D = Integer.parseInt(tokenizer.nextToken());

        solve();

        if (result == -1) {
            writer.write("use the stairs\n");
        } else {
            writer.write(result + "\n");
        }

        writer.flush();
        writer.close();
    }

    static void solve() {
        bfs();
    }

    static void bfs() {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(S, 0));
        }};
        boolean[] visited = new boolean[F + 1];
        visited[S] = true;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.floor == G) {
                result = current.count;
                break;
            }

            if (isInRange(current.floor + U) && !visited[current.floor + U]) {
                queue.add(new Bfs(current.floor + U, current.count + 1));
                visited[current.floor + U] = true;
            }
            if (isInRange(current.floor - D) && !visited[current.floor - D]) {
                queue.add(new Bfs(current.floor - D, current.count + 1));
                visited[current.floor - D] = true;
            }
        }
    }

    static boolean isInRange(int floor) {
        return floor >= 1 && floor <= F;
    }
}

class Bfs {
    int floor, count;
    Bfs(int floor, int count) {
        this.floor = floor;
        this.count = count;
    }
}
