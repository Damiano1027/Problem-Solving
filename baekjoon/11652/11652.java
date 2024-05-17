import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static Map<Long, Integer> frequencyMap = new HashMap<>();
    static List<Information> informations = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        for (int i = 0; i < N; i++) {
            long value = Long.parseLong(reader.readLine());
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }

        for (long value : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(value);
            informations.add(new Information(value, frequency));
        }

        Collections.sort(informations);
        long result = informations.get(0).value;

        writer.write(result + "\n");
        writer.flush();
        writer.close();
    }
}

class Information implements Comparable<Information> {
    long value;
    int frequency;
    Information(long value, int frequency) {
        this.value = value;
        this.frequency = frequency;
    }
    public int compareTo(Information o) {
        if (frequency < o.frequency) {
            return 1;
        } else if (frequency > o.frequency) {
            return -1;
        } else {
            return Long.compare(value, o.value);
        }
    }
}
