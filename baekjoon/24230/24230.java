import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[] targetColors;
    static List<Integer>[] tree;
    static int result;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        targetColors = new int[N + 1];
        tree = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 1; i <= N; i++) {
            targetColors[i] = Integer.parseInt(tokenizer.nextToken());
        }

        for (int i = 0; i < N - 1; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());

            tree[a].add(b);
            tree[b].add(a);
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        if (targetColors[1] > 0) {
            result++;
        }
        bfs();
    }

    static void bfs() {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(1, -1));
        }};
        boolean[] visited = new boolean[N + 1];
        visited[1] = true;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            for (Integer nextNumber : tree[current.number]) {
                if (nextNumber == current.prevNumber || visited[nextNumber]) {
                    continue;
                }

                if (targetColors[current.number] != targetColors[nextNumber]) {
                    result++;
                }
                queue.add(new Bfs(nextNumber, current.number));
                visited[nextNumber] = true;
            }
        }
    }
}

class Bfs {
    int number, prevNumber;
    Bfs(int number, int prevNumber) {
        this.number = number;
        this.prevNumber = prevNumber;
    }
}
