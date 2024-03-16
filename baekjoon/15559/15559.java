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
    static int[] uf;
    static Set<Integer> set = new HashSet<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N][M];
        uf = new int[N * M];
        for (int i = 0; i < N * M; i++) {
            uf[i] = i;
        }

        for (int row = 0; row < N; row++) {
            String line = reader.readLine();
            for (int col = 0; col < M; col++) {
                char ch = line.charAt(col);
                if (ch == 'N') {
                    matrix[row][col] = 0;
                } else if (ch == 'W') {
                    matrix[row][col] = 1;
                } else if (ch == 'S') {
                    matrix[row][col] = 2;
                } else {
                    matrix[row][col] = 3;
                }
            }
        }

        solve();

        writer.write(set.size() + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        boolean[][] visited = new boolean[N][M];
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                if (!visited[row][col]) {
                    dfs(row, col, visited);
                }
            }
        }

        for (int i = 0; i < N * M; i++) {
            set.add(find(i));
        }
    }

    static void dfs(int currentRow, int currentCol, boolean[][] visited) {
        visited[currentRow][currentCol] = true;

        int nextRow = currentRow + dRow[matrix[currentRow][currentCol]];
        int nextCol = currentCol + dCol[matrix[currentRow][currentCol]];

        int currentValue = getValue(currentRow, currentCol);
        int nextValue = getValue(nextRow, nextCol);
        union(currentValue, nextValue);

        if (!visited[nextRow][nextCol]) {
            dfs(nextRow, nextCol, visited);
        }
    }

    static void union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        uf[aRoot] = bRoot;
    }

    static int find(int value) {
        if (uf[value] == value) {
            return value;
        }

        return uf[value] = find(uf[value]);
    }

    static int getValue(int row, int col) {
        return row * M + col;
    }
}
