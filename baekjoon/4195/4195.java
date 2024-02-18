import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int testCase;
    static Map<String, String> ufMap;
    static Map<String, Integer> countMap;

    public static void main(String[] args) throws Exception {
        testCase = Integer.parseInt(reader.readLine());

        for (int i = 0; i < testCase; i++) {
            int F = Integer.parseInt(reader.readLine());
            List<Pair> pairs = new ArrayList<>();
            ufMap = new HashMap<>();
            countMap = new HashMap<>();

            for (int j = 0; j < F; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                String a = tokenizer.nextToken();
                String b = tokenizer.nextToken();

                ufMap.put(a, a);
                ufMap.put(b, b);
                countMap.put(a, 1);
                countMap.put(b, 1);
                pairs.add(new Pair(a, b));
            }

            for (Pair pair: pairs) {
                writer.write(String.format("%d\n", union(pair.a, pair.b)));
            }
        }

        writer.flush();

        writer.close();
    }

    static int union(String a, String b) {
        String aRoot = find(a);
        String bRoot = find(b);

        if (aRoot.equals(bRoot)) {
            return countMap.get(aRoot);
        }

        ufMap.put(aRoot, bRoot);

        int aRootSetCount = countMap.get(aRoot);
        int bRootSetCount = countMap.get(bRoot);
        int combinedSetCount = aRootSetCount + bRootSetCount;
        countMap.put(aRoot, combinedSetCount);
        countMap.put(bRoot, combinedSetCount);
        return combinedSetCount;
    }

    static String find(String name) {
        if (ufMap.get(name).equals(name)) {
            return name;
        }

        ufMap.put(name, find(ufMap.get(name)));
        return ufMap.get(name);
    }
}

class Pair {
    String a, b;
    Pair(String a, String b) {
        this.a = a;
        this.b = b;
    }
}
