import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static Map<String, Integer> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine(), ".");
            tokenizer.nextToken();
            String extension = tokenizer.nextToken();

            map.put(extension, map.getOrDefault(extension, 0) + 1);
        }

        List<Info> infoList = new ArrayList<>();
        for (String extension : map.keySet()) {
            infoList.add(new Info(extension, map.get(extension)));
        }

        Collections.sort(infoList);
        for (Info info : infoList) {
            writer.write(String.format("%s %d\n", info.extension, info.frequency));
        }
        writer.flush();
        writer.close();
    }

}

class Info implements Comparable<Info> {
    String extension;
    int frequency;
    Info(String extension, int frequency) {
        this.extension = extension;
        this.frequency = frequency;
    }
    public int compareTo(Info info) {
        return extension.compareTo(info.extension);
    }
}
