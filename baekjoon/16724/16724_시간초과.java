import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static char[][] staticMatrix;
    static Map<Location, Location> ufMap = new HashMap<>();
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        staticMatrix = new char[N][M];

        for (int row = 0; row < N; row++) {
            String line = reader.readLine();
            for (int col = 0; col < M; col++) {
                staticMatrix[row][col] = line.charAt(col);
            }
        }

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                ufMap.put(new Location(row, col), new Location(row, col));
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                char direction = staticMatrix[row][col];

                int aRow, aCol;
                if (direction == 'U') {
                    aRow = row - 1;
                    aCol = col;
                } else if (direction == 'L') {
                    aRow = row;
                    aCol = col - 1;
                } else if (direction == 'D') {
                    aRow = row + 1;
                    aCol = col;
                } else {
                    aRow = row;
                    aCol = col + 1;
                }

                Location location = new Location(row, col);
                Location aLocation = new Location(aRow, aCol);

                if (isInMatrix(aRow, aCol) && (find(location) != find(aLocation))) {
                    union(location, aLocation);
                }
            }
        }

        Set<Location> set = new HashSet<>();
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                set.add(find(new Location(row, col)));
            }
        }

        result = set.size();
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }

    static void union(Location a, Location b) {
        Location aRoot = find(a);
        Location bRoot = find(b);

        ufMap.put(aRoot, bRoot);
    }

    static Location find(Location location) {
        if (ufMap.get(location).equals(location)) {
            return location;
        }

        ufMap.put(location, find(ufMap.get(location)));
        return ufMap.get(location);
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
