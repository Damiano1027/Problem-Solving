import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int L, C;
    static char[] chars;
    static List<String> result = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        L = Integer.parseInt(tokenizer.nextToken());
        C = Integer.parseInt(tokenizer.nextToken());

        chars = new char[C];
        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < C; i++) {
            chars[i] = tokenizer.nextToken().charAt(0);
        }

        solve();
        Collections.sort(result);

        for (String r : result) {
            writer.write(r + "\n");
        }
        writer.flush();
        writer.close();
    }

    static void solve() {
        int currentVowelCount = 0, currentConsonantCount = 0;

        List<Character> characters = new ArrayList<>();
        Set<Character> visited = new HashSet<>();

        for (char ch : chars) {
            characters.add(ch);
            visited.add(ch);
            if (isVowel(ch)) {
                currentVowelCount++;
            } else {
                currentConsonantCount++;
            }

            dfs(characters, visited, currentVowelCount, currentConsonantCount);

            if (isVowel(ch)) {
                currentVowelCount--;
            } else {
                currentConsonantCount--;
            }
            visited.remove(ch);
            characters.remove(characters.size() - 1);
        }
    }

    static void dfs(List<Character> characters, Set<Character> visited, int currentVowelCount, int currentConsonantCount) {
        if (characters.size() == L) {
            if (currentVowelCount >= 1 && currentConsonantCount >= 2) {
                StringBuilder stringBuilder = new StringBuilder();
                for (char ch : characters) {
                    stringBuilder.append(ch);
                }
                result.add(stringBuilder.toString());
            }
            return;
        }

        char currentCh = characters.get(characters.size() - 1);
        visited.add(currentCh);
        for (char ch : chars) {
            if (visited.contains(ch) || currentCh > ch) {
                continue;
            }

            characters.add(ch);
            if (isVowel(ch)) {
                currentVowelCount++;
            } else {
                currentConsonantCount++;
            }

            dfs(characters, visited, currentVowelCount, currentConsonantCount);

            if (isVowel(ch)) {
                currentVowelCount--;
            } else {
                currentConsonantCount--;
            }
            characters.remove(characters.size() - 1);
        }
        visited.remove(currentCh);
    }

    static boolean isVowel(char ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }
}
