import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[][] matrix;
    static List<List<Information>> informationsList = new ArrayList<>();
    static class Information {
        int rowOffset, colOffset, rate;
        Information(int rowOffset, int colOffset, int rate) {
            this.rowOffset = rowOffset;
            this.colOffset = colOffset;
            this.rate = rate;
        }
    }
    static int direction;
    static int currentRow, currentCol;
    static int movingDistance;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int result;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        matrix = new int[N][N];

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < N; j++) {
                matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        init();
        boolean keepGoing = true;
        int firstSand = getTotalSand();

        while (keepGoing) {
            keepGoing = move();
            movingDistance++;
        }

        int lastSand = getTotalSand();

        result = firstSand - lastSand;
    }

    static void init() {
        direction = 1;
        currentRow = N / 2;
        currentCol = N / 2;
        movingDistance = 1;

        List<Information> northInformations = List.of(
                new Information(0, -1, 1),
                new Information(0, 1, 1),

                new Information(-1, -1, 7),
                new Information(-1, 1, 7),

                new Information(-2, -1, 10),
                new Information(-2, 1, 10),

                new Information(-3, 0, 5),

                new Information(-1, -2, 2),
                new Information(-1, 2, 2)
        );
        informationsList.add(northInformations);

        List<Information> westInformations = List.of(
                new Information(-1, 0, 1),
                new Information(1, 0, 1),

                new Information(-1, -1, 7),
                new Information(1, -1, 7),

                new Information(-1, -2, 10),
                new Information(1, -2, 10),

                new Information(0, -3, 5),

                new Information(-2, -1, 2),
                new Information(2, -1, 2)
        );
        informationsList.add(westInformations);

        List<Information> southInformations = List.of(
                new Information(0, -1, 1),
                new Information(0, 1, 1),

                new Information(1, -1, 7),
                new Information(1, 1, 7),

                new Information(2, -1, 10),
                new Information(2, 1, 10),

                new Information(3, 0, 5),

                new Information(1, -2, 2),
                new Information(1, 2, 2)
        );
        informationsList.add(southInformations);

        List<Information> eastInformations = List.of(
                new Information(-1, 0, 1),
                new Information(1, 0, 1),

                new Information(-1, 1, 7),
                new Information(1, 1, 7),

                new Information(-1, 2, 10),
                new Information(1, 2, 10),

                new Information(0, 3, 5),

                new Information(-2, 1, 2),
                new Information(2, 1, 2)
        );
        informationsList.add(eastInformations);
    }

    static void rotate() {
        direction++;
        if (direction > 3) {
            direction = 0;
        }
    }

    static boolean move() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < movingDistance; j++) {
                int nextRow = currentRow + dRow[direction];
                int nextCol = currentCol + dCol[direction];

                if (!isInMatrix(nextRow, nextCol)) {
                    return false;
                }

                moveTornado(nextRow, nextCol);

                currentRow = nextRow;
                currentCol = nextCol;
            }

            rotate();

            /*for (int d = 0; d < N; d++) {
                System.out.println(Arrays.toString(matrix[d]));
            }
            System.out.println("--------------");*/
        }

        return true;
    }

    static void moveTornado(int oldSandRow, int oldSandCol) {
        /*for (int d = 0; d < N; d++) {
            System.out.println(Arrays.toString(matrix[d]));
        }
        System.out.println("--------------");*/

        List<Information> informations = informationsList.get(direction);
        int oldSand = matrix[oldSandRow][oldSandCol];

        for (Information information: informations) {
            int targetRow = currentRow + information.rowOffset;
            int targetCol = currentCol + information.colOffset;

            int movedSand = (int) (oldSand * (information.rate / 100.0));
            if (isInMatrix(targetRow, targetCol)) {
                matrix[targetRow][targetCol] += movedSand;
            }

            matrix[oldSandRow][oldSandCol] -= movedSand;
        }

        int alphaRow, alphaCol;
        if (direction == 0) {
            alphaRow = currentRow - 2;
            alphaCol = currentCol;
        } else if (direction == 1) {
            alphaRow = currentRow;
            alphaCol = currentCol - 2;
        } else if (direction == 2) {
            alphaRow = currentRow + 2;
            alphaCol = currentCol;
        } else {
            alphaRow = currentRow;
            alphaCol = currentCol + 2;
        }

        if (isInMatrix(alphaRow, alphaCol)) {
            matrix[alphaRow][alphaCol] += matrix[oldSandRow][oldSandCol];
        }
        matrix[oldSandRow][oldSandCol] = 0;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    static int getTotalSand() {
        int totalSand = 0;

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                totalSand += matrix[row][col];
            }
        }

        return totalSand;
    }
}
