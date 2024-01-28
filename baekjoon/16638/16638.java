import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static char[] formula;
    static int result = Integer.MIN_VALUE;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());
        formula = new char[N];

        String line = reader.readLine();
        for (int i = 0; i < N; i++) {
            formula[i] = line.charAt(i);
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        if (N == 1) {
            result = Character.getNumericValue(formula[0]);
        } else if (N == 3) {
            result = calculate(Character.getNumericValue(formula[0]), formula[1], Character.getNumericValue(formula[2]));
        } else {
            int numberCount = formula.length / 2 + 1;
            for (int bracketCount = 0; bracketCount <= numberCount / 2; bracketCount++) {
                combinations(0, new HashSet<>(), bracketCount);
            }
        }
    }

    static void combinations(int currentIndex, Set<Integer> bracketIndexes, int bracketCount) {
        if (bracketIndexes.size() == bracketCount) {
            result = Math.max(result, calculate(bracketIndexes));
        }

        for (int i = currentIndex; i < formula.length - 2; i += 2) {
            bracketIndexes.add(i);
            combinations(i + 4, bracketIndexes, bracketCount);
            bracketIndexes.remove(i);
        }
    }

    static int calculate(Set<Integer> bracketIndexes) {
        List<Object> newFormula = new LinkedList<>();

        // 괄호 값 계산
        for (int i = 0; i < formula.length;) {
            if (bracketIndexes.contains(i)) {
                newFormula.add(calculate(Character.getNumericValue(formula[i]), formula[i + 1], Character.getNumericValue(formula[i + 2])));
                i += 3;
            } else {
                if (i % 2 == 0) {
                    newFormula.add(Character.getNumericValue(formula[i]));
                } else {
                    newFormula.add(formula[i]);
                }
                i++;
            }
        }

        // 곱셈
        for (int i = 0; i < newFormula.size();) {
            Object currentElement = newFormula.get(i);

            if (currentElement.getClass().equals(Character.class) && currentElement.equals('*')) {
                int calculatedValue = calculate((Integer) newFormula.get(i - 1), '*', (Integer) newFormula.get(i + 1));
                for (int k = 0; k < 3; k++) {
                    newFormula.remove(i - 1);
                }
                newFormula.add(i - 1, calculatedValue);
            } else {
                i++;
            }
        }

        // 덧셈 및 뺄셈
        if (newFormula.size() == 1) {
            return (Integer) newFormula.get(0);
        } else if (newFormula.size() == 3) {
            return calculate((Integer) newFormula.get(0), (Character) newFormula.get(1), (Integer) newFormula.get(2));
        } else {
            int value = calculate((Integer) newFormula.get(0), (Character) newFormula.get(1), (Integer) newFormula.get(2));
            for (int i = 4; i < newFormula.size(); i += 2) {
                value = calculate(value, (Character) newFormula.get(i - 1), (Integer) newFormula.get(i));
            }
            return value;
        }
    }

    static int calculate(int leftValue, char operator, int rightValue) {
        if (operator == '+') {
            return leftValue + rightValue;
        } else if (operator == '-') {
            return leftValue - rightValue;
        } else {
            return leftValue * rightValue;
        }
    }
}
