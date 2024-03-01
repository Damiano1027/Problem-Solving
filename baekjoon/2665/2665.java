import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int n;
    static int[][] matrix, cost;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(reader.readLine());
        matrix = new int[n][n];
        cost = new int[n][n];

        for (int row = 0; row < n; row++) {
            String line = reader.readLine();
            for (int col = 0; col < n; col++) {
                matrix[row][col] = Character.getNumericValue(line.charAt(col));
            }
        }

        dijkstra();

        writer.write(cost[n - 1][n - 1] + "\n");
        writer.flush();

        writer.close();
    }

    static void dijkstra() {
        boolean[][] visited = new boolean[n][n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                cost[row][col] = Integer.MAX_VALUE;
            }
        }
        PriorityQueue<Node> pq = new PriorityQueue<>() {{
            add(new Node(0, 0, 0));
        }};
        cost[0][0] = 0;

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();

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
                    newCost = cost[currentNode.row][currentNode.col] + 1;
                } else {
                    newCost = cost[currentNode.row][currentNode.col];
                }

                if (newCost < cost[aRow][aCol]) {
                    cost[aRow][aCol] = newCost;
                    pq.add(new Node(aRow, aCol, newCost));
                }
            }
        }
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n;
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
