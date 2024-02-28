import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int[][] matrix;
    static boolean[][] visited;
    static int M, N;
    static int[][] cost;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        M = Integer.parseInt(tokenizer.nextToken());
        N = Integer.parseInt(tokenizer.nextToken());

        matrix = new int[N][M];
        cost = new int[N][M];
        visited = new boolean[N][M];

        for (int row = 0; row < N; row++) {
            String line = reader.readLine();
            for (int col = 0; col < M; col++) {
                matrix[row][col] = Character.getNumericValue(line.charAt(col));
            }
        }

        solve();

        writer.write(result + "\n");
        writer.flush();

        writer.close();
    }

    static void solve() {
        dijkstra();
        result = cost[N - 1][M - 1];
    }

    static void dijkstra() {
        for (int row = 0; row < N; row++) {
            Arrays.fill(cost[row], Integer.MAX_VALUE);
        }
        PriorityQueue<Node> queue = new PriorityQueue<>() {{
            add(new Node(0, 0, 0));
        }};
        cost[0][0] = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited[currentNode.row][currentNode.col]) {
                continue;
            }
            visited[currentNode.row][currentNode.col] = true;

            for (int i = 0; i < 4; i++) {
                int aRow = currentNode.row + dRow[i];
                int aCol = currentNode.col + dCol[i];

                if (!isInMatrix(aRow, aCol)) {
                    continue;
                }

                int newCost;
                if (matrix[aRow][aCol] == 0) {
                    newCost = cost[currentNode.row][currentNode.col];
                } else {
                    newCost = cost[currentNode.row][currentNode.col] + 1;
                }

                if (newCost < cost[aRow][aCol]) {
                    cost[aRow][aCol] = newCost;
                    queue.add(new Node(aRow, aCol, newCost));
                }
            }
        }
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }
}

class Node implements Comparable<Node> {
    int row, col, cost;
    Node(int row, int col, int cost) {
        this.row = row;
        this.col = col;
        this.cost = cost;
    }
    public int compareTo(Node node) {
        return cost - node.cost;
    }
}
