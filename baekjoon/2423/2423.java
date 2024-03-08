import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static char[][] matrix;
    static int[] dRow1 = {-1, 0, 1, 0};
    static int[] dCol1 = {0, -1, 0, 1};
    static int[] dRow2 = {-1, 1};
    static int[] dCol2 = {-1, 1};
    static int[] dRow3 = {1, -1};
    static int[] dCol3 = {-1, 1};
    static String result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new char[N][M];

        for (int row = 0; row < N; row++) {
            String line = reader.readLine();
            for (int col = 0; col < M; col++) {
                matrix[row][col] = line.charAt(col);
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        int[][] cost = new int[N][M];
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                cost[row][col] = Integer.MAX_VALUE;
            }
        }

        Deque<Location> deque = new ArrayDeque<>();
        if (matrix[0][0] == '\\') {
            cost[0][0] = 0;
            deque.addFirst(new Location(0, 0, matrix[0][0]));
        } else {
            cost[0][0] = 1;
            deque.addLast(new Location(0, 0, reverse(matrix[0][0])));
        }

        while (!deque.isEmpty()) {
            Location currentLocation = deque.pollFirst();

            for (int i = 0; i < 4; i++) {
                int aRow = currentLocation.row + dRow1[i];
                int aCol = currentLocation.col + dCol1[i];
                if (!isInMatrix(aRow, aCol)) {
                    continue;
                }

                // 기호가 같으면 이동할 수 없는 경우이므로 기호를 바꾸고 가중치 1로 적용
                if (currentLocation.line == matrix[aRow][aCol]) {
                    int newCost = cost[currentLocation.row][currentLocation.col] + 1;
                    if (newCost < cost[aRow][aCol]) {
                        char reversedLine = reverse(currentLocation.line);
                        if (aRow == N - 1 && aCol == M - 1 && reversedLine == '/') {
                            continue;
                        }

                        cost[aRow][aCol] = newCost;
                        deque.addLast(new Location(aRow, aCol, reversedLine));
                    }
                }
                // 기호가 다르면 이동할 수 있는 경우이므로 가중치 0으로 적용
                else {
                    if (cost[currentLocation.row][currentLocation.col] < cost[aRow][aCol]) {
                        if (aRow == N - 1 && aCol == M - 1 && matrix[aRow][aCol] == '/') {
                            continue;
                        }

                        cost[aRow][aCol] = cost[currentLocation.row][currentLocation.col];
                        deque.addFirst(new Location(aRow, aCol, matrix[aRow][aCol]));
                    }
                }
            }

            for (int i = 0; i < 2; i++) {
                if (currentLocation.line == '/') {
                    continue;
                }

                int aRow = currentLocation.row + dRow2[i];
                int aCol = currentLocation.col + dCol2[i];
                if (!isInMatrix(aRow, aCol)) {
                    continue;
                }

                // 기호가 같으면 이동할 수 있는 경우이므로 가중치 0으로 적용
                if (currentLocation.line == matrix[aRow][aCol]) {
                    if (cost[currentLocation.row][currentLocation.col] < cost[aRow][aCol]) {
                        cost[aRow][aCol] = cost[currentLocation.row][currentLocation.col];
                        deque.addFirst(new Location(aRow, aCol, matrix[aRow][aCol]));
                    }
                }
                // 기호가 다르면 이동할 수 없는 경우이므로 기호를 바꾸고 가중치 1로 적용
                else {
                    int newCost = cost[currentLocation.row][currentLocation.col] + 1;
                    if (newCost < cost[aRow][aCol]) {
                        cost[aRow][aCol] = newCost;
                        deque.addLast(new Location(aRow, aCol, currentLocation.line));
                    }
                }
            }

            for (int i = 0; i < 2; i++) {
                if (currentLocation.line == '\\') {
                    continue;
                }

                int aRow = currentLocation.row + dRow3[i];
                int aCol = currentLocation.col + dCol3[i];
                if (!isInMatrix(aRow, aCol)) {
                    continue;
                }

                // 기호가 같으면 이동할 수 있는 경우이므로 가중치 0으로 적용
                if (currentLocation.line == matrix[aRow][aCol]) {
                    if (cost[currentLocation.row][currentLocation.col] < cost[aRow][aCol]) {
                        cost[aRow][aCol] = cost[currentLocation.row][currentLocation.col];
                        deque.addFirst(new Location(aRow, aCol, matrix[aRow][aCol]));
                    }
                }
                // 기호가 다르면 이동할 수 없는 경우이므로 기호를 바꾸고 가중치 1로 적용
                else {
                    int newCost = cost[currentLocation.row][currentLocation.col] + 1;
                    if (newCost < cost[aRow][aCol]) {
                        cost[aRow][aCol] = newCost;
                        deque.addLast(new Location(aRow, aCol, currentLocation.line));
                    }
                }
            }
        }

        result = (cost[N - 1][M - 1] == Integer.MAX_VALUE) ? "NO SOLUTION" : String.valueOf(cost[N - 1][M - 1]);
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }

    static char reverse(char line) {
        if (line == '/') {
            return '\\';
        } else {
            return '/';
        }
    }
}

class Location {
    int row, col;
    char line;
    Location(int row, int col, char line) {
        this.row = row;
        this.col = col;
        this.line = line;
    }
}
