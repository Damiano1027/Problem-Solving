import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N, M;
    static int[][] tree;
    static int[][] childCount;
    static StringTokenizer tokenizer;
    static List<Node>[] levelNodes;
    static int levelResult, breathResult;
    static int[] indegree;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        tree = new int[N + 1][2];
        childCount = new int[N + 1][2];
        levelNodes = new List[N + 1];
        indegree = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            levelNodes[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int parent = Integer.parseInt(tokenizer.nextToken());
            int leftChild = Integer.parseInt(tokenizer.nextToken());
            int rightChild = Integer.parseInt(tokenizer.nextToken());

            tree[parent][0] = leftChild;
            tree[parent][1] = rightChild;
            if (leftChild != -1) {
                indegree[leftChild]++;
            }
            if (rightChild != -1) {
                indegree[rightChild]++;
            }
        }

        solve();

        writer.write(String.format("%d %d\n", levelResult, breathResult));
        writer.flush();
        writer.close();
    }

    static void solve() {
        int rootNumber = 0;
        for (int i = 1; i <= N; i++) {
            if (indegree[i] == 0) {
                rootNumber = i;
                break;
            }
        }

        levelResult = 1;
        breathResult = 1;

        if (N == 1) {
            return;
        }

        dfs(rootNumber);
        bfs(rootNumber);

        for (int i = 2; i <= N; i++) {
            List<Node> currentLevelNodes = levelNodes[i];

            if (currentLevelNodes.isEmpty()) {
                continue;
            } else if (currentLevelNodes.size() == 1) {
                if (1 > breathResult) {
                    breathResult = 1;
                    levelResult = i;
                }
            } else {
                int leftCol = levelNodes[i].get(0).col;
                int rightCol = levelNodes[i].get(levelNodes[i].size() - 1).col;
                int breadth = rightCol - leftCol + 1;

                if (breadth > breathResult) {
                    breathResult = breadth;
                    levelResult = i;
                }
            }
        }
    }

    // 자식 개수 세팅
    static int dfs(int currentNode) {
        if (tree[currentNode][0] == -1 && tree[currentNode][1] == -1) {
            return 1;
        }

        int leftChildNumber = tree[currentNode][0];
        int rightChildNumber = tree[currentNode][1];
        if (leftChildNumber != -1) {
            childCount[currentNode][0] = dfs(leftChildNumber);
        }
        if (rightChildNumber != -1) {
            childCount[currentNode][1] = dfs(rightChildNumber);
        }

        return childCount[currentNode][0] + childCount[currentNode][1] + 1;
    }

    static void bfs(int rootNumber) {
        Queue<Node> queue = new LinkedList<>() {{
            add(new Node(rootNumber, 1, childCount[rootNumber][0] + 1));
        }};

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            levelNodes[currentNode.row].add(currentNode);

            int leftNumber = tree[currentNode.number][0];
            int rightNumber = tree[currentNode.number][1];
            if (leftNumber != -1) {
                queue.add(new Node(leftNumber, currentNode.row + 1, currentNode.col - childCount[leftNumber][1] - 1));
            }
            if (rightNumber != -1) {
                queue.add(new Node(rightNumber, currentNode.row + 1, currentNode.col + childCount[rightNumber][0] + 1));
            }
        }
    }
}

class Node {
    int number, row, col;
    Node(int number, int row, int col) {
        this.number = number;
        this.row = row;
        this.col = col;
    }
}
