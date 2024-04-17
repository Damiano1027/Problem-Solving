import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int n;
    static boolean possible = true;

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(reader.readLine());

        int coin5Count = n / 5;
        int remainder = n % 5;

        if (remainder % 2 != 0) {
            if (coin5Count == 0) {
                possible = false;
            } else {
                coin5Count--;
                remainder += 5;
            }
        }

        if (possible) {
            writer.write(coin5Count + (remainder / 2) + "\n");
        } else {
            writer.write("-1\n");
        }

        writer.flush();
        writer.close();
    }
}
