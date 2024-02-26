import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int remainingFuel;
    static int[][] matrix;
    static int baekjoonRow, baekjoonCol;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static Map<Location, Location> goalMap = new HashMap<>();
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        remainingFuel = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N + 1][N + 1];
        for (int row = 1; row <= N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 1; col <= N; col++) {
                int value = Integer.parseInt(tokenizer.nextToken());
                if (value == 1) {
                    matrix[row][col] = -1;
                }
            }
        }

        tokenizer = new StringTokenizer(reader.readLine());
        baekjoonRow = Integer.parseInt(tokenizer.nextToken());
        baekjoonCol = Integer.parseInt(tokenizer.nextToken());

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int passengerRow = Integer.parseInt(tokenizer.nextToken());
            int passengerCol = Integer.parseInt(tokenizer.nextToken());
            int goalRow = Integer.parseInt(tokenizer.nextToken());
            int goalCol = Integer.parseInt(tokenizer.nextToken());
            matrix[passengerRow][passengerCol] = 1;
            goalMap.put(new Location(passengerRow, passengerCol), new Location(goalRow, goalCol));
        }

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        for (int i = 0; i < M; i++) {
            Bfs passenger = bfs();
            if (passenger == null) {
                result = -1;
                return;
            }
            matrix[passenger.row][passenger.col] = 0;
            baekjoonRow = passenger.row;
            baekjoonCol = passenger.col;
            remainingFuel = passenger.remainingFuel;

            Location goalLocation = goalMap.get(new Location(passenger.row, passenger.col));
            Bfs goal = move(goalLocation);
            if (goal == null) {
                result = -1;
                return;
            }
            baekjoonRow = goal.row;
            baekjoonCol = goal.col;
            int removedFuel = remainingFuel - goal.remainingFuel;
            remainingFuel = goal.remainingFuel;
            remainingFuel += (removedFuel * 2);
        }

        result = remainingFuel;
    }

    static Bfs bfs() {
        List<Bfs> candidates = new ArrayList<>();

        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(baekjoonRow, baekjoonCol, remainingFuel, 0));
        }};
        boolean[][] visited = new boolean[N + 1][N + 1];
        visited[baekjoonRow][baekjoonCol] = true;

        while (!queue.isEmpty()) {
            Bfs bfs = queue.poll();

            if (matrix[bfs.row][bfs.col] == 1) {
                candidates.add(bfs);
            }

            for (int i = 0; i < 4; i++) {
                int aRow = bfs.row + dRow[i];
                int aCol = bfs.col + dCol[i];

                if (isInMatrix(aRow, aCol) && !visited[aRow][aCol] && matrix[aRow][aCol] != -1 && bfs.remainingFuel > 0) {
                    queue.add(new Bfs(aRow, aCol, bfs.remainingFuel - 1, bfs.distance + 1));
                    visited[aRow][aCol] = true;
                }
            }
        }

        if (candidates.isEmpty()) {
            return null;
        }

        candidates.sort((o1, o2) -> {
            if (o1.distance != o2.distance) {
                return o1.distance - o2.distance;
            }
            if (o1.row != o2.row) {
                return o1.row - o2.row;
            }
            return o1.col - o2.col;
        });

        return candidates.get(0);
    }

    static Bfs move(Location goalLocation) {
        Bfs successBfs = null;
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(baekjoonRow, baekjoonCol, remainingFuel, 0));
        }};
        boolean[][] visited = new boolean[N + 1][N + 1];
        visited[baekjoonRow][baekjoonCol] = true;

        while (!queue.isEmpty()) {
            Bfs bfs = queue.poll();

            if (bfs.row == goalLocation.row && bfs.col == goalLocation.col) {
                successBfs = bfs;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int aRow = bfs.row + dRow[i];
                int aCol = bfs.col + dCol[i];

                if (isInMatrix(aRow, aCol) && !visited[aRow][aCol] && matrix[aRow][aCol] != -1 && bfs.remainingFuel > 0) {
                    queue.add(new Bfs(aRow, aCol, bfs.remainingFuel - 1, bfs.distance + 1));
                    visited[aRow][aCol] = true;
                }
            }
        }

        return successBfs;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 1 && row <= N && col >= 1 && col <= N;
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public boolean equals(Object o) {
        Location location = (Location) o;
        return row == location.row && col == location.col;
    }
    public int hashCode() {
        return Objects.hash(row, col);
    }
}

class Bfs {
    int row, col, remainingFuel, distance;
    Bfs(int row, int col, int remainingFuel, int distance) {
        this.row = row;
        this.col = col;
        this.remainingFuel = remainingFuel;
        this.distance = distance;
    }
}
