import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static char[][] staticMatrix;
    static int result = -1;
    static class Marble {
        int row, col;
    }
    static class Check {
        boolean containsRedMarble, containsBlueMarble;
    }

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        staticMatrix = new char[N][M];

        for (int row = 0; row < N; row++) {
            String line = reader.readLine();
            for (int col = 0; col < M; col++) {
                staticMatrix[row][col] = line.charAt(col);
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        dfs(staticMatrix, 0, -1);
    }

    // direction -> 0: 북, 1: 서, 2: 남, 3: 동
    static void dfs(char[][] matrix, int count, int prevDirection) {
        if (count > 10) {
            return;
        }

        Check check = check(matrix);
        if (!check.containsRedMarble && check.containsBlueMarble) {
            result = (result == -1) ? count : Math.min(result, count);
            return;
        } else if (!check.containsRedMarble && !check.containsBlueMarble) {
            return;
        }

        for (int direction = 0; direction < 4; direction++) {
            if (direction == prevDirection) {
                continue;
            }

            char[][] tiltedMatrix = tilt(matrix, direction);
            if (equals(matrix, tiltedMatrix)) {
                continue;
            }

            dfs(tiltedMatrix, count + 1, direction);
        }
    }

    static char[][] tilt(char[][] oldMatrix, int direction) {
        char[][] copiedMatrix = copy(oldMatrix);
        Marble redMarble = new Marble();
        Marble blueMarble = new Marble();

        for (int row = 1; row < N - 1; row++) {
            for (int col = 1; col < M - 1; col++) {
                if (oldMatrix[row][col] == 'R') {
                    redMarble.row = row;
                    redMarble.col = col;
                }
                if (oldMatrix[row][col] == 'B') {
                    blueMarble.row = row;
                    blueMarble.col = col;
                }
            }
        }

        // 북쪽으로 기울임
        if (direction == 0) {
            if (redMarble.col == blueMarble.col) {
                // 위에 있는 구슬부터 먼저 이동
                if (redMarble.row < blueMarble.row) {
                    // 빨간 구슬 이동
                    for (int row = redMarble.row - 1; row > 0; row--) {
                        if (copiedMatrix[row][redMarble.col] == '.') {
                            copiedMatrix[row + 1][redMarble.col] = '.';
                            copiedMatrix[row][redMarble.col] = 'R';
                        } else if (copiedMatrix[row][redMarble.col] == 'O') {
                            copiedMatrix[row + 1][redMarble.col] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                    // 파란 구슬 이동
                    for (int row = blueMarble.row - 1; row > 0; row--) {
                        if (copiedMatrix[row][blueMarble.col] == '.') {
                            copiedMatrix[row + 1][blueMarble.col] = '.';
                            copiedMatrix[row][blueMarble.col] = 'B';
                        } else if (copiedMatrix[row][blueMarble.col] == 'O') {
                            copiedMatrix[row + 1][blueMarble.col] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                } else {
                    // 파란 구슬 이동
                    for (int row = blueMarble.row - 1; row > 0; row--) {
                        if (copiedMatrix[row][blueMarble.col] == '.') {
                            copiedMatrix[row + 1][blueMarble.col] = '.';
                            copiedMatrix[row][blueMarble.col] = 'B';
                        } else if (copiedMatrix[row][blueMarble.col] == 'O') {
                            copiedMatrix[row + 1][blueMarble.col] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                    // 빨간 구슬 이동
                    for (int row = redMarble.row - 1; row > 0; row--) {
                        if (copiedMatrix[row][redMarble.col] == '.') {
                            copiedMatrix[row + 1][redMarble.col] = '.';
                            copiedMatrix[row][redMarble.col] = 'R';
                        } else if (copiedMatrix[row][redMarble.col] == 'O') {
                            copiedMatrix[row + 1][redMarble.col] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                }
            } else {
                // 빨간 구슬 이동
                for (int row = redMarble.row - 1; row > 0; row--) {
                    if (copiedMatrix[row][redMarble.col] == '.') {
                        copiedMatrix[row + 1][redMarble.col] = '.';
                        copiedMatrix[row][redMarble.col] = 'R';
                    } else if (copiedMatrix[row][redMarble.col] == 'O') {
                        copiedMatrix[row + 1][redMarble.col] = '.';
                        break;
                    } else {
                        break;
                    }
                }
                // 파란 구슬 이동
                for (int row = blueMarble.row - 1; row > 0; row--) {
                    if (copiedMatrix[row][blueMarble.col] == '.') {
                        copiedMatrix[row + 1][blueMarble.col] = '.';
                        copiedMatrix[row][blueMarble.col] = 'B';
                    } else if (copiedMatrix[row][blueMarble.col] == 'O') {
                        copiedMatrix[row + 1][blueMarble.col] = '.';
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        // 서쪽으로 기울임
        else if (direction == 1) {
            if (redMarble.row == blueMarble.row) {
                // 왼쪽에 있는 구슬부터 먼저 이동
                if (redMarble.col < blueMarble.col) {
                    // 빨간 구슬 이동
                    for (int col = redMarble.col - 1; col > 0; col--) {
                        if (copiedMatrix[redMarble.row][col] == '.') {
                            copiedMatrix[redMarble.row][col + 1] = '.';
                            copiedMatrix[redMarble.row][col] = 'R';
                        } else if (copiedMatrix[redMarble.row][col] == 'O') {
                            copiedMatrix[redMarble.row][col + 1] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                    // 파란 구슬 이동
                    for (int col = blueMarble.col - 1; col > 0; col--) {
                        if (copiedMatrix[blueMarble.row][col] == '.') {
                            copiedMatrix[blueMarble.row][col + 1] = '.';
                            copiedMatrix[blueMarble.row][col] = 'B';
                        } else if (copiedMatrix[blueMarble.row][col] == 'O') {
                            copiedMatrix[blueMarble.row][col + 1] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                } else {
                    // 파란 구슬 이동
                    for (int col = blueMarble.col - 1; col > 0; col--) {
                        if (copiedMatrix[blueMarble.row][col] == '.') {
                            copiedMatrix[blueMarble.row][col + 1] = '.';
                            copiedMatrix[blueMarble.row][col] = 'B';
                        } else if (copiedMatrix[blueMarble.row][col] == 'O') {
                            copiedMatrix[blueMarble.row][col + 1] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                    // 빨간 구슬 이동
                    for (int col = redMarble.col - 1; col > 0; col--) {
                        if (copiedMatrix[redMarble.row][col] == '.') {
                            copiedMatrix[redMarble.row][col + 1] = '.';
                            copiedMatrix[redMarble.row][col] = 'R';
                        } else if (copiedMatrix[redMarble.row][col] == 'O') {
                            copiedMatrix[redMarble.row][col + 1] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                }
            } else {
                // 빨간 구슬 이동
                for (int col = redMarble.col - 1; col > 0; col--) {
                    if (copiedMatrix[redMarble.row][col] == '.') {
                        copiedMatrix[redMarble.row][col + 1] = '.';
                        copiedMatrix[redMarble.row][col] = 'R';
                    } else if (copiedMatrix[redMarble.row][col] == 'O') {
                        copiedMatrix[redMarble.row][col + 1] = '.';
                        break;
                    } else {
                        break;
                    }
                }
                // 파란 구슬 이동
                for (int col = blueMarble.col - 1; col > 0; col--) {
                    if (copiedMatrix[blueMarble.row][col] == '.') {
                        copiedMatrix[blueMarble.row][col + 1] = '.';
                        copiedMatrix[blueMarble.row][col] = 'B';
                    } else if (copiedMatrix[blueMarble.row][col] == 'O') {
                        copiedMatrix[blueMarble.row][col + 1] = '.';
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        // 남쪽으로 기울임
        else if (direction == 2) {
            if (redMarble.col == blueMarble.col) {
                // 아랫쪽에 있는 구슬부터 먼저 이동
                if (redMarble.row > blueMarble.row) {
                    // 빨간 구슬 이동
                    for (int row = redMarble.row + 1; row < N - 1; row++) {
                        if (copiedMatrix[row][redMarble.col] == '.') {
                            copiedMatrix[row - 1][redMarble.col] = '.';
                            copiedMatrix[row][redMarble.col] = 'R';
                        } else if (copiedMatrix[row][redMarble.col] == 'O') {
                            copiedMatrix[row - 1][redMarble.col] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                    // 파란 구슬 이동
                    for (int row = blueMarble.row + 1; row < N - 1; row++) {
                        if (copiedMatrix[row][blueMarble.col] == '.') {
                            copiedMatrix[row - 1][blueMarble.col] = '.';
                            copiedMatrix[row][blueMarble.col] = 'B';
                        } else if (copiedMatrix[row][blueMarble.col] == 'O') {
                            copiedMatrix[row - 1][blueMarble.col] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                } else {
                    // 파란 구슬 이동
                    for (int row = blueMarble.row + 1; row < N - 1; row++) {
                        if (copiedMatrix[row][blueMarble.col] == '.') {
                            copiedMatrix[row - 1][blueMarble.col] = '.';
                            copiedMatrix[row][blueMarble.col] = 'B';
                        } else if (copiedMatrix[row][blueMarble.col] == 'O') {
                            copiedMatrix[row - 1][blueMarble.col] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                    // 빨간 구슬 이동
                    for (int row = redMarble.row + 1; row < N - 1; row++) {
                        if (copiedMatrix[row][redMarble.col] == '.') {
                            copiedMatrix[row - 1][redMarble.col] = '.';
                            copiedMatrix[row][redMarble.col] = 'R';
                        } else if (copiedMatrix[row][redMarble.col] == 'O') {
                            copiedMatrix[row - 1][redMarble.col] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                }
            } else {
                // 빨간 구슬 이동
                for (int row = redMarble.row + 1; row < N - 1; row++) {
                    if (copiedMatrix[row][redMarble.col] == '.') {
                        copiedMatrix[row - 1][redMarble.col] = '.';
                        copiedMatrix[row][redMarble.col] = 'R';
                    } else if (copiedMatrix[row][redMarble.col] == 'O') {
                        copiedMatrix[row - 1][redMarble.col] = '.';
                        break;
                    } else {
                        break;
                    }
                }
                // 파란 구슬 이동
                for (int row = blueMarble.row + 1; row < N - 1; row++) {
                    if (copiedMatrix[row][blueMarble.col] == '.') {
                        copiedMatrix[row - 1][blueMarble.col] = '.';
                        copiedMatrix[row][blueMarble.col] = 'B';
                    } else if (copiedMatrix[row][blueMarble.col] == 'O') {
                        copiedMatrix[row - 1][blueMarble.col] = '.';
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        // 동쪽으로 기울임
        else {
            if (redMarble.row == blueMarble.row) {
                // 오른쪽에 있는 구슬부터 먼저 이동
                if (redMarble.col > blueMarble.col) {
                    // 빨간 구슬 이동
                    for (int col = redMarble.col + 1; col < M - 1; col++) {
                        if (copiedMatrix[redMarble.row][col] == '.') {
                            copiedMatrix[redMarble.row][col - 1] = '.';
                            copiedMatrix[redMarble.row][col] = 'R';
                        } else if (copiedMatrix[redMarble.row][col] == 'O') {
                            copiedMatrix[redMarble.row][col - 1] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                    // 파란 구슬 이동
                    for (int col = blueMarble.col + 1; col < M - 1; col++) {
                        if (copiedMatrix[blueMarble.row][col] == '.') {
                            copiedMatrix[blueMarble.row][col - 1] = '.';
                            copiedMatrix[blueMarble.row][col] = 'B';
                        } else if (copiedMatrix[blueMarble.row][col] == 'O') {
                            copiedMatrix[blueMarble.row][col - 1] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                } else {
                    // 파란 구슬 이동
                    for (int col = blueMarble.col + 1; col < M - 1; col++) {
                        if (copiedMatrix[blueMarble.row][col] == '.') {
                            copiedMatrix[blueMarble.row][col - 1] = '.';
                            copiedMatrix[blueMarble.row][col] = 'B';
                        } else if (copiedMatrix[blueMarble.row][col] == 'O') {
                            copiedMatrix[blueMarble.row][col - 1] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                    // 빨간 구슬 이동
                    for (int col = redMarble.col + 1; col < M - 1; col++) {
                        if (copiedMatrix[redMarble.row][col] == '.') {
                            copiedMatrix[redMarble.row][col - 1] = '.';
                            copiedMatrix[redMarble.row][col] = 'R';
                        } else if (copiedMatrix[redMarble.row][col] == 'O') {
                            copiedMatrix[redMarble.row][col - 1] = '.';
                            break;
                        } else {
                            break;
                        }
                    }
                }
            } else {
                // 빨간 구슬 이동
                for (int col = redMarble.col + 1; col < M - 1; col++) {
                    if (copiedMatrix[redMarble.row][col] == '.') {
                        copiedMatrix[redMarble.row][col - 1] = '.';
                        copiedMatrix[redMarble.row][col] = 'R';
                    } else if (copiedMatrix[redMarble.row][col] == 'O') {
                        copiedMatrix[redMarble.row][col - 1] = '.';
                        break;
                    } else {
                        break;
                    }
                }
                // 파란 구슬 이동
                for (int col = blueMarble.col + 1; col < M - 1; col++) {
                    if (copiedMatrix[blueMarble.row][col] == '.') {
                        copiedMatrix[blueMarble.row][col - 1] = '.';
                        copiedMatrix[blueMarble.row][col] = 'B';
                    } else if (copiedMatrix[blueMarble.row][col] == 'O') {
                        copiedMatrix[blueMarble.row][col - 1] = '.';
                        break;
                    } else {
                        break;
                    }
                }
            }
        }

        return copiedMatrix;
    }

    static boolean equals(char[][] matrix1, char[][] matrix2) {
        for (int row = 0; row < N; row++) {
            if (!Arrays.equals(matrix1[row], matrix2[row])) {
                return false;
            }
        }
        return true;
    }

    static char[][] copy(char[][] oldMatrix) {
        char[][] newMatrix = new char[N][M];

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                newMatrix[row][col] = oldMatrix[row][col];
            }
        }

        return newMatrix;
    }

    static Check check(char[][] matrix) {
        Check check = new Check();

        for (int row = 1; row < N - 1; row++) {
            for (int col = 1; col < M - 1; col++) {
                if (matrix[row][col] == 'R') {
                    check.containsRedMarble = true;
                }
                if (matrix[row][col] == 'B') {
                    check.containsBlueMarble = true;
                }
            }
        }

        return check;
    }
}
