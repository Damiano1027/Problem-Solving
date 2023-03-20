/*
   dfs로 쉽게 해결할 수 있다.
   https://school.programmers.co.kr/learn/courses/30/lessons/43165
*/

class Solution {
    public int count = 0;
    
    public int solution(int[] numbers, int target) {
        int answer = 0;
        
        dfs(numbers, target, true, 0, 0);
        dfs(numbers, target, false, 0, 0);
        
        answer = count;
        
        return answer;
    }
    
    public void dfs(int[] numbers, int target, boolean plus, int index, int value) {
        // 더하는 경우
        if (plus) {
            value += numbers[index];
        } 
        // 빼는 경우
        else {
            value -= numbers[index];
        }
        
        
        // 배열의 마지막일 경우
        if (index == numbers.length - 1) {
            if (value == target) {
                count++;
            }
        } 
        // 배열의 마지막이 아닐 경우에는 계속 탐색
        else {
            dfs(numbers, target, true, index + 1, value); // 더하는 경우
            dfs(numbers, target, false, index + 1, value); // 빼는 경우
        }
    }
}
