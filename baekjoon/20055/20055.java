import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, K;
    static Info[][] matrix;
    static class Info {
        int durability;
        boolean robotExist;
        Info(int durability) {
            this.durability = durability;
            robotExist = false;
        }
    }
    static class Location {
        int row, col;
        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    static List<Location> robotLocations = new LinkedList<>();
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        matrix = new Info[2][N];

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < N; i++) {
            matrix[0][i] = new Info(Integer.parseInt(tokenizer.nextToken()));
        }
        for (int i = N - 1; i >= 0; i--) {
            matrix[1][i] = new Info(Integer.parseInt(tokenizer.nextToken()));
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        int time = 0;

        do {
            time++;
            moveBelt();
            moveRobot();
            makeRobot();
        } while (!isEnd());

        result = time;
    }

    static void moveBelt() {
        Info temp = matrix[1][0];

        for (int j = 0; j < N - 1; j++) {
            matrix[1][j] = matrix[1][j + 1];
        }
        matrix[1][N - 1] = matrix[0][N - 1];
        for (int j = N - 1; j > 0; j--) {
            matrix[0][j] = matrix[0][j - 1];
        }
        matrix[0][0] = temp;

        for (Location robotLocation: robotLocations) {
            Location nextLocation = nextLocation(robotLocation);
            robotLocation.row = nextLocation.row;
            robotLocation.col = nextLocation.col;
        }

        if (matrix[0][N - 1].robotExist) {
            matrix[0][N - 1].robotExist = false;
            robotLocations.remove(0);
        }
    }

    static void moveRobot() {
        for (Location currentLocation: robotLocations) {
            Location nextLocation = nextLocation(currentLocation);
            if (isMovableTo(nextLocation)) {
                matrix[nextLocation.row][nextLocation.col].robotExist = true;
                matrix[nextLocation.row][nextLocation.col].durability--;
                matrix[currentLocation.row][currentLocation.col].robotExist = false;

                currentLocation.row = nextLocation.row;
                currentLocation.col = nextLocation.col;
            }
        }

        if (matrix[0][N - 1].robotExist) {
            matrix[0][N - 1].robotExist = false;
            robotLocations.remove(0);
        }
    }

    static void makeRobot() {
        if (matrix[0][0].durability != 0) {
            matrix[0][0].robotExist = true;
            matrix[0][0].durability--;
            robotLocations.add(new Location(0, 0));
        }
    }

    static boolean isMovableTo(Location location) {
        return !matrix[location.row][location.col].robotExist && matrix[location.row][location.col].durability >= 1;
    }

    static Location nextLocation(Location location) {
        int row = location.row;
        int col = location.col;

        if (row == 0 && col == N - 1) {
            row++;
        } else if (row == 1 && col == 0) {
            row--;
        } else {
            if (row == 0) {
                col++;
            } else {
                col--;
            }
        }

        return new Location(row, col);
    }

    static boolean isEnd() {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j].durability == 0) {
                    count++;
                }
            }
        }
        return count >= K;
    }
}
