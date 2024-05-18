import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static StringTokenizer tokenizer;
    static int[] dRow = {-1, 0, 1, 1, 1, 0, -1, -1};
    static int[] dCol = {-1, -1, -1, 0, 1, 1, 1, 0};

    public static void main(String[] args) throws Exception {
        while (true) {
            tokenizer = new StringTokenizer(reader.readLine());
            int w = Integer.parseInt(tokenizer.nextToken());
            int h = Integer.parseInt(tokenizer.nextToken());

            if (w == 0 && h == 0) {
                break;
            }

            int[][] matrix = new int[h][w];
            for (int i = 0; i < h; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                for (int j = 0; j < w; j++) {
                    matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
                }
            }

            boolean[][] visited = new boolean[h][w];
            int cnt = 0;
            for (int row = 0; row < h; row++) {
                for (int col = 0; col < w; col++) {
                    if (matrix[row][col] == 1 && !visited[row][col]) {
                        bfs(row, col, matrix, visited);
                        cnt++;
                    }
                }
            }

            writer.write(cnt + "\n");
        }
        writer.flush();
        writer.close();
    }

    static void bfs(int startRow, int startCol, int[][] matrix, boolean[][] visited) {
        Queue<Location> queue = new LinkedList<>() {{
            add(new Location(startRow, startCol));
        }};
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();

            for (int i = 0; i < 8; i++) {
                int aRow = currentLocation.row + dRow[i];
                int aCol = currentLocation.col + dCol[i];

                if (isInRange(aRow, aCol, matrix) && matrix[aRow][aCol] == 1 && !visited[aRow][aCol]) {
                    visited[aRow][aCol] = true;
                    queue.add(new Location(aRow, aCol));
                }
            }
        }
    }

    static boolean isInRange(int row, int col, int[][] matrix) {
        return row >= 0 && row < matrix.length && col >= 0 && col < matrix[0].length;
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
