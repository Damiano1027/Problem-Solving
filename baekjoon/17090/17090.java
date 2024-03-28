import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[][] direction;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int[] uf;
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        direction = new int[N][M];
        uf = new int[N * M];
        for (int i = 0; i < N * M; i++) {
            uf[i] = i;
        }

        for (int row = 0; row < N; row++) {
            String line = reader.readLine();
            for (int col = 0; col < M; col++) {
                char ch = line.charAt(col);

                if (ch == 'U') {
                    direction[row][col] = 0;
                } else if (ch == 'L') {
                    direction[row][col] = 1;
                } else if (ch == 'D') {
                    direction[row][col] = 2;
                } else {
                    direction[row][col] = 3;
                }
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                int currentLocationValue = getLocationValue(row, col);
                int nextRow = row + dRow[direction[row][col]];
                int nextCol = col + dCol[direction[row][col]];

                if (isInMatrix(nextRow, nextCol)) {
                    int nextLocationValue = getLocationValue(nextRow, nextCol);
                    union(currentLocationValue, nextLocationValue);
                }
            }
        }

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                int root = find(getLocationValue(row, col));
                int rootRow = root / M;
                int rootCol = root % M;

                int nextRowOfRoot = rootRow + dRow[direction[rootRow][rootCol]];
                int nextColOfRoot = rootCol + dCol[direction[rootRow][rootCol]];
                if (!isInMatrix(nextRowOfRoot, nextColOfRoot)) {
                    result++;
                }
            }
        }
    }

    static void union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        if (aRoot != bRoot) {
            Location aRootLocation = getLocation(aRoot);
            int nextRowOfARootLocation = aRootLocation.row + dRow[direction[aRootLocation.row][aRootLocation.col]];
            int nextColOfARootLocation = aRootLocation.col + dCol[direction[aRootLocation.row][aRootLocation.col]];
            if (!isInMatrix(nextRowOfARootLocation, nextColOfARootLocation)) {
                uf[bRoot] = aRoot;
                return;
            }

            Location bRootLocation = getLocation(bRoot);
            int nextRowOfBRootLocation = bRootLocation.row + dRow[direction[bRootLocation.row][bRootLocation.col]];
            int nextColOfBRootLocation = bRootLocation.col + dCol[direction[bRootLocation.row][bRootLocation.col]];
            if (!isInMatrix(nextRowOfBRootLocation, nextColOfBRootLocation)) {
                uf[aRoot] = bRoot;
                return;
            }

            uf[aRoot] = bRoot;
        }
    }

    static int find(int number) {
        if (uf[number] == number) {
            return number;
        }

        return uf[number] = find(uf[number]);
    }

    static int getLocationValue(int row, int col) {
        return row * M + col;
    }

    static Location getLocation(int locationValue) {
        return new Location(locationValue / M, locationValue % M);
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
