import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n, m;
    static int[][] matrix, resultMatrix;
    static int startRow, startCol;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[n][m];
        resultMatrix = new int[n][m];
        for (int row = 0; row < n; row++) {
            Arrays.fill(resultMatrix[row], -1);
        }

        for (int row = 0; row < n; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < m; col++) {
                int value = Integer.parseInt(tokenizer.nextToken());

                if (value == 2) {
                    startRow = row;
                    startCol = col;
                } else if (value == 0) {
                    resultMatrix[row][col] = 0;
                }

                matrix[row][col] = value;
            }
        }

        bfs();

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                writer.write(String.format("%d ", resultMatrix[row][col]));
            }
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }

    static void bfs() {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(startRow, startCol, 0));
        }};
        boolean[][] visited = new boolean[n][m];
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();
            resultMatrix[current.row][current.col] = current.distance;

            for (int i = 0; i < 4; i++) {
                int aRow = current.row + dRow[i];
                int aCol = current.col + dCol[i];

                if (isInRange(aRow, aCol) && !visited[aRow][aCol] && matrix[aRow][aCol] == 1) {
                    queue.add(new Bfs(aRow, aCol, current.distance + 1));
                    visited[aRow][aCol] = true;
                }
            }
        }
    }

    static boolean isInRange(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < m;
    }
}

class Bfs {
    int row, col, distance;
    Bfs(int row, int col, int distance) {
        this.row = row;
        this.col = col;
        this.distance = distance;
    }
}
