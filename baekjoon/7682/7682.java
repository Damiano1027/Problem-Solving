import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        while (true) {
            String str = reader.readLine();
            if (str.equals("end")) {
                break;
            }

            char[][] matrix = makeMatrix(str);

            if (isValid(matrix)) {
                writer.write("valid\n");
            } else {
                writer.write("invalid\n");
            }

            writer.flush();
        }

        writer.close();
    }
    private static char[][] makeMatrix(String str) {
        char[][] matrix = new char[3][3];

        int strIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = str.charAt(strIndex++);
            }
        }

        return matrix;
    }

    private static boolean isValid(char[][] matrix) {
        int countOfX = getCountOfX(matrix);
        int countOfO = getCountOfO(matrix);
        boolean exist3PartitionsOfX = isExistPartitionsOfX(matrix);
        boolean exist3PartitionsOfO = isExistPartitionsOfO(matrix);

        if (countOfX == 5 && countOfO == 4) {
            if (!exist3PartitionsOfX && !exist3PartitionsOfO) {
                return true;
            }
        }

        if (countOfX == countOfO) {
            if (exist3PartitionsOfO && !exist3PartitionsOfX) {
                return true;
            }
        }

        if (countOfX == countOfO + 1) {
            if (exist3PartitionsOfX && !exist3PartitionsOfO) {
                return true;
            }
        }

        return false;
    }

    private static int getCountOfX(char[][] matrix) {
        int count = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == 'X') {
                    count++;
                }
            }
        }

        return count;
    }

    private static int getCountOfO(char[][] matrix) {
        int count = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == 'O') {
                    count++;
                }
            }
        }

        return count;
    }

    private static boolean isExistPartitionsOfX(char[][] matrix) {
        // 가로 확인
        for (int i = 0; i < 3; i++) {
            if (matrix[i][0] == 'X' && matrix[i][0] == matrix[i][1] && matrix[i][1] == matrix[i][2]) {
                return true;
            }
        }

        // 세로 확인
        for (int i = 0; i < 3; i++) {
            if (matrix[0][i] == 'X' && matrix[0][i] == matrix[1][i] && matrix[1][i] == matrix[2][i]) {
                return true;
            }
        }

        // 대각선 확인
        if (matrix[0][0] == 'X' && matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2]) {
            return true;
        }
        if (matrix[0][2] == 'X' && matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0]) {
            return true;
        }

        return false;
    }

    private static boolean isExistPartitionsOfO(char[][] matrix) {
        // 가로 확인
        for (int i = 0; i < 3; i++) {
            if (matrix[i][0] == 'O' && matrix[i][0] == matrix[i][1] && matrix[i][1] == matrix[i][2]) {
                return true;
            }
        }

        // 세로 확인
        for (int i = 0; i < 3; i++) {
            if (matrix[0][i] == 'O' && matrix[0][i] == matrix[1][i] && matrix[1][i] == matrix[2][i]) {
                return true;
            }
        }

        // 대각선 확인
        if (matrix[0][0] == 'O' && matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2]) {
            return true;
        }
        if (matrix[0][2] == 'O' && matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0]) {
            return true;
        }

        return false;
    }
}
