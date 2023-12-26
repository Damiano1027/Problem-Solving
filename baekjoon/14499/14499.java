import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer stringTokenizer;
    static int N, M, K;
    static int[][] matrix;
    static int[] commands;
    static int currentRow, currentCol;
    static int[] faces1 = new int[4], faces2 = new int[2];
    static int topIndex = 0, bottomIndex = 2;

    public static void main(String[] args) throws IOException {
        stringTokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(stringTokenizer.nextToken());
        M = Integer.parseInt(stringTokenizer.nextToken());
        currentRow = Integer.parseInt(stringTokenizer.nextToken());
        currentCol = Integer.parseInt(stringTokenizer.nextToken());
        K = Integer.parseInt(stringTokenizer.nextToken());

        matrix = new int[N][M];
        commands = new int[K];

        for (int i = 0; i < N; i++) {
            stringTokenizer = new StringTokenizer(reader.readLine());
            int j = 0;
            while (stringTokenizer.hasMoreTokens()) {
                matrix[i][j++] = Integer.parseInt(stringTokenizer.nextToken());
            }
        }

        int i = 0;
        stringTokenizer = new StringTokenizer(reader.readLine());
        while (stringTokenizer.hasMoreTokens()) {
            commands[i++] = Integer.parseInt(stringTokenizer.nextToken());
        }

        solve();
        writer.close();
    }

    static void solve() throws IOException {
        for (int command : commands) {
            if (!isMovable(command)) {
                continue;
            }

            move(command);
            copy();

            writer.write(String.format("%d\n", faces1[topIndex]));
            writer.flush();
        }
    }

    static void copy() {
        if (matrix[currentRow][currentCol] == 0) {
            matrix[currentRow][currentCol] = faces1[bottomIndex];
        } else {
            faces1[bottomIndex] = matrix[currentRow][currentCol];
            matrix[currentRow][currentCol] = 0;
        }
    }

    static boolean isMovable(int command) {
        int nextRow = currentRow;
        int nextCol = currentCol;

        if (command == 1) {
            nextCol++;
        } else if (command == 2) {
            nextCol--;
        } else if (command == 3) {
            nextRow--;
        } else if (command == 4) {
            nextRow++;
        }

        return isInMatrix(nextRow, nextCol);
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }

    static void move(int command) {
        if (command == 1) {
            int temp1 = faces2[0];
            int temp2 = faces2[1];
            faces2[0] = faces1[bottomIndex];
            faces2[1] = faces1[topIndex];
            faces1[topIndex] = temp1;
            faces1[bottomIndex] = temp2;
            currentCol++;
        } else if (command == 2) {
            int temp1 = faces2[0];
            int temp2 = faces2[1];
            faces2[0] = faces1[topIndex];
            faces2[1] = faces1[bottomIndex];
            faces1[topIndex] = temp2;
            faces1[bottomIndex] = temp1;
            currentCol--;
        } else if (command == 3) {
            int temp = faces1[0];
            faces1[0] = faces1[1];
            faces1[1] = faces1[2];
            faces1[2] = faces1[3];
            faces1[3] = temp;
            currentRow--;
        } else if (command == 4) {
            int temp = faces1[3];
            faces1[3] = faces1[2];
            faces1[2] = faces1[1];
            faces1[1] = faces1[0];
            faces1[0] = temp;
            currentRow++;
        }
    }
}
