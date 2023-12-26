import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[][] matrix;
    static final List<List<Location>> shapeLocations = new ArrayList<>();
    static int result = Integer.MIN_VALUE;

    static class Location {
        int row, col;
        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws IOException {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N][M];

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < M; j++) {
                matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        initShapeLocations();

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        initShapeLocations();

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                for (List<Location> locations : shapeLocations) {
                    if (!isInMatrix(row, col, locations)) {
                        continue;
                    }

                    result = Math.max(result, sum(row, col, locations));
                }
            }
        }
    }

    static void initShapeLocations() {
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(0, 1),
                        new Location(0, 2),
                        new Location(0, 3))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(1, 0),
                        new Location(2, 0),
                        new Location(3, 0))
        );

        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(0, 1),
                        new Location(1, 0),
                        new Location(1, 1))
        );

        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(1, 0),
                        new Location(2, 0),
                        new Location(2, 1))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(1, 0),
                        new Location(2, 0),
                        new Location(2, -1))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(0, -1),
                        new Location(1, -1),
                        new Location(2, -1))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(0, 1),
                        new Location(1, 1),
                        new Location(2, 1))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(0, -1),
                        new Location(0, -2),
                        new Location(1, -2))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(0, 1),
                        new Location(0, 2),
                        new Location(1, 2))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(1, 0),
                        new Location(1, -1),
                        new Location(1, -2))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(1, 0),
                        new Location(1, 1),
                        new Location(1, 2))
        );

        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(1, 0),
                        new Location(1, 1),
                        new Location(2, 1))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(1, 0),
                        new Location(1, -1),
                        new Location(2, -1))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(0, -1),
                        new Location(1, -1),
                        new Location(1, -2))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(0, 1),
                        new Location(1, 1),
                        new Location(1, 2))
        );

        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(0, -1),
                        new Location(0, 1),
                        new Location(1, 0))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(1, 0),
                        new Location(1, 1),
                        new Location(2, 0))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(1, 0),
                        new Location(1, -1),
                        new Location(1, 1))
        );
        shapeLocations.add(
                List.of(new Location(0, 0),
                        new Location(-1, 0),
                        new Location(1, 0),
                        new Location(0, -1))
        );
    }

    static boolean isInMatrix(int startRow, int startCol, List<Location> locations) {
        for (Location location : locations) {
            int row = startRow + location.row;
            int col = startCol + location.col;

            if (row < 0 || row >= N | col < 0 || col >= M) {
                return false;
            }
        }
        return true;
    }

    static int sum(int startRow, int startCol, List<Location> locations) {
        int sum = 0;

        for (Location location : locations) {
            int row = startRow + location.row;
            int col = startCol + location.col;

            sum += matrix[row][col];
        }

        return sum;
    }
}
