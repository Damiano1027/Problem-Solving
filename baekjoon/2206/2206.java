import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[][] matrix;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N + 1][M + 1];

        for (int row = 1; row <= N; row++) {
            String line = reader.readLine();
            for (int col = 1; col <= M; col++) {
                matrix[row][col] = Character.getNumericValue(line.charAt(col - 1));
            }
        }

        writer.write(bfs() + "\n");
        writer.flush();
        writer.close();
    }

    static int bfs() {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(1, 1, 1, 0));
        }};
        boolean[][][] visited = new boolean[N + 1][M + 1][2];
        visited[1][1][0] = true;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.row == N && current.col == M) {
                return current.distance;
            }

            for (int i = 0; i < 4; i++) {
                int aRow = current.row + dRow[i];
                int aCol = current.col + dCol[i];

                if (!isInMatrix(aRow, aCol)) {
                    continue;
                }

                if (matrix[aRow][aCol] == 1) {
                    if (current.broken == 0 && !visited[aRow][aCol][0]) {
                        queue.add(new Bfs(aRow, aCol, current.distance + 1, 1));
                        visited[aRow][aCol][0] = true;
                    }
                } else {
                    if (!visited[aRow][aCol][current.broken]) {
                        queue.add(new Bfs(aRow, aCol, current.distance + 1, current.broken));
                        visited[aRow][aCol][current.broken] = true;
                    }
                }
            }
        }

        return -1;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 1 && row <= N && col >= 1 && col <= M;
    }
}

class Bfs {
    int row, col, distance, broken;
    Bfs(int row, int col, int distance, int broken) {
        this.row = row;
        this.col = col;
        this.distance = distance;
        this.broken = broken;
    }
}
