import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M;
    static int[] uf;
    static Set<Integer> knowTruthNumbers = new HashSet<>();
    static List<List<Integer>> partyPeopleNumbersList = new ArrayList<>();
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        uf = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            uf[i] = i;
        }

        tokenizer = new StringTokenizer(reader.readLine());
        int knowTruthPeopleCount = Integer.parseInt(tokenizer.nextToken());

        if (knowTruthPeopleCount == 0) {
            for (int i = 0; i < M; i++) {
                reader.readLine();
            }

            writer.write(String.format("%d\n", M));
            writer.flush();

            writer.close();
            return;
        }

        if (knowTruthPeopleCount == 1) {
            knowTruthNumbers.add(Integer.parseInt(tokenizer.nextToken()));
        } else if (knowTruthPeopleCount > 1) {
            int[] knowTruthNumberArray = new int[knowTruthPeopleCount];
            for (int i = 0; i < knowTruthPeopleCount; i++) {
                int number = Integer.parseInt(tokenizer.nextToken());
                knowTruthNumberArray[i] = number;
                knowTruthNumbers.add(number);
            }

            for (int i = 0; i < knowTruthPeopleCount - 1; i++) {
                union(knowTruthNumberArray[i], knowTruthNumberArray[i + 1]);
            }
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int count = Integer.parseInt(tokenizer.nextToken());

            List<Integer> partyPeopleNumbers = new ArrayList<>();
            for (int j = 0; j < count; j++) {
                partyPeopleNumbers.add(Integer.parseInt(tokenizer.nextToken()));
            }

            if (count == 1) {
                partyPeopleNumbersList.add(partyPeopleNumbers);
                continue;
            }

            for (int j = 0; j < count - 1; j++) {
                union(partyPeopleNumbers.get(j), partyPeopleNumbers.get(j + 1));
            }

            partyPeopleNumbersList.add(partyPeopleNumbers);
        }

        int knowTruthNumber = 0;
        for (int number: knowTruthNumbers) {
            knowTruthNumber = number;
            break;
        }

        for (List<Integer> partyPeopleNumbers: partyPeopleNumbersList) {
            boolean partyEnable = true;
            for (int partyPeopleNumber: partyPeopleNumbers) {
                if (find(partyPeopleNumber) == find(knowTruthNumber)) {
                    partyEnable = false;
                    break;
                }
            }

            if (partyEnable) {
                result++;
            }
        }

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void union(int a, int b) {
        int aRep = find(a);
        int bRep = find(b);

        uf[aRep] = bRep;
    }

    static int find(int number) {
        if (uf[number] == number) {
            return number;
        }

        return uf[number] = find(uf[number]);
    }
}
