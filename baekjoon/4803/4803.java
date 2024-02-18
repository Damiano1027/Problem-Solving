import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int[] uf;
    static List<String> result = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        int c = 1;
        while (true) {
            tokenizer = new StringTokenizer(reader.readLine());
            int n = Integer.parseInt(tokenizer.nextToken());
            int m = Integer.parseInt(tokenizer.nextToken());

            if (n == 0 && m == 0) {
                break;
            }

            uf = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                uf[i] = i;
            }
            Set<Integer> numberSet = new HashSet<>();
            for (int i = 1; i <= n; i++) {
                numberSet.add(i);
            }
            List<Integer>[] graph = new List[n + 1];
            for (int i = 1; i <= n; i++) {
                graph[i] = new ArrayList<>();
            }

            List<Pair> pairs = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int a = Integer.parseInt(tokenizer.nextToken());
                int b = Integer.parseInt(tokenizer.nextToken());

                graph[a].add(b);
                graph[b].add(a);
                pairs.add(new Pair(a, b));
            }

            boolean[] visited = new boolean[n + 1];
            for (Pair pair : pairs) {
                if (find(pair.a) == find(pair.b)) {
                    union(pair.a, pair.b);
                    removeNumbers(numberSet, visited, graph, pair.a);
                    removeNumbers(numberSet, visited, graph, pair.b);
                } else {
                    union(pair.a, pair.b);
                }
            }

            int treeCount = 0;
            for (int i = 1; i <= n; i++) {
                if (!visited[i]) {
                    removeNumbers(numberSet, visited, graph, i);
                    treeCount++;
                }
            }

            if (treeCount == 0) {
                result.add(String.format("Case %d: No trees.\n", c));
            } else if (treeCount == 1) {
                result.add(String.format("Case %d: There is one tree.\n", c));
            } else {
                result.add(String.format("Case %d: A forest of %d trees.\n", c, treeCount));
            }

            c++;
        }

        for (String r: result) {
            writer.write(r);
        }
        writer.flush();

        writer.close();
    }

    static int find(int number) {
        if (uf[number] == number) {
            return number;
        }

        return uf[number] = find(uf[number]);
    }

    static void union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        uf[aRoot] = bRoot;
    }

    static void removeNumbers(Set<Integer> numberSet, boolean[] visited, List<Integer>[] graph, int startNumber) {
        Queue<Integer> queue = new LinkedList<>() {{
            add(startNumber);
        }};
        visited[startNumber] = true;

        while (!queue.isEmpty()) {
            int number = queue.poll();
            numberSet.remove(number);

            for (int aNumber : graph[number]) {
                if (!visited[aNumber]) {
                    queue.add(aNumber);
                    visited[aNumber] = true;
                }
            }
        }
    }
}

class Pair {
    int a, b;
    Pair(int a, int b) {
        this.a = a;
        this.b = b;
    }
}
