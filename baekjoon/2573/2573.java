import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[][] matrix;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static Set<Location> icebergLocations = new HashSet<>();
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N][M];

        for (int row = 0; row < N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < M; col++) {
                int value = Integer.parseInt(tokenizer.nextToken());

                if (value != 0) {
                    matrix[row][col] = value;
                    icebergLocations.add(new Location(row, col));
                }
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        result = 0;
        int year = 1;

        while (!icebergLocations.isEmpty()) {
            year();

            if (isEnd()) {
                result = year;
                break;
            }
            year++;
        }
    }

    static void year() {
        List<Melt> meltList = new ArrayList<>();

        for (Location location : icebergLocations) {
            Melt melt = new Melt(location);

            for (int i = 0; i < 4; i++) {
                int aRow = location.row + dRow[i];
                int aCol = location.col + dCol[i];

                if (isInMatrix(aRow, aCol) && matrix[aRow][aCol] == 0) {
                    melt.value++;
                }
            }

            if (melt.value > 0) {
                meltList.add(melt);
            }
        }

        for (Melt melt : meltList) {
            Location location = melt.location;
            matrix[location.row][location.col] -= melt.value;
            if (matrix[location.row][location.col] < 0) {
                matrix[location.row][location.col] = 0;
            }
            if (matrix[location.row][location.col] == 0) {
                icebergLocations.remove(location);
            }
        }
    }

    static boolean isEnd() {
        int lumpCount = 0;
        boolean[][] visited = new boolean[N][M];

        for (Location location : icebergLocations) {
            if (!visited[location.row][location.col]) {
                if (lumpCount >= 1) {
                    return true;
                } else {
                    bfs(location, visited);
                    lumpCount++;
                }
            }
        }

        return false;
    }

    static void bfs(Location startLocation, boolean[][] visited) {
        Queue<Location> queue = new LinkedList<>() {{
            add(startLocation);
        }};
        visited[startLocation.row][startLocation.col] = true;

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();

            for (int i = 0; i < 4; i++) {
                int aRow = currentLocation.row + dRow[i];
                int aCol = currentLocation.col + dCol[i];

                if (isInMatrix(aRow, aCol) && !visited[aRow][aCol] && matrix[aRow][aCol] != 0) {
                    queue.add(new Location(aRow, aCol));
                    visited[aRow][aCol] = true;
                }
            }
        }
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
    public boolean equals(Object o) {
        Location location = (Location) o;
        return row == location.row && col == location.col;
    }
    public int hashCode() {
        return Objects.hash(row, col);
    }
}

class Melt {
    Location location;
    int value;
    Melt(Location location) {
        this.location = location;
    }
}
