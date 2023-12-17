import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 메모리 초과

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N, M;
    static char[][] matrix;
    static boolean[][] visited;

    public static void main(String[] args) throws Exception {
        StringTokenizer stringTokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(stringTokenizer.nextToken());
        M = Integer.parseInt(stringTokenizer.nextToken());
        matrix = new char[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            matrix[i] = toCharArray(reader.readLine());
        }

        writer.write(String.format("%d\n", dfs(0, 0, 1)));
        writer.close();
    }

    static char[] toCharArray(String str) {
        char[] array = new char[str.length()];

        for (int i = 0; i < str.length(); i++) {
            array[i] = str.charAt(i);
        }

        return array;
    }

    static int dfs(int currentRow, int currentCol, int count) {
        if (!isValidLocation(currentRow, currentCol)) {
            return count - 1;
        } 
        
        if (visited[currentRow][currentCol]) {
            return -1;
        }

        visited[currentRow][currentCol] = true;
        int distance = digit(matrix[currentRow][currentCol]);

        int[] arr = new int[4];
        arr[0] = dfs(currentRow - distance, currentCol, count + 1);
        setVisitedToFalse(currentRow - distance, currentCol);
        arr[1] = dfs(currentRow, currentCol - distance, count + 1);
        setVisitedToFalse(currentRow, currentCol - distance);
        arr[2] = dfs(currentRow + distance, currentCol, count + 1);
        setVisitedToFalse(currentRow + distance, currentCol);
        arr[3] = dfs(currentRow, currentCol + distance, count + 1);
        setVisitedToFalse(currentRow, currentCol + distance);

        Arrays.sort(arr);

        return arr[0] == -1 ? -1 : arr[3];
    }

    static void setVisitedToFalse(int row, int col) {
        if (!isValidLocation(row, col)) {
            return;
        }

        visited[row][col] = false;
    }

    static boolean isValidLocation(int row, int col) {
        return (row >= 0 && row < N && col >= 0 && col < M) && matrix[row][col] != 'H';
    }

    static int digit(char ch) {
        return ch - '0';
    }
}
