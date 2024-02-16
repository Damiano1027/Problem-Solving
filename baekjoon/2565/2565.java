import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static List<Cable> cables = new ArrayList<>();
    static int result;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            cables.add(new Cable(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken())));
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        cables.sort(Comparator.comparingInt(o -> o.left));
        Map<Integer, Integer> dpMap = new HashMap<>();
        dpMap.put(cables.get(0).left, 1);

        for (int i = 1; i < cables.size(); i++) {
            Cable cable = cables.get(i);
            dpMap.put(cable.left, 1);

            int max = -1;
            for (int j = 0; j < i; j++) {
                Cable prevCable = cables.get(j);
                if (prevCable.right < cable.right) {
                    max = Math.max(max, dpMap.get(prevCable.left));
                }
            }

            if (max != -1) {
                dpMap.put(cable.left, dpMap.get(cable.left) + max);
            }
        }

        int max = -1;
        for (Integer key: dpMap.keySet()) {
            max = Math.max(max, dpMap.get(key));
        }

        result = N - max;
    }
}

class Cable {
    int left, right;
    Cable(int left, int right) {
        this.left = left;
        this.right = right;
    }
}
