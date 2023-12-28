import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int R, C, T;
    static int[][] matrix;
    static final Location[] airCleanerLocations = {new Location(), new Location()};
    static List<DustInformation> dustInformations = new ArrayList<>();
    static int result;
    static class Location {
        int row, col;
    }
    static class DustInformation {
        int row, col, dust;
        DustInformation(int row, int col, int dust) {
            this.row = row;
            this.col = col;
            this.dust = dust;
        }
    }

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        R = Integer.parseInt(tokenizer.nextToken());
        C = Integer.parseInt(tokenizer.nextToken());
        T = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[R][C];

        for (int row = 0; row < R; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < C; col++) {
                matrix[row][col] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        setAirCleanerLocation();

        int time = 0;
        while (time < T) {
            spreadDust();
            runAirCleaner();
            dustInformations = new ArrayList<>();
            time++;
        }

        result = getTotalDust();
    }

    static void setAirCleanerLocation() {
        label:
        for (int row = 0; row < R; row++) {
            for (int col = 0; col < C; col++) {
                if (matrix[row][col] == -1) {
                    airCleanerLocations[0].row = row;
                    airCleanerLocations[0].col = col;
                    airCleanerLocations[1].row = row + 1;
                    airCleanerLocations[1].col = col;
                    break label;
                }
            }
        }
    }

    static void spreadDust() {
        int[] dRow = {-1, 0, 1, 0};
        int[] dCol = {0, -1, 0, 1};

        for (int row = 0; row < R; row++) {
            for (int col = 0; col < C; col++) {
                if (isAirCleanerLocation(row, col)) {
                    continue;
                }

                int spreadingDust = matrix[row][col] / 5;
                int spreadingCount = 0;

                for (int i = 0; i < 4; i++) {
                    int aRow = row + dRow[i];
                    int aCol = col + dCol[i];

                    if (!isValidDustLocation(aRow, aCol)) {
                        continue;
                    }

                    dustInformations.add(new DustInformation(aRow, aCol, spreadingDust));
                    spreadingCount++;
                }

                dustInformations.add(new DustInformation(row, col, matrix[row][col] - (spreadingCount * spreadingDust)));
            }
        }

        resetMatrix();
    }

    static boolean isValidDustLocation(int row, int col) {
        return row >= 0 && row < R && col >= 0 && col < C && !isAirCleanerLocation(row, col);
    }

    static boolean isAirCleanerLocation(int row, int col) {
        return (row == airCleanerLocations[0].row && col == airCleanerLocations[0].col) || (row == airCleanerLocations[1].row && col == airCleanerLocations[1].col);
    }

    static void resetMatrix() {
        matrix = new int[R][C];
        matrix[airCleanerLocations[0].row][airCleanerLocations[0].col] = -1;
        matrix[airCleanerLocations[1].row][airCleanerLocations[1].col] = -1;

        for (DustInformation dustInformation : dustInformations) {
            matrix[dustInformation.row][dustInformation.col] += dustInformation.dust;
        }
    }

    static void runAirCleaner() {
        runTopAirCleaner();
        runBottomAirCleaner();
    }

    static void runTopAirCleaner() {
        int row = airCleanerLocations[0].row - 2;
        int col = airCleanerLocations[0].col;
        int direction = 2; // 0: 북, 1: 서, 2: 남, 3: 동

        while (true) {
            if (direction == 0) {
                matrix[row - 1][col] = matrix[row][col];
            } else if (direction == 1) {
                matrix[row][col - 1] = matrix[row][col];
            } else if (direction == 2) {
                matrix[row + 1][col] = matrix[row][col];
            } else if (direction == 3) {
                matrix[row][col + 1] = matrix[row][col];
            }

            if (row == airCleanerLocations[0].row && col == airCleanerLocations[0].col + 1) {
                break;
            }

            if (row == 0 && col == 0) {
                direction = 1;
            } else if (row == 0 && col == C - 1) {
                direction = 0;
            } else if (row == airCleanerLocations[0].row && col == C - 1) {
                direction = 3;
            }

            if (direction == 0) {
                row++;
            } else if (direction == 1) {
                col++;
            } else if (direction == 2) {
                row--;
            } else if (direction == 3) {
                col--;
            }
        }

        matrix[row][col] = 0;
    }

    static void runBottomAirCleaner() {
        int row = airCleanerLocations[1].row + 2;
        int col = airCleanerLocations[1].col;
        int direction = 0; // 0: 북, 1: 서, 2: 남, 3: 동

        while (true) {
            if (direction == 0) {
                matrix[row - 1][col] = matrix[row][col];
            } else if (direction == 1) {
                matrix[row][col - 1] = matrix[row][col];
            } else if (direction == 2) {
                matrix[row + 1][col] = matrix[row][col];
            } else if (direction == 3) {
                matrix[row][col + 1] = matrix[row][col];
            }

            if (row == airCleanerLocations[1].row && col == airCleanerLocations[1].col + 1) {
                break;
            }

            if (row == R - 1 && col == 0) {
                direction = 1;
            } else if (row == R - 1 && col == C - 1) {
                direction = 2;
            } else if (row == airCleanerLocations[1].row && col == C - 1) {
                direction = 3;
            }

            if (direction == 0) {
                row++;
            } else if (direction == 1) {
                col++;
            } else if (direction == 2) {
                row--;
            } else if (direction == 3) {
                col--;
            }
        }

        matrix[row][col] = 0;
    }

    static int getTotalDust() {
        int dust = 0;

        for (int row = 0; row < R; row++) {
            for (int col = 0; col < C; col++) {
                if (matrix[row][col] > 0) {
                    dust += matrix[row][col];
                }
            }
        }

        return dust;
    }
}
