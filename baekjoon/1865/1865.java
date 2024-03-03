import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int TC;
    static int N, M, W;
    static List<Edge> edges;
    static List<Integer>[] graph;
    static long[] times;

    public static void main(String[] args) throws Exception {
        TC = Integer.parseInt(reader.readLine());

        for (int i = 0; i < TC; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            M = Integer.parseInt(tokenizer.nextToken());
            W = Integer.parseInt(tokenizer.nextToken());

            times = new long[N + 1];
            edges = new ArrayList<>();
            graph = new List[N + 1];
            for (int j = 1; j <= N; j++) {
                graph[j] = new ArrayList<>();
            }

            for (int j = 0; j < M; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int S = Integer.parseInt(tokenizer.nextToken());
                int E = Integer.parseInt(tokenizer.nextToken());
                int T = Integer.parseInt(tokenizer.nextToken());

                edges.add(new Edge(S, E, T));
                edges.add(new Edge(E, S, T));
                graph[S].add(E);
                graph[E].add(S);
            }

            for (int j = 0; j < W; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int S = Integer.parseInt(tokenizer.nextToken());
                int E = Integer.parseInt(tokenizer.nextToken());
                int T = Integer.parseInt(tokenizer.nextToken());

                edges.add(new Edge(S, E, -T));
                graph[S].add(E);
            }

            List<Integer> startNumbers = getStartNumbers();

            if (bellmanFord(startNumbers)) {
                writer.write("YES\n");
            } else {
                writer.write("NO\n");
            }
        }

        writer.flush();
        writer.close();
    }

    static List<Integer> getStartNumbers() {
        List<Integer> startNumbers = new ArrayList<>();
        boolean[] visited = new boolean[N + 1];

        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                startNumbers.add(bfs(i, visited));
            }
        }

        return startNumbers;
    }

    static int bfs(int start, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>() {{
            add(start);
        }};
        visited[start] = true;

        int node = start;
        while (!queue.isEmpty()) {
            Integer currentNode = queue.poll();
            node = currentNode;

            for (int nextNode : graph[currentNode]) {
                if (!visited[nextNode]) {
                    queue.add(nextNode);
                    visited[nextNode] = true;
                }
            }
        }

        return node;
    }

    static boolean bellmanFord(List<Integer> startNumbers) {
        for (int startNumber : startNumbers) {
            Arrays.fill(times, Long.MAX_VALUE);
            times[startNumber] = 0;

            for (int i = 0; i < N - 1; i++) {
                for (Edge edge : edges) {
                    if (times[edge.from] == Long.MAX_VALUE) {
                        continue;
                    }

                    long newTime = times[edge.from] + edge.time;
                    if (newTime < times[edge.to]) {
                        times[edge.to] = newTime;
                    }
                }
            }

           for (Edge edge : edges) {
               if (times[edge.from] == Long.MAX_VALUE) {
                   continue;
               }

               long newTime = times[edge.from] + edge.time;
               if (newTime < times[edge.to]) {
                   return true;
               }
           }
        }

        return false;
    }
}

class Edge {
    int from, to, time;
    Edge(int from, int to, int time) {
        this.from = from;
        this.to = to;
        this.time = time;
    }
}
