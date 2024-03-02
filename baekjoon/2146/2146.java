import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[][] matrix, numberedMatrix;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static List<Location> edgeLocations = new ArrayList<>();
    static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        matrix = new int[N][N];
        numberedMatrix = new int[N][N];

        for (int row = 0; row < N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < N; col++) {
                matrix[row][col] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        attachLandNumbers();
        for (Location edgeLocation : edgeLocations) {
            int distance = getDistance(edgeLocation);
            if (distance == -1) {
                continue;
            }

            result = Math.min(result, distance);
        }
    }

    static void attachLandNumbers() {
        boolean[][] visited = new boolean[N][N];
        int landNumber = 1;

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (matrix[row][col] == 1 && !visited[row][col]) {
                    attachLandNumber(landNumber++, row, col, visited);
                }
            }
        }
    }

    static void attachLandNumber(int landNumber, int row, int col, boolean[][] visited) {
        Queue<Location> queue = new LinkedList<>() {{
            add(new Location(row, col));
        }};
        visited[row][col] = true;

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            numberedMatrix[currentLocation.row][currentLocation.col] = landNumber;

            boolean seaExist = false;
            for (int i = 0; i < 4; i++) {
                int aRow = currentLocation.row + dRow[i];
                int aCol = currentLocation.col + dCol[i];

                if (!isInMatrix(aRow, aCol)) {
                    continue;
                }

                if (matrix[aRow][aCol] == 1) {
                    if (!visited[aRow][aCol]) {
                        queue.add(new Location(aRow, aCol));
                        visited[aRow][aCol] = true;
                    }
                } else {
                    seaExist = true;
                }
            }

            if (seaExist) {
                edgeLocations.add(currentLocation);
            }
        }
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    static int getDistance(Location startLocation) {
        int landNumber = numberedMatrix[startLocation.row][startLocation.col];
        boolean[][] visited = new boolean[N][N];

        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(startLocation.row, startLocation.col, -1));
        }};
        visited[startLocation.row][startLocation.col] = true;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (numberedMatrix[current.row][current.col] > 0 && numberedMatrix[current.row][current.col] != landNumber) {
                return current.distance;
            }

            for (int i = 0; i < 4; i++) {
                int aRow = current.row + dRow[i];
                int aCol = current.col + dCol[i];

                if (!isInMatrix(aRow, aCol)) {
                    continue;
                }

                if (!visited[aRow][aCol] && numberedMatrix[aRow][aCol] != landNumber) {
                    queue.add(new Bfs(aRow, aCol, current.distance + 1));
                    visited[aRow][aCol] = true;
                }
            }
        }

        return -1;
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

class Bfs {
    int row, col, distance;
    Bfs(int row, int col, int distance) {
        this.row = row;
        this.col = col;
        this.distance = distance;
    }
}
