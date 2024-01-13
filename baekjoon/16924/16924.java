import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static char[][] matrix;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static class Result {
        int x, y, s;
        Result(int x, int y, int s) {
            this.x = x;
            this.y = y;
            this.s = s;
        }
    }
    static List<Result> results = new ArrayList<>();
    static boolean isEnable;
    static int[][] centerVisited;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new char[N + 1][M + 1];
        centerVisited = new int[N + 1][M + 1];

        for (int i = 1; i <= N; i++) {
            String row = reader.readLine();
            for (int j = 1; j <= M; j++) {
                matrix[i][j] = row.charAt(j - 1);
            }
        }

        solve();

        if (isEnable && results.size() <= N * M) {
            writer.write(String.format("%d\n", results.size()));
            for (Result result: results) {
                writer.write(String.format("%d %d %d\n", result.x, result.y, result.s));
            }
        } else {
            writer.write("-1\n");
        }

        writer.flush();
        writer.close();
    }

    static void solve() {
        int minMatrixLineSize = Math.min(N, M);
        int maxCrossSize;

        if (minMatrixLineSize % 2 == 0) {
            maxCrossSize = minMatrixLineSize / 2 - 1;
        } else {
            maxCrossSize = minMatrixLineSize / 2;
        }

        char[][] newMatrix = newMatrix();

        for (int crossSize = maxCrossSize; crossSize >= 1; crossSize--) {
            for (int row = 1 + crossSize; row <= N - crossSize; row++) {
                for (int col = 1 + crossSize; col <= M - crossSize; col++) {
                    if (isCrossPlaceable(row, col, crossSize)) {
                        boolean placed = place(row, col, crossSize, newMatrix);
                        if (placed) {
                            results.add(new Result(row, col, crossSize));
                        }
                    }
                }
            }
        }

        if (equals(matrix, newMatrix)) {
            isEnable = true;
        }

        /*for (int i = 1; i <= N; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
        System.out.println("--------");
        for (int i = 1; i <= N; i++) {
            System.out.println(Arrays.toString(copiedMatrix[i]));
        }*/
    }

    static char[][] newMatrix() {
        char[][] newMatrix = new char[N + 1][M + 1];

        for (int row = 1; row <= N; row++) {
            Arrays.fill(newMatrix[row], '.');
        }

        return newMatrix;
    }

    static boolean isCrossPlaceable(int crossCenterRow, int crossCenterCol, int crossSize) {
        if (matrix[crossCenterRow][crossCenterCol] != '*') {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            int rowDirection = dRow[i];
            int colDirection = dCol[i];

            for (int j = 1; j <= crossSize; j++) {
                int nextRow = crossCenterRow + rowDirection * j;
                int nextCol = crossCenterCol + colDirection * j;

                if (matrix[nextRow][nextCol] != '*') {
                    return false;
                }
            }
        }

        return true;
    }

    static boolean place(int crossCenterRow, int crossCenterCol, int crossSize, char[][] newMatrix) {
        if (centerVisited[crossCenterRow][crossCenterCol] > crossSize) {
            return false;
        }

        centerVisited[crossCenterRow][crossCenterCol] = crossSize;
        newMatrix[crossCenterRow][crossCenterCol] = '*';

        for (int i = 0; i < 4; i++) {
            int rowDirection = dRow[i];
            int colDirection = dCol[i];

            for (int j = 1; j <= crossSize; j++) {
                int nextRow = crossCenterRow + rowDirection * j;
                int nextCol = crossCenterCol + colDirection * j;

                newMatrix[nextRow][nextCol] = '*';
            }
        }

        return true;
    }

    static boolean equals(char[][] matrix1, char[][] matrix2) {
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= M; col++) {
                if (matrix1[row][col] != matrix2[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }
}
