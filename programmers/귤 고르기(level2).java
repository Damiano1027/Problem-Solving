import java.util.*;

/*
    https://school.programmers.co.kr/learn/courses/30/lessons/138476
    
    - 특정 크기의 귤이 몇개 담겨있는지 map에 저장
    - map을 value 기준으로 내림차순 정렬
    - value가 가장 큰 귤부터 카운트 
*/
class Solution {
    public int solution(int k, int[] tangerine) {
        int answer = 0;
        
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int t : tangerine) {
            Integer value = map.getOrDefault(t, 0);
            value++;
            map.put(t, value);
        }
        
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        
        Set<Integer> set = new HashSet<>();
        
        for (Map.Entry<Integer, Integer> entry : entries) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            
            set.add(key);
            for (int i = value; i > 0; i--) {
                k--;
                
                if (k <= 0) {
                    break;
                }
            }
            
            if (k <= 0) {
                break;
            }
        }
        
        answer = set.size();
        
        return answer;
    }
}
