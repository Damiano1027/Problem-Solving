import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[][] matrix, setSizeMatrix, resultMatrix;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int[] uf;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N][M];
        setSizeMatrix = new int[N][M];
        resultMatrix = new int[N][M];
        uf = new int[N * M];
        for (int i = 0; i < N * M; i++) {
            uf[i] = i;
        }

        for (int i = 0; i < N; i++) {
            String line = reader.readLine();
            for (int j = 0; j < M; j++) {
                matrix[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }

        solve();

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                writer.write(String.format("%d", resultMatrix[row][col] % 10));
            }
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }

    static void solve() {
        makeSets();
        makeResultMatrix();
    }

    static void makeSets() {
        boolean[][] visited = new boolean[N][M];

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                if (!visited[row][col] && matrix[row][col] == 0) {
                    bfs(row, col, visited);
                }
            }
        }
    }

    static void bfs(int startRow, int startCol, boolean[][] visited) {
        Location startLocation = new Location(startRow, startCol);
        Queue<Location> queue = new LinkedList<>() {{
            add(startLocation);
        }};
        visited[startRow][startCol] = true;
        List<Location> locations = new ArrayList<>();
        int locationsCount = 0;

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            locations.add(currentLocation);
            locationsCount++;

            for (int i = 0; i < 4; i++) {
                int aRow = currentLocation.row + dRow[i];
                int aCol = currentLocation.col + dCol[i];

                if (!isInMatrix(aRow, aCol)) {
                    continue;
                }

                if (!visited[aRow][aCol] && matrix[aRow][aCol] == 0) {
                    queue.add(new Location(aRow, aCol));
                    visited[aRow][aCol] = true;
                }
            }
        }

        setSizeMatrix[startLocation.row][startLocation.col] = locationsCount;
        for (int i = 0; i < locations.size() - 1; i++) {
            Location leftLocation = locations.get(i);
            Location rightLocation = locations.get(i + 1);
            int leftLocationValue = getLocationValue(leftLocation);
            int rightLocationValue = getLocationValue(rightLocation);

            union(leftLocationValue, rightLocationValue);
            setSizeMatrix[rightLocation.row][rightLocation.col] = locationsCount;
        }
    }

    static void makeResultMatrix() {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                List<Location> aLocations = new ArrayList<>();
                if (matrix[row][col] == 1) {
                    resultMatrix[row][col]++;

                    for (int i = 0; i < 4; i++) {
                        int aRow = row + dRow[i];
                        int aCol = col + dCol[i];

                        if (isInMatrix(aRow, aCol) && matrix[aRow][aCol] == 0) {
                            aLocations.add(new Location(aRow, aCol));
                        }
                    }
                }

                Set<Integer> rootLocationValueSet = new HashSet<>();
                for (Location aLocation : aLocations) {
                    int root = find(getLocationValue(aLocation));
                    if (!rootLocationValueSet.contains(root)) {
                        resultMatrix[row][col] += setSizeMatrix[aLocation.row][aLocation.col];
                        rootLocationValueSet.add(root);
                    }
                }
            }
        }
    }

    static void union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        uf[aRoot] = bRoot;
    }

    static int find(int number) {
        if (uf[number] == number) {
            return number;
        }

        return uf[number] = find(uf[number]);
    }

    static int getLocationValue(Location location) {
        return location.row * M + location.col;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
