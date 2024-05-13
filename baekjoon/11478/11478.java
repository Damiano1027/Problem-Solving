import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static String S;
    static Set<String> set = new HashSet<>();

    public static void main(String[] args) throws Exception {
        S = reader.readLine();

        for (int size = 1; size <= S.length(); size++) {
            int leftIndex = 0;
            int rightIndex = size;

            while (rightIndex <= S.length()) {
                String subStr = S.substring(leftIndex, rightIndex);
                set.add(subStr);

                leftIndex++;
                rightIndex++;
            }
        }

        writer.write(set.size() + "\n");
        writer.flush();
        writer.close();
    }
}
