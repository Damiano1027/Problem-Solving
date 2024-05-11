import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static Map<String, List<String>> teamMembersMap = new HashMap<>();
    static Map<String, String> memberTeamMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        for (int i = 0; i < N; i++) {
            String teamName = reader.readLine();
            int size = Integer.parseInt(reader.readLine());
            teamMembersMap.put(teamName, new ArrayList<>());

            for (int j = 0; j < size; j++) {
                String name = reader.readLine();
                teamMembersMap.get(teamName).add(name);
                memberTeamMap.put(name, teamName);
            }
            Collections.sort(teamMembersMap.get(teamName));
        }

        for (int i = 0; i < M; i++) {
            String name = reader.readLine();

            int value = Integer.parseInt(reader.readLine());
            if (value == 1) {
                writer.write(memberTeamMap.get(name) + "\n");
            } else {
                for (String memberName : teamMembersMap.get(name)) {
                    writer.write(memberName + "\n");
                }
            }
        }

        writer.flush();
        writer.close();
    }
}
