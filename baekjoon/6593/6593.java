import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int L, R, C;
    static char[][][] building;
    static int startLayer, startRow, startCol;
    static int[] dLayer = {0, 0, 0, 0, -1, 1};
    static int[] dRow = {-1, 0, 1, 0, 0, 0};
    static int[] dCol = {0, -1, 0, 1, 0, 0};

    public static void main(String[] args) throws Exception {
        while (true) {
            tokenizer = new StringTokenizer(reader.readLine());
            L = Integer.parseInt(tokenizer.nextToken());
            R = Integer.parseInt(tokenizer.nextToken());
            C = Integer.parseInt(tokenizer.nextToken());

            if (L == 0 && R == 0 && C == 0) {
                break;
            }

            building = new char[L][R][C];
            for (int layer = 0; layer < L; layer++) {
                for (int row = 0; row < R; row++) {
                    String line = reader.readLine();
                    for (int col = 0; col < C; col++) {
                        building[layer][row][col] = line.charAt(col);
                        if (building[layer][row][col] == 'S') {
                            startLayer = layer;
                            startRow = row;
                            startCol = col;
                        }
                    }
                }
                reader.readLine();
            }

            int result = bfs();
            if (result == -1) {
                writer.write("Trapped!\n");
            } else {
                writer.write(String.format("Escaped in %d minute(s).\n", result));
            }
        }

        writer.flush();
        writer.close();
    }

    static int bfs() {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(startLayer, startRow, startCol, 0));
        }};
        boolean[][][] visited = new boolean[L][R][C];
        visited[startLayer][startRow][startCol] = true;

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (building[current.layer][current.row][current.col] == 'E') {
                return current.time;
            }

            for (int i = 0; i < 6; i++) {
                int aLayer = current.layer + dLayer[i];
                int aRow = current.row + dRow[i];
                int aCol = current.col + dCol[i];

                if (isInBuilding(aLayer, aRow, aCol) && !visited[aLayer][aRow][aCol] && building[aLayer][aRow][aCol] != '#') {
                    queue.add(new Bfs(aLayer, aRow, aCol, current.time + 1));
                    visited[aLayer][aRow][aCol] = true;
                }
            }
        }

        return -1;
    }

    static boolean isInBuilding(int layer, int row, int col) {
        return layer >= 0 && layer < L && row >= 0 && row < R && col >= 0 && col < C;
    }
}

class Bfs {
    int layer, row, col, time;
    Bfs(int layer, int row, int col, int time) {
        this.layer = layer;
        this.row = row;
        this.col = col;
        this.time = time;
    }
}
