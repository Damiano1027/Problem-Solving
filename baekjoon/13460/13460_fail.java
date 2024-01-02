import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    enum Direction {
        UP, LEFT, DOWN, RIGHT;
    }
    static class Result {
        boolean redFinished, blueFinished;
    }
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        char[][] matrix;
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new char[N][M];

        for (int i = 0; i < N; i++) {
            String line = reader.readLine();
            for (int j = 0; j < line.length(); j++) {
                matrix[i][j] = line.charAt(j);
            }
        }

        solve(matrix);

        if (min == Integer.MAX_VALUE) {
            writer.write("-1\n");
        } else {
            writer.write(String.format("%d\n", min));
        }
        writer.flush();
        writer.close();
    }

    static void solve(char[][] matrix) {
        dfs(1, matrix, Direction.UP);
        dfs(1, matrix, Direction.LEFT);
        dfs(1, matrix, Direction.DOWN);
        dfs(1, matrix, Direction.RIGHT);
    }

    static void dfs(int count, char[][] matrix, Direction direction) {
        if (count >= 11) {
            return;
        }

        if (direction.equals(Direction.UP)) {
            char[][] copiedMatrix = copiedMatrix(matrix);
            Result result = move(copiedMatrix, Direction.UP);
            if (result.redFinished && !result.blueFinished) {
                min = Math.min(min, count);
                return;
            } else {
                dfs(count + 1, copiedMatrix, Direction.LEFT);
                dfs(count + 1, copiedMatrix, Direction.DOWN);
                dfs(count + 1, copiedMatrix, Direction.RIGHT);
            }
        } else if (direction.equals(Direction.LEFT)) {
            char[][] copiedMatrix = copiedMatrix(matrix);
            Result result = move(copiedMatrix, Direction.LEFT);
            if (result.redFinished && !result.blueFinished) {
                min = Math.min(min, count);
                return;
            } else {
                dfs(count + 1, copiedMatrix, Direction.UP);
                dfs(count + 1, copiedMatrix, Direction.DOWN);
                dfs(count + 1, copiedMatrix, Direction.RIGHT);
            }
        } else if (direction.equals(Direction.DOWN)) {
            char[][] copiedMatrix = copiedMatrix(matrix);
            Result result = move(copiedMatrix, Direction.DOWN);
            if (result.redFinished && !result.blueFinished) {
                min = Math.min(min, count);
                return;
            } else {
                dfs(count + 1, copiedMatrix, Direction.UP);
                dfs(count + 1, copiedMatrix, Direction.LEFT);
                dfs(count + 1, copiedMatrix, Direction.RIGHT);
            }
        } else if (direction.equals(Direction.RIGHT)) {
            char[][] copiedMatrix = copiedMatrix(matrix);
            Result result = move(copiedMatrix, Direction.RIGHT);
            if (result.redFinished && !result.blueFinished) {
                min = Math.min(min, count);
                return;
            } else {
                dfs(count + 1, copiedMatrix, Direction.UP);
                dfs(count + 1, copiedMatrix, Direction.LEFT);
                dfs(count + 1, copiedMatrix, Direction.DOWN);
            }
        }
    }

    static char[][] copiedMatrix(char[][] matrix) {
        char[][] copiedMatrix = new char[N][M];

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                copiedMatrix[row][col] = matrix[row][col];
            }
        }

        return copiedMatrix;
    }

    static Result move(char[][] matrix, Direction direction) {
        Result result = new Result();
        int redRow = 0, redCol = 0, blueRow = 0, blueCol = 0;

        for (int row = 1; row < N - 1; row++) {
            for (int col = 1; col < M - 1; col++) {
                if (matrix[row][col] == 'R') {
                    redRow = row;
                    redCol = col;
                } else if (matrix[row][col] == 'B') {
                    blueRow = row;
                    blueCol = col;
                }
            }
        }

        if (direction.equals(Direction.UP)) {
            if (redRow < blueRow) {
                while (isMovableTo(redRow - 1, redCol, matrix)) {
                    matrix[redRow][redCol] = '.';

                    if (matrix[redRow - 1][redCol] == 'O') {
                        result.redFinished = true;
                        break;
                    } else {
                        matrix[redRow - 1][redCol] = 'R';
                        redRow--;
                    }
                }
                while (isMovableTo(blueRow - 1, blueCol, matrix)) {
                    matrix[blueRow][blueCol] = '.';

                    if (matrix[blueRow - 1][blueCol] == 'O') {
                        result.blueFinished = true;
                        break;
                    } else {
                        matrix[blueRow - 1][blueCol] = 'B';
                        blueRow--;
                    }
                }
            } else {
                while (isMovableTo(blueRow - 1, blueCol, matrix)) {
                    matrix[blueRow][blueCol] = '.';

                    if (matrix[blueRow - 1][blueCol] == 'O') {
                        result.blueFinished = true;
                        break;
                    } else {
                        matrix[blueRow - 1][blueCol] = 'B';
                        blueRow--;
                    }
                }
                while (isMovableTo(redRow - 1, redCol, matrix)) {
                    matrix[redRow][redCol] = '.';

                    if (matrix[redRow - 1][redCol] == 'O') {
                        result.redFinished = true;
                        break;
                    } else {
                        matrix[redRow - 1][redCol] = 'R';
                        redRow--;
                    }
                }
            }
        } else if (direction.equals(Direction.LEFT)) {
            if (redCol < blueCol) {
                while (isMovableTo(redRow, redCol - 1, matrix)) {
                    matrix[redRow][redCol] = '.';

                    if (matrix[redRow][redCol - 1] == 'O') {
                        result.redFinished = true;
                        break;
                    } else {
                        matrix[redRow][redCol - 1] = 'R';
                        redCol--;
                    }
                }
                while (isMovableTo(blueRow, blueCol - 1, matrix)) {
                    matrix[blueRow][blueCol] = '.';

                    if (matrix[blueRow][blueCol - 1] == 'O') {
                        result.blueFinished = true;
                        break;
                    } else {
                        matrix[blueRow][blueCol - 1] = 'B';
                        blueCol--;
                    }
                }
            } else {
                while (isMovableTo(blueRow, blueCol - 1, matrix)) {
                    matrix[blueRow][blueCol] = '.';

                    if (matrix[blueRow][blueCol - 1] == 'O') {
                        result.blueFinished = true;
                        break;
                    } else {
                        matrix[blueRow][blueCol - 1] = 'B';
                        blueCol--;
                    }
                }
                while (isMovableTo(redRow, redCol - 1, matrix)) {
                    matrix[redRow][redCol] = '.';

                    if (matrix[redRow][redCol - 1] == 'O') {
                        result.redFinished = true;
                        break;
                    } else {
                        matrix[redRow][redCol - 1] = 'R';
                        redCol--;
                    }
                }
            }
        } else if (direction.equals(Direction.DOWN)) {
            if (redRow > blueRow) {
                while (isMovableTo(redRow + 1, redCol, matrix)) {
                    matrix[redRow][redCol] = '.';

                    if (matrix[redRow + 1][redCol] == 'O') {
                        result.redFinished = true;
                        break;
                    } else {
                        matrix[redRow + 1][redCol] = 'R';
                        redRow++;
                    }
                }
                while (isMovableTo(blueRow + 1, blueCol, matrix)) {
                    matrix[blueRow][blueCol] = '.';

                    if (matrix[blueRow + 1][blueCol] == 'O') {
                        result.blueFinished = true;
                        break;
                    } else {
                        matrix[blueRow + 1][blueCol] = 'B';
                        blueRow++;
                    }
                }
            } else {
                while (isMovableTo(blueRow + 1, blueCol, matrix)) {
                    matrix[blueRow][blueCol] = '.';

                    if (matrix[blueRow + 1][blueCol] == 'O') {
                        result.blueFinished = true;
                        break;
                    } else {
                        matrix[blueRow + 1][blueCol] = 'B';
                        blueRow++;
                    }
                }
                while (isMovableTo(redRow + 1, redCol, matrix)) {
                    matrix[redRow][redCol] = '.';

                    if (matrix[redRow + 1][redCol] == 'O') {
                        result.redFinished = true;
                        break;
                    } else {
                        matrix[redRow + 1][redCol] = 'R';
                        redRow++;
                    }
                }
            }
        } else if (direction.equals(Direction.RIGHT)) {
            if (redCol > blueCol) {
                while (isMovableTo(redRow, redCol + 1, matrix)) {
                    matrix[redRow][redCol] = '.';

                    if (matrix[redRow][redCol + 1] == 'O') {
                        result.redFinished = true;
                        break;
                    } else {
                        matrix[redRow][redCol + 1] = 'R';
                        redCol++;
                    }
                }
                while (isMovableTo(blueRow, blueCol + 1, matrix)) {
                    matrix[blueRow][blueCol] = '.';

                    if (matrix[blueRow][blueCol + 1] == 'O') {
                        result.blueFinished = true;
                        break;
                    } else {
                        matrix[blueRow][blueCol + 1] = 'B';
                        blueCol++;
                    }
                }
            } else {
                while (isMovableTo(blueRow, blueCol + 1, matrix)) {
                    matrix[blueRow][blueCol] = '.';

                    if (matrix[blueRow][blueCol + 1] == 'O') {
                        result.blueFinished = true;
                        break;
                    } else {
                        matrix[blueRow][blueCol + 1] = 'B';
                        blueCol++;
                    }
                }
                while (isMovableTo(redRow, redCol + 1, matrix)) {
                    matrix[redRow][redCol] = '.';

                    if (matrix[redRow][redCol + 1] == 'O') {
                        result.redFinished = true;
                        break;
                    } else {
                        matrix[redRow][redCol + 1] = 'R';
                        redCol++;
                    }
                }
            }
        }

        return result;
    }

    static boolean isMovableTo(int row, int col, char[][] matrix) {
        return row > 0 && row < N - 1 && col > 0 && col < M - 1
                && (matrix[row][col] == '.' || matrix[row][col] == 'O');
    }
}
