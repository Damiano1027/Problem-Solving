import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    private static StringTokenizer tokenizer;
    private static int[] want;
    private static boolean[] visited, done;
    private static int count;


    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(reader.readLine());

        for (int i = 0; i < T; i++) {
            int n = Integer.parseInt(reader.readLine());

            want = new int[n + 1];

            tokenizer = new StringTokenizer(reader.readLine());
            int k = 1;
            while (tokenizer.hasMoreTokens()) {
                want[k++] = Integer.parseInt(tokenizer.nextToken());
            }
            visited = new boolean[n + 1];
            done = new boolean[n + 1];
            count = 0;

            for (int student = 1; student <= n; student++) {
                if (!visited[student]) {
                    dfs(student);
                }
            }

            writer.write(String.format("%d\n", n - count));
            writer.flush();
        }

        writer.close();
    }

    private static void dfs(int currentStudent) {
        visited[currentStudent] = true;

        int nextStudent = want[currentStudent];
        if (!visited[nextStudent]) {
            dfs(nextStudent);
        } else if (!done[nextStudent]) {
            dfsForMakeTeam(nextStudent);
        }

        done[currentStudent] = true;
    }

    private static void dfsForMakeTeam(int currentStudent) {
        done[currentStudent] = true;
        count++;

        int nextStudent = want[currentStudent];
        if (visited[nextStudent] && !done[nextStudent]) {
            dfsForMakeTeam(nextStudent);
        }
    }
}
