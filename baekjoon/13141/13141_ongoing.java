import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static class Edge implements Comparable<Edge> {
        boolean[] visited;
        int startVertex, endVertex;
        boolean reverse;
        int currentIndex;
        Edge(boolean[] visited, int startVertex, int endVertex, boolean reverse, int currentIndex) {
            this.visited = visited;
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.reverse = reverse;
            this.currentIndex = currentIndex;
        }
        Edge(int startVertex, int endVertex) {
            this.startVertex = startVertex;
            this.endVertex = endVertex;
        }
        public String toString() {
            int left, right;
            if (startVertex > endVertex) {
                left = endVertex;
                right = startVertex;
            } else {
                left = startVertex;
                right = endVertex;
            }
            return String.format("%d%d", left, right);
        }
        public boolean equals(Object o) {
            Edge edge = (Edge) o;
            return startVertex == edge.startVertex && endVertex == edge.endVertex;
        }
        boolean equalsEdge(Edge edge) {
            return toString().equals(edge.toString());
        }
        public int hashCode() {
            return Objects.hash(startVertex, endVertex);
        }
        public int compareTo(Edge o) {
            return toString().compareTo(o.toString());
        }
        void fire() {
            if (reverse) {
                visited[currentIndex--] = true;
            } else {
                visited[currentIndex++] = true;
            }
        }
        boolean isAllFired() {
            for (boolean visit: visited) {
                if (!visit) {
                    return false;
                }
            }
            return true;
        }
    }
    static Map<Integer, List<Edge>> map = new HashMap<>();
    static double result = -1;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        for (int vertex = 1; vertex <= N; vertex++) {
            map.put(vertex, new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int S = Integer.parseInt(tokenizer.nextToken());
            int E = Integer.parseInt(tokenizer.nextToken());
            int L = Integer.parseInt(tokenizer.nextToken());

            boolean[] visited = new boolean[L];
            map.get(S).add(new Edge(visited, S, E, false, 0));
            map.get(E).add(new Edge(visited, E, S, true, L - 1));
        }

        solve();

        writer.write(String.format("%.1f\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        for (int startVertex = 4; startVertex <= 4; startVertex++) {
            double time = process(startVertex);
            result = (result == -1) ? time : Math.min(result, time);
        }
    }

    static double process(int startVertex) {
        Map<Integer, Set<Edge>> copiedMap = copyMap();

        Set<Edge> startEdges = copiedMap.get(startVertex);
        copiedMap.put(startVertex, new HashSet<>());

        List<Edge> ongoingEdges = new ArrayList<>(startEdges);
        List<Edge> nextOngoingEdges = new ArrayList<>();
        double totalTime = 0.0;

        while (!ongoingEdges.isEmpty()) {
            Collections.sort(ongoingEdges);

            boolean oneTimeExist = false;
            boolean halfTimeExist = false;

            for (int i = 0; i < ongoingEdges.size();) {
                Edge edge = ongoingEdges.get(i);

                if (i + 1 < ongoingEdges.size() && edge.equalsEdge(ongoingEdges.get(i + 1))) {
                    Edge otherEdge = ongoingEdges.get(i + 1);

                    if (edge.currentIndex == otherEdge.currentIndex) {
                        edge.fire();
                        halfTimeExist = false;
                    } else {
                        edge.fire();
                        otherEdge.fire();
                        oneTimeExist = true;
                    }

                    if (!edge.isAllFired()) {
                        nextOngoingEdges.add(edge);
                        nextOngoingEdges.add(otherEdge);
                    }

                    i += 2;
                } else {
                    edge.fire();
                    oneTimeExist = true;

                    if (edge.isAllFired()) {
                        Set<Edge> nextEdges = copiedMap.get(edge.endVertex);
                        nextOngoingEdges.addAll(nextEdges);
                        copiedMap.put(edge.endVertex, new HashSet<>());
                    } else {
                        nextOngoingEdges.add(edge);
                    }

                    i++;
                }
            }

            if (oneTimeExist) {
                totalTime += 1.0;
            } else if (halfTimeExist) {
                totalTime += 0.5;
            }

            System.out.println(copiedMap);
            System.out.println(ongoingEdges);
            System.out.println(totalTime);
            System.out.println("--------------");

            ongoingEdges = new ArrayList<>(nextOngoingEdges);
            nextOngoingEdges = new ArrayList<>();
        }

        return totalTime;
    }

    static Map<Integer, Set<Edge>> copyMap() {
        Map<Integer, Set<Edge>> copiedMap = new HashMap<>();

        for (Integer key: map.keySet()) {
            copiedMap.put(key, new HashSet<>(map.get(key)));
        }

        return copiedMap;
    }
}
