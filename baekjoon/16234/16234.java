import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer stringTokenizer;
    static int N, L, R;
    static int[][] matrix;
    static boolean[][] visited;
    static int[] toIntArray(String str) {
        String[] strings = str.split(" ");
        int[] array = new int[strings.length];

        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.parseInt(strings[i]);
        }
        return array;
    }
    static class Location {
        int row;
        int col;

        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Location)) {
                return false;
            }

            Location other = (Location) obj;
            return row == other.row && col == other.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
    static class Result {
        int count;
        int sum;
        List<Location> locations = new ArrayList<>();
    }
    static HashMap<Location, ArrayList<Location>> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        stringTokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(stringTokenizer.nextToken());
        L = Integer.parseInt(stringTokenizer.nextToken());
        R = Integer.parseInt(stringTokenizer.nextToken());

        matrix = new int[N][N];
        initVisited();

        for (int i = 0; i < N; i++) {
            matrix[i] = toIntArray(reader.readLine());
        }

        writer.write(String.format("%d\n", solve()));
        writer.flush();
        writer.close();
    }

    static int solve() {
        int count = 0;

        while (isRemovableLineExist()) {
            initMap();
            initVisited();
            removeLines();

            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    if (visited[row][col]) {
                        continue;
                    }
                    Result result = bfs(row, col);
                    int value = result.sum / result.count;
                    for (Location location : result.locations) {
                        matrix[location.row][location.col] = value;
                    }
                }
            }

            count++;
        }

        return count;
    }

    static Result bfs(int row, int col) {
        Queue<Location> queue = new LinkedList<>();
        queue.add(new Location(row, col));
        visited[row][col] = true;
        Result result = new Result();

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            result.count++;
            result.sum += matrix[currentLocation.row][currentLocation.col];
            result.locations.add(currentLocation);

            for (Location location : map.get(currentLocation)) {
                if (!visited[location.row][location.col]) {
                    visited[location.row][location.col] = true;
                    queue.add(location);
                }
            }
        }

        return result;
    }

    static boolean isRemovableLineExist() {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (isValidLocation(row - 1, col) && isRemovable(row, col, row - 1, col)) {
                    return true;
                }
                if (isValidLocation(row, col - 1) && isRemovable(row, col, row, col - 1)) {
                    return true;
                }
                if (isValidLocation(row + 1, col) && isRemovable(row, col, row + 1, col)) {
                    return true;
                }
                if (isValidLocation(row, col + 1) && isRemovable(row, col, row, col + 1)) {
                    return true;
                }
            }
        }

        return false;
    }

    static void removeLines() {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (isValidLocation(row - 1, col) && isRemovable(row, col, row - 1, col)) {
                    map.get(new Location(row, col)).add(new Location(row - 1, col));
                }
                if (isValidLocation(row, col - 1) && isRemovable(row, col, row, col - 1)) {
                    map.get(new Location(row, col)).add(new Location(row, col - 1));
                }
                if (isValidLocation(row + 1, col) && isRemovable(row, col, row + 1, col)) {
                    map.get(new Location(row, col)).add(new Location(row + 1, col));
                }
                if (isValidLocation(row, col + 1) && isRemovable(row, col, row, col + 1)) {
                    map.get(new Location(row, col)).add(new Location(row, col + 1));
                }
            }
        }
    }

    static boolean isValidLocation(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    static boolean isRemovable(int row1, int col1, int row2, int col2) {
        int diff = Math.abs(matrix[row1][col1] - matrix[row2][col2]);
        return diff >= L && diff <= R;
    }

    static void initMap() {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                map.put(new Location(row, col), new ArrayList<>());
            }
        }
    }

    static void initVisited() {
        visited = new boolean[N][N];
    }
}
