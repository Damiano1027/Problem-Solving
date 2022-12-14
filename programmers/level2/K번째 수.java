import java.util.*;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        
        for (int i = 0; i < commands.length; i++) {
            int[] command = commands[i];
            answer[i] = getResult(array, command[0], command[1], command[2]);
        }
        
        return answer;
    }
    
    public int getResult(int[] array, int i, int j, int k) {
        int[] sliceArray = new int[j - i + 1];
        sliceArray = Arrays.copyOfRange(array, i - 1, j);
        Arrays.sort(sliceArray);
        
        return sliceArray[k - 1];
    }
}
