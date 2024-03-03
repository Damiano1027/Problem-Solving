import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static Edge[] edges;
    static long[] times;
    static boolean possible = true;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        edges = new Edge[M];
        times = new long[N + 1];

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int A = Integer.parseInt(tokenizer.nextToken());
            int B = Integer.parseInt(tokenizer.nextToken());
            int C = Integer.parseInt(tokenizer.nextToken());

            edges[i] = new Edge(A, B, C);
        }

        // 도시가 1개이면 출력할 것이 없음
        if (N == 1) {
            return;
        }

        solve();

        if (possible) {
            for (int i = 2; i <= N; i++) {
                if (times[i] == Long.MAX_VALUE) {
                    writer.write("-1\n");
                } else {
                    writer.write(times[i] + "\n");
                }
            }
        } else {
            writer.write("-1\n");
        }

        writer.flush();
        writer.close();
    }

    static void solve() {
        bellmanFord();
    }

    static void bellmanFord() {
        Arrays.fill(times, Long.MAX_VALUE);
        times[1] = 0;

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
                possible = false;
                break;
            }
        }
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
