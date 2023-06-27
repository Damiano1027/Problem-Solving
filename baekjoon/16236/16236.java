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
    private static Location[] adjacentDirections = {
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
            for (int j = 0; j < N; j++) {
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
            eaten = bfs(babyShark);

            if (eaten) {
                eatenCount++;
            }
            if (eatenCount >= babyShark.size) {
                babyShark.size++;
                eatenCount = 0;
            }
        }
    }

    private static boolean bfs(Shark babyShark) {
        // 방문 배열 setting
        boolean[][] visited = new boolean[N][N];
        visited[babyShark.location.row][babyShark.location.col] = true;

        // queue setting
        Queue<BFSInformation> queue = new LinkedList<>();
        queue.add(new BFSInformation(new Location(babyShark.location.row, babyShark.location.col), 0));

        // 먹을 수 있는 물고기들에 대한 bfs 정보 list
        List<BFSInformation> eatableFishInformations = new ArrayList<>();

        while (!queue.isEmpty()) {
            BFSInformation currentBFSInformation = queue.poll();

            for (Location adjoinDirection : adjacentDirections) {
                Location adjoinLocation = new Location(currentBFSInformation.location.row + adjoinDirection.row, currentBFSInformation.location.col + adjoinDirection.col);

                // 이동이 불가능하거나 이미 방문한 위치라면 pass
                if (!canMoveTo(adjoinLocation, babyShark.size) || visited[adjoinLocation.row][adjoinLocation.col]) {
                    continue;
                }

                // 지금부턴 이동이 가능한 경우임.

                visited[adjoinLocation.row][adjoinLocation.col] = true;

                // 먹을 수 있는 물고기가 위치한 곳이라면 list에 추가
                if (canEat(adjoinLocation, babyShark.size)) {
                    eatableFishInformations.add(new BFSInformation(new Location(adjoinLocation.row, adjoinLocation.col), currentBFSInformation.distance + 1));
                } else {
                    queue.add(new BFSInformation(new Location(adjoinLocation.row, adjoinLocation.col), currentBFSInformation.distance + 1));
                }
            }
        }

        // 먹을 수 있는 물고기가 없으면 없다고 알림
        if (eatableFishInformations.isEmpty()) {
            return false;
        }

        // 아래에서부턴 먹을 수 있는 물고기가 존재하는 경우임

        // 가장 거리가 가깝고, 가장 위에 있고, 가장 왼쪽에 있는 순으로 bfs 정보 추출
        eatableFishInformations.sort(BFSInformation::compareTo);
        BFSInformation nextBabySharkInformation = eatableFishInformations.get(0);

        // 최신화
        matrix[babyShark.location.row][babyShark.location.col] = 0;
        matrix[nextBabySharkInformation.location.row][nextBabySharkInformation.location.col] = 9;
        babyShark.location.row = nextBabySharkInformation.location.row;
        babyShark.location.col = nextBabySharkInformation.location.col;

        // 시간(거리) 누적
        result += nextBabySharkInformation.distance;

        // 먹을 수 있는 물고기가 있었다고 알림
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
        private int row;
        private int col;

        public Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static class BFSInformation {
        private Location location;
        private int distance;

        public BFSInformation(Location location, int distance) {
            this.location = location;
            this.distance = distance;
        }

        private int compareTo(BFSInformation bfsInformation) {
            if (this.distance != bfsInformation.distance) {
                return this.distance - bfsInformation.distance;
            } else if (this.location.row != bfsInformation.location.row) {
                return this.location.row - bfsInformation.location.row;
            } else {
                return this.location.col - bfsInformation.location.col;
            }
        }
    }

    private static class Shark {
        private Location location;
        private int size;

        public Shark(Location location, int size) {
            this.location = location;
            this.size = size;
        }
    }
}
