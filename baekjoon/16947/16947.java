import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static List<Integer>[] graph;
    static List<MiddleResult> middleResult = new ArrayList<>();
    static List<Result> result = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        graph = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());

            graph[a].add(b);
            graph[b].add(a);
        }

        solve();

        for (Result r : result) {
            writer.write(String.format("%d ", r.distance));
        }
        writer.newLine();
        writer.flush();
        writer.close();
    }

    static void solve() {
        dfs();
    }

    static void dfs() {
        boolean[] visited = new boolean[N + 1];
        for (int i = 1; i <= N; i++) {
            dfs(i, i, 0, visited);
        }

        for (MiddleResult mr : middleResult) {
            int distance = getDistance(mr.from, mr.to);
            result.add(new Result(mr.from, distance));
        }

        Collections.sort(result);
    }

    static boolean dfs(int startNode, int currentNode, int prevNode, boolean[] visited) {
        if (visited[currentNode]) {
            if (currentNode == startNode) {
                result.add(new Result(startNode, 0));
            } else {
                middleResult.add(new MiddleResult(startNode, currentNode));
            }
            return true;
        }

        boolean detected = false;

        visited[currentNode] = true;
        for (int nextNode : graph[currentNode]) {
            if (nextNode == prevNode) {
                continue;
            }

            if (dfs(startNode, nextNode, currentNode, visited)) {
                detected = true;
                break;
            }
        }
        visited[currentNode] = false;

        return detected;
    }

    static int getDistance(int from, int to) {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(from, 0));
        }};
        boolean[] visited = new boolean[N + 1];
        visited[from] = true;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.node == to) {
                return current.distance;
            }

            for (int nextNode : graph[current.node]) {
                if (visited[nextNode]) {
                    continue;
                }

                queue.add(new Bfs(nextNode, current.distance + 1));
                visited[nextNode] = true;
            }
        }

        return -1;
    }
}

class MiddleResult {
    int from, to;
    MiddleResult(int from, int to) {
        this.from = from;
        this.to = to;
    }
}

class Result implements Comparable<Result> {
    int node, distance;
    Result(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }
    public int compareTo(Result result) {
        return node - result.node;
    }
}

class Bfs {
    int node, distance;
    Bfs(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }
}
