import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, K;
    static int[][] staticMatrix;
    static class Rotate {
        int r, c, s;
        Rotate(int r, int c, int s) {
            this.r = r;
            this.c = c;
            this.s = s;
        }
        public boolean equals(Object o) {
            Rotate rotate = (Rotate) o;
            return r == rotate.r && c == rotate.c && s == rotate.s;
        }
    }
    static List<Rotate> rotates = new ArrayList<>();
    static int result = -1;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        staticMatrix = new int[N + 1][M + 1];

        for (int row = 1; row <= N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 1; col <= M; col++) {
                staticMatrix[row][col] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        for (int i = 0; i < K; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            rotates.add(new Rotate(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken())));
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        dfs(staticMatrix, new HashSet<>());
    }

    static void dfs(int[][] matrix, Set<Rotate> visited) {
        if (visited.size() >= rotates.size()) {
            //System.out.println("--------------------");
            if (result == -1) {
                result = getMatrixValue(matrix);
            } else {
                result = Math.min(result, getMatrixValue(matrix));
            }
        }

        for (Rotate rotate: rotates) {
            if (visited.contains(rotate)) {
                continue;
            }

            int[][] rotatedMatrix = getRotatedMatrix(matrix, rotate);

            //print(rotatedMatrix);

            visited.add(rotate);
            dfs(rotatedMatrix, visited);
            visited.remove(rotate);
        }
    }

    static int[][] getRotatedMatrix(int[][] oldMatrix, Rotate rotate) {
        int[][] newMatrix = new int[N + 1][M + 1];

        /*
             가장 왼쪽 윗 칸이 (r-s, c-s), 가장 오른쪽 아랫 칸이 (r+s, c+s)인 정사각형
             행 범위: (r-s ~ r+s)
             열 범위: (c-s ~ c+s)
         */

        boolean[][] visited = new boolean[N + 1][M + 1];

        int upperRow = rotate.r - rotate.s;
        int underRow = rotate.r + rotate.s;
        int leftCol = rotate.c - rotate.s;
        int rightCol = rotate.c + rotate.s;

        while (upperRow != underRow) {
            int oldUpRightValue = oldMatrix[upperRow][rightCol];

            for (int col = rightCol - 1; col >= leftCol; col--) {
                newMatrix[upperRow][col + 1] = oldMatrix[upperRow][col];
                visited[upperRow][col + 1] = true;
            }
            for (int row = upperRow + 1; row <= underRow; row++) {
                newMatrix[row - 1][leftCol] = oldMatrix[row][leftCol];
                visited[row - 1][leftCol] = true;
            }
            for (int col = leftCol + 1; col <= rightCol; col++) {
                newMatrix[underRow][col - 1] = oldMatrix[underRow][col];
                visited[underRow][col - 1] = true;
            }
            for (int row = underRow - 1; row > upperRow; row--) {
                newMatrix[row + 1][rightCol] = oldMatrix[row][rightCol];
                visited[row + 1][rightCol] = true;
            }

            newMatrix[upperRow + 1][rightCol] = oldUpRightValue;
            visited[upperRow + 1][rightCol] = true;

            upperRow++;
            leftCol++;
            underRow--;
            rightCol--;
        }

        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= M; col++) {
                if (!visited[row][col]) {
                    newMatrix[row][col] = oldMatrix[row][col];
                }
            }
        }

        return newMatrix;
    }

    static int getMatrixValue(int[][] matrix) {
        int min = 0;

        for (int row = 1; row <= N; row++) {
            int sum = 0;
            for (int col = 1; col <= M; col++) {
                sum += matrix[row][col];
            }

            if (row == 1) {
                min = sum;
            } else {
                min = Math.min(min, sum);
            }
        }

        return min;
    }

    /*static void print(int[][] matrix) {
        for (int row = 1; row <= N; row++) {
            System.out.println(Arrays.toString(matrix[row]));
        }
        System.out.println();
    }*/
}
