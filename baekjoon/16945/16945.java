import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int[][] matrix = new int[3][3];
    static int result = Integer.MAX_VALUE;


    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 3; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        dfs(new ArrayList<>());
    }

    static void dfs(List<Integer> numbers) {
        if (numbers.size() == 6) {
            int row1Sum = numbers.get(0) + numbers.get(1) + numbers.get(2);
            int row2Sum = numbers.get(3) + numbers.get(4) + numbers.get(5);
            if (row1Sum != row2Sum) {
                return;
            }
        }
        else if (numbers.size() == 7) {
            int[] sums = new int[4];
            sums[0] = numbers.get(0) + numbers.get(1) + numbers.get(2);
            sums[1] = numbers.get(3) + numbers.get(4) + numbers.get(5);
            sums[2] = numbers.get(0) + numbers.get(3) + numbers.get(6);
            sums[3] = numbers.get(2) + numbers.get(4) + numbers.get(6);
            for (int i = 0; i < 3; i++) {
                if (sums[i] != sums[i + 1]) {
                    return;
                }
            }
        }
        else if (numbers.size() == 8) {
            int[] sums = new int[5];
            sums[0] = numbers.get(0) + numbers.get(1) + numbers.get(2);
            sums[1] = numbers.get(3) + numbers.get(4) + numbers.get(5);
            sums[2] = numbers.get(0) + numbers.get(3) + numbers.get(6);
            sums[3] = numbers.get(1) + numbers.get(4) + numbers.get(7);
            sums[4] = numbers.get(2) + numbers.get(4) + numbers.get(6);
            for (int i = 0; i < 4; i++) {
                if (sums[i] != sums[i + 1]) {
                    return;
                }
            }
        } else if (numbers.size() == 9) {
            int[][] matrix = toMatrix(numbers);
            if (isMagicSquare(matrix)) {
                result = Math.min(result, cost(matrix));
            }
            return;
        }

        for (int i = 1; i <= 9; i++) {
            if (numbers.contains(i)) {
                continue;
            }

            numbers.add(i);
            dfs(numbers);
            numbers.remove(numbers.size() - 1);
        }
    }

    static int[][] toMatrix(List<Integer> numbers) {
        int[][] matrix = new int[3][3];
        int index = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = numbers.get(index++);
            }
        }

        return matrix;
    }

    static boolean isMagicSquare(int[][] matrix) {
        int diagonal1Sum = matrix[0][0] + matrix[1][1] + matrix[2][2];
        int diagonal2Sum = matrix[0][2] + matrix[1][1] + matrix[2][0];

        if (diagonal1Sum != diagonal2Sum) {
            return false;
        }

        for (int row = 0; row < 3; row++) {
            int rowSum = matrix[row][0] + matrix[row][1] + matrix[row][2];
            if (rowSum != diagonal1Sum) {
                return false;
            }
        }
        for (int col = 0; col < 3; col++) {
            int colSum = matrix[0][col] + matrix[1][col] + matrix[2][col];
            if (colSum != diagonal1Sum) {
                return false;
            }
        }

        return true;
    }

    static int cost(int[][] magicSquareMatrix) {
        int cost = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] != magicSquareMatrix[i][j]) {
                    cost += Math.abs(matrix[i][j] - magicSquareMatrix[i][j]);
                }
            }
        }

        return cost;
    }
}
