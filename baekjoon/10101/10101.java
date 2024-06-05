import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int a, b, c;

    public static void main(String[] args) throws Exception {
        a = Integer.parseInt(reader.readLine());
        b = Integer.parseInt(reader.readLine());
        c = Integer.parseInt(reader.readLine());

        if (a == b && b == c && a == 60) {
            writer.write("Equilateral\n");
            writer.flush();
            writer.close();
            return;
        }
        if (a + b + c == 180 && (a == b || b == c || c == a)) {
            writer.write("Isosceles\n");
            writer.flush();
            writer.close();
            return;
        }
        if (a + b + c == 180 && !(a == b || b == c || c == a)) {
            writer.write("Scalene\n");
            writer.flush();
            writer.close();
            return;
        }
        if (a + b + c != 180) {
            writer.write("Error\n");
            writer.flush();
            writer.close();
            return;
        }
    }
}
