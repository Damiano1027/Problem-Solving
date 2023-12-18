import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 참고: https://steady-life.tistory.com/67 

public class Main {

    static int N, M, answer = 0;
    static int[][] map;
    static boolean[][] visit;
    static int[][] dp;

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] nm = br.readLine().split(" ");
        N = Integer.parseInt(nm[0]);
        M = Integer.parseInt(nm[1]);

        map = new int[N][M];
        visit = new boolean[N][M];
        dp = new int[N][M];

        for (int i = 0; i < N; i++) {
            String str = br.readLine();

            for (int j = 0; j < M; j++) {
                dp[i][j] = -1;

                if (str.charAt(j) == 'H') {
                    map[i][j] = 0;
                } else {
                    map[i][j] = str.charAt(j) - '0';
                }
            }
        }

        answer = DFS(0, 0);

        System.out.println(answer);
    }

    static int DFS(int x, int y) {
        if (x < 0 || y < 0 || x >= N || y >= M || map[x][y] == 0) {
            return 0;
        }

        if (visit[x][y]) {
            System.out.println(-1);
            System.exit(0);
        }

        if (dp[x][y] != -1) {
            return dp[x][y];
        }

        visit[x][y] = true;
        dp[x][y] = 0;

        for (int i = 0; i < 4; i++) {
            int nx = x + (map[x][y] * dx[i]);
            int ny = y + (map[x][y] * dy[i]);

            dp[x][y] = Math.max(dp[x][y], DFS(nx, ny) + 1);
        }

        visit[x][y] = false;

        return dp[x][y];
    }
}
