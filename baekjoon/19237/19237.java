import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, k;
    static int[] sharkDirections;
    static Location[] sharkLocations;
    static int[][] smell, smellSharkNumber;
    static int[][][] directionPriority;
    static boolean[] valid;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        k = Integer.parseInt(tokenizer.nextToken());

        sharkDirections = new int[M + 1];
        sharkLocations = new Location[M + 1];
        smell = new int[N][N];
        smellSharkNumber = new int[N][N];
        directionPriority = new int[M + 1][4][4];
        valid = new boolean[M + 1];
        Arrays.fill(valid, true);

        for (int row = 0; row < N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < N; col++) {
                int value = Integer.parseInt(tokenizer.nextToken());

                if (value > 0) {
                    sharkLocations[value] = new Location(row, col);
                }
            }
        }

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 1; i <= M; i++) {
            sharkDirections[i] = convertDirection(Integer.parseInt(tokenizer.nextToken()));
        }

        for (int i = 1; i <= M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < 4; j++) {
                int direction = Integer.parseInt(tokenizer.nextToken());
                directionPriority[i][0][j] = convertDirection(direction);
            }

            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < 4; j++) {
                int direction = Integer.parseInt(tokenizer.nextToken());
                directionPriority[i][2][j] = convertDirection(direction);
            }

            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < 4; j++) {
                int direction = Integer.parseInt(tokenizer.nextToken());
                directionPriority[i][1][j] = convertDirection(direction);
            }

            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < 4; j++) {
                int direction = Integer.parseInt(tokenizer.nextToken());
                directionPriority[i][3][j] = convertDirection(direction);
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static int convertDirection(int direction) {
        if (direction == 1) {
            direction = 0;
        } else if (direction == 3) {
            direction = 1;
        } else if (direction == 4) {
            direction = 3;
        }
        return direction;
    }

    static void solve() {
        int time = 0;

        spreadSmell();
        while (time < 1001) {
            moveSharks();
            time++;

            if (isEnd()) {
                break;
            }
        }

        result = (time > 1000) ? -1 : time;
    }

    static void spreadSmell() {
        for (int sharkNumber = 1; sharkNumber <= M; sharkNumber++) {
            Location sharkLocation = sharkLocations[sharkNumber];
            smell[sharkLocation.row][sharkLocation.col] = k;
            smellSharkNumber[sharkLocation.row][sharkLocation.col] = sharkNumber;
        }
    }

    /*
        int[] sharkDirections (O)
        Location[] sharkLocations (O)
        int[][] smell (O)
        int[] smellSharkNumber (O)
        boolean[] valid (O)
     */
    static void moveSharks() {
        List<SharkMove> sharkMoves = new ArrayList<>();

        for (int sharkNumber = 1; sharkNumber <= M; sharkNumber++) {
            if (!valid[sharkNumber]) {
                continue;
            }

            Location nextLocation = getNextLocation(sharkNumber);
            sharkMoves.add(new SharkMove(nextLocation, sharkNumber));
        }

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (smell[row][col] > 0) {
                    smell[row][col]--;

                    if (smell[row][col] == 0) {
                        smellSharkNumber[row][col] = 0;
                    }
                }
            }
        }

        int[][] tempMatrix = new int[N][N];
        for (SharkMove sharkMove : sharkMoves) {
            Location location = sharkMove.location;

            if (tempMatrix[location.row][location.col] > 0) {
                if (sharkMove.sharkNumber < tempMatrix[location.row][location.col]) {
                    valid[tempMatrix[location.row][location.col]] = false;

                    tempMatrix[location.row][location.col] = sharkMove.sharkNumber;
                    sharkLocations[sharkMove.sharkNumber] = location;
                    smell[location.row][location.col] = k;
                    smellSharkNumber[location.row][location.col] = sharkMove.sharkNumber;
                } else {
                    valid[sharkMove.sharkNumber] = false;
                }
            } else {
                tempMatrix[location.row][location.col] = sharkMove.sharkNumber;
                sharkLocations[sharkMove.sharkNumber] = location;
                smell[location.row][location.col] = k;
                smellSharkNumber[location.row][location.col] = sharkMove.sharkNumber;
            }
        }
    }

    static Location getNextLocation(int sharkNumber) {
        int sharkDirection = sharkDirections[sharkNumber];
        Location sharkLocation = sharkLocations[sharkNumber];

        for (int i = 0; i < 4; i++) {
            int nextDirection = directionPriority[sharkNumber][sharkDirection][i];
            int aRow = sharkLocation.row + dRow[nextDirection];
            int aCol = sharkLocation.col + dCol[nextDirection];

            if (isInMatrix(aRow, aCol) && smell[aRow][aCol] == 0) {
                sharkDirections[sharkNumber] = nextDirection;
                return new Location(aRow, aCol);
            }
        }

        for (int i = 0; i < 4; i++) {
            int nextDirection = directionPriority[sharkNumber][sharkDirection][i];
            int aRow = sharkLocation.row + dRow[nextDirection];
            int aCol = sharkLocation.col + dCol[nextDirection];

            if (isInMatrix(aRow, aCol) && smellSharkNumber[aRow][aCol] == sharkNumber) {
                sharkDirections[sharkNumber] = nextDirection;
                return new Location(aRow, aCol);
            }
        }

        return null;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    static boolean isEnd() {
        for (int i = 2; i <= M; i++) {
            if (valid[i]) {
                return false;
            }
        }

        return true;
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

class SharkMove {
    Location location;
    int sharkNumber;
    SharkMove(Location location, int sharkNumber) {
        this.location = location;
        this.sharkNumber = sharkNumber;
    }
}
