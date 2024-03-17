import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[][] score;
    static int result = Integer.MIN_VALUE;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        score = new int[N][M];

        for (int row = 0; row < N; row++) {
            String line = reader.readLine();
            for (int col = 0; col < M; col++) {
                score[row][col] = Character.getNumericValue(line.charAt(col));
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        if (N == 1 && M == 1) {
            result = score[0][0];
            return;
        }

        dfs(new Location(0, 0), new ArrayList<>(), new boolean[N][M]);
    }

    static void dfs(Location currentLocation, List<List<Location>> blocks, boolean[][] visited) {
        if (currentLocation.row >= N && isFull(visited)) {
            int sum = getSum(blocks);
            result = Math.max(result, sum);
            return;
        }

        if (visited[currentLocation.row][currentLocation.col]) {
            dfs(getNextLocation(currentLocation), blocks, visited);
        } else {
            List<List<Location>> nextBlocks = makeBlocks(currentLocation, visited);
            for (List<Location> nextBlock : nextBlocks) {
                for (Location location : nextBlock) {
                    visited[location.row][location.col] = true;
                }
                blocks.add(nextBlock);

                dfs(getNextLocation(currentLocation), blocks, visited);

                blocks.remove(blocks.size() - 1);
                for (Location location : nextBlock) {
                    visited[location.row][location.col] = false;
                }
            }
        }
    }

    static List<List<Location>> makeBlocks(Location startLocation, boolean[][] visited) {
        List<List<Location>> blocks = new ArrayList<>();

        if (isInMatrix(startLocation.row, startLocation.col) && !visited[startLocation.row][startLocation.col]) {
            blocks.add(List.of(startLocation));
        }

        boolean enable = true;
        for (int col = startLocation.col; col <= startLocation.col + 1; col++) {
            if (!isInMatrix(startLocation.row, col) || visited[startLocation.row][col]) {
                enable = false;
                break;
            }
        }
        if (enable) {
            List<Location> locations = new ArrayList<>();
            for (int col = startLocation.col; col <= startLocation.col + 1; col++) {
                locations.add(new Location(startLocation.row, col));
            }
            blocks.add(locations);
        }

        enable = true;
        for (int col = startLocation.col; col <= startLocation.col + 2; col++) {
            if (!isInMatrix(startLocation.row, col) || visited[startLocation.row][col]) {
                enable = false;
                break;
            }
        }
        if (enable) {
            List<Location> locations = new ArrayList<>();
            for (int col = startLocation.col; col <= startLocation.col + 2; col++) {
                locations.add(new Location(startLocation.row, col));
            }
            blocks.add(locations);
        }

        enable = true;
        for (int col = startLocation.col; col <= startLocation.col + 3; col++) {
            if (!isInMatrix(startLocation.row, col) || visited[startLocation.row][col]) {
                enable = false;
                break;
            }
        }
        if (enable) {
            List<Location> locations = new ArrayList<>();
            for (int col = startLocation.col; col <= startLocation.col + 3; col++) {
                locations.add(new Location(startLocation.row, col));
            }
            blocks.add(locations);
        }

        enable = true;
        for (int row = startLocation.row; row <= startLocation.row + 1; row++) {
            if (!isInMatrix(row, startLocation.col) || visited[row][startLocation.col]) {
                enable = false;
                break;
            }
        }
        if (enable) {
            List<Location> locations = new ArrayList<>();
            for (int row = startLocation.row; row <= startLocation.row + 1; row++) {
                locations.add(new Location(row, startLocation.col));
            }
            blocks.add(locations);
        }

        enable = true;
        for (int row = startLocation.row; row <= startLocation.row + 2; row++) {
            if (!isInMatrix(row, startLocation.col) || visited[row][startLocation.col]) {
                enable = false;
                break;
            }
        }
        if (enable) {
            List<Location> locations = new ArrayList<>();
            for (int row = startLocation.row; row <= startLocation.row + 2; row++) {
                locations.add(new Location(row, startLocation.col));
            }
            blocks.add(locations);
        }

        enable = true;
        for (int row = startLocation.row; row <= startLocation.row + 3; row++) {
            if (!isInMatrix(row, startLocation.col) || visited[row][startLocation.col]) {
                enable = false;
                break;
            }
        }
        if (enable) {
            List<Location> locations = new ArrayList<>();
            for (int row = startLocation.row; row <= startLocation.row + 3; row++) {
                locations.add(new Location(row, startLocation.col));
            }
            blocks.add(locations);
        }

        return blocks;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }

    static boolean isFull(boolean[][] visited) {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                if (!visited[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    static int getSum(List<List<Location>> blocks) {
        int sum = 0;

        for (List<Location> block : blocks) {
            StringBuilder stringBuilder = new StringBuilder();

            for (Location location : block) {
                stringBuilder.append(score[location.row][location.col]);
            }

            sum += Integer.parseInt(stringBuilder.toString());
        }

        return sum;
    }

    static Location getNextLocation(Location location) {
        if (location.col < M - 1) {
            return new Location(location.row, location.col + 1);
        } else {
            return new Location(location.row + 1, 0);
        }
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
