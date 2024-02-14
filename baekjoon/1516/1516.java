import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[] indegree, time;
    static List<Integer>[] nextNodes;
    static List<Integer>[] prevNodes;
    static int[] result;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        time = new int[N + 1];
        nextNodes = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            nextNodes[i] = new ArrayList<>();
        }
        prevNodes = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            prevNodes[i] = new ArrayList<>();
        }
        indegree = new int[N + 1];
        result = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            time[i] = Integer.parseInt(tokenizer.nextToken());

            while (tokenizer.hasMoreTokens()) {
                int value = Integer.parseInt(tokenizer.nextToken());
                if (value == -1) {
                    break;
                }

                nextNodes[value].add(i);
                prevNodes[i].add(value);
                indegree[i]++;
            }
        }

        solve();

        for (int i = 1; i <= N; i++) {
            writer.write(String.format("%d\n", result[i]));
        }
        writer.flush();

        writer.close();
    }

    static void solve() {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();

            if (!prevNodes[node].isEmpty()) {
                time[node] += getMaxTime(prevNodes[node]);
            }
            result[node] = time[node];

            for (int nextNode: nextNodes[node]) {
                indegree[nextNode]--;
                if (indegree[nextNode] == 0) {
                    queue.add(nextNode);
                }
            }
        }
    }

    static int getMaxTime(List<Integer> prevNodes) {
        int max = Integer.MIN_VALUE;

        for (int node: prevNodes) {
            max = Math.max(max, time[node]);
        }

        return max;
    }
}
