import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, K;
    static int[][] matrix;
    static class ValueControlInformation {
        int row, col, change;
        ValueControlInformation(int row, int col, int change) {
            this.row = row;
            this.col = col;
            this.change = change;
        }
        public String toString() {
            return String.format("%d %d %d", row, col, change);
        }
    }
    static class VisitedInformation {
        int row1, col1, row2, col2;
        VisitedInformation(int row1, int col1, int row2, int col2) {
            this.row1 = row1;
            this.col1 = col1;
            this.row2 = row2;
            this.col2 = col2;
        }
        public boolean equals(Object o) {
            VisitedInformation visitedInformation = (VisitedInformation) o;
            return row1 == visitedInformation.row1 && col1 == visitedInformation.col1
                    && row2 == visitedInformation.row2 && col2 == visitedInformation.col2;
        }
        public int hashCode() {
            return Objects.hash(row1, col1, row2, col2);
        }
    }
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        matrix = newMatrix();

        tokenizer = new StringTokenizer(reader.readLine());
        for (int col = 0; col < N; col++) {
            matrix[N - 1][col] = Integer.parseInt(tokenizer.nextToken());
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static int[][] newMatrix() {
        int[][] matrix = new int[N][N];

        for (int row = 0; row < N; row++) {
            Arrays.fill(matrix[row], -1);
        }

        return matrix;
    }

    static void solve() {
        int count = 0;

        do {
            step1();
            step2();
            step3();
            step4();
            step5();
            step6();
            step4();
            step5();
            count++;
        } while (!isEnd());

        result = count;

        /*for (int i = 0; i < N; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }*/
    }

    static void step1() {
        int[] copiedArray = Arrays.copyOf(matrix[N - 1], N);
        Arrays.sort(copiedArray);
        int min = copiedArray[0];

        for (int col = 0; col < N; col++) {
            if (matrix[N - 1][col] == min) {
                matrix[N - 1][col]++;
            }
        }
    }

    static void step2() {
        matrix[N - 2][1] = matrix[N - 1][0];
        matrix[N - 1][0] = -1;
    }

    static void step3() {
        while (true) {
            List<List<Integer>> partialMatrix = new ArrayList<>();
            int nextCol = N;

            for (int col = 0; col < N; col++) {
                if (matrix[N - 1][col] == -1) {
                    continue;
                }

                List<Integer> list = new ArrayList<>();
                for (int row = N - 1; row >= 0; row--) {
                    if (matrix[row][col] == -1) {
                        break;
                    }

                    list.add(matrix[row][col]);
                }

                if (list.size() >= 2) {
                    partialMatrix.add(list);
                } else {
                    nextCol = col;
                    break;
                }
            }

            int[][] newMatrix = newMatrix();
            int newMatrixCol = 0;
            for (int col = nextCol; col < N; col++) {
                int value = matrix[N - 1][col];
                if (value == -1) {
                    break;
                }
                newMatrix[N - 1][newMatrixCol++] = matrix[N - 1][col];
            }

            /*System.out.println(partialMatrix);
            System.out.println(Arrays.toString(newMatrix[N - 1]));
            System.out.println("------------------");*/
            if (newMatrixCol < partialMatrix.get(0).size()) {
                break;
            }

            int newRow = N - 1 - partialMatrix.size();
            for (List<Integer> list: partialMatrix) {
                for (int col = 0; col < list.size(); col++) {
                    newMatrix[newRow][col] = list.get(col);
                }
                newRow++;
            }

            matrix = newMatrix;
        }
    }

    static void step4() {
        int[] dRow = {-1, 0, 1, 0};
        int[] dCol = {0, -1, 0, 1};
        Set<VisitedInformation> visited = new HashSet<>();
        List<ValueControlInformation> valueControlInformations = new ArrayList<>();

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (matrix[row][col] == -1) {
                    continue;
                }

                for (int i = 0; i < 4; i++) {
                    int aRow = row + dRow[i];
                    int aCol = col + dCol[i];

                    if (!isValidLocation(aRow, aCol)) {
                        continue;
                    }
                    if (visited.contains(new VisitedInformation(row, col, aRow, aCol))) {
                        continue;
                    }

                    int difference = Math.abs(matrix[row][col] - matrix[aRow][aCol]);
                    int d = difference / 5;

                    if (d > 0) {
                        if (matrix[row][col] > matrix[aRow][aCol]) {
                            valueControlInformations.add(new ValueControlInformation(row, col, -d));
                            valueControlInformations.add(new ValueControlInformation(aRow, aCol, d));
                        } else {
                            valueControlInformations.add(new ValueControlInformation(aRow, aCol, -d));
                            valueControlInformations.add(new ValueControlInformation(row, col, d));
                        }

                        visited.add(new VisitedInformation(row, col, aRow, aCol));
                        visited.add(new VisitedInformation(aRow, aCol, row, col));
                    }
                }
            }
        }

        for (ValueControlInformation informations: valueControlInformations) {
            matrix[informations.row][informations.col] += informations.change;
        }
    }

    static boolean isValidLocation(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N
                && matrix[row][col] != -1;
    }

    static void step5() {
        int[][] newMatrix = newMatrix();
        int nextMatrixCol = 0;

        for (int col = 0; col < N; col++) {
            for (int row = N - 1; row >= 0; row--) {
                if (matrix[row][col] == -1) {
                    continue;
                }

                newMatrix[N - 1][nextMatrixCol++] = matrix[row][col];
            }
        }

        matrix = newMatrix;
    }

    static void step6() {
        int n = 2;
        int count = 0;

        while (count < 2) {
            int middleCol = N / n - 1;

            List<List<Integer>> partialMatrix = new ArrayList<>();

            for (int row = N - 1; row >= 0; row--) {
                if (matrix[row][middleCol] == -1) {
                    break;
                }

                List<Integer> list = new ArrayList<>();
                for (int col = middleCol; col >= 0; col--) {
                    list.add(matrix[row][col]);
                }

                partialMatrix.add(list);
            }

            int[][] newMatrix = newMatrix();

            for (int row = N - 1; row >= 0; row--) {
                if (matrix[row][middleCol + 1] == -1) {
                    int newMatrixRow = row;
                    for (int i = partialMatrix.size() - 1; i >= 0; i--) {
                        for (int j = 0; j < partialMatrix.get(i).size(); j++) {
                            int value = partialMatrix.get(i).get(j);
                            newMatrix[newMatrixRow][j] = value;
                        }
                        newMatrixRow--;
                    }

                    break;
                }

                int newMatrixCol = 0;
                for (int col = middleCol + 1; col < N; col++) {
                    newMatrix[row][newMatrixCol++] = matrix[row][col];
                }
            }

            matrix = newMatrix;

            count++;
            n *= 2;
        }
    }

    static boolean isEnd() {
        int[] array = Arrays.copyOf(matrix[N - 1], N);
        Arrays.sort(array);

        return array[N - 1] - array[0] <= K;
    }
}
