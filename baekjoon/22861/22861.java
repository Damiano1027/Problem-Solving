import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, K, Q;
    static Map<String, Set<Node>> treeMap = new HashMap<>();

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
                treeMap.put(parent, new HashSet<>());
            }

            if (isFolder == 1) {
                treeMap.get(parent).add(new Node(child, true));
            } else {
                treeMap.get(parent).add(new Node(child, false));
            }
        }

        K = Integer.parseInt(reader.readLine());
        for (int i = 0; i < K; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            String A = tokenizer.nextToken();
            String B = tokenizer.nextToken();

            tokenizer = new StringTokenizer(A, "/");
            String aName = null;
            while (tokenizer.hasMoreTokens()) {
                aName = tokenizer.nextToken();
            }
            tokenizer = new StringTokenizer(B, "/");
            String bName = null;
            while (tokenizer.hasMoreTokens()) {
                bName = tokenizer.nextToken();
            }

            dfsForMove("main", ".", aName, bName);
        }

        Q = Integer.parseInt(reader.readLine());
        for (int i = 0; i < Q; i++) {
            tokenizer = new StringTokenizer(reader.readLine(), "/");
            String folder = null;
            while (tokenizer.hasMoreTokens()) {
                folder = tokenizer.nextToken();
            }

            Map<String, Integer> frequencyMap = new HashMap<>();
            bfsForFindAnswer(folder, frequencyMap);

            int count = 0;
            for (String name : frequencyMap.keySet()) {
                count += frequencyMap.get(name);
            }
            writer.write(String.format("%d %d\n", frequencyMap.size(), count));
        }

        writer.flush();
        writer.close();
    }

    static boolean dfsForMove(String currentName, String prevName, String aName, String bName) {
        if (currentName.equals(aName)) {
            if (treeMap.get(aName) != null) {
                for (Node nextNode : treeMap.get(aName)) {
                    if (treeMap.get(bName) == null) {
                        treeMap.put(bName, new HashSet<>());
                    }
                    treeMap.get(bName).add(nextNode);
                }
            }

            if (treeMap.get(prevName) != null) {
                treeMap.get(prevName).remove(new Node(currentName));
            }
            return true;
        }

        boolean detected = false;
        if (treeMap.get(currentName) != null) {
            for (Node nextNode : treeMap.get(currentName)) {
                if (dfsForMove(nextNode.name, currentName, aName, bName)) {
                    detected = true;
                    break;
                }
            }
        }

        return detected;
    }

    static void bfsForFindAnswer(String rootFolder, Map<String, Integer> frequencyMap) {
        Queue<Node> queue = new LinkedList<>() {{
            add(new Node(rootFolder, true));
        }};

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (!currentNode.isFolder) {
                frequencyMap.put(currentNode.name, frequencyMap.getOrDefault(currentNode.name, 0) + 1);
            }

            if (treeMap.get(currentNode.name) != null) {
                queue.addAll(treeMap.get(currentNode.name));
            }
        }
    }
}

class Node {
    String name;
    boolean isFolder;
    Node(String name) {
        this.name = name;
    }
    Node(String name, boolean isFolder) {
        this.name = name;
        this.isFolder = isFolder;
    }
    public boolean equals(Object o) {
        Node node = (Node) o;
        return name.equals(node.name);
    }
    public int hashCode() {
        return name.hashCode();
    }
}
