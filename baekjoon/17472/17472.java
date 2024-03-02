import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[][] matrix, numberedMatrix;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int[] uf;
    static PriorityQueue<Edge> edges = new PriorityQueue<>();
    static int islandCount;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N][M];
        numberedMatrix = new int[N][M];

        for (int row = 0; row < N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < M; col++) {
                matrix[row][col] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        attachIslandNumber();
        makeEdges();

        if (edges.size() < islandCount - 1) {
            result = -1;
            return;
        }
        kruskal();

        for (int islandNumber = 1; islandNumber < islandCount; islandNumber++) {
            if (find(islandNumber) != find(islandNumber + 1)) {
                result = -1;
                return;
            }
        }
    }

    static void attachIslandNumber() {
        boolean[][] visited = new boolean[N][M];
        int islandNumber = 1;

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                if (!visited[row][col] && matrix[row][col] == 1) {
                    bfs(islandNumber++, row, col, visited);
                }
            }
        }

        islandCount = islandNumber - 1;

        // union-find 배열 초기화
        uf = new int[islandNumber];
        for (int i = 1; i <= islandCount; i++) {
            uf[i] = i;
        }
    }

    static void bfs(int islandNumber, int row, int col, boolean[][] visited) {
        Queue<Location> queue = new LinkedList<>() {{
            add(new Location(row, col));
        }};
        visited[row][col] = true;

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            numberedMatrix[currentLocation.row][currentLocation.col] = islandNumber;

            for (int i = 0; i < 4; i++) {
                int aRow = currentLocation.row + dRow[i];
                int aCol = currentLocation.col + dCol[i];

                if (isInMatrix(aRow, aCol) && !visited[aRow][aCol] && matrix[aRow][aCol] == 1) {
                    queue.add(new Location(aRow, aCol));
                    visited[aRow][aCol] = true;
                }
            }
        }
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }

    static void makeEdges() {
        /*
             다리의 조건
             - 양 끝은 섬과 인접한 바다에 있어야 한다.
             - 가로 또는 세로만 가능하다.
             - 길이는 2 이상이어야 한다.
             - 가로이면 양 끝이 가로 방향으로 섬과 인접해야하고, 세로도 마찬가지다.
         */

        for (int row = 0; row < N; row++) {
            List<EdgeMaking> edgeMakings = new ArrayList<>();
            for (int col = 0; col < M; col++) {
                if (numberedMatrix[row][col] > 0) {
                    edgeMakings.add(new EdgeMaking(row, col, numberedMatrix[row][col]));
                }
            }

            for (int i = 0; i < edgeMakings.size() - 1; i++) {
                EdgeMaking left = edgeMakings.get(i);
                EdgeMaking right = edgeMakings.get(i + 1);

                if (left.islandNumber != right.islandNumber) {
                    int bridgeDistance = right.col - left.col - 1;
                    if (bridgeDistance >= 2) {
                        edges.add(new Edge(left.islandNumber, right.islandNumber, bridgeDistance));
                    }
                }
            }
        }

        for (int col = 0; col < M; col++) {
            List<EdgeMaking> edgeMakings = new ArrayList<>();
            for (int row = 0; row < N; row++) {
                if (numberedMatrix[row][col] > 0) {
                    edgeMakings.add(new EdgeMaking(row, col, numberedMatrix[row][col]));
                }
            }

            for (int i = 0; i < edgeMakings.size() - 1; i++) {
                EdgeMaking up = edgeMakings.get(i);
                EdgeMaking down = edgeMakings.get(i + 1);

                if (up.islandNumber != down.islandNumber) {
                    int bridgeDistance = down.row - up.row - 1;
                    if (bridgeDistance >= 2) {
                        edges.add(new Edge(up.islandNumber, down.islandNumber, bridgeDistance));
                    }
                }
            }
        }
    }

    static void kruskal() {
        for (int i = 0; i < islandCount - 1;) {
            Edge edge = edges.poll();
            if (edge == null) {
                break;
            }

            if (find(edge.islandNumber1) != find(edge.islandNumber2)) {
                union(edge.islandNumber1, edge.islandNumber2);
                result += edge.distance;
                i++;
            }
        }
    }

    static void union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        if (aRoot != bRoot) {
            uf[aRoot] = bRoot;
        }
    }

    static int find(int number) {
        if (uf[number] == number) {
            return number;
        }

        return uf[number] = find(uf[number]);
    }
}

class Location {
    int row, col;
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

class Edge implements Comparable<Edge> {
    int islandNumber1, islandNumber2, distance;
    Edge(int islandNumber1, int islandNumber2, int distance) {
        this.islandNumber1 = islandNumber1;
        this.islandNumber2 = islandNumber2;
        this.distance = distance;
    }
    public int compareTo(Edge edge) {
        return distance - edge.distance;
    }
}

class EdgeMaking {
    int row, col, islandNumber;
    EdgeMaking(int row, int col, int islandNumber) {
        this.row = row;
        this.col = col;
        this.islandNumber = islandNumber;
    }
}
