class Solution {
    static boolean[] visited = new boolean[200];

    public int solution(int n, int[][] computers) {
        int answer = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, computers);
                answer++;
            }
        }

        return answer;
    }

    void dfs(int n, int[][] computers) {
        visited[n] = true;

        for (int i = 0; i < computers[n].length; i++) {
            if (computers[n][i] == 1) {
                if (!visited[i]) {
                    dfs(i, computers);
                }
            }
        }
    }
}
