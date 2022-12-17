import java.util.*;

class Solution {
    public static List<Pair> pairs = new LinkedList<>();
    public static Map<Integer, Set<Integer>> map = new HashMap<>();
    public static boolean[] visited = new boolean[20001];

    public int solution(int n, int[][] edge) {
        int answer;

        for (int[] e : edge) {
            int edge1 = e[0];
            int edge2 = e[1];

            Set<Integer> adjoinsOfEdge1 = map.getOrDefault(edge1, new HashSet<>());
            Set<Integer> adjoinsOfEdge2 = map.getOrDefault(edge2, new HashSet<>());

            adjoinsOfEdge1.add(edge2);
            adjoinsOfEdge2.add(edge1);
            
            map.put(edge1, adjoinsOfEdge1);
            map.put(edge2, adjoinsOfEdge2);
        }

        int maxDistance = bfs(1);

        answer = (int) pairs.stream()
                .filter(pair -> maxDistance == pair.cnt)
                .count();
        
        return answer;
    }

    public int bfs(int start) {
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(start, 0));
        visited[start] = true;
        int maxDistance = 0;

        while (!queue.isEmpty()) {
            Pair pair = queue.poll();
            pairs.add(pair);
            maxDistance = pair.cnt;

            for (Integer edge : map.get(pair.n)) {
                if (!visited[edge]) {
                    visited[edge] = true;

                    queue.add(new Pair(edge, pair.cnt + 1));
                }
            }
        }

        return maxDistance;
    }



    public static class Pair {
        int n;
        int cnt;

        Pair(int n, int cnt) {
            this.n = n;
            this.cnt = cnt;
        }
    }
}
