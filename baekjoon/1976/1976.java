import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[] uf;
    static String result = "YES";

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        M = Integer.parseInt(reader.readLine());

        uf = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            uf[i] = i;
        }

        for (int i = 1; i <= N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 1; j <= N; j++) {
                int value = Integer.parseInt(tokenizer.nextToken());
                if (value == 1) {
                    union(i, j);
                }
            }
        }

        tokenizer = new StringTokenizer(reader.readLine());
        int[] plan = new int[M];
        for (int i = 0; i < M; i++) {
            plan[i] = Integer.parseInt(tokenizer.nextToken());
        }

        for (int i = 0; i < M - 1; i++) {
            int a = plan[i];
            int b = plan[i + 1];

            if (find(a) != find(b)) {
                result = "NO";
                break;
            }
        }

        writer.write(result);
        writer.newLine();
        writer.flush();

        writer.close();
    }

    static void union(int a, int b) {
        int aRep = find(a);
        int bRep = find(b);

        if (aRep != bRep) {
            if (aRep < bRep) {
                uf[bRep] = aRep;
            } else {
                uf[aRep] = bRep;
            }
        }
    }

    static int find(int number) {
        if (uf[number] == number) {
            return number;
        }

        return uf[number] = find(uf[number]);
    }

}
