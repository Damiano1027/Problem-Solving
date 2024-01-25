import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int tc;
    static char[][][] cube;
    static class Location {
        int faceNumber, row, col;
        Location(int faceNumber, int row, int col) {
            this.faceNumber = faceNumber;
            this.row = row;
            this.col = col;
        }
    }
    static Location[][][] locations = new Location[6][3][3];
    static {
        for (int faceNumber = 0; faceNumber < 6; faceNumber++) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    locations[faceNumber][row][col] = new Location(faceNumber, row, col);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        tc = Integer.parseInt(reader.readLine());

        for (int i = 0; i < tc; i++) {
            int n = Integer.parseInt(reader.readLine());
            tokenizer = new StringTokenizer(reader.readLine());
            initCube();

            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                char face = token.charAt(0);
                char direction = token.charAt(1);

                rotate(face, direction);
            }

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    writer.write(String.format("%c", cube[0][row][col]));
                }
                writer.newLine();
            }
        }

        writer.flush();
        writer.close();
    }

    static void initCube() {
        // (첫번째 인덱스 기준) -> 0: 윗면, 1: 앞면, 2: 아랫면, 3: 뒷면, 4: 왼쪽면, 5: 오른쪽면
        cube = new char[6][3][3];

        // 윗면: 흰색
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cube[0][row][col] = 'w';
            }
        }
        // 아랫면: 노란색
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cube[2][row][col] = 'y';
            }
        }
        // 앞면: 빨간색
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cube[1][row][col] = 'r';
            }
        }
        // 뒷면: 오렌지색
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cube[3][row][col] = 'o';
            }
        }
        // 왼쪽면: 초록색
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cube[4][row][col] = 'g';
            }
        }
        // 오른쪽면: 파란색
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cube[5][row][col] = 'b';
            }
        }
    }

    static void rotate(char face, char direction) {
        Location[] upper = new Location[3];
        Location[] left = new Location[3];
        Location[] under = new Location[3];
        Location[] right = new Location[3];
        Location[] front = new Location[9];

        if (face == 'U') {
            upper[0] = locations[3][0][2];
            upper[1] = locations[3][0][1];
            upper[2] = locations[3][0][0];

            left[0] = locations[4][0][2];
            left[1] = locations[4][0][1];
            left[2] = locations[4][0][0];

            under[0] = locations[1][0][2];
            under[1] = locations[1][0][1];
            under[2] = locations[1][0][0];

            right[0] = locations[5][0][2];
            right[1] = locations[5][0][1];
            right[2] = locations[5][0][0];

            int row = 0, col = 0;
            for (int i = 0; i < 9; i++) {
                front[i] = locations[0][row][col];
                col++;

                if (col > 2) {
                    row++;
                    col = 0;
                }
            }
        } else if (face == 'D') {
            upper[0] = locations[1][2][0];
            upper[1] = locations[1][2][1];
            upper[2] = locations[1][2][2];

            left[0] = locations[4][2][0];
            left[1] = locations[4][2][1];
            left[2] = locations[4][2][2];

            under[0] = locations[3][2][0];
            under[1] = locations[3][2][1];
            under[2] = locations[3][2][2];

            right[0] = locations[5][2][0];
            right[1] = locations[5][2][1];
            right[2] = locations[5][2][2];

            int row = 0, col = 0;
            for (int i = 0; i < 9; i++) {
                front[i] = locations[2][row][col];
                col++;

                if (col > 2) {
                    row++;
                    col = 0;
                }
            }
        } else if (face == 'F') {
            upper[0] = locations[0][2][0];
            upper[1] = locations[0][2][1];
            upper[2] = locations[0][2][2];

            left[0] = locations[4][2][2];
            left[1] = locations[4][1][2];
            left[2] = locations[4][0][2];

            under[0] = locations[2][0][2];
            under[1] = locations[2][0][1];
            under[2] = locations[2][0][0];

            right[0] = locations[5][0][0];
            right[1] = locations[5][1][0];
            right[2] = locations[5][2][0];

            int row = 0, col = 0;
            for (int i = 0; i < 9; i++) {
                front[i] = locations[1][row][col];
                col++;

                if (col > 2) {
                    row++;
                    col = 0;
                }
            }
        } else if (face == 'B') {
            upper[0] = locations[0][0][2];
            upper[1] = locations[0][0][1];
            upper[2] = locations[0][0][0];

            left[0] = locations[5][2][2];
            left[1] = locations[5][1][2];
            left[2] = locations[5][0][2];

            under[0] = locations[2][2][0];
            under[1] = locations[2][2][1];
            under[2] = locations[2][2][2];

            right[0] = locations[4][0][0];
            right[1] = locations[4][1][0];
            right[2] = locations[4][2][0];

            int row = 0, col = 0;
            for (int i = 0; i < 9; i++) {
                front[i] = locations[3][row][col];
                col++;

                if (col > 2) {
                    row++;
                    col = 0;
                }
            }
        } else if (face == 'L') {
            upper[0] = locations[0][0][0];
            upper[1] = locations[0][1][0];
            upper[2] = locations[0][2][0];

            left[0] = locations[3][2][2];
            left[1] = locations[3][1][2];
            left[2] = locations[3][0][2];

            under[0] = locations[2][0][0];
            under[1] = locations[2][1][0];
            under[2] = locations[2][2][0];

            right[0] = locations[1][0][0];
            right[1] = locations[1][1][0];
            right[2] = locations[1][2][0];

            int row = 0, col = 0;
            for (int i = 0; i < 9; i++) {
                front[i] = locations[4][row][col];
                col++;

                if (col > 2) {
                    row++;
                    col = 0;
                }
            }
        } else {
            upper[0] = locations[0][2][2];
            upper[1] = locations[0][1][2];
            upper[2] = locations[0][0][2];

            left[0] = locations[1][2][2];
            left[1] = locations[1][1][2];
            left[2] = locations[1][0][2];

            under[0] = locations[2][2][2];
            under[1] = locations[2][1][2];
            under[2] = locations[2][0][2];

            right[0] = locations[3][0][0];
            right[1] = locations[3][1][0];
            right[2] = locations[3][2][0];

            int row = 0, col = 0;
            for (int i = 0; i < 9; i++) {
                front[i] = locations[5][row][col];
                col++;

                if (col > 2) {
                    row++;
                    col = 0;
                }
            }
        }

        if (direction == '+') {
            rotateRight(upper, left, under, right, front);
        } else {
            rotateLeft(upper, left, under, right, front);
        }
    }

    static void rotateLeft(Location[] upper, Location[] left, Location[] under, Location[] right, Location[] front) {
        char[] oldUpperColors = getLineColors(upper);
        char[] oldLeftColors = getLineColors(left);
        char[] oldUnderColors = getLineColors(under);
        char[] oldRightColors = getLineColors(right);
        char[][] oldFrontColors = getMatrixColors(front);

        for (int i = 0; i < 3; i++) {
            cube[left[i].faceNumber][left[i].row][left[i].col] = oldUpperColors[i];
        }
        for (int i = 0; i < 3; i++) {
            cube[under[i].faceNumber][under[i].row][under[i].col] = oldLeftColors[i];
        }
        for (int i = 0; i < 3; i++) {
            cube[right[i].faceNumber][right[i].row][right[i].col] = oldUnderColors[i];
        }
        for (int i = 0; i < 3; i++) {
            cube[upper[i].faceNumber][upper[i].row][upper[i].col] = oldRightColors[i];
        }

        int row = 0, col = 2;
        for (int i = 0; i < 9; i++) {
            cube[front[i].faceNumber][front[i].row][front[i].col] = oldFrontColors[row][col];
            row++;

            if (row >= 3) {
                col--;
                row = 0;
            }
        }
    }

    static void rotateRight(Location[] upper, Location[] left, Location[] under, Location[] right, Location[] front) {
        char[] oldUpperColors = getLineColors(upper);
        char[] oldLeftColors = getLineColors(left);
        char[] oldUnderColors = getLineColors(under);
        char[] oldRightColors = getLineColors(right);
        char[][] oldFrontColors = getMatrixColors(front);

        for (int i = 0; i < 3; i++) {
            cube[right[i].faceNumber][right[i].row][right[i].col] = oldUpperColors[i];
        }
        for (int i = 0; i < 3; i++) {
            cube[upper[i].faceNumber][upper[i].row][upper[i].col] = oldLeftColors[i];
        }
        for (int i = 0; i < 3; i++) {
            cube[left[i].faceNumber][left[i].row][left[i].col] = oldUnderColors[i];
        }
        for (int i = 0; i < 3; i++) {
            cube[under[i].faceNumber][under[i].row][under[i].col] = oldRightColors[i];
        }

        int row = 2, col = 0;
        for (int i = 0; i < 9; i++) {
            cube[front[i].faceNumber][front[i].row][front[i].col] = oldFrontColors[row][col];
            row--;

            if (row < 0) {
                col++;
                row = 2;
            }
        }
    }

    static char[] getLineColors(Location[] locations) {
        char[] colors = new char[3];

        for (int i = 0; i < 3; i++) {
            colors[i] = cube[locations[i].faceNumber][locations[i].row][locations[i].col];
        }

        return colors;
    }

    static char[][] getMatrixColors(Location[] locations) {
        char[][] colors = new char[3][3];

        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                colors[i][j] = cube[locations[k].faceNumber][locations[k].row][locations[k].col];
                k++;
            }
        }

        return colors;
    }
}
