import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[][] matrix;
    static class Location {
        int row, col;
        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    static final List<Location> virusLocations = new ArrayList<>();
    static final List<List<Location>> virusLocationCombinations = new ArrayList<>();
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, -1, 0, 1};
    static class Bfs {
        Location location;
        int time;
        Bfs(Location location, int time) {
            this.location = location;
            this.time = time;
        }
    }
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N][N];

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < N; j++) {
                int value = Integer.parseInt(tokenizer.nextToken());
                matrix[i][j] = value;
                if (value == 2) {
                    virusLocations.add(new Location(i, j));
                }
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        makeCombinations(0, new ArrayList<>(), 0);
        int minTime = Integer.MAX_VALUE;

        for (List<Location> combination: virusLocationCombinations) {
            int[][] copiedMatrix = copiedMatrix();
            List<Bfs> bfsInformations = bfs(copiedMatrix, combination);
            if (!isAllVirusSpread(copiedMatrix)) {
                continue;
            }

            int time = bfsInformations.isEmpty() ? 0 : bfsInformations.get(bfsInformations.size() - 1).time;
            minTime = Math.min(minTime, time);
        }

        result = (minTime == Integer.MAX_VALUE) ? -1 : minTime;
    }

    static void makeCombinations(int currentIndex, List<Location> locations, int count) {
        if (locations.size() == M) {
            virusLocationCombinations.add(new ArrayList<>(locations));
            return;
        }

        for (int i = currentIndex; i < virusLocations.size(); i++) {
            locations.add(virusLocations.get(i));
            makeCombinations(i + 1, locations, count + 1);
            locations.remove(locations.size() - 1);
        }
    }

    static int[][] copiedMatrix() {
        int[][] copiedMatrix = new int[N][N];

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                copiedMatrix[row][col] = matrix[row][col];
            }
        }

        return copiedMatrix;
    }

    static List<Bfs> bfs(int[][] copiedMatrix, List<Location> combination) {
        Queue<Bfs> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];
        for (Location virusLocation: combination) {
            queue.add(new Bfs(virusLocation, 0));
            visited[virusLocation.row][virusLocation.col] = true;
        }
        List<Bfs> bfsInformations = new ArrayList<>();

        while (!queue.isEmpty()) {
            Bfs bfsInfo = queue.poll();
            int currentRow = bfsInfo.location.row;
            int currentCol = bfsInfo.location.col;
            int currentTime = bfsInfo.time;

            copiedMatrix[currentRow][currentCol] = -1;

            if (matrix[currentRow][currentCol] == 0) {
                bfsInformations.add(bfsInfo);
            }

            for (int i = 0; i < 4; i++) {
                int nextRow = currentRow + dx[i];
                int nextCol = currentCol + dy[i];

                if (isMovableTo(copiedMatrix, nextRow, nextCol) && !visited[nextRow][nextCol]) {
                    queue.add(new Bfs(new Location(nextRow, nextCol), currentTime + 1));
                    visited[nextRow][nextCol] = true;
                }
            }
        }

        return bfsInformations;
    }

    static boolean isMovableTo(int[][] copiedMatrix, int row, int col) {
        return isInMatrix(row, col) && copiedMatrix[row][col] != 1;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    static boolean isAllVirusSpread(int[][] copiedMatrix) {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (matrix[row][col] == 0 && copiedMatrix[row][col] != -1) {
                    return false;
                }
            }
        }
        return true;
    }
}
