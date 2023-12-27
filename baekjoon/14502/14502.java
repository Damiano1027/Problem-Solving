import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[][] matrix;
    static int result = 0;
    static final List<Location> emptyLocations = new ArrayList<>();
    static final List<List<Location>> combinations = new ArrayList<>();
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
                int value = Integer.parseInt(tokenizer.nextToken());
                matrix[i][j] = value;

                if (value == 0) {
                    emptyLocations.add(new Location(i, j));
                }
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        makeCombinations(0, new ArrayList<>());
        simulate();
    }

    static void makeCombinations(int currentIndex, List<Location> locations) {
        if (locations.size() >= 3) {
            combinations.add(new ArrayList<>(locations));
            return;
        }

        for (int i = currentIndex; i < emptyLocations.size(); i++) {
            locations.add(emptyLocations.get(i));
            makeCombinations(i + 1, locations);
            locations.remove(locations.size() - 1);
        }
    }

    static void simulate() {
        for (List<Location> combination : combinations) {
            int[][] copiedMatrix = copiedMatrix();

            for (Location location : combination) {
                copiedMatrix[location.row][location.col] = 1;
            }

            spreadOutVirus(copiedMatrix);
            int safetyAreaSize = getSafetyAreaSize(copiedMatrix);

            result = Math.max(result, safetyAreaSize);
        }
    }

    static int[][] copiedMatrix() {
        int[][] copiedMatrix = new int[N][M];

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                copiedMatrix[row][col] = matrix[row][col];
            }
        }

        return copiedMatrix;
    }

    static void spreadOutVirus(int[][] matrix) {
        boolean[][] visited = new boolean[N][M];
        List<Location> virusLocations = new ArrayList<>();

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                if (matrix[row][col] == 2) {
                    virusLocations.add(new Location(row, col));
                }
            }
        }

        for (Location virusLocation : virusLocations) {
            bfsForSpreadOutVirus(matrix, virusLocation.row, virusLocation.col, visited);
        }
    }

    static void bfsForSpreadOutVirus(int[][] matrix, int startRow, int startCol, boolean[][] visited) {
        Queue<Location> queue = new LinkedList<>();
        queue.add(new Location(startRow, startCol));
        visited[startRow][startCol] = true;
        int[] dRow = {-1, 0, 1, 0};
        int[] dCol = {0, -1, 0, 1};

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            matrix[currentLocation.row][currentLocation.col] = 2;

            for (int i = 0; i < 4; i++) {
                int nextRow = currentLocation.row + dRow[i];
                int nextCol = currentLocation.col + dCol[i];

                if (!isInMatrix(nextRow, nextCol)) {
                    continue;
                }
                if (visited[nextRow][nextCol]) {
                    continue;
                }
                if (matrix[nextRow][nextCol] != 0) {
                    continue;
                }

                queue.add(new Location(nextRow, nextCol));
                visited[nextRow][nextCol] = true;
            }
        }
    }

    static int getSafetyAreaSize(int[][] matrix) {
        int count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (matrix[i][j] == 0) {
                    count++;
                }
            }
        }

        return count;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }
}
