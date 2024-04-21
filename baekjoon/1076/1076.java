import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static Map<String, Information> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
        map.put("black", new Information(0, 1));
        map.put("brown", new Information(1, 10));
        map.put("red", new Information(2, 100));
        map.put("orange", new Information(3, 1000));
        map.put("yellow", new Information(4, 10000));
        map.put("green", new Information(5, 100000));
        map.put("blue", new Information(6, 1000000));
        map.put("violet", new Information(7, 10000000));
        map.put("grey", new Information(8, 100000000));
        map.put("white", new Information(9, 1000000000));

        String str1 = reader.readLine();
        String str2 = reader.readLine();
        String str3 = reader.readLine();
        long result = Integer.parseInt(String.valueOf(map.get(str1).value) + String.valueOf(map.get(str2).value)) * map.get(str3).multiply;
        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }
}

class Information {
    int value;
    long multiply;
    Information(int value, long multiply) {
        this.value = value;
        this.multiply = multiply;
    }
}
