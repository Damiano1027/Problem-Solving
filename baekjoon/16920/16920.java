import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, P;
    static int[] S;
    static int[][] matrix;
    static int emptyLocationCount;
    static List<Location>[] locations, lastLocations;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        P = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N][M];
        emptyLocationCount = N * M;
        S = new int[P + 1];
        locations = new List[P + 1];
        for (int i = 1; i <= P; i++) {
            locations[i] = new ArrayList<>();
        }
        lastLocations = new List[P + 1];
        for (int i = 1; i <= P; i++) {
            lastLocations[i] = new ArrayList<>();
        }

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 1; i <= P; i++) {
            S[i] = Integer.parseInt(tokenizer.nextToken());
        }

        for (int row = 0; row < N; row++) {
            String line = reader.readLine();
            for (int col = 0; col < M; col++) {
                char ch = line.charAt(col);
                if (ch == '.') {
                    matrix[row][col] = 0;
                } else if (ch == '#') {
                    matrix[row][col] = -1;
                    emptyLocationCount--;
                } else {
                    int number = Character.getNumericValue(ch);
                    matrix[row][col] = number;
                    locations[number].add(new Location(row, col));
                    lastLocations[number].add(new Location(row, col));
                    emptyLocationCount--;
                }
            }
        }

        solve();

        for (int i = 1; i <= P; i++) {
            writer.write(String.format("%d ", locations[i].size()));
        }
        writer.newLine();
        writer.flush();
        writer.close();
    }

    static void solve() {
        label:
        while (true) {
            int failCount = 0;

            for (int number = 1; number <= P; number++) {
                if (!bfs(number)) {
                    failCount++;
                }

                if (emptyLocationCount == 0) {
                    break label;
                }
            }

            if (failCount == P) {
                break;
            }
        }
    }

    static boolean bfs(int number) {
        boolean[][] visited = new boolean[N][M];
        int maxCount = S[number];
        List<Location> placeableLocations = new ArrayList<>();

        Queue<Bfs> queue = new LinkedList<>();
        for (Location location : lastLocations[number]) {
            for (int i = 0; i < 4; i++) {
                int aRow = location.row + dRow[i];
                int aCol = location.col + dCol[i];

                if (isInMatrix(aRow, aCol) && matrix[aRow][aCol] == 0 && !visited[aRow][aCol]) {
                    queue.add(new Bfs(aRow, aCol, 1));
                    visited[aRow][aCol] = true;
                }
            }
        }

        if (queue.isEmpty()) {
            return false;
        }

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();
            placeableLocations.add(new Location(current.row, current.col));

            for (int i = 0; i < 4; i++) {
                int nextRow = current.row + dRow[i];
                int nextCol = current.col + dCol[i];

                if (isInMatrix(nextRow, nextCol) && current.count < maxCount
                        && matrix[nextRow][nextCol] == 0 && !visited[nextRow][nextCol]) {
                    queue.add(new Bfs(nextRow, nextCol, current.count + 1));
                    visited[nextRow][nextCol] = true;
                }
            }
        }

        lastLocations[number] = new ArrayList<>();
        for (Location location : placeableLocations) {
            matrix[location.row][location.col] = number;
            locations[number].add(location);
            lastLocations[number].add(location);
            emptyLocationCount--;
        }

        return true;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

class Bfs {
    int row, col, count;
    Bfs(int row, int col, int count) {
        this.row = row;
        this.col = col;
        this.count = count;
    }
}
