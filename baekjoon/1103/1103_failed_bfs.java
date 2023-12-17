import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 틀림

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

        writer.write(String.format("%d\n", bfs(0, 0)));
        writer.close();
    }

    static char[] toCharArray(String str) {
        char[] array = new char[str.length()];

        for (int i = 0; i < str.length(); i++) {
            array[i] = str.charAt(i);
        }

        return array;
    }

    static int bfs(int startRow, int startCol) {
        Queue<State> queue = new LinkedList<>();
        queue.add(new State(startRow, startCol, 1));
        visited[startRow][startCol] = true;
        int max = 1;

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            max = currentState.count;
            visited[currentState.row][currentState.col] = true;
            int distance = digit(matrix[currentState.row][currentState.col]);

            if (isValidLocation(currentState.row - distance, currentState.col)) {
                if (visited[currentState.row - distance][currentState.col]) {
                    return -1;
                } else {
                    queue.add(new State(currentState.row - distance, currentState.col, currentState.count + 1));
                }
            }
            if (isValidLocation(currentState.row, currentState.col - distance)) {
                if (visited[currentState.row][currentState.col - distance]) {
                    return -1;
                } else {
                    queue.add(new State(currentState.row, currentState.col - distance, currentState.count + 1));
                }
            }
            if (isValidLocation(currentState.row + distance, currentState.col)) {
                if (visited[currentState.row + distance][currentState.col]) {
                    return -1;
                } else {
                    queue.add(new State(currentState.row + distance, currentState.col, currentState.count + 1));
                }
            }
            if (isValidLocation(currentState.row, currentState.col + distance)) {
                if (visited[currentState.row][currentState.col + distance]) {
                    return -1;
                } else {
                    queue.add(new State(currentState.row, currentState.col + distance, currentState.count + 1));
                }
            }
        }

        return max;
    }

    static class State {
        int row;
        int col;
        int count;

        State(int row, int col, int count) {
            this.row = row;
            this.col = col;
            this.count = count;
        }
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
