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
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N][N];

        for (int row = 0; row < N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < N; col++) {
                matrix[row][col] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        while (true) {
            Block block = step1();
            if (block == null) {
                break;
            }

            step2(block);
            step3();
            step4();
            step3();
        }
    }

    static Block step1() {
        List<Block> blocks = new ArrayList<>();

        Set<Location> visited = new HashSet<>();
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (matrix[row][col] > 0 && matrix[row][col] != Integer.MAX_VALUE && !visited.contains(new Location(row, col))) {
                    Block block = bfs(row, col, visited);
                    if (block != null) {
                        blocks.add(block);
                    }
                }
            }
        }

        if (blocks.isEmpty()) {
            return null;
        } else if (blocks.size() == 1) {
            return blocks.get(0);
        } else {
            blocks.sort((o1, o2) -> {
                if (o1.locations.size() != o2.locations.size()) {
                    return o1.locations.size() - o2.locations.size();
                }
                if (o1.rainbowLocationCount != o2.rainbowLocationCount) {
                    return o1.rainbowLocationCount - o2.rainbowLocationCount;
                }
                if (o1.rootLocation.row != o2.rootLocation.row) {
                    return o1.rootLocation.row - o2.rootLocation.row;
                }
                return o1.rootLocation.col - o2.rootLocation.col;
            });
            return blocks.get(blocks.size() - 1);
        }
    }

    static void step2(Block block) {
        for (Location location : block.locations) {
            matrix[location.row][location.col] = Integer.MAX_VALUE;
        }

        result += (int) Math.pow(block.locations.size(), 2);
    }

    static void step3() {
        if (N == 1) {
            return;
        }

        for (int row = N - 2; row >= 0; row--) {
            for (int col = 0; col < N; col++) {
                if (matrix[row][col] != Integer.MAX_VALUE && matrix[row][col] != -1) {
                    int nextRow = row;
                    for (int r = row + 1; r < N; r++) {
                        if (matrix[r][col] != Integer.MAX_VALUE) {
                            break;
                        }
                        nextRow = r;
                    }

                    int temp = matrix[row][col];
                    matrix[row][col] = Integer.MAX_VALUE;
                    matrix[nextRow][col] = temp;
                }
            }
        }
    }

    static void step4() {
        int[][] copiedMatrix = copyMatrix();
        int r = 0, c = N - 1;

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                matrix[row][col] = copiedMatrix[r++][c];
            }
            if (r >= N) {
                r = 0;
                c--;
            }
        }
    }

    static Block bfs(int startRow, int startCol, Set<Location> visited) {
        /*
             블록 성립 조건
             - 일반 블록(1 이상의 수)이 적어도 1개 존재 (O) -> 탐색시 확인
             - 일반 블록의 색은 모두 같아야 함 (O) -> 탐색시 확인
             - 검은색 블록(-1)은 포함되면 안됨 (O) -> 탐색시 확인
             - 무지개 블록은 얼마나 들어있든 노상관 (O) -> 탐색시 확인
             - 그룹에 속한 블록의 개수는 2개 이상 (O)
             - 기준 블록: 무지개 블록이 아닌 블록 중에서 행의 번호가 가장 작은 블록, 그러한 블록이 여러개면 열의 번호가 가장 작은 블록
         */
        List<Location> locations = new ArrayList<>();
        int color = matrix[startRow][startCol];

        Queue<Location> queue = new LinkedList<>() {{
            add(new Location(startRow, startCol));
        }};
        visited.add(new Location(startRow, startCol));

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            locations.add(currentLocation);

            for (int i = 0; i < 4; i++) {
                int aRow = currentLocation.row + dRow[i];
                int aCol = currentLocation.col + dCol[i];

                if (isInMatrix(aRow, aCol) && (matrix[aRow][aCol] == color || matrix[aRow][aCol] == 0) && !visited.contains(new Location(aRow, aCol))) {
                    Location aLocation = new Location(aRow, aCol);
                    queue.add(aLocation);
                    visited.add(aLocation);
                }
            }
        }

        if (locations.size() < 2) {
            if (locations.size() == 1) {
                visited.remove(locations.get(0));
            }
            return null;
        }

        List<Location> nonRainbowLocations = new ArrayList<>();
        for (Location location : locations) {
            if (matrix[location.row][location.col] > 0) {
                nonRainbowLocations.add(location);
            }
        }

        nonRainbowLocations.sort((o1, o2) -> {
            if (o1.row != o2.row) {
                return o1.row - o2.row;
            }
            return o1.col - o2.col;
        });

        return new Block(locations, nonRainbowLocations.get(0), locations.size() - nonRainbowLocations.size());
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    static int[][] copyMatrix() {
        int[][] copiedMatrix = new int[N][N];

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                copiedMatrix[row][col] = matrix[row][col];
            }
        }

        return copiedMatrix;
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

class Block {
    List<Location> locations;
    Location rootLocation;
    int rainbowLocationCount;
    Block(List<Location> locations, Location rootLocation, int rainbowLocationCount) {
        this.locations = locations;
        this.rootLocation = rootLocation;
        this.rainbowLocationCount = rainbowLocationCount;
    }
}
