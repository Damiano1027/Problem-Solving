import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, S, D;
    static List<Integer>[] tree;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        S = Integer.parseInt(tokenizer.nextToken());
        D = Integer.parseInt(tokenizer.nextToken());

        tree = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());

            tree[x].add(y);
            tree[y].add(x);
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        dfs(S, -1, 0);
    }

    static int dfs(int currentNode, int prevNode, int count) {
        // 탐색시 마지막 노드
        if (tree[currentNode].size() == 1 && tree[currentNode].get(0).equals(prevNode)) {
            return count;
        }

        int maxCount = Integer.MIN_VALUE;
        for (int nextNode : tree[currentNode]) {
            if (nextNode == prevNode) {
                continue;
            }

            int lastCount = dfs(nextNode, currentNode, count + 1);
            if (lastCount - count > D) {
                result += 2;
            }

            maxCount = Math.max(maxCount, lastCount);
        }

        return maxCount;
    }
}
