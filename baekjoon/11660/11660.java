import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[][] matrix, sum;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N + 1][N + 1];
        sum = new int[N + 1][N + 1];

        for (int row = 1; row <= N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 1; col <= N; col++) {
                matrix[row][col] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        makeSumMatrix();

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int row1 = Integer.parseInt(tokenizer.nextToken());
            int col1 = Integer.parseInt(tokenizer.nextToken());
            int row2 = Integer.parseInt(tokenizer.nextToken());
            int col2 = Integer.parseInt(tokenizer.nextToken());

            int result;
            if (row1 == 1 && col1 == 1) {
                result = sum[row2][col2];
            } else if (col1 == 1) {
                result = sum[row2][col2] - sum[row1 - 1][col2];
            } else if (row1 == 1) {
                result = sum[row2][col2] - sum[row2][col1 - 1];
            } else {
                result = sum[row2][col2] - sum[row1 - 1][col2] - sum[row2][col1 - 1] + sum[row1 - 1][col1 - 1];
            }

            writer.write(result + "\n");
        }

        writer.flush();
        writer.close();
    }

    static void makeSumMatrix() {
        sum[1][1] = matrix[1][1];
        for (int row = 2; row <= N; row++) {
            sum[row][1] = sum[row - 1][1] + matrix[row][1];
        }
        for (int col = 2; col <= N; col++) {
            sum[1][col] = sum[1][col - 1] + matrix[1][col];
        }
        for (int row = 2; row <= N; row++) {
            for (int col = 2; col <= N; col++) {
                sum[row][col] = sum[row - 1][col] + sum[row][col - 1] - sum[row - 1][col - 1] + matrix[row][col];
            }
        }
    }
}
