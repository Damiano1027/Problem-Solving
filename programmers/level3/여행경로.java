import java.util.*;

class Solution {
    public List<String> result = new LinkedList<>();
    public boolean[] visited = new boolean[100000001];
    public boolean check = false;

    public String[] solution(String[][] tickets) {
        String[] answer;

        Arrays.sort(tickets, Comparator.comparing(o -> o[1]));

        dfs("ICN", tickets, 0);

        answer = result.toArray(new String[result.size()]);

        return answer;
    }

    public void dfs(String from, String[][] tickets, int depth) {
        if (depth == tickets.length) {
            check = true;
        }
        result.add(from);

        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i][0].equals(from) && !visited[i]) {
                visited[i] = true;
                dfs(tickets[i][1], tickets, depth + 1);

                if (!check) {
                    result.remove(result.size() - 1);
                    visited[i] = false;
                }
            }
        }
    }
}
