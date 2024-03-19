import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static char[][] matrix;
    static Location startLocation, endLocation;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());
        startLocation = new Location(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()));
        endLocation = new Location(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()));

        matrix = new char[N + 1][M + 1];
        for (int row = 1; row <= N; row++) {
            String line = reader.readLine();
            for (int col = 1; col <= M; col++) {
                matrix[row][col] = line.charAt(col - 1);
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        bfs01();
    }

    static void bfs01() {
        int[][] cost = new int[N + 1][M + 1];
        for (int row = 1; row <= N; row++) {
            Arrays.fill(cost[row], Integer.MAX_VALUE);
        }

        Deque<Location> deque = new ArrayDeque<>() {{
            add(startLocation);
        }};
        cost[startLocation.row][startLocation.col] = 0;

        while (!deque.isEmpty()) {
            Location currentLocation = deque.pollFirst();

            for (int i = 0; i < 4; i++) {
                int aRow = currentLocation.row + dRow[i];
                int aCol = currentLocation.col + dCol[i];

                if (!isInMatrix(aRow, aCol)) {
                    continue;
                }

                if (matrix[aRow][aCol] == '1' || matrix[aRow][aCol] == '#') {
                    int newCost = cost[currentLocation.row][currentLocation.col] + 1;
                    if (newCost < cost[aRow][aCol]) {
                        cost[aRow][aCol] = newCost;
                        deque.addLast(new Location(aRow, aCol));
                    }
                } else {
                    if (cost[currentLocation.row][currentLocation.col] < cost[aRow][aCol]) {
                        cost[aRow][aCol] = cost[currentLocation.row][currentLocation.col];
                        deque.addFirst(new Location(aRow, aCol));
                    }

                }
            }
        }

        result = cost[endLocation.row][endLocation.col];
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 1 && row <= N && col >= 1 && col <= M;
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
