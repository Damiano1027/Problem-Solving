import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, Q;
    static Map<String, List<Node>> treeMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        for (int i = 0; i < N + M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            String parent = tokenizer.nextToken();
            String child = tokenizer.nextToken();
            int isFolder = Integer.parseInt(tokenizer.nextToken());

            if (treeMap.get(parent) == null) {
                treeMap.put(parent, new ArrayList<>());
            }

            if (isFolder == 1) {
                treeMap.get(parent).add(new Node(child, true));
            } else {
                treeMap.get(parent).add(new Node(child, false));
            }
        }

        Q = Integer.parseInt(reader.readLine());
        for (int i = 0; i < Q; i++) {
            tokenizer = new StringTokenizer(reader.readLine(), "/");
            String name = null;
            while (tokenizer.hasMoreTokens()) {
                name = tokenizer.nextToken();
            }

            Map<String, Integer> frequencyMap = new HashMap<>();
            dfs(new Node(name, true), frequencyMap);

            writer.write(String.format("%d ", frequencyMap.size()));
            int fileCount = 0;
            for (String n : frequencyMap.keySet()) {
                fileCount += frequencyMap.get(n);
            }
            writer.write(String.format("%d\n", fileCount));
        }

        writer.flush();
        writer.close();
    }

    static void dfs(Node currentNode, Map<String, Integer> frequencyMap) {
        if (!currentNode.isFolder) {
            frequencyMap.put(currentNode.name, frequencyMap.getOrDefault(currentNode.name, 0) + 1);
        }

        if (treeMap.get(currentNode.name) != null) {
            for (Node nextNode : treeMap.get(currentNode.name)) {
                dfs(nextNode, frequencyMap);
            }
        }
    }
}

class Node {
    String name;
    boolean isFolder;
    Node(String name, boolean isFolder) {
        this.name = name;
        this.isFolder = isFolder;
    }
}
