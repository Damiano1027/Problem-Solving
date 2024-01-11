import java.io.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static String stringNumber;
    static int result = 0;

    public static void main(String[] args) throws Exception {
        stringNumber = reader.readLine();

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        int size = stringNumber.length();
        StringBuilder stringBuilder;

        result += sum(stringNumber);
        size--;
        while (size > 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("9".repeat(size));
            result += sum(stringBuilder.toString());
            size--;
        }
    }

    static int sum(String stringNumber) {
        int number = Integer.parseInt(stringNumber);
        int size = stringNumber.length();
        return size * (number - (int) Math.pow(10, size - 1) + 1);
    }
}
