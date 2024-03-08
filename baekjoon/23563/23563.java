import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int H, W;
    static char[][] matrix;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int startRow, startCol, endRow, endCol;
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        H = Integer.parseInt(tokenizer.nextToken());
        W = Integer.parseInt(tokenizer.nextToken());

        matrix = new char[H][W];

        for (int row = 0; row < H; row++) {
            String line = reader.readLine();
            for (int col = 0; col < W; col++) {
                char ch = line.charAt(col);
                if (ch == 'S') {
                    startRow = row;
                    startCol = col;
                } else if (ch == 'E') {
                    endRow = row;
                    endCol = col;
                }
                matrix[row][col] = ch;
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        bfs();
    }

    static void bfs() {
        int[][] times = new int[H][W];
        for (int row = 0; row < H; row++) {
            for (int col = 0; col < W; col++) {
                times[row][col] = Integer.MAX_VALUE;
            }
        }
        Deque<Location> deque = new ArrayDeque<>() {{
            addFirst(new Location(startRow, startCol));
        }};
        times[startRow][startCol] = 0;

        while (!deque.isEmpty()) {
            Location currentLocation = deque.pollFirst();

            boolean adjacentWall = isAdjacentWall(currentLocation.row, currentLocation.col);
            for (int i = 0; i < 4; i++) {
                int aRow = currentLocation.row + dRow[i];
                int aCol = currentLocation.col + dCol[i];

                if (!isInMatrix(aRow, aCol) || matrix[aRow][aCol] == '#') {
                    continue;
                }

                if (adjacentWall && isAdjacentWall(aRow, aCol)) {
                    if (times[currentLocation.row][currentLocation.col] < times[aRow][aCol]) {
                        times[aRow][aCol] = times[currentLocation.row][currentLocation.col];
                        deque.addFirst(new Location(aRow, aCol));
                    }
                } else {
                    if (times[currentLocation.row][currentLocation.col] + 1 < times[aRow][aCol]) {
                        times[aRow][aCol] = times[currentLocation.row][currentLocation.col] + 1;
                        deque.addLast(new Location(aRow, aCol));
                    }
                }
            }
        }

        result = times[endRow][endCol];
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < H && col >= 0 && col < W;
    }

    static boolean isAdjacentWall(int row, int col) {
        for (int i = 0; i < 4; i++) {
            int aRow = row + dRow[i];
            int aCol = col + dCol[i];

            if (isInMatrix(aRow, aCol) && matrix[aRow][aCol] == '#') {
                return true;
            }
        }
        return false;
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
