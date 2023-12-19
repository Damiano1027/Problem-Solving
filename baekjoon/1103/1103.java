import java.io.*;
import java.util.*;

/*
     지식
     - 무한번 움직인다는 것은 순환이 발생했다는 것이다.
     - 기저 조건에서 0을 반환하고, dfs() 호출 결과값에 +1 하여 memo에 저장
     주의할 점
     - '#' 입력이 들어오므로 char 2차원 배열로 저장해야 함
 */
public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer stringTokenizer;
    static int N, M;
    static char[][] matrix;
    static boolean[][] visited;
    static int[][] memo;

    public static void main(String[] args) throws IOException {
        stringTokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(stringTokenizer.nextToken());
        M = Integer.parseInt(stringTokenizer.nextToken());

        matrix = new char[N][M];
        visited = new boolean[N][M];
        memo = new int[N][M];

        for (int i = 0; i < N; i++) {
            matrix[i] = toCharArray(reader.readLine());
            for (int j = 0; j < M; j++) {
                memo[i][j] = -1;
            }
        }

        writer.write(String.format("%d\n", dfs(0, 0)));
        writer.flush();
        writer.close();
    }

    static char[] toCharArray(String str) {
        char[] charArray = new char[str.length()];

        for (int i = 0; i < str.length(); i++) {
            charArray[i] = str.charAt(i);
        }

        return charArray;
    }

    static int dfs(int currentRow, int currentCol) throws IOException {
        if (!isValidLocation(currentRow, currentCol)) {
            return 0;
        }
        if (visited[currentRow][currentCol]) {
            writer.write("-1\n");
            writer.flush();
            writer.close();
            System.exit(0);
        }
        if (memo[currentRow][currentCol] != -1) {
            return memo[currentRow][currentCol];
        }

        int[] dRow = {-1, 0, 1, 0};
        int[] dCol = {0, -1, 0, 1};
        int distance = Character.getNumericValue(matrix[currentRow][currentCol]);

        visited[currentRow][currentCol] = true;
        for (int i = 0; i < 4; i++) {
            int nextRow = currentRow + dRow[i] * distance;
            int nextCol = currentCol + dCol[i] * distance;

            memo[currentRow][currentCol] = Math.max(memo[currentRow][currentCol], dfs(nextRow, nextCol) + 1);
        }
        visited[currentRow][currentCol] = false;

        return memo[currentRow][currentCol];
    }

    static boolean isValidLocation(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M && matrix[row][col] != 'H';
    }
}
