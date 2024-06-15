import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static String str;

    public static void main(String[] args) throws Exception {
        str = reader.readLine();

        for (int i = 0; i < 3; i++) {
            writer.write(":fan:");
        }
        writer.write("\n");
        writer.write(String.format(":fan::%s::fan:\n", str));
        for (int i = 0; i < 3; i++) {
            writer.write(":fan:");
        }
        writer.write("\n");

        writer.flush();
        writer.close();
    }
}
