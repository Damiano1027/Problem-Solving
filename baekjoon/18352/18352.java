import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M, K, X;
    static Map<Integer, List<Integer>> map = new HashMap<>();
    static int[] min = new int[300001];

    public static void main(String[] args) throws Exception {
        Arrays.fill(min, -1);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();
        StringTokenizer st = new StringTokenizer(input, " ");

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        for (int i = 0; i < M; i++) {
            String temp = br.readLine();
            st = new StringTokenizer(temp);

            int key = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            map.computeIfAbsent(key, k -> new LinkedList<>());
            map.get(key).add(value);
        }

        bfs(X);

        boolean notExist = true;
        for (int i = 1; i <= N; i++) {
            if (min[i] == K) {
                notExist = false;
                System.out.println(i);
            }
        }

        if (notExist) {
            System.out.println(-1);
        }
    }

    static class Pair {
        int num;
        int cnt;

        Pair(int num, int cnt) {
            this.num = num;
            this.cnt = cnt;
        }
    }

   static void bfs(int n) {
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(n, 0));

        while (!queue.isEmpty()) {
            Pair pair = queue.poll();

            if (min[pair.num] != -1) {
                continue;
            }
            min[pair.num] = pair.cnt;

            List<Integer> adjoins = map.get(pair.num);
            if (adjoins == null) {
                continue;
            }

            for (Integer adjoin : adjoins) {
                queue.offer(new Pair(adjoin, pair.cnt + 1));
            }
        }
   }
}
