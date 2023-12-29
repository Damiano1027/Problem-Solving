import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, K;
    static int[] dRow = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dCol = {0, 1, 1, 1, 0, -1, -1, -1};
    static class FireBall {
        int mass, speed, direction;
        FireBall(int mass, int speed, int direction) {
            this.mass = mass;
            this.speed = speed;
            this.direction = direction;
        }
    }
    static List<FireBall>[][] matrix;
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        matrix = new List[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = new LinkedList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            matrix[Integer.parseInt(tokenizer.nextToken()) - 1][Integer.parseInt(tokenizer.nextToken()) - 1].add(new FireBall(
                    Integer.parseInt(tokenizer.nextToken()),
                    Integer.parseInt(tokenizer.nextToken()),
                    Integer.parseInt(tokenizer.nextToken())
            ));
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        int command = 0;

        while (command < K) {
            move();

            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    if (matrix[row][col].size() < 2) {
                        continue;
                    }

                    combineAndDivide(row, col);
                }
            }

            command++;
        }

        result = getSumOfMass();
    }

    static void move() {
        List<FireBall>[][] tempMatrix = new List[N][N];
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                tempMatrix[row][col] = new LinkedList<>();
            }
        }

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                for (int i = 0; i < matrix[row][col].size(); i++) {
                    FireBall fireBall = matrix[row][col].get(i);

                    int nextRow = (row + fireBall.speed * dRow[fireBall.direction]);
                    int nextCol = (col + fireBall.speed * dCol[fireBall.direction]);

                    nextRow = convert(nextRow);
                    nextCol = convert(nextCol);

                    matrix[row][col].remove(i);
                    i--;
                    tempMatrix[nextRow][nextCol].add(fireBall);
                }
            }
        }

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (tempMatrix[row][col].isEmpty()) {
                    continue;
                }

                matrix[row][col] = tempMatrix[row][col];
            }
        }
    }

    static void combineAndDivide(int row, int col) {
        int massSum = 0, speedSum = 0;
        for (FireBall fireBall : matrix[row][col]) {
            massSum += fireBall.mass;
            speedSum += fireBall.speed;
        }

        List<FireBall> dividedFireBalls = new LinkedList<>();
        int direction = isDirectionAllEven(matrix[row][col]) || isDirectionAllOdd(matrix[row][col]) ? 0 : 1;
        int mass = massSum / 5;
        int speed = speedSum / matrix[row][col].size();

        if (mass != 0) {
            for (int i = 0; i < 4; i++) {
                dividedFireBalls.add(new FireBall(mass, speed, direction));
                direction += 2;
            }
        }

        matrix[row][col] = new LinkedList<>(dividedFireBalls);
    }

    static int convert(int value) {
        if (value < 0) {
            do {
                value += N;
            } while (value < 0);
        } else if (value >= N) {
            do {
                value -= N;
            } while (value >= N);
        } else {
            return value;
        }
        return value;
    }

    static int getSumOfMass() {
        int sum = 0;

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                for (FireBall fireBall : matrix[row][col]) {
                    sum += fireBall.mass;
                }
            }
        }

        return sum;
    }

    static boolean isDirectionAllEven(List<FireBall> fireBalls) {
        for (FireBall fireBall : fireBalls) {
            if (fireBall.direction % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    static boolean isDirectionAllOdd(List<FireBall> fireBalls) {
        for (FireBall fireBall : fireBalls) {
            if (fireBall.direction % 2 != 1) {
                return false;
            }
        }
        return true;
    }
}
