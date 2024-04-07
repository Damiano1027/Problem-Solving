import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int H, W;
    static boolean[][] matrix;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        H = Integer.parseInt(tokenizer.nextToken());
        W = Integer.parseInt(tokenizer.nextToken());

        matrix = new boolean[H][W];

        tokenizer = new StringTokenizer(reader.readLine());

        int col = 0;
        while (tokenizer.hasMoreTokens()) {
            int height = Integer.parseInt(tokenizer.nextToken());
            for (int row = H - 1; row > H - 1 - height; row--) {
                matrix[row][col] = true;
            }
            col++;
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        for (int row = H - 1; row >= 0; row--) {
            Integer prevCol = null;
            for (int col = 0; col < W; col++) {
                if (matrix[row][col]) {
                    if (prevCol != null) {
                        result += (col - prevCol - 1);
                    }
                    prevCol = col;
                }
            }
        }
    }
}
