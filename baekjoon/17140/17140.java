import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int r, c, k;
    static int result;
    static Matrix matrix;
    static class Matrix {
        int[][] matrix;
        Matrix(int[][] matrix) {
            this.matrix = matrix;
        }
        int read(int row, int col) {
            return matrix[row][col];
        }
        int rowSize() {
            return matrix.length;
        }
        int colSize() {
            return matrix[0].length;
        }
        boolean rowSmallerThanCol() {
            return rowSize() < colSize();
        }
        void operate() {
            if (rowSmallerThanCol()) {
                C();
            } else {
                R();
            }
        }
        void R() {
            List<List<Integer>> sortedRows = new ArrayList<>();

            int max = Integer.MIN_VALUE;
            for (int i = 0; i < rowSize(); i++) {
                List<Integer> sortedRow = sortRow(i);
                sortedRows.add(sortedRow);

                max = Math.max(max, sortedRow.size());
            }

            matrix = new int[rowSize()][max];
            for (int i = 0; i < sortedRows.size(); i++) {
                for (int j = 0; j < sortedRows.get(i).size(); j++) {
                    matrix[i][j] = sortedRows.get(i).get(j);
                }
            }
        }
        void C() {
            List<List<Integer>> sortedCols = new ArrayList<>();

            int max = Integer.MIN_VALUE;
            for (int i = 0; i < colSize(); i++) {
                List<Integer> sortedCol = sortCol(i);
                sortedCols.add(sortedCol);

                max = Math.max(max, sortedCol.size());
            }

            matrix = new int[max][colSize()];
            for (int i = 0; i < sortedCols.size(); i++) {
                for (int j = 0; j < sortedCols.get(i).size(); j++) {
                    matrix[j][i] = sortedCols.get(i).get(j);
                }
            }
        }
        List<Integer> sortRow(int row) {
            Map<Integer, Integer> frequencyMap = new HashMap<>();
            for (int value : matrix[row]) {
                if (value == 0) {
                    continue;
                }
                frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
            }

            List<Information> informations = new ArrayList<>();
            for (Integer key : frequencyMap.keySet()) {
                informations.add(new Information(key, frequencyMap.get(key)));
            }
            informations.sort(Information::compare);

            List<Integer> sortedRow = new ArrayList<>();
            for (Information information : informations) {
                sortedRow.add(information.value);
                sortedRow.add(information.frequency);
            }

            return sortedRow;
        }
        List<Integer> sortCol(int col) {
            Map<Integer, Integer> frequencyMap = new HashMap<>();
            for (int i = 0; i < rowSize(); i++) {
                int value = matrix[i][col];
                if (value == 0) {
                    continue;
                }
                frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
            }

            List<Information> informations = new ArrayList<>();
            for (Integer key : frequencyMap.keySet()) {
                informations.add(new Information(key, frequencyMap.get(key)));
            }
            informations.sort(Information::compare);

            List<Integer> sortedCol = new ArrayList<>();
            for (Information information : informations) {
                sortedCol.add(information.value);
                sortedCol.add(information.frequency);
            }

            return sortedCol;
        }
        boolean isInRange(int row, int col) {
            return row >= 0 && row < rowSize() && col >= 0 && col < colSize();
        }
    }
    static class Information {
        int value, frequency;
        Information(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }
        public int compare(Information other) {
            if (frequency == other.frequency) {
                return value - other.value;
            } else if (frequency < other.frequency) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        r = Integer.parseInt(tokenizer.nextToken());
        c = Integer.parseInt(tokenizer.nextToken());
        k = Integer.parseInt(tokenizer.nextToken());

        int[][] inputMatrix = new int[3][3];
        for (int i = 0; i < 3; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < 3; j++) {
                inputMatrix[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        matrix = new Matrix(inputMatrix);

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        int time = 0;

        while (time <= 100) {
            if (matrix.isInRange(r - 1, c - 1) && matrix.read(r - 1, c - 1) == k) {
                result = time;
                return;
            }

            matrix.operate();

            time++;
        }

        result = -1;
    }
}
