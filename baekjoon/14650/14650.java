import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static Set<String> set = new HashSet<>();
    
    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        solve();
        
        writer.write(set.size() + "\n");
        writer.flush();
        writer.close();
    }
    
    static void solve() {
        dfs("");
    }
    
    static void dfs(String str) {
        if (str.length() == N && str.length() > 0) {
            int value = Integer.parseInt(str);
            if (value % 3 == 0 && value != 0 && str.charAt(0) != '0') {
                set.add(str);
            }
            return;
        }
        
        for (char ch = '0'; ch <= '2'; ch++) {
            String newStr = str + ch;
            dfs(newStr);
        }
    }
}
