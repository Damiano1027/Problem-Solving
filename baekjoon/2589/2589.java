import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static char[][] staticMatrix;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        staticMatrix = new char[N][M];

        for (int row = 0; row < N; row++) {
            String line = reader.readLine();
            for (int col = 0; col < M; col++) {
                staticMatrix[row][col] = line.charAt(col);
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        int max = Integer.MIN_VALUE;

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                if (isLand(row, col)) {
                    max = Math.max(max, bfs(row, col));
                }
            }
        }

        result = max;
    }

    static boolean isLand(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M
                && staticMatrix[row][col] == 'L';
    }

    static int bfs(int row, int col) {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(row, col, 0));
        }};
        boolean[][] visited = new boolean[N][M];
        visited[row][col] = true;

        int maxTime = 0;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();
            maxTime = current.time;

            for (int i = 0; i < 4; i++) {
                int aRow = current.row + dRow[i];
                int aCol = current.col + dCol[i];

                if (isLand(aRow, aCol) && !visited[aRow][aCol]) {
                    queue.add(new Bfs(aRow, aCol, current.time + 1));
                    visited[aRow][aCol] = true;
                }
            }
        }

        return maxTime;
    }
}

class Bfs {
    int row, col, time;
    Bfs(int row, int col, int time) {
        this.row = row;
        this.col = col;
        this.time = time;
    }
}
