import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int R, C;
    static char[][] matrix;
    static boolean[][] isPipe, isPromising;
    static int[] dRow = {-1, 0, 1};
    static int[] dCol = {1, 1, 1};
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        R = Integer.parseInt(tokenizer.nextToken());
        C = Integer.parseInt(tokenizer.nextToken());

        matrix = new char[R][C];
        isPipe = new boolean[R][C];
        isPromising = new boolean[R][C];

        for (int row = 0; row < R; row++) {
            String line = reader.readLine();
            for (int col = 0; col < C; col++) {
                matrix[row][col] = line.charAt(col);
                isPromising[row][col] = true;
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        dfs();
    }

    static void dfs() {
        for (int row = 0; row < R; row++) {
            Location startLocation = new Location(row, 0);
            List<Location> locations = new ArrayList<>() {{
                add(startLocation);
            }};
            dfs(startLocation, locations);
        }
    }

    static boolean dfs(Location currentLocation, List<Location> locations) {
        if (currentLocation.col == C - 1) {
            for (Location location : locations) {
                isPipe[location.row][location.col] = true;
            }
            result++;
            return true;
        }

        boolean pipelineCreated = false;
        for (int i = 0; i < 3; i++) {
            int aRow = currentLocation.row + dRow[i];
            int aCol = currentLocation.col + dCol[i];
            if (!isInMatrix(aRow, aCol) || isPipe[aRow][aCol] || matrix[aRow][aCol] == 'x' || !isPromising[aRow][aCol]) {
                continue;
            }

            Location nextLocation = new Location(aRow, aCol);
            locations.add(nextLocation);
            boolean created = dfs(nextLocation, locations);
            locations.remove(locations.size() - 1);

            if (created) {
                pipelineCreated = true;
                break;
            }
        }

        isPromising[currentLocation.row][currentLocation.col] = pipelineCreated;

        return pipelineCreated;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < R && col >= 0 && col < C;
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
