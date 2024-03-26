import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static List<Integer>[] tree;
    static int rootNumber, removeNumber;
    static int result;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        tree = new List[N];
        for (int i = 0; i < N; i++) {
            tree[i] = new ArrayList<>();
        }

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < N; i++) {
            int number = Integer.parseInt(tokenizer.nextToken());
            if (number == -1) {
                rootNumber = i;
                continue;
            }

            tree[number].add(i);
        }

        removeNumber = Integer.parseInt(reader.readLine());

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        bfs();
    }

    static void bfs() {
        Queue<Integer> queue = new LinkedList<>() {{
            add(rootNumber);
        }};
        boolean[] visited = new boolean[N];
        visited[removeNumber] = true;

        while (!queue.isEmpty()) {
            int currentNumber = queue.poll();

            if (currentNumber == removeNumber) {
                continue;
            }

            if (tree[currentNumber].isEmpty() || (tree[currentNumber].size() == 1 && tree[currentNumber].contains(removeNumber))) {
                result++;
            }
            for (int nextNumber : tree[currentNumber]) {
                if (!visited[nextNumber]) {
                    queue.add(nextNumber);
                    visited[nextNumber] = true;
                }
            }
        }
    }
}
