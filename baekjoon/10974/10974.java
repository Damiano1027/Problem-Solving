import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static Set<String> strSet = new HashSet<>();
    static Set<String> visitedSet = new HashSet<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        solve();
    }

    static void solve() throws Exception {
        Set<Integer> visited = new HashSet<>();

        for (int number = 1; number <= N; number++) {
            dfs("", visited);
        }

        writer.flush();
        writer.close();
    }

    static void dfs(String result, Set<Integer> visited) throws Exception {
        if (visitedSet.contains(result)) {
            return;
        }

        if (result.length() == N) {
            if (!strSet.contains(result)) {
                for (int i = 0; i < result.length(); i++) {
                    char ch = result.charAt(i);
                    writer.write(String.format("%c ", ch));
                }
                writer.write("\n");

                strSet.add(result);
            }

            return;
        }

        for (int number = 1; number <= N; number++) {
            if (visited.contains(number)) {
                continue;
            }

            char nextCharNumber = (char) (number + '0');

            result = result + nextCharNumber;
            visited.add(number);
            dfs(result, visited);
            visited.remove(number);
            result = result.substring(0, result.length() - 1);
        }

        visitedSet.add(result);
    }
}
