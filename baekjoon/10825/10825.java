import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static List<Information> informations = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            String name = tokenizer.nextToken();
            int koreanScore = Integer.parseInt(tokenizer.nextToken());
            int englishScore = Integer.parseInt(tokenizer.nextToken());
            int mathScore = Integer.parseInt(tokenizer.nextToken());

            informations.add(new Information(name, koreanScore, englishScore, mathScore));
        }

        Collections.sort(informations);
        for (Information information : informations) {
            writer.write(information.name + "\n");
        }
        writer.flush();
        writer.close();
    }
}

class Information implements Comparable<Information> {
    String name;
    int koreanScore, englishScore, mathScore;
    Information(String name, int koreanScore, int englishScore, int mathScore) {
        this.name = name;
        this.koreanScore = koreanScore;
        this.englishScore = englishScore;
        this.mathScore = mathScore;
    }
    public int compareTo(Information o) {
        if (koreanScore < o.koreanScore) {
            return 1;
        } else if (koreanScore > o.koreanScore) {
            return -1;
        }

        if (englishScore < o.englishScore) {
            return -1;
        } else if (englishScore > o.englishScore) {
            return 1;
        }

        if (mathScore < o.mathScore) {
            return 1;
        } else if (mathScore > o.mathScore) {
            return -1;
        }

        return name.compareTo(o.name);
    }
}
