import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer stringTokenizer;
    static int N, K, L;
    static int[][] matrix; // 0: 아무것도 없음, 1: 사과, 2: 뱀
    static final Map<Integer, Character> convertingDirectionsMap = new HashMap<>();
    static Direction currentDirection;
    static int headRow = 1, headCol = 1;
    static final List<Location> snakeLocations = new LinkedList<>();
    enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }
    static class Location {
        int row;
        int col;
        Location (int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(reader.readLine());
        K = Integer.parseInt(reader.readLine());

        matrix = new int[N + 1][N + 1];

        for (int i = 0; i < K; i++) {
            stringTokenizer = new StringTokenizer(reader.readLine());
            matrix[Integer.parseInt(stringTokenizer.nextToken())][Integer.parseInt(stringTokenizer.nextToken())] = 1;
        }

        L = Integer.parseInt(reader.readLine());

        for (int i = 0; i < L; i++) {
            stringTokenizer = new StringTokenizer(reader.readLine());
            convertingDirectionsMap.put(Integer.parseInt(stringTokenizer.nextToken()), stringTokenizer.nextToken().charAt(0));
        }

        writer.write(String.format("%d\n", solve()));
        writer.flush();
        writer.close();
    }

    static int solve() {
        currentDirection = Direction.RIGHT;
        int time = 0;
        snakeLocations.add(new Location(headRow, headCol));

        while (isMovable()) {
            move();
            time++;

            if (convertingDirectionsMap.containsKey(time)) {
                char rotatingDirection = convertingDirectionsMap.get(time);
                if (rotatingDirection == 'L') {
                    rotateLeft();
                } else if (rotatingDirection == 'D') {
                    rotateRight();
                }
            }
        }

        return time + 1;
    }

    static void rotateRight() {
        if (currentDirection.equals(Direction.UP)) {
            currentDirection = Direction.RIGHT;
        } else if (currentDirection.equals(Direction.RIGHT)) {
            currentDirection = Direction.DOWN;
        } else if (currentDirection.equals(Direction.DOWN)) {
            currentDirection = Direction.LEFT;
        } else if (currentDirection.equals(Direction.LEFT)) {
            currentDirection = Direction.UP;
        }
    }

    static void rotateLeft() {
        if (currentDirection.equals(Direction.UP)) {
            currentDirection = Direction.LEFT;
        } else if (currentDirection.equals(Direction.LEFT)) {
            currentDirection = Direction.DOWN;
        } else if (currentDirection.equals(Direction.DOWN)) {
            currentDirection = Direction.RIGHT;
        } else if (currentDirection.equals(Direction.RIGHT)) {
            currentDirection = Direction.UP;
        }
    }

    static boolean isMovable() {
        int nextRow = headRow, nextCol = headCol;

        if (currentDirection.equals(Direction.UP)) {
            nextRow--;
        } else if (currentDirection.equals(Direction.LEFT)) {
            nextCol--;
        } else if (currentDirection.equals(Direction.DOWN)) {
            nextRow++;
        } else if (currentDirection.equals(Direction.RIGHT)) {
            nextCol++;
        }

        return isInMatrix(nextRow, nextCol) && !isSnake(nextRow, nextCol);
    }

    static boolean isInMatrix(int row, int col) {
        return row > 0 && row <= N && col > 0 && col <= N;
    }

    static boolean isSnake(int row, int col) {
        return matrix[row][col] == 2;
    }

    static void move() {
        boolean appleExist = false;

        if (currentDirection.equals(Direction.UP)) {
            headRow = headRow - 1;
        } else if (currentDirection.equals(Direction.LEFT)) {
            headCol = headCol - 1;
        } else if (currentDirection.equals(Direction.DOWN)) {
            headRow = headRow + 1;
        } else if (currentDirection.equals(Direction.RIGHT)) {
            headCol = headCol + 1;
        }

        appleExist = (matrix[headRow][headCol] == 1);
        matrix[headRow][headCol] = 2;
        snakeLocations.add(new Location(headRow, headCol));

        if (!appleExist) {
            Location tail = snakeLocations.get(0);
            snakeLocations.remove(tail);
            matrix[tail.row][tail.col] = 0;
        }
    }
}
