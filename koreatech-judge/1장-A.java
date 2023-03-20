import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
 
public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final int SIZE = 3;
 
    public static void main(String[] args) throws Exception {
        int T = Integer.parseInt(br.readLine());
 
        for (int i = 0; i < T; i++) {
            String str = br.readLine();
            int result = getResult(str);
 
            bw.write(result + "\n");
            bw.flush();
        }
 
        bw.close();
    }
 
    private static int getResult(String str) {
        if (str.length() < SIZE) {
            return 0;
        }
 
        int result = 0;
 
        int begin = 0;
        int end = SIZE;
 
        // 검사할 문자열의 시작과 끝 인덱스를 지정하고, 1씩 증가키시면서 문자열을 잘라 좋은 부분문자열인지 확인한다.
        while (end <= str.length()) {
            String substr = str.substring(begin, end);
 
            if (isGood(substr)) {
                result++;
            }
 
            begin++;
            end++;
        }
 
        return result;
    }
 
    // set은 중복된 값을 담을 수 없으므로, set에 문자들이 들어간 후 기존의 size와 같은지 확인한다.
    private static boolean isGood(String str) {
        Set<Character> set = new HashSet<>();
 
        for (int i = 0; i < str.length(); i++) {
            set.add(str.charAt(i));
        }
 
        return set.size() == SIZE;
    }
 
    /**************************************************************
     Problem: 1241
     User: damiano1027
     Language: Java
     Result: 모두 맞음
     Time:192 ms
     Memory:44072 kb
     ****************************************************************/
}
