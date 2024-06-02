import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static List<Info> infoList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            infoList.add(new Info(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken())));
        }

        Collections.sort(infoList);
        for (Info info : infoList) {
            writer.write(String.format("%d %d\n", info.x, info.y));
        }
        writer.flush();
        writer.close();
    }

}

class Info implements Comparable<Info> {
    int x, y;
    Info(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int compareTo(Info info) {
        if (y < info.y) {
            return -1;
        } else if (y > info.y) {
            return 1;
        } else {
            return Integer.compare(x, info.x);
        }
    }
}
