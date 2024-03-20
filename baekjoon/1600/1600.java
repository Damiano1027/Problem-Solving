import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int K, W, H;
    static int[][] matrix;
    static int[] dRow1 = {-1, 0, 1, 0}, dRow2 = {-2, -1, 1, 2, 2, 1, -1, -2};
    static int[] dCol1 = {0, -1, 0, 1}, dCol2 = {-1, -2, -2, -1, 1, 2, 2, 1};

    static int result = -1;

    public static void main(String[] args) throws Exception {
        K = Integer.parseInt(reader.readLine());
        tokenizer = new StringTokenizer(reader.readLine());
        W = Integer.parseInt(tokenizer.nextToken());
        H = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[H][W];

        for (int row = 0; row < H; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < W; col++) {
                matrix[row][col] = Integer.parseInt(tokenizer.nextToken());
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
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(0, 0, 0, 0, 0));
        }};
        // 마지막 값의 의미 -> 0: 인접한 칸에서 이동하였음을 표시, 1: 말의 이동으로 이동하였음을 표시
        boolean[][][][] visited = new boolean[H][W][K + 1][2];
        visited[0][0][0][0] = true;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.row == H - 1 && current.col == W - 1) {
                result = current.count;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int aRow = current.row + dRow1[i];
                int aCol = current.col + dCol1[i];

                if (isInMatrix(aRow, aCol) && matrix[aRow][aCol] == 0 && !visited[aRow][aCol][current.horseMoveCount][0]) {
                    queue.add(new Bfs(aRow, aCol, current.horseMoveCount, current.count + 1, 0));
                    visited[aRow][aCol][current.horseMoveCount][0] = true;
                }
            }

            if (current.horseMoveCount < K) {
                for (int i = 0; i < 8; i++) {
                    int aRow = current.row + dRow2[i];
                    int aCol = current.col + dCol2[i];

                    if (isInMatrix(aRow, aCol) && matrix[aRow][aCol] == 0 && !visited[aRow][aCol][current.horseMoveCount][1]) {
                        queue.add(new Bfs(aRow, aCol, current.horseMoveCount + 1, current.count + 1, 1));
                        visited[aRow][aCol][current.horseMoveCount][1] = true;
                    }
                }
            }
        }
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < H && col >= 0 && col < W;
    }
}

class Bfs {
    int row, col, horseMoveCount, count, isMoveHorse;
    Bfs(int row, int col, int horseMoveCount, int count, int isMoveHorse) {
        this.row = row;
        this.col = col;
        this.horseMoveCount = horseMoveCount;
        this.count = count;
        this.isMoveHorse = isMoveHorse;
    }
}
