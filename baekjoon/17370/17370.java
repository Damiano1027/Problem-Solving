import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static int[] dRow1 = {-2, 1, 1}, dRow2 = {-1, -1, 2};
    static int[] dCol1 = {0, -1, 1}, dCol2 = {-1, 1, 0};
    static int result = 0;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        dfs();
        result = result / 3;
    }

    static void dfs() {
        Location startLocation = new Location(0, 0);
        Set<Location> visitedLocations = new HashSet<>();

        visitedLocations.add(startLocation);
        for (int i = 0; i < 3; i++) {
            int nextRow = startLocation.row + dRow1[i];
            int nextCol = startLocation.col + dCol1[i];

            Location nextLocation = new Location(nextRow, nextCol);
            dfs(nextLocation, visitedLocations, startLocation, 1, 0);
        }
        visitedLocations.remove(startLocation);
    }

    static void dfs(Location currentLocation, Set<Location> visitedLocations, Location prevLocation, int forkNumber, int rotateCount) {
        if (visitedLocations.contains(currentLocation)) {
            if (rotateCount == N) {
                result++;
            }
            return;
        }
        if (rotateCount >= N) {
            return;
        }

        visitedLocations.add(currentLocation);
        if (forkNumber == 0) {
            for (int i = 0; i < 3; i++) {
                int nextRow = currentLocation.row + dRow1[i];
                int nextCol = currentLocation.col + dCol1[i];

                Location nextLocation = new Location(nextRow, nextCol);
                if (prevLocation.equals(nextLocation)) {
                    continue;
                }

                dfs(nextLocation, visitedLocations, currentLocation, reverseForkNumber(forkNumber), rotateCount + 1);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                int nextRow = currentLocation.row + dRow2[i];
                int nextCol = currentLocation.col + dCol2[i];

                Location nextLocation = new Location(nextRow, nextCol);
                if (prevLocation.equals(nextLocation)) {
                    continue;
                }

                dfs(nextLocation, visitedLocations, currentLocation, reverseForkNumber(forkNumber), rotateCount + 1);
            }
        }
        visitedLocations.remove(currentLocation);
    }

    static int reverseForkNumber(int number) {
        return (number + 1) % 2;
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
