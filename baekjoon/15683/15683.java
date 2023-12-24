import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer stringTokenizer;
    static int N, M;
    static int[][] matrix;
    static final List<Location> cctvLocations = new ArrayList<>();
    static int result = Integer.MAX_VALUE;
    static int[] toIntArray(String string) {
        String[] strings = string.split(" ");
        int[] intArray = new int[strings.length];

        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = Integer.parseInt(strings[i]);
        }

        return intArray;
    }
    static int[][] copiedMatrix() {
        int[][] copiedMatrix = new int[N][M];

        for (int i = 0; i < N; i++) {
            System.arraycopy(matrix[i], 0, copiedMatrix[i], 0, M);
        }

        return copiedMatrix;
    }
    static class CCTV {
        Location location;
        List<Direction> directions;

        CCTV(Location location, List<Direction> directions) {
            this.location = location;
            this.directions = directions;
        }
    }
    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    static class Location {
        int row;
        int col;

        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws IOException {
        stringTokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(stringTokenizer.nextToken());
        M = Integer.parseInt(stringTokenizer.nextToken());

        matrix = new int[N][M];
        for (int i = 0; i < N; i++) {
            matrix[i] = toIntArray(reader.readLine());
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void setCCTVLocations() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (matrix[i][j] >= 1 && matrix[i][j] <= 5) {
                    cctvLocations.add(new Location(i, j));
                }
            }
        }
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }

    static void draw(int[][] matrix, List<CCTV> cctvs) {
        for (CCTV cctv : cctvs) {
            Location location = cctv.location;
            for (Direction direction : cctv.directions) {
                draw(matrix, location, direction);
            }
        }
    }

    static void draw(int[][] matrix, Location currentLocation, Direction direction) {
        int row = currentLocation.row;
        int col = currentLocation.col;

        while (true) {
            if (direction.equals(Direction.UP)) {
                row--;
            } else if (direction.equals(Direction.DOWN)) {
                row++;
            } else if (direction.equals(Direction.LEFT)) {
                col--;
            } else if (direction.equals(Direction.RIGHT)) {
                col++;
            }

            if (!isInMatrix(row, col) || matrix[row][col] == 6) {
                break;
            }
            if (matrix[row][col] == '#' || (matrix[row][col] >= 1 && matrix[row][col] <= 5)) {
                continue;
            }
            if (matrix[row][col] == 0) {
                matrix[row][col] = '#';
            }
        }
    }

    static int getBlindSpotCount(int[][] matrix) {
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

    static void dfs(int index, List<CCTV> cctvs) {
        if (index >= cctvLocations.size()) {
            int[][] copiedMatrix = copiedMatrix();
            draw(copiedMatrix, cctvs);
            int blindSpotCount = getBlindSpotCount(copiedMatrix);
            result = Math.min(result, blindSpotCount);
            return;
        }

        Location currentLocation = cctvLocations.get(index);
        if (matrix[currentLocation.row][currentLocation.col] == 1) {
            cctvs.add(new CCTV(currentLocation, List.of(Direction.UP)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);

            cctvs.add(new CCTV(currentLocation, List.of(Direction.DOWN)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);

            cctvs.add(new CCTV(currentLocation, List.of(Direction.LEFT)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);

            cctvs.add(new CCTV(currentLocation, List.of(Direction.RIGHT)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);
        }
        if (matrix[currentLocation.row][currentLocation.col] == 2) {
            cctvs.add(new CCTV(currentLocation, List.of(Direction.UP, Direction.DOWN)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);

            cctvs.add(new CCTV(currentLocation, List.of(Direction.LEFT, Direction.RIGHT)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);
        }
        if (matrix[currentLocation.row][currentLocation.col] == 3) {
            cctvs.add(new CCTV(currentLocation, List.of(Direction.UP, Direction.RIGHT)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);

            cctvs.add(new CCTV(currentLocation, List.of(Direction.RIGHT, Direction.DOWN)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);

            cctvs.add(new CCTV(currentLocation, List.of(Direction.DOWN, Direction.LEFT)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);

            cctvs.add(new CCTV(currentLocation, List.of(Direction.LEFT, Direction.UP)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);
        }
        if (matrix[currentLocation.row][currentLocation.col] == 4) {
            cctvs.add(new CCTV(currentLocation, List.of(Direction.UP, Direction.RIGHT, Direction.DOWN)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);

            cctvs.add(new CCTV(currentLocation, List.of(Direction.RIGHT, Direction.DOWN, Direction.LEFT)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);

            cctvs.add(new CCTV(currentLocation, List.of(Direction.DOWN, Direction.LEFT, Direction.UP)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);

            cctvs.add(new CCTV(currentLocation, List.of(Direction.LEFT, Direction.UP, Direction.RIGHT)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);
        }
        if (matrix[currentLocation.row][currentLocation.col] == 5) {
            cctvs.add(new CCTV(currentLocation, List.of(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT)));
            dfs(index + 1, cctvs);
            cctvs.remove(cctvs.size() - 1);
        }
    }

    static void solve() {
        setCCTVLocations();
        dfs(0, new LinkedList<>());
    }
}
