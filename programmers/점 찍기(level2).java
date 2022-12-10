/*
    - 원의 방정식으로 접근
    - 제곱수를 long으로 타입 변환 해야함 
*/

class Solution {
    public long solution(int k, int d) {
        long answer = 0;

        for (int x = 0; x <= d; x += k) {
            long powD = (long) d * d;
            long powX = (long) x * x;

            long y = (long) Math.sqrt(powD - powX);
            answer += (Math.floor(y / (long)k) + 1);
        }

        return answer;
    }
}
