import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int M, N;
    static int[][] staticMatrix;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int[][] memo;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());

        M = Integer.parseInt(tokenizer.nextToken());
        N = Integer.parseInt(tokenizer.nextToken());

        staticMatrix = new int[M][N];
        memo = new int[M][N];

        for (int row = 0; row < M; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < N; col++) {
                staticMatrix[row][col] = Integer.parseInt(tokenizer.nextToken());
                memo[row][col] = -1;
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        dfs(0, 0);
        result = memo[0][0];
    }

    static int dfs(int row, int col) {
        int routes = 0;

        for (int direction = 0; direction < 4; direction++) {
            int aRow = row + dRow[direction];
            int aCol = col + dCol[direction];
            if (!isInMatrix(aRow, aCol)) {
                continue;
            }
            if (staticMatrix[row][col] <= staticMatrix[aRow][aCol]) {
                continue;
            }

            // 끝지점
            if (aRow == M - 1 && aCol == N - 1) {
                routes++;
            } else {
                // 방문했던 곳
                if (memo[aRow][aCol] >= 0) {
                    routes += memo[aRow][aCol];
                }
                // 방문하지 않은 곳
                else {
                    routes += dfs(aRow, aCol);
                }
            }
        }

        memo[row][col] = routes;
        return memo[row][col];
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < M && col >= 0 && col < N;
    }
}
