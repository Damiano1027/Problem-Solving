import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[][] matrix;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int sharkRow, sharkCol;
    static int marble1ExplodeCount = 0, marble2ExplodeCount = 0, marble3ExplodeCount = 0, result;
    static List<Location> locations = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N + 1][N + 1];
        sharkRow = (N + 1) / 2;
        sharkCol = (N + 1) / 2;

        int currentRow = sharkRow, currentCol = sharkCol;
        int direction = 1;

        for (int moveCount = 1; moveCount <= N - 1; moveCount++) {
            for (int i = 0; i < moveCount; i++) {
                currentRow += dRow[direction];
                currentCol += dCol[direction];
                locations.add(new Location(currentRow, currentCol));
            }

            direction = rotate(direction);

            for (int i = 0; i < moveCount; i++) {
                currentRow += dRow[direction];
                currentCol += dCol[direction];
                locations.add(new Location(currentRow, currentCol));
            }

            direction = rotate(direction);
        }

        for (int i = 0; i < N - 1; i++) {
            currentRow += dRow[direction];
            currentCol += dCol[direction];
            locations.add(new Location(currentRow, currentCol));
        }

        for (int row = 1; row <= N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 1; col <= N; col++) {
                matrix[row][col] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int d = Integer.parseInt(tokenizer.nextToken());
            int s = Integer.parseInt(tokenizer.nextToken());

            if (d == 1) {
                d = 0;
            } else if (d == 3) {
                d = 1;
            } else if (d == 4) {
                d = 3;
            }

            step1(d, s); // 얼음 파편 던지기 및 빈칸으로 구슬 이동
            step2(); // 더이상 폭발하는 구슬이 없을때까지 구슬 폭발 및 구슬 이동
            step3(); // 구슬 변화
        }

        result = marble1ExplodeCount + 2 * marble2ExplodeCount + 3 * marble3ExplodeCount;

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void step1(int d, int s) {
        int emptyLocationsCount = 0;

        // 얼음 파편 던지기
        for (int offSet = 1; offSet <= s; offSet++) {
            int targetRow = sharkRow + offSet * dRow[d];
            int targetCol = sharkCol + offSet * dCol[d];
            matrix[targetRow][targetCol] = 0;
            emptyLocationsCount++;
        }

        // 구슬 이동
        for (int k = 0; k < emptyLocationsCount; k++) {
            for (int i = 0; i < locations.size() - 1; i++) {
                Location location = locations.get(i);
                if (matrix[location.row][location.col] == 0) {
                    for (int j = i; j < locations.size() - 1; j++) {
                        Location leftLocation = locations.get(j);
                        Location rightLocation = locations.get(j + 1);
                        matrix[leftLocation.row][leftLocation.col] = matrix[rightLocation.row][rightLocation.col];
                    }
                    matrix[1][1] = 0;
                    break;
                }
            }
        }
    }

    static void step2() {
        while (true) {
            List<List<Location>> sameMarbleLocationsList = new ArrayList<>();

            // 폭발시킬 구슬 확인
            int sameCount = 0;
            for (int i = 0; i < locations.size() - 1; i++) {
                Location leftLocation = locations.get(i);
                Location rightLocation = locations.get(i + 1);

                if (i + 1 == locations.size() - 1) {
                    if (matrix[leftLocation.row][leftLocation.col] != 0 && matrix[rightLocation.row][rightLocation.col] != 0
                            && matrix[leftLocation.row][leftLocation.col] == matrix[rightLocation.row][rightLocation.col]) {
                        sameCount++;
                        if (sameCount >= 3) {
                            List<Location> sameMarbleLocations = new ArrayList<>();
                            for (int j = i - (sameCount - 1); j <= i + 1; j++) {
                                sameMarbleLocations.add(locations.get(j));
                            }
                            sameMarbleLocationsList.add(sameMarbleLocations);
                        }
                    } else {
                        if (sameCount >= 3) {
                            List<Location> sameMarbleLocations = new ArrayList<>();
                            for (int j = i - sameCount; j <= i; j++) {
                                sameMarbleLocations.add(locations.get(j));
                            }
                            sameMarbleLocationsList.add(sameMarbleLocations);
                        }
                    }
                    break;
                }

                if (matrix[leftLocation.row][leftLocation.col] != 0 && matrix[rightLocation.row][rightLocation.col] != 0
                        && matrix[leftLocation.row][leftLocation.col] == matrix[rightLocation.row][rightLocation.col]) {
                    sameCount++;
                } else {
                    if (sameCount >= 3) {
                        List<Location> sameMarbleLocations = new ArrayList<>();
                        for (int j = i - sameCount; j <= i; j++) {
                            sameMarbleLocations.add(locations.get(j));
                        }
                        sameMarbleLocationsList.add(sameMarbleLocations);
                    }
                    sameCount = 0;
                }
            }

            // 구슬이 폭발하지 않으면 중단
            if (sameMarbleLocationsList.isEmpty()) {
                break;
            }

            // 구슬 폭발
            int emptyLocationsCount = 0;
            for (List<Location> sameMarbleLocations : sameMarbleLocationsList) {
                for (Location sameMarbleLocation : sameMarbleLocations) {
                    if (matrix[sameMarbleLocation.row][sameMarbleLocation.col] == 1) {
                        marble1ExplodeCount++;
                    } else if (matrix[sameMarbleLocation.row][sameMarbleLocation.col] == 2) {
                        marble2ExplodeCount++;
                    } else if (matrix[sameMarbleLocation.row][sameMarbleLocation.col] == 3) {
                        marble3ExplodeCount++;
                    }

                    matrix[sameMarbleLocation.row][sameMarbleLocation.col] = 0;
                    emptyLocationsCount++;
                }
            }

            // 구슬 이동
            for (int k = 0; k < emptyLocationsCount; k++) {
                for (int i = 0; i < locations.size() - 1; i++) {
                    Location location = locations.get(i);
                    if (matrix[location.row][location.col] == 0) {
                        for (int j = i; j < locations.size() - 1; j++) {
                            Location leftLocation = locations.get(j);
                            Location rightLocation = locations.get(j + 1);
                            matrix[leftLocation.row][leftLocation.col] = matrix[rightLocation.row][rightLocation.col];
                        }
                        matrix[1][1] = 0;
                        break;
                    }
                }
            }
        }
    }

    static void step3() {
        List<List<Location>> sameMarbleLocationsList = new ArrayList<>();
        boolean[][] visited = new boolean[N + 1][N + 1];

        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            if (matrix[location.row][location.col] == 0) {
                break;
            }
            if (visited[location.row][location.col]) {
                continue;
            }

            List<Location> sameMarbleLocations = new ArrayList<>();
            Queue<Location> queue = new LinkedList<>() {{
                add(location);
            }};
            visited[location.row][location.col] = true;

            int j = i;
            while (!queue.isEmpty()) {
                Location currentLocation = queue.poll();
                sameMarbleLocations.add(currentLocation);

                if (j + 1 < locations.size()) {
                    Location nextLocation = locations.get(j + 1);
                    if (matrix[nextLocation.row][nextLocation.col] == matrix[location.row][location.col]) {
                        queue.add(nextLocation);
                        visited[nextLocation.row][nextLocation.col] = true;
                        j++;
                    }
                }
            }
            sameMarbleLocationsList.add(sameMarbleLocations);
        }

        List<Integer> newMarbles = new ArrayList<>();
        for (List<Location> sameMarbleLocations : sameMarbleLocationsList) {
            newMarbles.add(sameMarbleLocations.size());
            newMarbles.add(matrix[sameMarbleLocations.get(0).row][sameMarbleLocations.get(0).col]);
        }

        for (int i = 0; i < locations.size(); i++) {
            if (i >= newMarbles.size()) {
                for (int j = i; j < locations.size(); j++) {
                    Location location = locations.get(j);
                    matrix[location.row][location.col] = 0;
                }
                break;
            }

            Location location = locations.get(i);
            matrix[location.row][location.col] = newMarbles.get(i);
        }
    }

    static int rotate(int direction) {
        return (direction + 1) % 4;
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
