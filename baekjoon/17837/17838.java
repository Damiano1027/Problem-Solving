import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, K;
    static char[][] staticColorMatrix;
    static class Horse {
        int number;
        int row, col;
        int direction; // 0: 북, 1: 서, 2: 남, 3: 동
        Horse(int number, int row, int col, int direction) {
            this.number = number;
            this.row = row;
            this.col = col;
            this.direction = direction;
        }
        void reverseDirection() {
            direction = (direction + 2) % 4;
        }
    }
    static List<Horse>[][] staticHorseMatrix;
    static Horse[] horses;
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int result = -1;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        staticColorMatrix = new char[N + 1][N + 1];
        staticHorseMatrix = new List[N + 1][N + 1];
        horses = new Horse[K + 1];

        for (int row = 1; row <= N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 1; col <= N; col++) {
                int value = Integer.parseInt(tokenizer.nextToken());

                if (value == 0) {
                    staticColorMatrix[row][col] = 'w';
                } else if (value == 1) {
                    staticColorMatrix[row][col] = 'r';
                } else {
                    staticColorMatrix[row][col] = 'b';
                }
            }
        }

        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                staticHorseMatrix[row][col] = new LinkedList<>();
            }
        }

        for (int i = 1; i <= K; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int row = Integer.parseInt(tokenizer.nextToken());
            int col = Integer.parseInt(tokenizer.nextToken());
            int direction = Integer.parseInt(tokenizer.nextToken());

            if (direction == 1) {
                direction = 3;
            } else if (direction == 2) {
                direction = 1;
            } else if (direction == 3) {
                direction = 0;
            } else {
                direction = 2;
            }

            horses[i] = new Horse(i, row, col, direction);
            staticHorseMatrix[row][col].add(horses[i]);
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        int turn = 1;

        label:
        while (turn < 1000) {
            for (int i = 1; i <= K; i++) {
                moveHorse(i);

                if (isEnd()) {
                    result = turn;
                    break label;
                }
            }

            turn++;
        }
    }

    static void moveHorse(int horseNumber) {
        Horse currentHorse = horses[horseNumber];
        int nextRow = currentHorse.row + dRow[currentHorse.direction];
        int nextCol = currentHorse.col + dCol[currentHorse.direction];

        // 흰색인 경우
        if (isInMatrix(nextRow, nextCol) && staticColorMatrix[nextRow][nextCol] == 'w') {
            List<Horse> currentLocationHorses = staticHorseMatrix[currentHorse.row][currentHorse.col];

            for (int i = 0; i < currentLocationHorses.size(); i++) {
                Horse horse = currentLocationHorses.get(i);

                if (horse.number == horseNumber) {
                    for (int j = i; j < currentLocationHorses.size();) {
                        Horse removedHorse = currentLocationHorses.remove(j);
                        staticHorseMatrix[nextRow][nextCol].add(removedHorse);
                        removedHorse.row = nextRow;
                        removedHorse.col = nextCol;
                    }

                    break;
                }
            }
        }
        // 빨간색인 경우
        else if (isInMatrix(nextRow, nextCol) && staticColorMatrix[nextRow][nextCol] == 'r') {
            List<Horse> currentLocationHorses = staticHorseMatrix[currentHorse.row][currentHorse.col];

            for (int i = 0; i < currentLocationHorses.size(); i++) {
                Horse horse = currentLocationHorses.get(i);

                if (horse.number == horseNumber) {
                    for (int j = currentLocationHorses.size() - 1; j >= i; j--) {
                        Horse removedHorse = currentLocationHorses.remove(j);
                        staticHorseMatrix[nextRow][nextCol].add(removedHorse);
                        removedHorse.row = nextRow;
                        removedHorse.col = nextCol;
                    }

                    break;
                }
            }
        }
        // 파란색인 경우
        else if (!isInMatrix(nextRow, nextCol) || staticColorMatrix[nextRow][nextCol] == 'b') {
            List<Horse> currentLocationHorses = staticHorseMatrix[currentHorse.row][currentHorse.col];

            for (int i = 0; i < currentLocationHorses.size(); i++) {
                Horse horse = currentLocationHorses.get(i);

                if (horse.number == horseNumber) {
                    horse.reverseDirection();
                    int reversedNextRow = horse.row + dRow[horse.direction];
                    int reversedNextCol = horse.col + dCol[horse.direction];

                    if (isInMatrix(reversedNextRow, reversedNextCol) && staticColorMatrix[reversedNextRow][reversedNextCol] != 'b') {
                        if (staticColorMatrix[reversedNextRow][reversedNextCol] == 'w') {
                            for (int j = i; j < currentLocationHorses.size();) {
                                Horse removedHorse = currentLocationHorses.remove(j);
                                staticHorseMatrix[reversedNextRow][reversedNextCol].add(removedHorse);
                                removedHorse.row = reversedNextRow;
                                removedHorse.col = reversedNextCol;
                            }
                        } else {
                            for (int j = currentLocationHorses.size() - 1; j >= i; j--) {
                                Horse removedHorse = currentLocationHorses.remove(j);
                                staticHorseMatrix[reversedNextRow][reversedNextCol].add(removedHorse);
                                removedHorse.row = reversedNextRow;
                                removedHorse.col = reversedNextCol;
                            }
                        }
                    }

                    break;
                }
            }
        }
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 1 && row <= N && col >= 1 && col <= N;
    }

    static boolean isEnd() {
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                if (staticHorseMatrix[row][col].size() >= 4) {
                    return true;
                }
            }
        }
        return false;
    }
}
