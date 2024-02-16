import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static class Cable {
        int left, right;
        Cable(int left, int right) {
            this.left = left;
            this.right = right;
        }
        public boolean equals(Object o) {
            Cable cable = (Cable) o;
            return left == cable.left && right == cable.right;
        }
        public int hashCode() {
            return Objects.hash(left, right);
        }
    }
    static Set<Cable> cables = new HashSet<>();
    static Map<Set<Cable>, Integer> dpMap = new HashMap<>();
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
        result = dfs(cables);
    }

    static int dfs(Set<Cable> cables) {
        if (dpMap.containsKey(cables)) {
            return dpMap.get(cables);
        }

        List<Cable> cableList = new ArrayList<>(cables);

        if (!containsCross(cables, cableList)) {
            dpMap.put(cables, N - cables.size());
            return N - cables.size();
        }

        int min = Integer.MAX_VALUE;

        for (Cable cable: cableList) {
            cables.remove(cable);
            int dfsResult = dfs(cables);
            cables.add(cable);

            min = Math.min(min, dfsResult);
        }

        dpMap.put(cables, min);
        return min;
    }

    static boolean containsCross(Set<Cable> cables, List<Cable> cableList) {
        for (Cable cableInList: cableList) {
            for (Cable cableInSet: cables) {
                if (cableInList.equals(cableInSet)) {
                    continue;
                }

                if ((cableInSet.left < cableInList.left && cableInSet.right > cableInList.right)
                        || (cableInSet.left > cableInList.left && cableInSet.right < cableInList.right)) {
                    return true;
                }
            }
        }

        return false;
    }
}
