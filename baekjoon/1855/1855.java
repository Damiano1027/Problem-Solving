import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int K;
    static char[][] matrix;

    public static void main(String[] args) throws Exception {
        K = Integer.parseInt(reader.readLine());
        String str = reader.readLine();

        matrix = new char[str.length() / K][K];

        int k = 0;
        for (int i = 0; i < matrix.length;) {
            for (int j = 0; j < K; j++) {
                matrix[i][j] = str.charAt(k++);
            }
            i++;

            if (i >= matrix.length) {
                continue;
            }

            for (int j = K - 1; j >= 0; j--) {
                matrix[i][j] = str.charAt(k++);
            }
            i++;
        }

        for (int i = 0; i < K; i++) {
            for (int j = 0; j < matrix.length; j++) {
                writer.write(matrix[j][i]);
            }
        }
        writer.flush();
        writer.close();
    }
}
