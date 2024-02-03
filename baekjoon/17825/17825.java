import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static class Number {
        int number;
        Number(int number) {
            this.number = number;
        }
    }
    static Map<Number, List<Number>> staticMap = new HashMap<>();
    static Number startNumber;
    static int[] values = new int[10];
    static int result = -1;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());

        for (int i = 0; i < 10; i++) {
            values[i] = Integer.parseInt(tokenizer.nextToken());
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        set();
        Number[] numbers = new Number[4];
        for (int i = 0; i < 4; i++) {
            numbers[i] = startNumber;
        }

        dfs(0, 0, numbers);
    }

    static void set() {
        // --------------- 시작 ~ 10 ------------------
        Number fromNumber = new Number(0);
        startNumber = fromNumber;

        for (int i = 0; i < 5; i++) {
            Number toNumber = new Number(fromNumber.number + 2);
            staticMap.put(fromNumber, List.of(toNumber));
            fromNumber = toNumber;
        }

        // --------------- 10, 12, 13 ------------------
        Number redFromNumber = new Number(fromNumber.number + 2);
        Number blueFromNumber = new Number(fromNumber.number + 3);
        staticMap.put(fromNumber, List.of(redFromNumber, blueFromNumber));

        Number number12 = redFromNumber;
        Number number13 = blueFromNumber;

        // --------------- 12 ~ 20 ------------------
        fromNumber = number12;

        for (int i = 0; i < 4; i++) {
            Number toNumber = new Number(fromNumber.number + 2);
            staticMap.put(fromNumber, List.of(toNumber));
            fromNumber = toNumber;
        }

        Number number20 = fromNumber;

        // --------------- 20, 22, 22 ------------------
        redFromNumber = new Number(number20.number + 2);
        blueFromNumber = new Number(number20.number + 2);
        staticMap.put(number20, List.of(redFromNumber, blueFromNumber));

        Number redNumber22 = redFromNumber;
        Number blueNumber22 = blueFromNumber;

        // --------------- 13 ~ 25 ------------------
        fromNumber = number13;

        for (int i = 0; i < 2; i++) {
            Number toNumber = new Number(fromNumber.number + 3);
            staticMap.put(fromNumber, List.of(toNumber));
            fromNumber = toNumber;
        }

        Number number25 = new Number(fromNumber.number + 6);
        staticMap.put(fromNumber, List.of(number25));

        // --------------- 22 ~ 25 ------------------
        Number number24 = new Number(blueNumber22.number + 2);
        staticMap.put(blueNumber22, List.of(number24));

        staticMap.put(number24, List.of(number25));

        // --------------- 22 ~ 30 ------------------
        fromNumber = redNumber22;

        for (int i = 0; i < 4; i++) {
            Number toNumber = new Number(fromNumber.number + 2);
            staticMap.put(fromNumber, List.of(toNumber));
            fromNumber = toNumber;
        }

        Number number30 = fromNumber;

        // --------------- 30, 28, 32 ------------------
        Number blueNumber28 = new Number(number30.number - 2);
        Number redNumber32 = new Number(number30.number + 2);
        staticMap.put(number30, List.of(redNumber32, blueNumber28));

        // --------------- 28 ~ 25 ------------------
        fromNumber = blueNumber28;

        for (int i = 0; i < 2; i++) {
            Number toNumber = new Number(fromNumber.number - 1);
            staticMap.put(fromNumber, List.of(toNumber));
            fromNumber = toNumber;
        }

        staticMap.put(fromNumber, List.of(number25));

        // --------------- 25 ~ 40 ------------------
        fromNumber = number25;

        for (int i = 0; i < 3; i++) {
            Number toNumber = new Number(fromNumber.number + 5);
            staticMap.put(fromNumber, List.of(toNumber));
            fromNumber = toNumber;
        }

        Number number40 = fromNumber;

        // --------------- 32 ~ 40 ------------------
        fromNumber = redNumber32;

        for (int i = 0; i < 3; i++) {
            Number toNumber = new Number(fromNumber.number + 2);
            staticMap.put(fromNumber, List.of(toNumber));
            fromNumber = toNumber;
        }

        staticMap.put(fromNumber, List.of(number40));
    }

    static void dfs(int count, int score, Number[] numbers) {
        if (count == 10) {
            result = (result == -1) ? score : Math.max(result, score);
            return;
        }

        for (int i = 0; i < 4; i++) {
            Number[] copiedNumbers = Arrays.copyOf(numbers, 4);

            if (copiedNumbers[i] == null) {
                continue;
            }

            int addedScore = calculateScore(copiedNumbers, values[count], i);

            if (addedScore == -1) {
                continue;
            }

            dfs(count + 1, score + addedScore, copiedNumbers);
        }
    }

    static int calculateScore(Number[] numbers, int moveCount, int numberIndex) {
        Number currentNumber = numbers[numberIndex];

        if (staticMap.get(currentNumber) == null) {
            numbers[numberIndex] = null;
            return 0;
        }

        if (staticMap.get(currentNumber).size() == 2) {
            currentNumber = staticMap.get(currentNumber).get(1);

            for (int i = 0; i < moveCount - 1; i++) {
                if (staticMap.get(currentNumber) == null) {
                    numbers[numberIndex] = null;
                    return 0;
                }

                currentNumber = staticMap.get(currentNumber).get(0);
            }
        } else {
            for (int i = 0; i < moveCount; i++) {
                if (staticMap.get(currentNumber) == null) {
                    numbers[numberIndex] = null;
                    return 0;
                }

                currentNumber = staticMap.get(currentNumber).get(0);
            }
        }

        for (int i = 0; i < 4; i++) {
            if (i != numberIndex && currentNumber == numbers[i]) {
                return -1;
            }
        }

        numbers[numberIndex] = currentNumber;
        return currentNumber.number;
    }
}
