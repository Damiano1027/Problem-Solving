import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static List<Node>[] tree;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        tree = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int distance = Integer.parseInt(tokenizer.nextToken());

            tree[a].add(new Node(b, distance));
            tree[b].add(new Node(a, distance));
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int startNumber = Integer.parseInt(tokenizer.nextToken());
            int endNumber = Integer.parseInt(tokenizer.nextToken());

            writer.write(bfs(startNumber, endNumber) + "\n");
        }
        writer.flush();
        writer.close();
    }

    static int bfs(int startNumber, int endNumber) {
        Queue<Bfs> queue = new LinkedList<>() {{
            add(new Bfs(startNumber, -1, 0));
        }};
        boolean[] visited = new boolean[N + 1];
        visited[startNumber] = true;

        int distance = 0;
        while (!queue.isEmpty()) {
            Bfs current = queue.poll();

            if (current.number == endNumber) {
                distance = current.distance;
                break;
            }

            for (Node nextNode : tree[current.number]) {
                if (nextNode.number == current.prevNumber) {
                    continue;
                }

                if (!visited[nextNode.number]) {
                    queue.add(new Bfs(nextNode.number, current.number, current.distance + nextNode.distance));
                    visited[nextNode.number] = true;
                }
            }
        }

        return distance;
    }
}

class Node {
    int number, distance;
    Node(int number, int distance) {
        this.number = number;
        this.distance = distance;
    }
}

class Bfs {
    int number, prevNumber, distance;
    Bfs(int number, int prevNumber, int distance) {
        this.number = number;
        this.prevNumber = prevNumber;
        this.distance = distance;
    }
}
