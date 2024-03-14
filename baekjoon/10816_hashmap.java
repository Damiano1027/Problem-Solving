import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static Map<Integer, Integer> frequencyMap = new HashMap<>();
    
    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        tokenizer = new StringTokenizer(reader.readLine());
        
        for (int i = 0; i < N; i++) {
            int value = Integer.parseInt(tokenizer.nextToken());
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }
        
        M = Integer.parseInt(reader.readLine());
        tokenizer = new StringTokenizer(reader.readLine());
        
        for (int i = 0; i < M; i++) {
            int value = Integer.parseInt(tokenizer.nextToken());
            if (frequencyMap.get(value) == null) {
                writer.write("0 ");
            } else {
                writer.write(frequencyMap.get(value) + " ");
            }
        }
        writer.newLine();
        writer.flush();
        writer.close();
    }
}
