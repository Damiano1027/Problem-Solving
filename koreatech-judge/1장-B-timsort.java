import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
 
public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
 
    public static void main(String[] args) throws Exception {
        int T = Integer.parseInt(br.readLine());
 
        for (int i = 0; i < T; i++) {
            int N = Integer.parseInt(br.readLine());
            int[] array = toIntArray(br.readLine().split(" "));
            timSort(array, 0, array.length);
            printArray(array);
        }
        bw.close();
    }
 
    private static int[] toIntArray(String[] stringArray) {
        int[] array = new int[stringArray.length];
 
        for (int i = 0; i < stringArray.length; i++) {
            array[i] = Integer.parseInt(stringArray[i]);
        }
 
        return array;
    }
 
    private static void printArray(int[] array) throws Exception {
        for (int i = 0; i < array.length; i++) {
            bw.write(array[i] + " ");
            bw.flush();
        }
        bw.write("\n");
        bw.flush();
    }
 
    // 배열을 끝까지 쪼갠 후 각가 삽입 정렬한 다음에, 다시 병합해나간다.
    private static void timSort(int[] array, int begin, int end) {
        if (end - begin <= 1) {
            return;
        }
 
        int middle = (begin + end) / 2;
 
        timSort(array, begin, middle);
        timSort(array, middle, end);
        insertionSort(array, begin, end);
    }
 
    // 쪼개진 배열을 삽입 정렬한다.
    private static void insertionSort(int[] array, int begin, int end) {
        for (int i = begin + 1; i < end; i++) {
            for (int j = i; j > begin; j--) {
                if (array[j - 1] < array[j]) {
                    break;
                }
 
                int temp = array[j - 1];
                array[j - 1] = array[j];
                array[j] = temp;
            }
        }
    }
 
    /**************************************************************
     Problem: 1192
     User: damiano1027
     Language: Java
     Result: 모두 맞음
     Time:2998 ms
     Memory:100924 kb
     ****************************************************************/
}
