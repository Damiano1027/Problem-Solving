import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[][] matrix, cost;
    static boolean[][] visited;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        int count = 1;
        while (true) {
            N = Integer.parseInt(reader.readLine());
            if (N == 0) {
                break;
            }

            matrix = new int[N][N];
            cost = new int[N][N];
            visited = new boolean[N][N];
            for (int row = 0; row < N; row++) {
                tokenizer = new StringTokenizer(reader.readLine());
                for (int col = 0; col < N; col++) {
                    matrix[row][col] = Integer.parseInt(tokenizer.nextToken());
                }
            }

            writer.write(String.format("Problem %d: %d\n", count++, dijkstra()));
        }

        writer.flush();

        writer.close();
    }

    static int dijkstra() {
        for (int row = 0; row < N; row++) {
            Arrays.fill(cost[row], Integer.MAX_VALUE);
        }
        PriorityQueue<Node> queue = new PriorityQueue<>() {{
            add(new Node(0, 0, matrix[0][0]));
        }};
        cost[0][0] = matrix[0][0];

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

                int newCost = cost[currentNode.row][currentNode.col] + matrix[aRow][aCol];
                if (newCost < cost[aRow][aCol]) {
                    cost[aRow][aCol] = newCost;
                    queue.add(new Node(aRow, aCol, newCost));
                }
            }
        }

        return cost[N - 1][N - 1];
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
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
