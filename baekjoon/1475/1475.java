import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int[] frequencyArr = new int[9];

    public static void main(String[] args) throws Exception {
        String number = reader.readLine();

        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            int value = Character.getNumericValue(ch);

            if (value == 9) {
                frequencyArr[6]++;
            } else {
                frequencyArr[value]++;
            }
        }

        frequencyArr[6] = (frequencyArr[6] - 1) / 2 + 1;
        Arrays.sort(frequencyArr);

        writer.write(frequencyArr[8] + "\n");
        writer.flush();
        writer.close();
    }
}
