import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int T, N, K, W;
    static int[] times, totalTimes, indegree;
    static List<Integer>[] graph;

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(reader.readLine());

        for (int k = 0; k < T; k++) {
            tokenizer = new StringTokenizer(reader.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            K = Integer.parseInt(tokenizer.nextToken());

            times = new int[N + 1];
            totalTimes = new int[N + 1];
            indegree = new int[N + 1];
            graph = new List[N + 1];

            tokenizer = new StringTokenizer(reader.readLine());
            for (int i = 1; i <= N; i++) {
                times[i] = Integer.parseInt(tokenizer.nextToken());
            }
            Arrays.fill(totalTimes, -1);

            for (int i = 1; i <= N; i++) {
                graph[i] = new ArrayList<>();
            }
            for (int i = 0; i < K; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int X = Integer.parseInt(tokenizer.nextToken());
                int Y = Integer.parseInt(tokenizer.nextToken());

                graph[X].add(Y);
                indegree[Y]++;
            }

            W = Integer.parseInt(reader.readLine());

            topologySort();

            writer.write(totalTimes[W] + "\n");
        }

        writer.flush();

        writer.close();
    }

    static void topologySort() {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
                totalTimes[i] = 0;
            }
        }

        while (!queue.isEmpty()) {
            int currentNumber = queue.poll();

            totalTimes[currentNumber] += times[currentNumber];
            for (int nextNumber : graph[currentNumber]) {
                if (totalTimes[nextNumber] == -1) {
                    totalTimes[nextNumber] = totalTimes[currentNumber];
                } else {
                    totalTimes[nextNumber] = Math.max(totalTimes[nextNumber], totalTimes[currentNumber]);
                }

                indegree[nextNumber]--;
                if (indegree[nextNumber] == 0) {
                    queue.add(nextNumber);
                }
            }
        }
    }
}
