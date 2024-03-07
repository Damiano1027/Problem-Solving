import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int S, P;
    static String originalStr;
    static int[] minFrequency = new int[4], frequency = new int[4];
    static int startIndex, endIndex;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        S = Integer.parseInt(tokenizer.nextToken());
        P = Integer.parseInt(tokenizer.nextToken());

        originalStr = reader.readLine();
        tokenizer = new StringTokenizer(reader.readLine());
        // 0: A, 1: C, 2: G, 3: T
        for (int i = 0; i < 4; i++) {
            minFrequency[i] = Integer.parseInt(tokenizer.nextToken());
        }

        solve();

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }

    static void solve() {
        startIndex = 0;
        endIndex = P - 1;

        for (int i = startIndex; i <= endIndex; i++) {
            if (originalStr.charAt(i) == 'A') {
                frequency[0]++;
            } else if (originalStr.charAt(i) == 'C') {
                frequency[1]++;
            } else if (originalStr.charAt(i) == 'G') {
                frequency[2]++;
            } else if (originalStr.charAt(i) == 'T') {
                frequency[3]++;
            }
        }

        while (true) {
            if (isSatisfied()) {
                result++;
            }

            char removedCh = originalStr.charAt(startIndex);

            startIndex++;
            endIndex++;
            if (endIndex >= S) {
                break;
            }

            char addCh = originalStr.charAt(endIndex);

            updateFrequency(removedCh, addCh);
        }
    }

    static void updateFrequency(char removedCh, char addedCh) {
        if (removedCh == 'A') {
            frequency[0]--;
        } else if (removedCh == 'C') {
            frequency[1]--;
        } else if (removedCh == 'G') {
            frequency[2]--;
        } else if (removedCh == 'T') {
            frequency[3]--;
        }

        if (addedCh == 'A') {
            frequency[0]++;
        } else if (addedCh == 'C') {
            frequency[1]++;
        } else if (addedCh == 'G') {
            frequency[2]++;
        } else if (addedCh == 'T') {
            frequency[3]++;
        }
    }

    static boolean isSatisfied() {
        return frequency[0] >= minFrequency[0] && frequency[1] >= minFrequency[1]
                && frequency[2] >= minFrequency[2] && frequency[3] >= minFrequency[3];
    }
}
