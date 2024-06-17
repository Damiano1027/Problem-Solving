import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int result = 0;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 5; i++) {
            result += Integer.parseInt(reader.readLine());
        }

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }
}
