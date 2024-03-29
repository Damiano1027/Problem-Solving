import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n;
    static int[][] matrix, memo;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int result = Integer.MIN_VALUE;

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(reader.readLine());
        matrix = new int[n][n];
        memo = new int[n][n];
        for (int row = 0; row < n; row++) {
            Arrays.fill(memo[row], -1);
        }

        for (int row = 0; row < n; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < n; col++) {
                matrix[row][col] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (memo[row][col] == -1) {
                    memo[row][col] = dfs(row, col, 0);
                }
                result = Math.max(result, memo[row][col]);
            }
        }
        result++;
    }

    static int dfs(int currentRow, int currentCol, int count) {
        if (memo[currentRow][currentCol] != -1) {
            return count + memo[currentRow][currentCol];
        }
        if (!isMovable(currentRow, currentCol)) {
            return count;
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < 4; i++) {
            int aRow = currentRow + dRow[i];
            int aCol = currentCol + dCol[i];

            if (isInMatrix(aRow, aCol) && matrix[aRow][aCol] > matrix[currentRow][currentCol]) {
                max = Math.max(max, dfs(aRow, aCol, count + 1));
            }
        }

        memo[currentRow][currentCol] = max - count;
        return max;
    }

    static boolean isMovable(int row, int col) {
        for (int i = 0; i < 4; i++) {
            int aRow = row + dRow[i];
            int aCol = col + dCol[i];

            if (isInMatrix(aRow, aCol) && matrix[aRow][aCol] > matrix[row][col]) {
                return true;
            }
        }
        return false;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n;
    }
}
