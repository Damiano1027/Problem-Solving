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
        List<Integer> indegreeZeroNodes = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            if (indegree[i] == 0) {
                indegreeZeroNodes.add(i);
            }
        }

        while (!indegreeZeroNodes.isEmpty()) {
            Map<Integer, List<Integer>> prevNodesMap = new HashMap<>();
            List<Integer> nextNodeList = new ArrayList<>();

            for (int node: indegreeZeroNodes) {
                if (!prevNodes[node].isEmpty()) {
                    time[node] += getMinTime(prevNodes[node]);
                }

                result[node] = time[node];

                for (int nextNode: nextNodes[node]) {
                    indegree[nextNode]--;
                    nextNodeList.add(nextNode);

                    if (prevNodesMap.get(nextNode) == null) {
                        prevNodesMap.put(nextNode, new ArrayList<>());
                    }
                    prevNodesMap.get(nextNode).add(node);
                }
            }

            indegreeZeroNodes = new ArrayList<>();

            for (int nextNode: nextNodeList) {
                if (indegree[nextNode] == 0) {
                    prevNodes[nextNode] = prevNodesMap.get(nextNode);
                    indegreeZeroNodes.add(nextNode);
                }
            }
        }
    }

    static int getMinTime(List<Integer> prevNodes) {
        int min = Integer.MAX_VALUE;

        for (int node: prevNodes) {
            min = Math.min(min, time[node]);
        }

        return min;
    }
}
