import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    private final static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    private static String oldString, targetString;

    public static void main(String[] args) throws Exception {
        oldString = reader.readLine();
        targetString = reader.readLine();

        if (solve(targetString)) {
            System.out.println("1");
        } else {
            System.out.println("0");
        }
    }

    private static boolean solve(String str) {
        if (oldString.length() == str.length()) {
            return oldString.equals(str);
        }

        if (str.charAt(str.length() - 1) == 'A') {
            String operatedString = operation1(str);
            if (solve(operatedString)) {
                return true;
            }
        }

        if (str.charAt(0) == 'B') {
            String operatedString = operation2(str);
            if (solve(operatedString)) {
                return true;
            }
        }

        return false;
    }

    private static String operation1(String oldString) {
        StringBuilder stringBuilder = new StringBuilder(oldString);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private static String operation2(String oldString) {
        StringBuilder stringBuilder = new StringBuilder(oldString);
        stringBuilder.reverse();
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
