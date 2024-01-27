import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int result;
    
    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        int[][] matrix = new int[N][N];

        for (int row = 0; row < N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < N; col++) {
                matrix[row][col] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        solve(matrix);

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve(int[][] matrix) {
        dfs(matrix, 0, -1);
    }

    // direction -> 0: 북, 1: 서, 2: 남, 3: 동
    static void dfs(int[][] matrix, int count, int prevDirection) {
        if (count >= 5) {
            return;
        }

        for (int direction = 0; direction < 4; direction++) {
            if (direction == prevDirection) {
                continue;
            }

            int[][] movedMatrix = move(matrix, direction);
            int blockMaxValue = getBlockMaxValue(movedMatrix);
            result = Math.max(result, blockMaxValue);

            dfs(movedMatrix, count + 1, direction);
        }
    }

    static int[][] move(int[][] oldMatrix, int direction) {
        int[][] newMatrix = copyMatrix(oldMatrix);

        moveToDirection(newMatrix, direction);
        crash(newMatrix, direction);
        moveToDirection(newMatrix, direction);

        return newMatrix;
    }

    static void moveToDirection(int[][] matrix, int direction) {
        if (direction == 0) {
            for (int col = 0; col < N; col++) {
                for (int row = 0; row < N; row++) {
                    if (matrix[row][col] == 0) {
                        continue;
                    }

                    for (int nextRow = row - 1; nextRow >= 0; nextRow--) {
                        if (matrix[nextRow][col] == 0) {
                            matrix[nextRow][col] = matrix[nextRow + 1][col];
                            matrix[nextRow + 1][col] = 0;
                        } else {
                            break;
                        }
                    }
                }
            }
        } else if (direction == 1) {
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    if (matrix[row][col] == 0) {
                        continue;
                    }

                    for (int nextCol = col - 1; nextCol >= 0; nextCol--) {
                        if (matrix[row][nextCol] == 0) {
                            matrix[row][nextCol] = matrix[row][nextCol + 1];
                            matrix[row][nextCol + 1] = 0;
                        } else {
                            break;
                        }
                    }
                }
            }
        } else if (direction == 2) {
            for (int col = 0; col < N; col++) {

                for (int row = N - 1; row >= 0; row--) {
                    if (matrix[row][col] == 0) {
                        continue;
                    }

                    for (int nextRow = row + 1; nextRow < N; nextRow++) {
                        if (matrix[nextRow][col] == 0) {
                            matrix[nextRow][col] = matrix[nextRow - 1][col];
                            matrix[nextRow - 1][col] = 0;
                        } else {
                            break;
                        }
                    }
                }

            }
        } else {
            for (int row = 0; row < N; row++) {
                for (int col = N - 1; col >= 0; col--) {
                    if (matrix[row][col] == 0) {
                        continue;
                    }

                    for (int nextCol = col + 1; nextCol < N; nextCol++) {
                        if (matrix[row][nextCol] == 0) {
                            matrix[row][nextCol] = matrix[row][nextCol - 1];
                            matrix[row][nextCol - 1] = 0;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    static void crash(int[][] matrix, int direction) {
        if (direction == 0) {
            for (int col = 0; col < N; col++) {
                for (int row = 0; row < N - 1;) {
                    if (matrix[row][col] == 0) {
                        row++;
                        continue;
                    }

                    if (matrix[row][col] == matrix[row + 1][col]) {
                        matrix[row][col] *= 2;
                        matrix[row + 1][col] = 0;
                        row += 2;
                    } else {
                        row++;
                    }
                }
            }
        } else if (direction == 1) {
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N - 1;) {
                    if (matrix[row][col] == 0) {
                        col++;
                        continue;
                    }

                    if (matrix[row][col] == matrix[row][col + 1]) {
                        matrix[row][col] *= 2;
                        matrix[row][col + 1] = 0;
                        col += 2;
                    } else {
                        col++;
                    }
                }
            }
        } else if (direction == 2) {
            for (int col = 0; col < N; col++) {
                for (int row = N - 1; row > 0;) {
                    if (matrix[row][col] == 0) {
                        row--;
                        continue;
                    }

                    if (matrix[row][col] == matrix[row - 1][col]) {
                        matrix[row][col] *= 2;
                        matrix[row - 1][col] = 0;
                        row -= 2;
                    } else {
                        row--;
                    }
                }
            }
        } else {
            for (int row = 0; row < N; row++) {
                for (int col = N - 1; col > 0;) {
                    if (matrix[row][col] == 0) {
                        col--;
                        continue;
                    }

                    if (matrix[row][col] == matrix[row][col - 1]) {
                        matrix[row][col] *= 2;
                        matrix[row][col - 1] = 0;
                        col -= 2;
                    } else {
                        col--;
                    }
                }
            }
        }
    }

    static int[][] copyMatrix(int[][] oldMatrix) {
        int[][] newMatrix = new int[N][N];

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                newMatrix[row][col] = oldMatrix[row][col];
            }
        }

        return newMatrix;
    }

    static int getBlockMaxValue(int[][] matrix) {
        int maxValue = 0;

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                maxValue = Math.max(maxValue, matrix[row][col]);
            }
        }

        return maxValue;
    }
}
