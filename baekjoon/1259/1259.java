import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws Exception {
        while (true) {
            String str = reader.readLine();
            if (str.equals("0")) {
                break;
            }

            if (isPalindrome(str)) {
                writer.write("yes\n");
            } else {
                writer.write("no\n");
            }
        }

        writer.flush();
        writer.close();
    }

    static boolean isPalindrome(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            stringBuilder.append(str.charAt(i));
        }
        String reversedStr = stringBuilder.toString();

        return str.equals(reversedStr);
    }
}
