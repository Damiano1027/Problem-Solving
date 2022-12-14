import java.util.*;

public class Solution {
    public int[] solution(int []arr) {
        int[] answer = {};


        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                queue.add(arr[i]);
                continue;
            }

            if (arr[i] == arr[i - 1]) {
                continue;
            }

            queue.add(arr[i]);
        }

        answer = new int[queue.size()];
        int i = 0;
        while (!queue.isEmpty()) {
            answer[i] = queue.poll();
            i++;
        }

        return answer;
    }
}
