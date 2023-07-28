import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    private static int N, M, D;
    private static int[][] matrix, tempMatrix;
    private static int maxAttackedEnemyCount;

    public static void main(String[] args) throws IOException {
        int[] input = toIntArray(reader.readLine().split(" "));

        N = input[0];
        M = input[1];
        D = input[2];

        matrix = new int[N + 1][M];

        for (int i = 0; i < N; i++) {
            matrix[i] = toIntArray(reader.readLine().split(" "));
        }

        solve();

        writer.write(String.format("%d\n", maxAttackedEnemyCount));
        writer.flush();

        writer.close();
    }

    private static int[] toIntArray(String[] strings) {
        int[] ints = new int[strings.length];

        for (int i = 0; i < strings.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }

        return ints;
    }

    private static void solve() {
        for (int i = 0; i < M - 2; i++) {
            for (int j = i + 1; j < M - 1; j++) {
                for (int k = j + 1; k < M; k++) {
                    tempMatrix = new int[N][M];
                    copyMatrix(matrix, tempMatrix);

                    int firstArcherCol = i;
                    int secondArcherCol = j;
                    int thirdArcherCol = k;

                    int attackedEnemyCount = 0;

                    while (isEnemyExist()) {
                        Set<Location> attackedLocations = new HashSet<>();
                        attack(firstArcherCol, attackedLocations);
                        attack(secondArcherCol, attackedLocations);
                        attack(thirdArcherCol, attackedLocations);

                        for (Location attackedLocation : attackedLocations) {
                            tempMatrix[attackedLocation.row][attackedLocation.col] = 0;
                        }
                        attackedEnemyCount += attackedLocations.size();

                        moveEnemy();
                    }

                    if (attackedEnemyCount > maxAttackedEnemyCount) {
                        maxAttackedEnemyCount = attackedEnemyCount;
                    }
                }
            }
        }
    }

    private static void copyMatrix(int[][] matrix1, int[][] matrix2) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                matrix2[i][j] = matrix1[i][j];
            }
        }
    }

    private static boolean isEnemyExist() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (tempMatrix[i][j] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void attack(int archerCol, Set<Location> attackedLocations) {
        Location minDistanceEnemyLocation = null;
        int minDistance = Integer.MAX_VALUE;

        for (int j = 0; j < M; j++) {
            for (int i = 0; i < N; i++) {
                if (tempMatrix[i][j] == 1 && distance(N, archerCol, i, j) <= D) {
                    if (minDistanceEnemyLocation == null) {
                        minDistance = distance(N, archerCol, i, j);
                        minDistanceEnemyLocation = new Location(i, j);
                        continue;
                    }

                    int distance = distance(N, archerCol, i, j);
                    if (distance < minDistance) {
                        minDistance = distance;
                        minDistanceEnemyLocation = new Location(i, j);
                    }
                }
            }
        }

        if (minDistanceEnemyLocation != null) {
            attackedLocations.add(minDistanceEnemyLocation);
        }
    }

    private static int distance(int row1, int col1, int row2, int col2) {
        return Math.abs(row1 - row2) + Math.abs(col1 - col2);
    }

    private static void moveEnemy() {
        for (int i = N - 1; i > 0; i--) {
            tempMatrix[i] = tempMatrix[i - 1];
        }
        tempMatrix[0] = new int[M];
    }

    private static class Location {
        int row, col;

        public Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public boolean equals(Object object) {
            if (object == null) {
                return false;
            }
            if (object == this) {
                return true;
            }
            if (!(object instanceof Location)) {
                return false;
            }

            return row == ((Location) object).row && col == ((Location) object).col;
        }

        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
}
