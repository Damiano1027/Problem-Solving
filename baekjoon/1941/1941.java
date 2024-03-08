import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static char[][] matrix = new char[5][5];
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int result = 0;

    public static void main(String[] args) throws Exception {
        for (int row = 0; row < 5; row++) {
            String line = reader.readLine();
            for (int col = 0; col < 5; col++) {
                matrix[row][col] = line.charAt(col);
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        combination(0, new ArrayList<>());
    }

    static void combination(int currentNumber, List<Integer> numbers) {
        if (numbers.size() >= 7) {
            if (isSatisfied(numbers)) {
                result++;
            }
            return;
        }

        for (int i = currentNumber; i < 25; i++) {
            numbers.add(i);
            combination(i + 1, numbers);
            numbers.remove(numbers.size() - 1);
        }
    }

    static boolean isSatisfied(List<Integer> numbers) {
        List<Location> locations = new ArrayList<>();
        for (int number : numbers) {
            locations.add(new Location(number / 5, number % 5));
        }

        int sCount = 0;
        boolean[][] tempMatrix = new boolean[5][5];
        for (Location location : locations) {
            tempMatrix[location.row][location.col] = true;
            if (matrix[location.row][location.col] == 'S') {
                sCount++;
            }
        }
        if (sCount < 4) {
            return false;
        }

        Location startLocation = locations.get(0);
        Queue<Location> queue = new LinkedList<>() {{
            add(startLocation);
        }};
        boolean[][] visited = new boolean[5][5];
        visited[startLocation.row][startLocation.col] = true;
        int count = 0;

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            count++;

            for (int i = 0; i < 4; i++) {
                int aRow = currentLocation.row + dRow[i];
                int aCol = currentLocation.col + dCol[i];

                if (isInMatrix(aRow, aCol) && !visited[aRow][aCol] && tempMatrix[aRow][aCol]) {
                    queue.add(new Location(aRow, aCol));
                    visited[aRow][aCol] = true;
                }
            }
        }

        return count == 7;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < 5 && col >= 0 && col < 5;
    }
}


class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

