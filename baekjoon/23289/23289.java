import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int R, C, K, W;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static class Heater {
        int row, col, direction; // direction -> 북: 0, 서: 1, 남: 2, 동: 3
        Heater(int row, int col, int direction) {
            this.row = row;
            this.col = col;
            this.direction = direction;
        }
    }
    static class Location {
        int row, col;
        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
        Location north() {
            return new Location(row - 1, col);
        }
        Location west() {
            return new Location(row, col - 1);
        }
        Location south() {
            return new Location(row + 1, col);
        }
        Location east() {
            return new Location(row, col + 1);
        }
    }
    static class Wall {
        Location location1, location2;
        Wall(Location location1, Location location2) {
            this.location1 = location1;
            this.location2 = location2;
        }
        public boolean equals(Object o) {
            Wall wall = (Wall) o;
            return location1.row == wall.location1.row && location1.col == wall.location1.col
                    && location2.row == wall.location2.row && location2.col == wall.location2.col;
        }
        public int hashCode() {
            return Objects.hash(location1.row, location1.col, location2.row, location2.col);
        }
        public String toString() {
            return String.format("(%d %d | %d %d)", location1.row, location1.col, location2.row, location2.col);
        }
    }
    static class Temperature {
        int row, col, temperature;
        Temperature(int row, int col, int temperature) {
            this.row = row;
            this.col = col;
            this.temperature = temperature;
        }
    }
    static List<Heater> heaters = new ArrayList<>();
    static List<Location> checkingLocations = new ArrayList<>();
    static Set<Wall> walls = new HashSet<>();
    static int[][] staticMatrix;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        R = Integer.parseInt(tokenizer.nextToken());
        C = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        staticMatrix = new int[R][C];

        for (int row = 0; row < R; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < C; col++) {
                int value = Integer.parseInt(tokenizer.nextToken());

                if (value == 1) {
                    heaters.add(new Heater(row, col, 3));
                } else if (value == 2) {
                    heaters.add(new Heater(row, col, 1));
                } else if (value == 3) {
                    heaters.add(new Heater(row, col, 0));
                } else if (value == 4) {
                    heaters.add(new Heater(row, col, 2));
                } else if (value == 5) {
                    checkingLocations.add(new Location(row, col));
                }
            }
        }

        W = Integer.parseInt(reader.readLine());

        for (int i = 0; i < W; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());
            int t = Integer.parseInt(tokenizer.nextToken());

            if (t == 0) {
                walls.add(new Wall(new Location(x - 1, y - 1), new Location(x - 2, y - 1)));
                walls.add(new Wall(new Location(x - 2, y - 1), new Location(x - 1, y - 1)));
            } else {
                walls.add(new Wall(new Location(x - 1, y - 1), new Location(x - 1, y)));
                walls.add(new Wall(new Location(x - 1, y), new Location(x - 1, y - 1)));
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        do {
            blowWind();
            adjustTemperatureAdjacentLocations();
            reduceTemperatureOfEdge();

            //print(staticMatrix);
            result++;
        } while (!isEnd() && result < 101);
    }

    /*
         집에 있는 온풍기에서 바람이 한번 나오고 온도가 조절된다.

         바람의 이동에 의한 온도 상승
           - 온풍기에서 바람이 나오는 방향에 있는 인접한 칸은 온도가 5도 올라간다.
           - 그 방향으로 바람이 한칸 더 가서 3개의 칸도 온도가 k-1만큼 올라간다.
             - 해당 칸이 없으면 바람이 이동하지 않는다.
             - 바람이 해당 칸으로 여러번 도착해도 온도는 한번만 상승한다.
             - 벽이 있다면 바람이 이동할 수 없다.
           - 온풍기가 2개 이상 있다면 각각의 온풍기에 의해서 상승한 온도를 모두 합한다.
             - 이때문에 온풍기가 있는 위치도 온도가 상승할 수 있다.
     */
    static void blowWind() {
        for (Heater heater: heaters) {
            blowWind(heater);
        }
    }

    static void blowWind(Heater heater) {
        int[][] matrix = new int[R][C];

        int startRow = heater.row + dRow[heater.direction];
        int startCol = heater.col + dCol[heater.direction];

        if (!isInMatrix(startRow, startCol)) {
            return;
        }

        Queue<Temperature> queue = new LinkedList<>() {{
            add(new Temperature(startRow, startCol, 5));
        }};
        boolean[][] visited = new boolean[R][C];
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            Temperature current = queue.poll();
            matrix[current.row][current.col] = current.temperature;

            /*
                 다음 과정을 진행할 수 없는 경우
                 - 현재 위치의 상승온도가 1 이하
                 - 해당 칸이 없는 경우 (범위를 벗어난 경우)
                 - 벽이 있는 경우
                 - 이미 방문한 경우
             */

            if (current.temperature <= 1) {
                continue;
            }

            int aRow = current.row + dRow[heater.direction];
            int aCol = current.col + dCol[heater.direction];

            // 히터의 방향이 북쪽
            if (heater.direction == 0) {
                for (int col = aCol - 1; col <= aCol + 1; col++) {
                    int nextRow = aRow;
                    int nextCol = col;

                    if (!isInMatrix(nextRow, nextCol)
                            || visited[nextRow][nextCol]
                            || isBlockedByWall(new Location(current.row, current.col), new Location(nextRow, nextCol), heater.direction)) {
                        continue;
                    }
                    queue.add(new Temperature(nextRow, nextCol, current.temperature - 1));
                    visited[nextRow][nextCol] = true;
                }
            }
            // 히터의 방향이 서쪽
            else if (heater.direction == 1) {
                for (int row = aRow + 1; row >= aRow - 1; row--) {
                    int nextRow = row;
                    int nextCol = aCol;

                    if (!isInMatrix(nextRow, nextCol)
                            || visited[nextRow][nextCol]
                            || isBlockedByWall(new Location(current.row, current.col), new Location(nextRow, nextCol), heater.direction)) {
                        continue;
                    }
                    queue.add(new Temperature(nextRow, nextCol, current.temperature - 1));
                    visited[nextRow][nextCol] = true;
                }
            }
            // 히터의 방향이 남쪽
            else if (heater.direction == 2) {
                for (int col = aCol + 1; col >= aCol - 1; col--) {
                    int nextRow = aRow;
                    int nextCol = col;

                    if (!isInMatrix(nextRow, nextCol)
                            || visited[nextRow][nextCol]
                            || isBlockedByWall(new Location(current.row, current.col), new Location(nextRow, nextCol), heater.direction)) {
                        continue;
                    }
                    queue.add(new Temperature(nextRow, nextCol, current.temperature - 1));
                    visited[nextRow][nextCol] = true;
                }
            }
            // 히터의 방향이 동쪽
            else if (heater.direction == 3) {
                for (int row = aRow - 1; row <= aRow + 1; row++) {
                    int nextRow = row;
                    int nextCol = aCol;

                    if (!isInMatrix(nextRow, nextCol)
                            || visited[nextRow][nextCol]
                            || isBlockedByWall(new Location(current.row, current.col), new Location(nextRow, nextCol), heater.direction)) {
                        continue;
                    }
                    queue.add(new Temperature(nextRow, nextCol, current.temperature - 1));
                    visited[nextRow][nextCol] = true;
                }
            }
        }

        for (int row = 0; row < R; row++) {
            for (int col = 0; col < C; col++) {
                staticMatrix[row][col] += matrix[row][col];
            }
        }

        //print(matrix);
    }

    /*
         인접 칸에 대한 온도 조절
           - 모든 인접한 칸에 대해 다음을 수행
             - 온도가 높은 칸에서 낮은 칸으로 (두 칸의 온도 차이 / 4)만큼 변경된다.
               - 높은 칸은 해당 값만큼 감소, 낮은 칸은 증가
             - 인접한 두 칸 사이에 벽이 있는 경우에는 온도가 조절되지 않음
             - 모든 칸에서 동시에 발생
     */
    static void adjustTemperatureAdjacentLocations() {
        List<Temperature> changedTemperatures = new ArrayList<>();
        Set<Wall> visited = new HashSet<>();

        for (int row = 0; row < R; row++) {
            for (int col = 0; col < C; col++) {
                for (int i = 0; i < 4; i++) {
                    int aRow = row + dRow[i];
                    int aCol = col + dCol[i];

                    Location currentLocation = new Location(row, col);
                    Location adjacentLocation = new Location(aRow, aCol);

                    if (visited.contains(new Wall(currentLocation, adjacentLocation))) {
                        continue;
                    }
                    if (!isInMatrix(aRow, aCol)) {
                        continue;
                    }
                    if (walls.contains(new Wall(new Location(row, col), new Location(aRow, aCol)))) {
                        continue;
                    }
                    if (staticMatrix[row][col] == staticMatrix[aRow][aCol]) {
                        continue;
                    }

                    int currentLocationValue = staticMatrix[row][col];
                    int adjacentLocationValue = staticMatrix[aRow][aCol];

                    int difference = Math.abs(currentLocationValue - adjacentLocationValue);

                    // matrix 복사 후 바로 계산 반영하는 것도 방법이다.
                    if (currentLocationValue > adjacentLocationValue) {
                        changedTemperatures.add(new Temperature(row, col, -(difference / 4)));
                        changedTemperatures.add(new Temperature(aRow, aCol, difference / 4));
                    } else {
                        changedTemperatures.add(new Temperature(row, col, difference / 4));
                        changedTemperatures.add(new Temperature(aRow, aCol, -(difference / 4)));
                    }

                    visited.add(new Wall(currentLocation, adjacentLocation));
                    visited.add(new Wall(adjacentLocation, currentLocation));
                }
            }
        }

        for (Temperature changedTemperature: changedTemperatures) {
            staticMatrix[changedTemperature.row][changedTemperature.col] += changedTemperature.temperature;
            if (staticMatrix[changedTemperature.row][changedTemperature.col] < 0) {
                staticMatrix[changedTemperature.row][changedTemperature.col] = 0;
            }
        }
    }

    /*
         가장 바깥쪽 칸중 온도가 1 이상인 칸은 온도가 1씩 감소
     */
    static void reduceTemperatureOfEdge() {
        int row = 0;
        int col = 0;
        for (; col < C; col++) {
            if (staticMatrix[row][col] >= 1) {
                staticMatrix[row][col]--;
            }
        }

        col--;
        row++;
        for (; row < R; row++) {
            if (staticMatrix[row][col] >= 1) {
                staticMatrix[row][col]--;
            }
        }

        row--;
        col--;
        for (; col >= 0; col--) {
            if (staticMatrix[row][col] >= 1) {
                staticMatrix[row][col]--;
            }
        }

        col++;
        row--;
        for (; row > 0; row--) {
            if (staticMatrix[row][col] >= 1) {
                staticMatrix[row][col]--;
            }
        }
    }

    static boolean isEnd() {
        for (Location location: checkingLocations) {
            if (staticMatrix[location.row][location.col] < K) {
                return false;
            }
        }
        return true;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < R && col >= 0 && col < C;
    }

    static boolean isBlockedByWall(Location location1, Location location2, int heaterDirection) {
        // 칸이 좌우로 붙어있는 경우
        if (location1.row == location2.row && Math.abs(location1.col - location2.col) == 1) {
            return walls.contains(new Wall(location1, location2));
        }
        // 칸이 위아래로 붙어있는 경우
        else if (location1.col == location2.col && Math.abs(location1.row - location2.row) == 1) {
            return walls.contains(new Wall(location1, location2));
        }
        // 칸이 대각선으로 붙어있는 경우
        else {
            // 왼쪽 위로 움직여야 하는 경우
            if (location1.row - 1 == location2.row && location1.col - 1 == location2.col) {
                if (heaterDirection == 0) {
                    return walls.contains(new Wall(location2, location2.south()))
                            || walls.contains(new Wall(location1, location1.west()));
                } else {
                    return walls.contains(new Wall(location2, location2.east()))
                            || walls.contains(new Wall(location1, location1.north()));
                }
            }
            // 오른쪽 위로 움직어야 하는 경우
            else if (location1.row - 1 == location2.row && location1.col + 1 == location2.col) {
                if (heaterDirection == 0) {
                    return walls.contains(new Wall(location2, location2.south()))
                            || walls.contains(new Wall(location1, location1.east()));
                } else {
                    return walls.contains(new Wall(location2, location2.west()))
                            || walls.contains(new Wall(location1, location1.north()));
                }
            }
            // 왼쪽 아래로 움직여야 하는 경우
            else if (location1.row + 1 == location2.row && location1.col - 1 == location2.col) {
                if (heaterDirection == 2) {
                    return walls.contains(new Wall(location2, location2.north()))
                            || walls.contains(new Wall(location1, location1.west()));
                } else {
                    return walls.contains(new Wall(location2, location2.east()))
                            || walls.contains(new Wall(location1, location1.south()));
                }
            }
            // 오른쪽 아래로 움직여야 하는 경우
            else {
                if (heaterDirection == 2) {
                    return walls.contains(new Wall(location2, location2.north()))
                            || walls.contains(new Wall(location1, location1.east()));
                } else {
                    return walls.contains(new Wall(location2, location2.west()))
                            || walls.contains(new Wall(location1, location1.south()));
                }
            }
        }
    }
}
