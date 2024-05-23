import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, C;
    static Map<Integer, Integer> frequencyMap = new HashMap<>(), orderMap = new HashMap<>();
    static List<Information> informations = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tokenizer.nextToken());
        C = Integer.parseInt(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());

        int order = 1;
        while (tokenizer.hasMoreTokens()) {
            int value = Integer.parseInt(tokenizer.nextToken());

            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
            if (!orderMap.containsKey(value)) {
                orderMap.put(value, order++);
            }
        }

        for (Integer value : frequencyMap.keySet()) {
            informations.add(new Information(value, frequencyMap.get(value), orderMap.get(value)));
        }

        Collections.sort(informations);

        for (Information information : informations) {
            for (int i = 0; i < information.frequency; i++) {
                writer.write(String.format("%d ", information.value));
            }
        }
        writer.newLine();
        writer.flush();
        writer.close();
    }
}

class Information implements Comparable<Information> {
    int value, frequency, order;
    Information(int value, int frequency, int order) {
        this.value = value;
        this.frequency = frequency;
        this.order = order;
    }
    public int compareTo(Information o) {
        if (frequency < o.frequency) {
            return 1;
        } else if (frequency > o.frequency) {
            return -1;
        } else {
            return Integer.compare(order, o.order);
        }
    }
}
