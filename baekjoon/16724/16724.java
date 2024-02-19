import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static char[][] staticMatrix;
    static int[][] visited;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        staticMatrix = new char[N][M];
        visited = new int[N][M];

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
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                if (visited[row][col] == 0) {
                    search(row, col);
                }
            }
        }
    }

    static void search(int startRow, int startCol) {
        Queue<Location> queue = new LinkedList<>() {{
            add(new Location(startRow, startCol));
        }};
        visited[startRow][startCol] = 1;
        Queue<Location> finalQueue = new LinkedList<>();

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            finalQueue.add(currentLocation);
            char direction = staticMatrix[currentLocation.row][currentLocation.col];

            int aRow, aCol;
            if (direction == 'U') {
                aRow = currentLocation.row - 1;
                aCol = currentLocation.col;
            } else if (direction == 'L') {
                aRow = currentLocation.row;
                aCol = currentLocation.col - 1;
            } else if (direction == 'D') {
                aRow = currentLocation.row + 1;
                aCol = currentLocation.col;
            } else {
                aRow = currentLocation.row;
                aCol = currentLocation.col + 1;
            }

            if (visited[aRow][aCol] == 0) {
                queue.add(new Location(aRow, aCol));
                visited[aRow][aCol] = 1;
            } else if (visited[aRow][aCol] == 1) {
                result++;
                break;
            } else {
                break;
            }
        }

        while (!finalQueue.isEmpty()) {
            Location location = finalQueue.poll();
            visited[location.row][location.col] = 2;
        }
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
