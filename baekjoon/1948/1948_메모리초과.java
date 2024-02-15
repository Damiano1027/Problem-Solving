import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int n, m;
    static List<Edge>[] graph;
    static class Edge {
        int from, to;
        int time;
        Edge(int from, int to, int time) {
            this.from = from;
            this.to = to;
            this.time = time;
        }
        public boolean equals(Object o) {
            Edge edge = (Edge) o;
            return from == edge.from && to == edge.to;
        }
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }
    static int startNode, endNode;
    static class Edges {
        List<Edge> edgeList;
        int totalTime;
        Edges(List<Edge> edgeList, int totalTime) {
            this.edgeList = edgeList;
            this.totalTime = totalTime;
        }
    }
    static List<Edges> edgesList = new ArrayList<>();
    static int result1, result2;

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(reader.readLine());
        m = Integer.parseInt(reader.readLine());
        graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(tokenizer.nextToken());
            int to = Integer.parseInt(tokenizer.nextToken());
            int time = Integer.parseInt(tokenizer.nextToken());

            graph[from].add(new Edge(from, to, time));
        }

        tokenizer = new StringTokenizer(reader.readLine());
        startNode = Integer.parseInt(tokenizer.nextToken());
        endNode = Integer.parseInt(tokenizer.nextToken());

        solve();

        writer.write(String.format("%d\n%d\n", result1, result2));
        writer.flush();

        writer.close();
    }

    static void solve() {
        dfs(startNode, new ArrayList<>(), 0);
        edgesList.sort(Comparator.comparingInt(o -> o.totalTime));
        int maxTotalTime = edgesList.get(edgesList.size() - 1).totalTime;
        Set<Edge> noRestEdges = new HashSet<>();

        for (int i = edgesList.size() - 1; i >= 0; i--) {
            Edges edges = edgesList.get(i);
            if (edges.totalTime != maxTotalTime) {
                break;
            }

            noRestEdges.addAll(edges.edgeList);
        }

        result1 = maxTotalTime;
        result2 = noRestEdges.size();
    }

    static void dfs(int currentNode, List<Edge> edges, int totalTime) {
        if (currentNode == endNode) {
            edgesList.add(new Edges(new ArrayList<>(edges), totalTime));
            return;
        }

        for (Edge nextEdge: graph[currentNode]) {
            edges.add(nextEdge);
            dfs(nextEdge.to, edges, totalTime + nextEdge.time);
            edges.remove(edges.size() - 1);
        }
    }
}
