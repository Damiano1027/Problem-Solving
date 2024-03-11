import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        solve();

        writer.flush();
        writer.close();
    }

    static void solve() throws Exception {
        dfs();
    }

    static void dfs() throws Exception {
        dfs("2");
        dfs("3");
        dfs("5");
        dfs("7");
    }

    static void dfs(String currentStrNumber) throws Exception {
        if (currentStrNumber.length() == N && isPrimeNumber(currentStrNumber)) {
            writer.write(currentStrNumber + "\n");
            return;
        }
        if (!isPrimeNumber(currentStrNumber)) {
            return;
        }

        for (char ch = '0'; ch <= '9'; ch++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(currentStrNumber);
            stringBuilder.append(ch);
            dfs(stringBuilder.toString());
        }
    }

    static boolean isPrimeNumber(String strNumber) {
        int number = Integer.parseInt(strNumber);

        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
