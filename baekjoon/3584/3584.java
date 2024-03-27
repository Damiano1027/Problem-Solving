import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int T;
    static int[] parentNodes;
    static Set<Integer> parentNodeSet;

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(reader.readLine());

        for (int i = 0; i < T; i++) {
            int N = Integer.parseInt(reader.readLine());
            parentNodes = new int[N + 1];
            parentNodeSet = new HashSet<>();

            for (int j = 0; j < N - 1; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int A = Integer.parseInt(tokenizer.nextToken());
                int B = Integer.parseInt(tokenizer.nextToken());

                parentNodes[B] = A;
            }

            tokenizer = new StringTokenizer(reader.readLine());
            int node1 = Integer.parseInt(tokenizer.nextToken());
            int node2 = Integer.parseInt(tokenizer.nextToken());

            setParents(node1);

            writer.write(findCommonParent(node2) + "\n");
        }

        writer.flush();
        writer.close();
    }

    static void setParents(int currentNode) {
        parentNodeSet.add(currentNode);

        if (parentNodes[currentNode] != 0) {
            setParents(parentNodes[currentNode]);
        }
    }

    static int findCommonParent(int currentNode) {
        if (parentNodeSet.contains(currentNode)) {
            return currentNode;
        }

        return findCommonParent(parentNodes[currentNode]);
    }
}
