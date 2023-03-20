import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
 
public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
 
    public static void main(String[] args) throws Exception {
        int T = Integer.parseInt(br.readLine());
 
        for (int i = 0; i < T; i++) {
            int N = Integer.parseInt(br.readLine());
            int[] array = toIntArray(br.readLine().split(" "));
            mergeSort(array);
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
 
    // System.out을 이용할 경우 시간초과가 발생한다.
    private static void printArray(int[] array) throws Exception {
        for (int i = 0; i < array.length; i++) {
            bw.write(array[i] + " ");
            bw.flush();
        }
        bw.write("\n");
        bw.flush();
    }
 
    private static void mergeSort(int[] array) {
        if (array.length <= 1) {
            return;
        }
 
        int middle = array.length / 2;
        int[] leftArray = Arrays.copyOfRange(array, 0, middle);
        int[] rightArray = Arrays.copyOfRange(array, middle, array.length);
 
        mergeSort(leftArray);
        mergeSort(rightArray);
        merge(array, leftArray, rightArray);
    }
 
    private static void merge(int[] array, int[] leftArray, int[] rightArray) {
        int index = 0;
        int leftIndex = 0;
        int rightIndex = 0;
 
        // 왼쪽 배열과 오른쪽 배열의 요소들을 앞부분부터 비교하면서 작은 수를 결과 배열에 담는다.
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (leftArray[leftIndex] < rightArray[rightIndex]) {
                array[index] = leftArray[leftIndex];
                leftIndex++;
            } else {
                array[index] = rightArray[rightIndex];
                rightIndex++;
            }
            index++;
        }
 
        // 왼쪽 배열 또는 오른쪽 배열에 값이 남아있는 경우 전부 결과 배열에 담아준다.
        while (leftIndex < leftArray.length) {
            array[index] = leftArray[leftIndex];
            leftIndex++;
            index++;
        }
        while (rightIndex < rightArray.length) {
            array[index] = rightArray[rightIndex];
            rightIndex++;
            index++;
        }
    }
 
    /**************************************************************
     Problem: 1192
     User: damiano1027
     Language: Java
     Result: 모두 맞음
     Time:2028 ms
     Memory:117724 kb
 
     [공간복잡도 분석]
     재귀 호출의 경우는 depth가 공간복잡도가 된다.
     만약 배열의 길이가 n이라면
     재귀호출하면서 2^n 부분으로 배열이 쪼개지므로
     공간복잡도는 O(logN) 이다.
     ****************************************************************/
}

