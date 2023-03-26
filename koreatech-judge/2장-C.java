/*
    [문제 설명]
     목표 정수 M(>=0)과 N개의 서로 다른 양의 정수가 주어집니다. 이들 정수를 원하는 만큼 합하여 목표 정수를 만들 수 있는 방법을 알고 싶습니다. 
     예를 들어 목표 정수가 7이고, [5, 3, 4, 7]이 주어지면 3+4, 4+3, 7은 모두 7이므로 목표 정수를 만들 수 있습니다.    
     
    [입력 설명]
     첫 줄에는 테스트케이스 T(1<=T<=10)가 주어집니다. 각 테스트케이스마다 첫 줄에는 목표 정수 M(0<=M<=19)과 N(2<=N<=5)이 주어집니다. 그다음 줄에는 N개의 서로 다른 양의 정수 X(1<=X<=19)가 주어집니다.
     
    [출력 설명]
     N개의 정수를 원하는 만큼 합하여 목표 정수를 만들 수 있으면 그것의 조합을 구성하는 수의 개수와 조합을 출력하고, 만들 수 없으면 -1를 출력합니다. 예를 들어 목표값이 8이고, [3, 2]가 주어지면 4 2 2 2 2 또는 3 3 3 2 등 답은 여러 가지가 존재할 수 있습니다. 여러 답이 있을 때 그 중 하나만 출력하면 됩니다.
     
    [입력 예시] 
     3
     7 4
     5 3 4 7
     7 2
     2 4
     0 2
     2 4
     
    [출력 예시] 
     2 3 4
     -1
     0
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Reader reader = Reader.create();
    private static final Writer writer = Writer.create();
    private static int T;
    private static List<Integer> result; // 조합

    public static void main(String[] args) throws Exception {
        T = reader.readInt();

        for (int i = 0; i < T; i++) {
            initResult();

            int[] inputs = reader.readInts();
            int M = inputs[0]; // M: 목표 정수
            int N = inputs[1]; // N: 서로 다른 N개의 개수라는 뜻

            int[] values = reader.readInts();
            howSum(M, values, new ArrayList<>(), 0);
            writer.write(result);
        }

        writer.close();
    }

    private static void initResult() {
        result = null;
    }

    private static void howSum(int currentValue, int[] values, List<Integer> combination, int count) {
        if (result != null) {
            return;
        }
        if (currentValue <= 0) {
            if (currentValue == 0) {
                result = new ArrayList<>(combination);
            }
            return;
        }

        for (int value : values) {
            combination.add(value);
            howSum(currentValue - value, values, combination, ++count);
            removeLastElement(combination);
        }
    }

    private static void removeLastElement(List<Integer> list) {
        list.remove(list.size() - 1);
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
            int[] ints = new int[strings.length];

            for (int i = 0; i < strings.length; i++) {
                ints[i] = Integer.parseInt(strings[i]);
            }

            return ints;
        }
    }

    private static class Writer {
        private static final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        public static Writer create() {
            return new Writer();
        }
        
        public void write(List<Integer> results) throws Exception {
            if (results == null) {
                bufferedWriter.write("-1");
            } else {
                bufferedWriter.write(makeStringFrom(results));
            }

            writeNewLine();
            flush();
        }

        private String makeStringFrom(List<Integer> results) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(results.size());

            for (Integer value : results) {
                stringBuilder.append(" ");
                stringBuilder.append(value);
            }

            return stringBuilder.toString();
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
