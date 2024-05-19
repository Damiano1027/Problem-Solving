import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int n, m;
    static StringTokenizer tokenizer;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int[][] matrix;
    static boolean[][] visited;
    static int result1, result2;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[n][m];
        visited = new boolean[n][m];

        for (int row = 0; row < n; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < m; col++) {
                matrix[row][col] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (matrix[row][col] == 1 && !visited[row][col]) {
                    result1++;
                    result2 = Math.max(result2, bfs(row, col));
                }
            }
        }

        writer.write(result1 + "\n");
        writer.write(result2 + "\n");
        writer.flush();
        writer.close();
    }

    static int bfs(int startRow, int startCol) {
        Queue<Location> queue = new LinkedList<>() {{
            add(new Location(startRow, startCol));
        }};
        visited[startRow][startCol] = true;
        int size = 0;

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            size++;

            for (int i = 0; i < 4; i++) {
                int aRow = currentLocation.row + dRow[i];
                int aCol = currentLocation.col + dCol[i];

                if (isInRange(aRow, aCol) && matrix[aRow][aCol] == 1 && !visited[aRow][aCol]) {
                    visited[aRow][aCol] = true;
                    queue.add(new Location(aRow, aCol));
                }
            }
        }

        return size;
    }

    static boolean isInRange(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < m;
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
