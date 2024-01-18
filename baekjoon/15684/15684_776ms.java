import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, H;
    // row번 점선이 col, col+1번 세로선을 연결한다는 의미
    static boolean[][] visited;
    static class Location {
        int row, col;
        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        H = Integer.parseInt(tokenizer.nextToken());

        visited = new boolean[H + 1][N + 1];

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int row = Integer.parseInt(tokenizer.nextToken());
            int col = Integer.parseInt(tokenizer.nextToken());
            visited[row][col] = true;
        }

        solve();
    }

    static void solve() throws Exception {
        for (int size = 0; size <= 3; size++) {
            combinations(new Location(1, 1), size, 0);
        }

        writer.write("-1\n");
        writer.flush();
        writer.close();
    }

    static void combinations(Location currentLocation, int maxSize, int count) throws Exception {
        if (count == maxSize) {
            if (isSatisfied()) {
                writer.write(String.format("%d\n", maxSize));
                writer.flush();
                writer.close();
                System.exit(0);
            }
            return;
        }

        if (!isInMatrix(currentLocation.row, currentLocation.col)) {
            return;
        }

        for (int col = currentLocation.col; col < N; col++) {
            Location location = new Location(currentLocation.row, col);
            if (isPlaceable(location)) {
                visited[location.row][location.col] = true;
                combinations(nextLocation(location), maxSize, count + 1);
                visited[location.row][location.col] = false;
            }
        }
        for (int row = currentLocation.row + 1; row <= H; row++) {
            for (int col = 1; col < N; col++) {
                Location location = new Location(row, col);
                if (isPlaceable(location)) {
                    visited[location.row][location.col] = true;
                    combinations(nextLocation(location), maxSize, count + 1);
                    visited[location.row][location.col] = false;
                }
            }
        }
    }

    static boolean isPlaceable(Location location) {
        if (visited[location.row][location.col]) {
            return false;
        }

        int row = location.row;
        int col = location.col;

        if (isInMatrix(row, col + 1) && visited[row][col + 1]) {
            return false;
        }
        if (isInMatrix(row, col - 1) && visited[row][col - 1]) {
            return false;
        }

        return true;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 1 && row <= H && col >= 1 && col < N;
    }

    static Location nextLocation(Location location) {
        int row = location.row;
        int col = location.col;
        col++;
        if (col >= N) {
            row++;
            col = 1;
        }

        return new Location(row, col);
    }

    static boolean isSatisfied() {
        for (int col = 1; col <= N; col++) {
            if (!isPassedSimulation(col)) {
                return false;
            }
        }
        return true;
    }

    static boolean isPassedSimulation(int col) {
        int currentRow = 1;
        int currentCol = col;

        while (currentRow <= H) {
            if (visited[currentRow][currentCol - 1]) {
                currentCol--;
            } else if (visited[currentRow][currentCol]){
                currentCol++;
            }

            currentRow++;
        }

        return col == currentCol;
    }
}
