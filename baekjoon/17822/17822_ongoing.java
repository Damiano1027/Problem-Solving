import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, T;
    static int[][] circles;
    static class Location {
        int circleNumber;
        int j;
        Location(int circleNumber, int j) {
            this.circleNumber = circleNumber;
            this.j = j;
        }
    }
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        T = Integer.parseInt(tokenizer.nextToken());

        circles = new int[N + 1][M];

        for (int circleNumber = 1; circleNumber <= N; circleNumber++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < M; j++) {
                circles[circleNumber][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        for (int i = 0; i < T; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(tokenizer.nextToken());
            int d = Integer.parseInt(tokenizer.nextToken());
            int k = Integer.parseInt(tokenizer.nextToken());

            for (int number = x; number <= N; number *= 2) {
                step1(number, d, k);
            }

            step2();
        }

        result = getResult();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void step1(int circleNumber, int direction, int count) {
        int[] circle = circles[circleNumber];
        int[] newCircle = new int[M];
        Arrays.fill(newCircle, -1);

        // 시계방향
        if (direction == 0) {
            for (int i = 0; i < M; i++) {
                if (circle[i] == -1) {
                    continue;
                }
                newCircle[add(i, count)] = circle[i];
            }
        }
        // 반시계방향
        else {
            for (int i = 0; i < M; i++) {
                if (circle[i] == -1) {
                    continue;
                }
                newCircle[subtract(i, count)] = circle[i];
            }
        }

        circles[circleNumber] = newCircle;
    }

    static int add(int a, int b) {
        return (a + b) % M;
    }

    static int subtract(int a, int b) {
        int result = a - b;
        if (result < 0) {
            result += M;
        }
        return result;
    }

    static void step2() {
        List<Location> locations = new ArrayList<>();
        boolean removable = false;

        for (int j = 0; j < M; j++) {
            for (int circleNumber = 1; circleNumber < N; circleNumber++) {
                if (circles[circleNumber][j] == -1 || circles[circleNumber + 1][j] == -1) {
                    continue;
                }

                if (circles[circleNumber][j] == circles[circleNumber + 1][j]) {
                    removable = true;
                    locations.add(new Location(circleNumber, j));
                    locations.add(new Location(circleNumber + 1, j));
                }
            }
        }

        for (int circleNumber = 1; circleNumber <= N; circleNumber++) {
            for (int j = 0; j < M; j++) {
                int leftValue, rightValue;

                leftValue = circles[circleNumber][j];
                if (j == M - 1) {
                    rightValue = circles[circleNumber][0];
                } else {
                    rightValue = circles[circleNumber][j + 1];
                }

                if (leftValue == -1 || rightValue == -1) {
                    continue;
                }

                if (leftValue == rightValue) {
                    removable = false;
                    locations.add(new Location(circleNumber, j));
                    if (j == M - 1) {
                        locations.add(new Location(circleNumber, 0));
                    } else {
                        locations.add(new Location(circleNumber, j + 1));
                    }
                }
            }
        }

        if (removable) {
            for (Location location: locations) {
                circles[location.circleNumber][location.j] = -1;
            }
        } else {
            int sum = 0;
            int count = 0;
            double average;

            for (int circleNumber = 1; circleNumber <= N; circleNumber++) {
                for (int j = 0; j < M; j++) {
                    int value = circles[circleNumber][j];
                    if (value == -1) {
                        continue;
                    }

                    sum += value;
                    count++;
                }
            }

            average = sum / (double) count;

            for (int circleNumber = 1; circleNumber <= N; circleNumber++) {
                for (int j = 0; j < M; j++) {
                    int value = circles[circleNumber][j];
                    if (value == -1) {
                        continue;
                    }

                    if (value > average) {
                        circles[circleNumber][j]--;
                    } else if (value < average) {
                        circles[circleNumber][j]++;
                    }
                }
            }
        }
    }

    static int getResult() {
        int sum = 0;

        for (int circleNumber = 1; circleNumber <= N; circleNumber++) {
            for (int j = 0; j < M; j++) {
                int value = circles[circleNumber][j];
                if (value == -1) {
                    continue;
                }

                sum += value;
            }
        }

        return sum;
    }
}
