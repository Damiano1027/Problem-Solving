import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[][] matrix;
    static int result;
    static class Combination {
        List<Integer> startTeam;
        List<Integer> linkTeam;
        Combination(List<Integer> startTeam, List<Integer> linkTeam) {
            this.startTeam = startTeam;
            this.linkTeam = linkTeam;
        }
        int startTeamLevel() {
            int level = 0;
            for (int i = 0; i < startTeam.size() - 1; i++) {
                for (int j = i + 1; j < startTeam.size(); j++) {
                    int value1 = startTeam.get(i);
                    int value2 = startTeam.get(j);
                    level += matrix[value1 - 1][value2 - 1];
                    level += matrix[value2 - 1][value1 - 1];
                }
            }
            return level;
        }
        int linkTeamLevel() {
            int level = 0;
            for (int i = 0; i < linkTeam.size() - 1; i++) {
                for (int j = i + 1; j < linkTeam.size(); j++) {
                    int value1 = linkTeam.get(i);
                    int value2 = linkTeam.get(j);
                    level += matrix[value1 - 1][value2 - 1];
                    level += matrix[value2 - 1][value1 - 1];
                }
            }
            return level;
        }
        int difference() {
            return Math.abs(startTeamLevel() - linkTeamLevel());
        }

        @Override
        public String toString() {
            return String.format("%s %s\n", startTeam.toString(), linkTeam.toString());
        }
    }
    static List<Combination> combinations = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        matrix = new int[N][N];

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < N; j++) {
                matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        makeCombinations(1, new ArrayList<>(), 0);

        int min = Integer.MAX_VALUE;
        for (Combination combination : combinations) {
            min = Math.min(min, combination.difference());
            //System.out.println(combination.toString());
        }

        result = min;
    }

    static void makeCombinations(int currentNumber, List<Integer> numbers, int count) {
        if (count >= N / 2) {
            List<Integer> restNumbers = getRestNumbers(numbers);
            combinations.add(new Combination(new ArrayList<>(numbers), restNumbers));
            return;
        }

        for (int i = currentNumber; i <= N; i++) {
            numbers.add(i);
            makeCombinations(i + 1, numbers, count + 1);
            numbers.remove(numbers.size() - 1);
        }
    }

    static List<Integer> getRestNumbers(List<Integer> numbers) {
        List<Integer> restNumbers = new ArrayList<>();

        for (int i = 1; i <= N; i++) {
            if (numbers.contains(i)) {
                continue;
            }
            restNumbers.add(i);
        }

        return restNumbers;
    }
}
