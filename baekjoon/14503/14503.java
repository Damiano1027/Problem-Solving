import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N, M;
    static int[][] matrix;
    static Robot robot;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        int[] firstLine = toIntArray(reader.readLine());
        N = firstLine[0];
        M = firstLine[1];
        matrix = new int[N][M];

        int[] secondLine = toIntArray(reader.readLine());
        robot = new Robot(secondLine[0], secondLine[1], secondLine[2]);

        for (int i = 0; i < N; i++) {
            matrix[i] = toIntArray(reader.readLine());
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.close();
    }

    static int[] toIntArray(String line) {
        String[] strings = line.split(" ");
        int[] array = new int[strings.length];

        for (int i = 0; i < strings.length; i++) {
            array[i] = Integer.parseInt(strings[i]);
        }

        return array;
    }

    static class Robot {
        int row;
        int col;
        int direction; // 0:북, 1:동, 2:남, 3:서

        Robot(int row, int col, int direction) {
            this.row = row;
            this.col = col;
            this.direction = direction;
        }

        boolean isDirectionNorth() {
            return direction == 0;
        }
        boolean isDirectionEast() {
            return direction == 1;
        }
        boolean isDirectionSouth() {
            return direction == 2;
        }
        boolean isDirectionWest() {
            return direction == 3;
        }

        void moveToNorth() {
            row--;
        }
        void moveToEast() {
            col++;
        }
        void moveToSouth() {
            row++;
        }
        void moveToWest() {
            col--;
        }
    }

    static void solve() {
        while (true) {
            if (!step()) {
                break;
            }
        }
    }

    static boolean step() {
        if (matrix[robot.row][robot.col] == 0) {
            matrix[robot.row][robot.col] = 2;
            result++;
        }

        if (hasCleanablePlaceInAround(robot.row, robot.col)) {
            robot.direction = robot.direction - 1;
            if (robot.direction < 0) {
                robot.direction = 3;
            }

            if (robot.isDirectionNorth() && isCleanable(robot.row - 1, robot.col)) {
                robot.row = robot.row - 1;
            } else if (robot.isDirectionWest() && isCleanable(robot.row, robot.col - 1)) {
                robot.col = robot.col - 1;
            } else if (robot.isDirectionSouth() && isCleanable(robot.row + 1, robot.col)) {
                robot.row = robot.row + 1;
            } else if (robot.isDirectionEast() && isCleanable(robot.row, robot.col + 1)) {
                robot.col = robot.col + 1;
            }
        } else {
            if (robot.isDirectionNorth() && isMovableTo(robot.row + 1, robot.col)) {
                robot.moveToSouth();
            } else if (robot.isDirectionWest() && isMovableTo(robot.row, robot.col + 1)) {
                robot.moveToEast();
            } else if (robot.isDirectionSouth() && isMovableTo(robot.row - 1, robot.col)) {
                robot.moveToNorth();
            } else if (robot.isDirectionEast() && isMovableTo(robot.row, robot.col - 1)) {
                robot.moveToWest();
            } else {
                return false;
            }
        }

        return true;
    }

    static boolean hasCleanablePlaceInAround(int row, int col) {
        return isCleanable(row - 1, col) || isCleanable(row, col - 1)
                || isCleanable(row + 1, col) || isCleanable(row, col + 1);
    }

    static boolean isCleanable(int row, int col) {
        return isInRange(row, col) && matrix[row][col] == 0;
    }

    static boolean isMovableTo(int row, int col) {
        return isInRange(row, col) && matrix[row][col] != 1;
    }

    static boolean isInRange(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }
}
