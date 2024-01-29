import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static class Location {
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
    static Set<Location> dragonCurveLocations = new HashSet<>();
    static int result = 0;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());
            int d = Integer.parseInt(tokenizer.nextToken());
            int g = Integer.parseInt(tokenizer.nextToken());

            List<Location> dragonCurve = makeDragonCurve(x, y, d, g);
            dragonCurveLocations.addAll(dragonCurve);
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static List<Location> makeDragonCurve(int x, int y, int d, int g) {
        // d: 시작 방향(0: 오른쪽, 1: 위쪽, 2: 왼쪽, 3: 아래쪽), g: 세대

        int row = y;
        int col = x;

        List<Location> dragonCurve = new ArrayList<>();
        dragonCurve.add(new Location(row, col));

        if (d == 0) {
            dragonCurve.add(new Location(row, col + 1));
        } else if (d == 1) {
            dragonCurve.add(new Location(row - 1, col));
        } else if (d == 2) {
            dragonCurve.add(new Location(row, col - 1));
        } else if (d == 3) {
            dragonCurve.add(new Location(row + 1, col));
        }

        for (int generation = 1; generation <= g; generation++) {
            Location endPoint = dragonCurve.get(dragonCurve.size() - 1);
            List<Location> offsetsFromEndPoint = makeOffsetsFromEndPoint(dragonCurve);

            for (Location offset: offsetsFromEndPoint) {
                int newRow = endPoint.row + offset.row;
                int newCol = endPoint.col + offset.col;

                dragonCurve.add(new Location(newRow, newCol));
            }
        }

        return dragonCurve;
    }

    static List<Location> makeOffsetsFromEndPoint(List<Location> dragonCurve) {
        Location endPoint = dragonCurve.get(dragonCurve.size() - 1);
        List<Location> offsets = new ArrayList<>();

        for (int i = dragonCurve.size() - 2; i >= 0; i--) {
            Location currentLocation = dragonCurve.get(i);

            int oldRowOffset = currentLocation.row - endPoint.row;
            int oldColOffset = currentLocation.col - endPoint.col;
            int newRowOffset = oldColOffset;
            int newColOffset = -oldRowOffset;

            offsets.add(new Location(newRowOffset, newColOffset));
        }

        return offsets;
    }

    static void solve() {
        for (int row = 0; row <= 99; row++) {
            for (int col = 0; col <= 99; col++) {
                if (containsSquare(row, col)) {
                    result++;
                }
            }
        }
    }

    static boolean containsSquare(int leftUpRow, int leftUpCol) {
        Location location1 = new Location(leftUpRow, leftUpCol);
        Location location2 = new Location(leftUpRow + 1, leftUpCol);
        Location location3 = new Location(leftUpRow, leftUpCol + 1);
        Location location4 = new Location(leftUpRow + 1, leftUpCol + 1);

        return dragonCurveLocations.contains(location1) && dragonCurveLocations.contains(location2)
                && dragonCurveLocations.contains(location3) && dragonCurveLocations.contains(location4);
    }
}
