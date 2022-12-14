import java.util.Arrays;
import java.util.LinkedList;

class Solution {
    boolean possible;

    public int solution(String begin, String target, String[] words) {
        int answer = 0;
        LinkedList<String> wordList = new LinkedList<>(Arrays.asList(words));

        if (!wordList.contains(target)) {
            return answer;
        }

        answer = dfs(begin, wordList, 0, target);

        return possible ? answer : 0;
    }

    int dfs(String word, LinkedList<String> words, int cnt, String target) {
        if (word.equals(target)) {
            possible = true;
            return cnt;
        }

        Integer min = null;
        int i = 0;
        for (String w : words) {
            if (isOneDiff(word, w)) {
                LinkedList<String> copyWords = new LinkedList<>(words);
                copyWords.remove(w);

                if (i == 0) {
                    min = dfs(w, copyWords, cnt + 1, target);
                }
                else {
                    min = Math.min(min, dfs(w, copyWords, cnt + 1, target));
                }

                i++;
            }
        }

        return min == null ? Integer.MAX_VALUE : min;
    }

    boolean isOneDiff(String word1, String word2) {
        int cnt = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                cnt++;
            }
        }
        return cnt == 1;
    }
}
