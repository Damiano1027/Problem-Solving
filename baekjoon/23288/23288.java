import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, K;
    static int[][] matrix;
    static int[] dice = new int[6];
    static int uppersideIndex = 1, undersideIndex = 3;
    static int currentRow = 0, currentCol = 0;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    enum Direction {
        UP, LEFT, DOWN, RIGHT
    }
    static Direction direction = Direction.RIGHT;
    static class Location {
        int row, col;
        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N][M];

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < M; j++) {
                matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        dice[0] = 2;
        dice[1] = 1;
        dice[2] = 5;
        dice[3] = 6;
        dice[4] = 4;
        dice[5] = 3;

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        int count = 0;

        while (count < K) {
            step1();
            step2();
            step3();

            count++;
        }
    }

    static void step1() {
        int nextRow = currentRow + dRow[direction.ordinal()];
        int nextCol = currentCol + dCol[direction.ordinal()];

        if (!isInMatrix(nextRow, nextCol)) {
            if (direction.equals(Direction.UP)) {
                direction = Direction.DOWN;
            } else if (direction.equals(Direction.DOWN)) {
                direction = Direction.UP;
            } else if (direction.equals(Direction.LEFT)) {
                direction = Direction.RIGHT;
            } else if (direction.equals(Direction.RIGHT)) {
                direction = Direction.LEFT;
            }

            nextRow = currentRow + dRow[direction.ordinal()];
            nextCol = currentCol + dCol[direction.ordinal()];
        }

        currentRow = nextRow;
        currentCol = nextCol;

        if (direction.equals(Direction.UP)) {
            rollUp();
        } else if (direction.equals(Direction.DOWN)) {
            rollDown();
        } else if (direction.equals(Direction.LEFT)) {
            rollLeft();
        } else if (direction.equals(Direction.RIGHT)) {
            rollRight();
        }
    }

    static void step2() {
        int value = matrix[currentRow][currentCol];

        Queue<Location> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];
        queue.add(new Location(currentRow, currentCol));
        visited[currentRow][currentCol] = true;
        int count = 0;

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            count++;

            for (int i = 0; i < 4; i++) {
                int nextRow = currentLocation.row + dRow[i];
                int nextCol = currentLocation.col + dCol[i];

                if (isInMatrix(nextRow, nextCol) && !visited[nextRow][nextCol] && matrix[nextRow][nextCol] == value) {
                    queue.add(new Location(nextRow, nextCol));
                    visited[nextRow][nextCol] = true;
                }
            }
        }

        result += value * count;
    }

    static void step3() {
        int diceUnderValue = dice[undersideIndex];
        int sectionValue = matrix[currentRow][currentCol];

        if (diceUnderValue > sectionValue) {
            rotateDirectionRight();
        } else if (diceUnderValue < sectionValue) {
            rotateDirectionLeft();
        }
    }

    static void rollUp() {
        int temp = dice[0];

        for (int i = 0; i < 3; i++) {
            dice[i] = dice[i + 1];
        }

        dice[3] = temp;

    }

    static void rollLeft() {
        int temp1 = dice[4];
        int temp2 = dice[5];

        dice[4] = dice[uppersideIndex];
        dice[5] = dice[undersideIndex];

        dice[uppersideIndex] = temp2;
        dice[undersideIndex] = temp1;
    }

    static void rollDown() {
        int temp = dice[3];

        for (int i = 3; i > 0; i--) {
            dice[i] = dice[i - 1];
        }

        dice[0] = temp;
    }

    static void rollRight() {
        int temp1 = dice[4];
        int temp2 = dice[5];

        dice[4] = dice[undersideIndex];
        dice[5] = dice[uppersideIndex];

        dice[uppersideIndex] = temp1;
        dice[undersideIndex] = temp2;
    }

    static void rotateDirectionRight() {
        if (direction.equals(Direction.UP)) {
            direction = Direction.RIGHT;
        } else if (direction.equals(Direction.RIGHT)) {
            direction = Direction.DOWN;
        } else if (direction.equals(Direction.DOWN)) {
            direction = Direction.LEFT;
        } else if (direction.equals(Direction.LEFT)) {
            direction = Direction.UP;
        }
    }

    static void rotateDirectionLeft() {
        if (direction.equals(Direction.UP)) {
            direction = Direction.LEFT;
        } else if (direction.equals(Direction.LEFT)) {
            direction = Direction.DOWN;
        } else if (direction.equals(Direction.DOWN)) {
            direction = Direction.RIGHT;
        } else if (direction.equals(Direction.RIGHT)) {
            direction = Direction.UP;
        }
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }
}
