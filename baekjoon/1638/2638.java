import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static char[][] matrix;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static Set<Location> cheeseLocations = new HashSet<>();
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new char[N][M];

        for (int row = 0; row < N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < M; col++) {
                int value = Integer.parseInt(tokenizer.nextToken());

                if (value == 1) {
                    cheeseLocations.add(new Location(row, col));
                    matrix[row][col] = 'c';
                }
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        if (cheeseLocations.isEmpty()) {
            result = 0;
            return;
        }

        int time = 0;
        spreadAir();
        setEmptyLocations();
        do {
            meltCheese();
            spreadAir();
            time++;
        } while (!cheeseLocations.isEmpty());

        result = time;
    }

    static void spreadAir() {
        Queue<Location> queue = new LinkedList<>() {{
            add(new Location(0, 0));
        }};
        boolean[][] visited = new boolean[N][M];
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            matrix[currentLocation.row][currentLocation.col] = 'a';

            for (int i = 0; i < 4; i++) {
                int aRow = currentLocation.row + dRow[i];
                int aCol = currentLocation.col + dCol[i];
                if (isMovableTo(aRow, aCol) && !visited[aRow][aCol]) {
                    queue.add(new Location(aRow, aCol));
                    visited[aRow][aCol] = true;
                }
            }
        }
    }

    static void meltCheese() {
        List<Location> meltLocations = new ArrayList<>();

        for (Location cheeseLocation : cheeseLocations) {
            if (isMeltable(cheeseLocation)) {
                meltLocations.add(cheeseLocation);
            }
        }

        for (Location meltLocation : meltLocations) {
            cheeseLocations.remove(meltLocation);
            matrix[meltLocation.row][meltLocation.col] = 'e';
        }
    }

    static boolean isMeltable(Location location) {
        int airCount = 0;

        for (int i = 0; i < 4; i++) {
            int aRow = location.row + dRow[i];
            int aCol = location.col + dCol[i];

            if (isInMatrix(aRow, aCol) && matrix[aRow][aCol] == 'a') {
                airCount++;
            }

            if (airCount >= 2) {
                return true;
            }
        }

        return false;
    }

    static void setEmptyLocations() {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                if (matrix[row][col] == 'a' || cheeseLocations.contains(new Location(row, col))) {
                    continue;
                }

                matrix[row][col] = 'e';
            }
        }
    }

    static boolean isMovableTo(int row, int col) {
        return isInMatrix(row, col) && matrix[row][col] != 'c';
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
