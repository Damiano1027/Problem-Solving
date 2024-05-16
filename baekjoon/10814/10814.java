import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static StringTokenizer tokenizer;
    static List<Information> informations = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        for (int i = 1; i <= N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int age = Integer.parseInt(tokenizer.nextToken());
            String name = tokenizer.nextToken();
            informations.add(new Information(i, age, name));
        }

        Collections.sort(informations);

        for (Information information : informations) {
            writer.write(String.format("%d %s\n", information.age, information.name));
        }

        writer.flush();
        writer.close();
    }
}

class Information implements Comparable<Information> {
    int order, age;
    String name;
    Information(int order, int age, String name) {
        this.order = order;
        this.age = age;
        this.name = name;
    }
    public int compareTo(Information o) {
        if (age < o.age) {
            return -1;
        } else if (age > o.age) {
            return 1;
        } else {
            return order - o.order;
        }
    }
}
