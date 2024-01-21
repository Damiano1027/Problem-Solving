import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int result;
    static class Location {
        int row, col;
        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
        Location copy() {
            return new Location(row, col);
        }
    }

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        int[][] matrix = new int[N][N];

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < N; j++) {
                matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        solve(matrix);

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve(int[][] matrix) {
        matrix[0][0] = 2;
        matrix[0][1] = 2;
        dfs(matrix, new Location(0, 0), new Location(0, 1));
    }

    static void dfs(int[][] matrix, Location location1, Location location2) {
        if (matrix[N - 1][N - 1] == 2) {
            result++;
            return;
        }

        if (isHorizontal(location1, location2)) {
            if (isInMatrix(location2.row, location2.col + 1) && matrix[location2.row][location2.col + 1] == 0) {
                matrix[location1.row][location1.col] = 0;
                matrix[location2.row][location2.col + 1] = 2;

                Location copiedLocation1 = location1.copy();
                Location copiedLocation2 = location2.copy();
                copiedLocation1.col++;
                copiedLocation2.col++;

                dfs(matrix, copiedLocation1, copiedLocation2);

                matrix[copiedLocation2.row][copiedLocation2.col] = 0;
                matrix[location1.row][location1.col] = 2;
                matrix[location2.row][location2.col] = 2;
            }
            if (isInMatrix(location2.row + 1, location2.col + 1) && matrix[location2.row + 1][location2.col + 1] == 0
                    && matrix[location2.row][location2.col + 1] == 0 && matrix[location2.row + 1][location2.col] == 0) {
                matrix[location1.row][location1.col] = 0;
                matrix[location2.row + 1][location2.col + 1] = 2;

                Location copiedLocation1 = location1.copy();
                Location copiedLocation2 = location2.copy();
                copiedLocation1.col++;
                copiedLocation2.row++;
                copiedLocation2.col++;

                dfs(matrix, copiedLocation1, copiedLocation2);

                matrix[copiedLocation2.row][copiedLocation2.col] = 0;
                matrix[location1.row][location1.col] = 2;
                matrix[location2.row][location2.col] = 2;
            }
        } else if (isVertical(location1, location2)) {
            if (isInMatrix(location2.row + 1, location2.col) && matrix[location2.row + 1][location2.col] == 0) {
                matrix[location1.row][location1.col] = 0;
                matrix[location2.row + 1][location2.col] = 2;

                Location copiedLocation1 = location1.copy();
                Location copiedLocation2 = location2.copy();
                copiedLocation1.row++;
                copiedLocation2.row++;

                dfs(matrix, copiedLocation1, copiedLocation2);

                matrix[copiedLocation2.row][copiedLocation2.col] = 0;
                matrix[location1.row][location1.col] = 2;
                matrix[location2.row][location2.col] = 2;
            }
            if (isInMatrix(location2.row + 1, location2.col + 1) && matrix[location2.row + 1][location2.col + 1] == 0
                    && matrix[location2.row][location2.col + 1] == 0 && matrix[location2.row + 1][location2.col] == 0) {
                matrix[location1.row][location1.col] = 0;
                matrix[location2.row + 1][location2.col + 1] = 2;

                Location copiedLocation1 = location1.copy();
                Location copiedLocation2 = location2.copy();
                copiedLocation1.row++;
                copiedLocation2.row++;
                copiedLocation2.col++;

                dfs(matrix, copiedLocation1, copiedLocation2);

                matrix[copiedLocation2.row][copiedLocation2.col] = 0;
                matrix[location1.row][location1.col] = 2;
                matrix[location2.row][location2.col] = 2;
            }
        } else if (isDiagonal(location1, location2)) {
            if (isInMatrix(location2.row, location2.col + 1) && matrix[location2.row][location2.col + 1] == 0) {
                matrix[location1.row][location1.col] = 0;
                matrix[location2.row][location2.col + 1] = 2;

                Location copiedLocation1 = location1.copy();
                Location copiedLocation2 = location2.copy();
                copiedLocation1.row++;
                copiedLocation1.col++;
                copiedLocation2.col++;

                dfs(matrix, copiedLocation1, copiedLocation2);

                matrix[copiedLocation2.row][copiedLocation2.col] = 0;
                matrix[location1.row][location1.col] = 2;
                matrix[location2.row][location2.col] = 2;
            }
            if (isInMatrix(location2.row + 1, location2.col) && matrix[location2.row + 1][location2.col] == 0) {
                matrix[location1.row][location1.col] = 0;
                matrix[location2.row + 1][location2.col] = 2;

                Location copiedLocation1 = location1.copy();
                Location copiedLocation2 = location2.copy();
                copiedLocation1.row++;
                copiedLocation1.col++;
                copiedLocation2.row++;

                dfs(matrix, copiedLocation1, copiedLocation2);

                matrix[copiedLocation2.row][copiedLocation2.col] = 0;
                matrix[location1.row][location1.col] = 2;
                matrix[location2.row][location2.col] = 2;
            }
            if (isInMatrix(location2.row + 1, location2.col + 1) && matrix[location2.row + 1][location2.col + 1] == 0
                    && matrix[location2.row][location2.col + 1] == 0 && matrix[location2.row + 1][location2.col] == 0) {
                matrix[location1.row][location1.col] = 0;
                matrix[location2.row + 1][location2.col + 1] = 2;

                Location copiedLocation1 = location1.copy();
                Location copiedLocation2 = location2.copy();
                copiedLocation1.row++;
                copiedLocation1.col++;
                copiedLocation2.row++;
                copiedLocation2.col++;

                dfs(matrix, copiedLocation1, copiedLocation2);

                matrix[copiedLocation2.row][copiedLocation2.col] = 0;
                matrix[location1.row][location1.col] = 2;
                matrix[location2.row][location2.col] = 2;
            }
        }
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    static boolean isHorizontal(Location location1, Location location2) {
        return location1.row == location2.row;
    }

    static boolean isVertical(Location location1, Location location2) {
        return location1.col == location2.col;
    }

    static boolean isDiagonal(Location location1, Location location2) {
        return location1.row + 1 == location2.row && location1.col + 1 == location2.col;
    }
}
