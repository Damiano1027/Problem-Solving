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
        // 방문 체크 배열 (행, 열, 낮 or 밤 (0, 1), 벽을 부순 횟수)
        boolean[][][][] visited = new boolean[N][M][2][K + 1];
        visited[0][0][0][0] = true;
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(0, 0, 0, 0, 1));
        }};

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

                // 현재 위치에 가만히 있는 경우
                if (!visited[current.row][current.col][current.time][current.brokenCount]) {
                    queue.add(new Bfs(current.row, current.col, changeTime(current.time), current.brokenCount, current.distance + 1));
                    visited[current.row][current.col][current.time][current.brokenCount] = true;
                }

                /*
                    인접한 칸으로 이동하는 경우
                    - 해당 칸이 빈공간인 경우
                        1. 현재 상태를 기준으로 방문하지 않았어야 함
                    - 해당 칸이 벽일 경우
                        1. 낮이어야 함
                        2. 벽을 K개 미만 부쉈어야 함
                        3. 현재 상태를 기준으로 방문하지 않았어야 함
                */
                if (matrix[aRow][aCol] == 1) {
                    if (current.time == 0 && current.brokenCount < K && !visited[aRow][aCol][current.time][current.brokenCount]) {
                        queue.add(new Bfs(aRow, aCol, changeTime(current.time), current.brokenCount + 1, current.distance + 1));
                        visited[aRow][aCol][current.time][current.brokenCount] = true;
                    }
                } else {
                    if (!visited[aRow][aCol][current.time][current.brokenCount]) {
                        queue.add(new Bfs(aRow, aCol, changeTime(current.time), current.brokenCount, current.distance + 1));
                        visited[aRow][aCol][current.time][current.brokenCount] = true;
                    }
                }
            }
        }

        return -1;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }

    static int changeTime(int time) {
        return (time + 1) % 2;
    }
}

class Bfs {
    int row, col, time, brokenCount, distance;
    Bfs(int row, int col, int time, int brokenCount, int distance) {
        this.row = row;
        this.col = col;
        this.time = time;
        this.brokenCount = brokenCount;
        this.distance = distance;
    }
}
