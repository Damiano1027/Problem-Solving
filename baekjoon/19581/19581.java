import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static List<Node>[] tree;
    static int result;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        tree = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            tokenizer = new StringTokenizer(reader.readLine());

            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int cost = Integer.parseInt(tokenizer.nextToken());

            tree[a].add(new Node(b, cost));
            tree[b].add(new Node(a, cost));
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        maxCost = Integer.MIN_VALUE;
        searchNode1(1);

        maxCost = Integer.MIN_VALUE;
        searchNode2(node1);
        treeLength = maxCost;

        resultCandidate1 = Integer.MIN_VALUE;
        bfs1();
        resultCandidate2 = Integer.MIN_VALUE;
        bfs2();
        result = Math.max(resultCandidate1, resultCandidate2);
    }

    static int maxCost = Integer.MIN_VALUE;
    static int node1, node2, treeLength;
    static int resultCandidate1, resultCandidate2;
    static void searchNode1(int startNumber) {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(startNumber, -1, 0));
        }};

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (tree[current.number].size() == 1 && tree[current.number].get(0).number == current.prevNumber) {
                if (current.totalCost > maxCost) {
                    maxCost = current.totalCost;
                    node1 = current.number;
                }
                continue;
            }

            for (Node nextNode : tree[current.number]) {
                if (nextNode.number == current.prevNumber) {
                    continue;
                }

                queue.add(new Bfs(nextNode.number, current.number, current.totalCost + nextNode.cost));
            }
        }
    }

    static void searchNode2(int startNumber) {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(startNumber, -1, 0));
        }};

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (tree[current.number].size() == 1 && tree[current.number].get(0).number == current.prevNumber) {
                if (current.totalCost > maxCost) {
                    maxCost = current.totalCost;
                    node2 = current.number;
                }
                continue;
            }

            for (Node nextNode : tree[current.number]) {
                if (nextNode.number == current.prevNumber) {
                    continue;
                }

                queue.add(new Bfs(nextNode.number, current.number, current.totalCost + nextNode.cost));
            }
        }
    }

    static void bfs1() {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(node1, -1, 0));
        }};

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.number != node2 && current.totalCost <= treeLength && current.totalCost > resultCandidate1) {
                resultCandidate1 = current.totalCost;
            }

            if (tree[current.number].size() == 1 && tree[current.number].get(0).number == current.prevNumber) {
                continue;
            }

            for (Node nextNode : tree[current.number]) {
                if (nextNode.number == current.prevNumber) {
                    continue;
                }

                queue.add(new Bfs(nextNode.number, current.number, current.totalCost + nextNode.cost));
            }
        }
    }

    static void bfs2() {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(node2, -1, 0));
        }};

        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.number != node1 && current.totalCost <= treeLength && current.totalCost > resultCandidate2) {
                resultCandidate2 = current.totalCost;
            }

            if (tree[current.number].size() == 1 && tree[current.number].get(0).number == current.prevNumber) {
                continue;
            }

            for (Node nextNode : tree[current.number]) {
                if (nextNode.number == current.prevNumber) {
                    continue;
                }

                queue.add(new Bfs(nextNode.number, current.number, current.totalCost + nextNode.cost));
            }
        }
    }
}

class Node {
    int number, cost;
    Node(int number, int cost) {
        this.number = number;
        this.cost = cost;
    }
}

class Bfs {
    int number, prevNumber, totalCost;
    Bfs(int number, int prevNumber, int totalCost) {
        this.number = number;
        this.prevNumber = prevNumber;
        this.totalCost = totalCost;
    }
}
