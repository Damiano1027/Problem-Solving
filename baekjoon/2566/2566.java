import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int[][] matrix = new int[10][10];

    public static void main(String[] args) throws Exception {
        int max = -1;
        int maxRow = 0, maxCol = 0;

        for (int row = 1; row <= 9; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 1; col <= 9; col++) {
                matrix[row][col] = Integer.parseInt(tokenizer.nextToken());
                if (matrix[row][col] > max) {
                    max = matrix[row][col];
                    maxRow = row;
                    maxCol = col;
                }
            }
        }  

        writer.write(max + "\n");
        writer.write(maxRow + " " + maxCol + "\n");
        writer.flush();
        writer.close();
    }
}
