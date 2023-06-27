import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    private static int N;
    private static int[][] matrix;
    private static Location[] adjoinCriteria = {
            new Location(-1, 0),
            new Location(0, -1),
            new Location(1, 0),
            new Location(0, 1)
    };
    private static int result;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(reader.readLine());
        matrix = new int[N][N];

        for (int i = 0; i < N; i++) {
            matrix[i] = toIntArray(reader.readLine().split(" "));
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    private static int[] toIntArray(String[] strings) {
        int[] arr = new int[strings.length];

        for (int i = 0; i < strings.length; i++) {
            arr[i] = Integer.parseInt(strings[i]);
        }

        return arr;
    }

    /*
       요구사항

        [이동]
        - 자신의 크기보다 큰 물고기가 있는 칸은 이동 불가, 그 이외에는 이동 가능

        [먹기]
        - 자신의 크기보다 작은 물고기만 먹을 수 있음
        - 자신의 크기와 같은 수의 물고기를 먹을때마다 크기가 1 증가

        [먹을 물고기 선정 방법]
        - 먹을 수 있는 물고기가 공간에 없다면 엄마 상어에게 도움을 요청
        - 먹을 수 있는 물고기가 1마리라면 그 물고기를 먹으러 감
        - 먹을 수 있는 물고기가 2마리 이상이라면 가장 가까운 물고기를 먹으러 감
           - 가까움의 기준
             - 지나가야 하는 칸의 개수
             - 가장 가까운 물고기가 여러개라면
               - 가장 위에 있는 물고기 우선
               - 그 다음 가장 왼쪽에 있는 물고기 우선
     */

    private static void solve() {
        Location babySharkLocation = null;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N ; j++) {
                if (matrix[i][j] == 9) {
                    babySharkLocation = new Location(i, j);
                    break;
                }
            }
        }

        Shark babyShark = new Shark(babySharkLocation, 2);

        boolean eaten = true;
        int eatenCount = 0;
        while (eaten) {
            eaten = bfs(babyShark, babyShark.size);

            if (eaten) {
                eatenCount++;
            }
            if (eatenCount >= babyShark.size) {
                babyShark.size++;
                eatenCount = 0;
            }
        }
    }

    private static boolean bfs(Shark babyShark, int babySharkSize) {
        boolean[][] visited = new boolean[N][N];
        visited[babyShark.location.row][babyShark.location.col] = true;
        Queue<Information> queue = new LinkedList<>();
        queue.add(new Information(new Location(babyShark.location.row, babyShark.location.col), 0));

        List<Information> eatableInformations = new ArrayList<>();

        while (!queue.isEmpty()) {
            Information currentInformation = queue.poll();

            for (Location adjoinCriterion : adjoinCriteria) {
                Location adjoinLocation = new Location(currentInformation.location.row + adjoinCriterion.row, currentInformation.location.col + adjoinCriterion.col);

                // 이동 가능하고 방문하지 않는 경우만 고려
                if (!canMoveTo(adjoinLocation, babySharkSize) || visited[adjoinLocation.row][adjoinLocation.col]) {
                    continue;
                }

                // 먹을 수 있다면
                if (canEat(adjoinLocation, babySharkSize)) {
                    visited[adjoinLocation.row][adjoinLocation.col] = true;
                    eatableInformations.add(new Information(new Location(adjoinLocation.row, adjoinLocation.col), currentInformation.distance + 1));
                } else {
                    visited[adjoinLocation.row][adjoinLocation.col] = true;
                    queue.add(new Information(new Location(adjoinLocation.row, adjoinLocation.col), currentInformation.distance + 1));
                }
            }
        }

        if (eatableInformations.isEmpty()) {
            return false;
        }

        eatableInformations.sort(Information::compareTo);
        Information nextBabySharkInformation = eatableInformations.get(0);
        matrix[babyShark.location.row][babyShark.location.col] = 0;
        matrix[nextBabySharkInformation.location.row][nextBabySharkInformation.location.col] = 9;
        babyShark.location.row = nextBabySharkInformation.location.row;
        babyShark.location.col = nextBabySharkInformation.location.col;
        result += nextBabySharkInformation.distance;

        return true;
    }

    private static boolean canMoveTo(Location location, int babySharkSize) {
        if (location.row < 0 || location.row >= N || location.col < 0 || location.col >= N) {
            return false;
        }

        return matrix[location.row][location.col] <= babySharkSize;
    }

    private static boolean canEat(Location location, int babySharkSize) {
        if (matrix[location.row][location.col] == 0) {
            return false;
        }

        return matrix[location.row][location.col] < babySharkSize;
    }

    private static class Location {
        int row;
        int col;

        public Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static class Information {
        Location location;
        int distance;

        public Information(Location location, int distance) {
            this.location = location;
            this.distance = distance;
        }

        public int compareTo(Information information) {
            if (this.distance != information.distance) {
                return this.distance - information.distance;
            } else if (this.location.row != information.location.row) {
                return this.location.row - information.location.row;
            } else {
                return this.location.col - information.location.col;
            }
        }
    }

    private static class Shark {
        Location location;
        int size;

        public Shark(Location location, int size) {
            this.location = location;
            this.size = size;
        }
    }
}
