import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, K;
    static int[][] matrix;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N][M];

        for (int i = 0; i < N; i++) {
            String line = reader.readLine();
            for (int j = 0; j < M; j++) {
                matrix[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }

        writer.write(bfs() + "\n");
        writer.flush();
        writer.close();
    }

    static int bfs() {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(0, 0, 0, 1));
        }};
        boolean[][][] visited = new boolean[N][M][K + 1];
        visited[0][0][0] = true;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.row == N - 1 && current.col == M - 1) {
                return current.distance;
            }

            for (int i = 0; i < 4; i++) {
                int aRow = current.row + dRow[i];
                int aCol = current.col + dCol[i];

                if (!isInMatrix(aRow, aCol)) {
                    continue;
                }

                if (matrix[aRow][aCol] == 1) {
                    if (current.brokenCount < K && !visited[aRow][aCol][current.brokenCount]) {
                        queue.add(new Bfs(aRow, aCol, current.brokenCount + 1, current.distance + 1));
                        visited[aRow][aCol][current.brokenCount] = true;
                    }
                } else {
                    if (!visited[aRow][aCol][current.brokenCount]) {
                        queue.add(new Bfs(aRow, aCol, current.brokenCount, current.distance + 1));
                        visited[aRow][aCol][current.brokenCount] = true;
                    }
                }
            }
        }

        return -1;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }

}

class Bfs {
    int row, col, brokenCount, distance;
    Bfs(int row, int col, int brokenCount, int distance) {
        this.row = row;
        this.col = col;
        this.brokenCount = brokenCount;
        this.distance = distance;
    }
}
