/*
    [문제 설명]
     목표 정수 M(>=0)과 N개의 서로 다른 양의 정수가 주어집니다. 이들 정수를 원하는 만큼 합하여 목표 정수를 만들 수 있는지 알고 싶습니다.
     예를 들어 목표 정수가 7이고, [5, 3, 4, 7]이 주어지면 3+4는 7이므로 목표 정수를 만들 수 있습니다.   
    
    [입력 설명]
     첫 줄에는 테스트케이스 T(1<=T<=10)가 주어집니다. 각 테스트케이스마다 첫 줄에는 목표 정수 M(0<=M<=19)과 N(2<=N<=5)이 주어집니다. 그다음 줄에는 N개의 서로 다른 양의 정수 X(1<=X<=19)가 주어집니다.
    
    [출력 설명]
     N개의 정수를 원하는 만큼 합하여 목표 정수를 만들 수 있으면 true를 출력하고, 만들 수 없으면 false를 출력합니다.
    
    [입력 예시]
     3
     7 4
     5 3 4 7
     7 2
     2 4
     0 2
     2 4
    [출력 예시]
     true
     false
     true
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    private static final Reader reader = Reader.create();
    private static final Writer writer = Writer.create();
    private static int T;
    private static boolean result;

    public static void main(String[] args) throws Exception {
        T = reader.readInt();

        for (int i = 0; i < T; i++) {
            initResult();

            int[] inputs = reader.readInts();
            int M = inputs[0]; // M: 목표 정수
            int N = inputs[1]; // N: 서로 다른 N개의 개수라는 뜻

            int[] values = reader.readInts();
            canSum(M, values);
            writer.write(result);
        }

        writer.close();
    }

    private static void initResult() {
        result = false;
    }

    private static void canSum(int currentValue, int[] values) {
        if (currentValue <= 0) {
            if (currentValue == 0) {
                result = true;
            }
            return;
        }

        for (int i = 0; i < values.length; i++) {
            canSum(currentValue - values[i], values);
        }
    }

    private static class Reader {
        private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        public static Reader create() {
            return new Reader();
        }

        public int readInt() throws Exception {
            return Integer.parseInt(bufferedReader.readLine());
        }

        public int[] readInts() throws Exception {
            return parseToInts(bufferedReader.readLine().split(" "));
        }

        private int[] parseToInts(String[] strings) {
            int[] integers = new int[strings.length];

            for (int i = 0; i < strings.length; i++) {
                integers[i] = Integer.parseInt(strings[i]);
            }

            return integers;
        }
    }

    private static class Writer {
        private static final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        public static Writer create() {
            return new Writer();
        }

        public void write(boolean bool) throws Exception {
            bufferedWriter.write(String.valueOf(bool));
            writeNewLine();
            flush();
        }

        public void close() throws Exception {
            bufferedWriter.close();
        }

        private void writeNewLine() throws Exception {
            bufferedWriter.write("\n");
        }

        private void flush() throws Exception {
            bufferedWriter.flush();
        }
    }
}
