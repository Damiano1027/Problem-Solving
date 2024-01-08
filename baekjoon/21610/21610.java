import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[][] matrix;
    static int[] dRow = {-10, 0, -1, -1, -1, 0, 1, 1, 1};
    static int[] dCol = {-10, -1, -1, 0, 1, 1, 1, 0, -1};
    static class MoveInformation {
        int direction, space;
        MoveInformation(int direction, int space) {
            this.direction = direction;
            this.space = space;
        }
    }
    static class Location {
        int row, col;
        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Location)) {
                return false;
            }

            Location other = (Location) obj;
            return row == other.row && col == other.col;
        }
        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
    static final List<MoveInformation> moveInformations = new ArrayList<>();
    static Set<Location> cloudLocations = new HashSet<>();
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 1; j <= N; j++) {
                matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            moveInformations.add(new MoveInformation(
                    Integer.parseInt(tokenizer.nextToken()),
                    Integer.parseInt(tokenizer.nextToken())
            ));
        }

        cloudLocations.add(new Location(N, 1));
        cloudLocations.add(new Location(N, 2));
        cloudLocations.add(new Location(N-1, 1));
        cloudLocations.add(new Location(N-1, 2));

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        for (MoveInformation moveInformation: moveInformations) {
            step1(moveInformation);
            step2();
            Set<Location> removedCloudLocations = step3();
            step4(removedCloudLocations);
            step5(removedCloudLocations);
        }

        result = sumOfWater();
    }

    static void step1(MoveInformation moveInformation) {
        Set<Location> nextCloudLocations = new HashSet<>();

        for (Location cloudLocation: cloudLocations) {
            int nextRow = cloudLocation.row + dRow[moveInformation.direction] * moveInformation.space;
            int nextCol = cloudLocation.col + dCol[moveInformation.direction] * moveInformation.space;
            nextRow = real(nextRow);
            nextCol = real(nextCol);

            nextCloudLocations.add(new Location(nextRow, nextCol));
        }

        cloudLocations = new HashSet<>(nextCloudLocations);
    }

    static void step2() {
        for (Location cloudLocation: cloudLocations) {
            matrix[cloudLocation.row][cloudLocation.col]++;
        }
    }

    static Set<Location> step3() {
        Set<Location> removedCloudLocations = new HashSet<>(cloudLocations);
        cloudLocations = new HashSet<>();
        return removedCloudLocations;
    }

    static void step4(Set<Location> removedCloudLocations) {
        int[] dRow = {-1, -1, 1, 1};
        int[] dCol = {-1, 1, -1, 1};

        for (Location removedCloudLocation: removedCloudLocations) {
            int waterExistCount = 0;

            for (int i = 0; i < 4; i++) {
                int diagonalRow = removedCloudLocation.row + dRow[i];
                int diagonalCol = removedCloudLocation.col + dCol[i];

                if (!isRange(diagonalRow, diagonalCol)) {
                    continue;
                }

                if (matrix[diagonalRow][diagonalCol] > 0) {
                    waterExistCount++;
                }
            }

            matrix[removedCloudLocation.row][removedCloudLocation.col] += waterExistCount;
        }
    }

    static void step5(Set<Location> removedCloudLocations) {
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                Location location = new Location(row, col);
                if (matrix[row][col] >= 2 && !removedCloudLocations.contains(location)) {
                    cloudLocations.add(location);
                    matrix[row][col] -= 2;
                }
            }
        }
    }

    static int sumOfWater() {
        int sum = 0;

        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                sum += matrix[row][col];
            }
        }

        return sum;
    }

    static int real(int value) {
        if (value < 1) {
            do {
                value += N;
            } while (value < 1);
        } else if (value > N) {
            do {
                value -= N;
            } while (value > N);
        }

        return value;
    }

    static boolean isRange(int row, int col) {
        return row >= 1 && row <= N && col >= 1 && col <= N;
    }
}
