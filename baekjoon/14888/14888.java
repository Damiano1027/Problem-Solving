import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[] numbers;
    static final List<Character> operators = new ArrayList<>();
    static final List<List<Character>> combinations = new ArrayList<>();
    static int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        numbers = new int[N];

        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < N; i++) {
            numbers[i] = Integer.parseInt(tokenizer.nextToken());
        }

        tokenizer = new StringTokenizer(reader.readLine());
        int n1 = Integer.parseInt(tokenizer.nextToken());
        int n2 = Integer.parseInt(tokenizer.nextToken());
        int n3 = Integer.parseInt(tokenizer.nextToken());
        int n4 = Integer.parseInt(tokenizer.nextToken());
        for (int i = 0; i < n1; i++) {
            operators.add('+');
        }
        for (int i = 0; i < n2; i++) {
            operators.add('-');
        }
        for (int i = 0; i < n3; i++) {
            operators.add('x');
        }
        for (int i = 0; i < n4; i++) {
            operators.add('/');
        }

        solve();

        writer.write(String.format("%d\n", max));
        writer.write(String.format("%d\n", min));
        writer.flush();
        writer.close();
    }

    static void solve() {
        makeCombinations();
        for (List<Character> combination: combinations) {
            int result = calculate(combination);
            min = Math.min(min, result);
            max = Math.max(max, result);
        }
    }

    static void makeCombinations() {
        for (int i = 0; i < operators.size(); i++) {
            makeCombinations(i, new ArrayList<>(), new boolean[operators.size()]);
        }
    }

    static void makeCombinations(int currentIndex, List<Character> operatorList, boolean[] visited) {
        char currentOperator = operators.get(currentIndex);
        operatorList.add(currentOperator);

        if (operatorList.size() == operators.size()) {
            combinations.add(new ArrayList<>(operatorList));
        } else {
            visited[currentIndex] = true;
            for (int i = 0; i < operators.size(); i++) {
                if (visited[i]) {
                    continue;
                }
                makeCombinations(i, operatorList, visited);
            }
            visited[currentIndex] = false;
        }

        operatorList.remove(operatorList.size() - 1);
    }

    static int calculate(List<Character> operators) {
        int result = 0;

        for (int i = 0; i < numbers.length; i++) {
            if (i == 0) {
                result = numbers[i];
                continue;
            }

            char operator = operators.get(i - 1);
            if (operator == '+') {
                result += numbers[i];
            } else if (operator == '-') {
                result -= numbers[i];
            } else if (operator == 'x') {
                result *= numbers[i];
            } else if (operator == '/') {
                result /= numbers[i];
            }
        }

        return result;
    }
}
