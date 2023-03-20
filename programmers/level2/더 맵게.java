import java.util.*;
import java.util.stream.Collectors;

/* 
   우선순위 큐를 이용한다.
*/

class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        boolean enabled = false;
        
        PriorityQueue<Integer> queue = Arrays.stream(scoville)
                .boxed()
                .collect(Collectors.toCollection(PriorityQueue::new));
        
        while (queue.size() >= 2) {
            Integer min1 = queue.poll();
            Integer min2 = queue.poll();
            
            Integer newScoville = getScoville(min1, min2);
            queue.add(newScoville);
            
            answer++;
            
            if (isAllHigherThanK(queue, K)) {
                enabled = true;
                break;
            }
        } 
        
        return enabled ? answer : -1;
    }
    
    public Integer getScoville(int scoville1, int scoville2) {
        return scoville1 + (scoville2 * 2);
    }
    
    public boolean isAllHigherThanK(PriorityQueue<Integer> queue, int K) {
        for (Integer scoville : queue) {
            if (scoville < K) {
                return false;
            }
        }
        
        return true;
    }
}
