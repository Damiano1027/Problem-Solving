import java.util.*;

class Solution {
    public int solution(int[] nums) {
        int answer = 0;
        int choice = nums.length / 2;

        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            Integer value = map.getOrDefault(num, 0);
            value++;
            map.put(num, value);
        }

        HashSet<Integer> set = new HashSet<>();

        int choiceCount = 1;
        while (choiceCount <= choice) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();

                if (!value.equals(0)) {
                    set.add(key);
                    value--;
                    map.put(key, value);
                    choiceCount++;
                }

                if (choiceCount > choice) {
                    break;
                }
            }
        }

        answer = set.size();

        return answer;
    }
}
