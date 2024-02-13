import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, Q;
    static int[][] staticMatrix;
    static int matrixLength;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static class Location {
        int row, col;

        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        Q = Integer.parseInt(tokenizer.nextToken());

        matrixLength = (int) Math.pow(2, N);
        staticMatrix = new int[matrixLength][matrixLength];

        for (int row = 0; row < matrixLength; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < matrixLength; col++) {
                staticMatrix[row][col] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < Q; i++) {
            int L = Integer.parseInt(tokenizer.nextToken());
            step(L);
        }

        writer.write(String.format("%d\n", getSum()));
        writer.write(String.format("%d\n", getSectionCount()));
        writer.flush();

        writer.close();
    }

    static void step(int L) {
        int partitionLength = (int) Math.pow(2, L);

        for (int row = 0; row < matrixLength; row += partitionLength) {
            for (int col = 0; col < matrixLength; col += partitionLength) {
                rotate(row, col, partitionLength);
            }
        }

        reduceIce();
    }

    static void rotate(int startRow, int startCol, int partitionLength) {
        if (partitionLength == 1) {
            return;
        }

        int upRow = startRow;
        int downRow = startRow + partitionLength - 1;
        int leftCol = startCol;
        int rightCol = startCol + partitionLength - 1;

        Queue<Integer> tempMatrix = new LinkedList<>();

        for (int row = upRow; row <= downRow; row++) {
            for (int col = leftCol; col <= rightCol; col++) {
                tempMatrix.add(staticMatrix[row][col]);
            }
        }

        for (int col = rightCol; col >= leftCol; col--) {
            for (int row = upRow; row <= downRow; row++) {
                staticMatrix[row][col] = tempMatrix.poll();
            }
        }
    }

    static void reduceIce() {
        List<Location> locations = new ArrayList<>();

        for (int row = 0; row < matrixLength; row++) {
            for (int col = 0; col < matrixLength; col++) {
                if (staticMatrix[row][col] == 0) {
                    continue;
                }

                int aIceCount = 0;
                for (int i = 0; i < 4; i++) {
                    int aRow = row + dRow[i];
                    int aCol = col + dCol[i];

                    if (isInMatrix(aRow, aCol) && staticMatrix[aRow][aCol] > 0) {
                        aIceCount++;
                    }
                }

                if (aIceCount < 3) {
                    locations.add(new Location(row, col));
                }
            }
        }

        for (Location location: locations) {
            staticMatrix[location.row][location.col]--;
        }
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < matrixLength && col >= 0 && col < matrixLength;
    }

    static int getSum() {
        int sum = 0;
        for (int row = 0; row < matrixLength; row++) {
            for (int col = 0; col < matrixLength; col++) {
                sum += staticMatrix[row][col];
            }
        }
        return sum;
    }

    static int getSectionCount() {
        boolean[][] visited = new boolean[matrixLength][matrixLength];
        int max = 0;

        for (int row = 0; row < matrixLength; row++) {
            for (int col = 0; col < matrixLength; col++) {
                if (visited[row][col] || staticMatrix[row][col] == 0) {
                    continue;
                }

                int sectionCount = bfs(row, col, visited);
                max = Math.max(max, sectionCount);
            }
        }

        return max;
    }

    static int bfs(int startRow, int startCol, boolean[][] visited) {
        Queue<Location> queue = new LinkedList<>();
        queue.add(new Location(startRow, startCol));
        visited[startRow][startCol] = true;
        int sectionCount = 0;

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            sectionCount++;

            for (int i = 0; i < 4; i++) {
                int aRow = currentLocation.row + dRow[i];
                int aCol = currentLocation.col + dCol[i];

                if (isInMatrix(aRow, aCol) && !visited[aRow][aCol] && staticMatrix[aRow][aCol] > 0) {
                    visited[aRow][aCol] = true;
                    queue.add(new Location(aRow, aCol));
                }
            }
        }

        return sectionCount;
    }
}
