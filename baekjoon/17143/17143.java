import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int R, C, M;
    static class Shark {
        int row, col, speed, direction, size;
        Shark(int row, int col, int speed, int direction, int size) {
            this.row = row;
            this.col = col;
            this.speed = speed;
            this.direction = direction;
            this.size = size;
        }
        void reverseDirection() {
            direction = (direction + 2) % 4;
        }
    }
    static Set<Shark>[][] staticMatrix;
    static Set<Shark> aliveSharks = new HashSet<>();
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, -1, 0, 1};
    static int result = 0;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        R = Integer.parseInt(tokenizer.nextToken());
        C = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());

        staticMatrix = new Set[R + 1][C + 1];
        for (int row = 1; row <= R; row++) {
            for (int col = 1; col <= C; col++) {
                staticMatrix[row][col] = new HashSet<>();
            }
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int row = Integer.parseInt(tokenizer.nextToken());
            int col = Integer.parseInt(tokenizer.nextToken());
            int speed = Integer.parseInt(tokenizer.nextToken());
            int direction = Integer.parseInt(tokenizer.nextToken());
            int size = Integer.parseInt(tokenizer.nextToken());

            if (direction == 1) {
                direction = 0;
            } else if (direction == 4) {
                direction = 1;
            }

            Shark shark = new Shark(row, col, speed, direction, size);
            staticMatrix[row][col].add(shark);
            aliveSharks.add(shark);
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        for (int col = 1; col <= C + 1; col++) {
            if (col <= C) {
                step2(col);
            }
            step3();
            afterStep();
        }
    }

    // 가장 가까운 상어를 잡는다.
    static void step2(int currentCol) {
        for (int row = 1; row <= R; row++) {
            if (!staticMatrix[row][currentCol].isEmpty()) {
                for (Shark shark: staticMatrix[row][currentCol]) {
                    result += shark.size;
                    aliveSharks.remove(shark);
                }
                staticMatrix[row][currentCol] = new HashSet<>();
                break;
            }
        }
    }

    // 상어들이 이동한다.
    static void step3() {
        for (Shark shark: aliveSharks) {
            move(shark);
        }
    }

    // 같은 칸에서 크기가 가장 큰 상어가 나머지 상어를 모두 잡아먹는다.
    static void afterStep() {
        Set<Shark> copiedAliveSharks = new HashSet<>(aliveSharks);

        for (Shark aliveShark: copiedAliveSharks) {
            if (staticMatrix[aliveShark.row][aliveShark.col].size() >= 2) {
                Shark biggestShark = null;
                for (Shark sharkInLocation: staticMatrix[aliveShark.row][aliveShark.col]) {
                    if (biggestShark == null) {
                        biggestShark = sharkInLocation;
                    } else {
                        biggestShark = (sharkInLocation.size > biggestShark.size) ? sharkInLocation : biggestShark;
                    }
                }

                staticMatrix[aliveShark.row][aliveShark.col].remove(biggestShark);

                for (Shark deadShark: staticMatrix[aliveShark.row][aliveShark.col]) {
                    aliveSharks.remove(deadShark);
                }

                staticMatrix[aliveShark.row][aliveShark.col] = new HashSet<>();
                staticMatrix[aliveShark.row][aliveShark.col].add(biggestShark);
            }
        }
    }

    static void move(Shark shark) {
        // 북쪽
        if (shark.direction == 0) {
            int nextRow = shark.row + dRow[shark.direction] * shark.speed;
            int sharkCol = shark.col;

            if (isInMatrix(nextRow, sharkCol)) {
                staticMatrix[shark.row][sharkCol].remove(shark);
                staticMatrix[nextRow][sharkCol].add(shark);
                shark.row = nextRow;
            } else {
                int remainingSpeed = shark.speed - (shark.row - 1);

                staticMatrix[shark.row][sharkCol].remove(shark);
                staticMatrix[1][sharkCol].add(shark);
                shark.row = 1;
                shark.reverseDirection();

                int dividedValue = (remainingSpeed - 1) / (R - 1);
                if (dividedValue % 2 == 0) {
                    if (remainingSpeed % (R - 1) == 0) {
                        nextRow = R;
                    } else {
                        nextRow = shark.row + dRow[shark.direction] * (remainingSpeed % (R - 1));
                    }

                    staticMatrix[shark.row][sharkCol].remove(shark);
                    staticMatrix[nextRow][sharkCol].add(shark);
                    shark.row = nextRow;
                } else {
                    staticMatrix[shark.row][sharkCol].remove(shark);
                    staticMatrix[R][sharkCol].add(shark);
                    shark.row = R;
                    shark.reverseDirection();

                    if (remainingSpeed % (R - 1) == 0) {
                        nextRow = 1;
                    } else {
                        nextRow = shark.row + dRow[shark.direction] * (remainingSpeed % (R - 1));
                    }

                    staticMatrix[shark.row][sharkCol].remove(shark);
                    staticMatrix[nextRow][sharkCol].add(shark);
                    shark.row = nextRow;
                }
            }
        }
        // 서쪽
        else if (shark.direction == 1) {
            int sharkRow = shark.row;
            int nextCol = shark.col + dCol[shark.direction] * shark.speed;

            if (isInMatrix(sharkRow, nextCol)) {
                staticMatrix[sharkRow][shark.col].remove(shark);
                staticMatrix[sharkRow][nextCol].add(shark);
                shark.col = nextCol;
            } else {
                int remainingSpeed = shark.speed - (shark.col - 1);

                staticMatrix[sharkRow][shark.col].remove(shark);
                staticMatrix[sharkRow][1].add(shark);
                shark.col = 1;
                shark.reverseDirection();

                int dividedValue = (remainingSpeed - 1) / (C - 1);
                if (dividedValue % 2 == 0) {
                    if (remainingSpeed % (C - 1) == 0) {
                        nextCol = C;
                    } else {
                        nextCol = shark.col + dCol[shark.direction] * (remainingSpeed % (C - 1));
                    }

                    staticMatrix[sharkRow][shark.col].remove(shark);
                    staticMatrix[sharkRow][nextCol].add(shark);
                    shark.col = nextCol;
                } else {
                    staticMatrix[sharkRow][shark.col].remove(shark);
                    staticMatrix[sharkRow][C].add(shark);
                    shark.col = C;
                    shark.reverseDirection();

                    if (remainingSpeed % (C - 1) == 0) {
                        nextCol = 1;
                    } else {
                        nextCol = shark.col + dCol[shark.direction] * (remainingSpeed % (C - 1));
                    }

                    staticMatrix[sharkRow][shark.col].remove(shark);
                    staticMatrix[sharkRow][nextCol].add(shark);
                    shark.col = nextCol;
                }
            }
        }
        // 남쪽
        else if (shark.direction == 2) {
            int nextRow = shark.row + dRow[shark.direction] * shark.speed;
            int sharkCol = shark.col;

            if (isInMatrix(nextRow, sharkCol)) {
                staticMatrix[shark.row][sharkCol].remove(shark);
                staticMatrix[nextRow][sharkCol].add(shark);
                shark.row = nextRow;
            } else {
                int remainingSpeed = shark.speed - (R - shark.row);

                staticMatrix[shark.row][sharkCol].remove(shark);
                staticMatrix[R][sharkCol].add(shark);
                shark.row = R;
                shark.reverseDirection();

                int dividedValue = (remainingSpeed - 1) / (R - 1);
                if (dividedValue % 2 == 0) {
                    if (remainingSpeed % (R - 1) == 0) {
                        nextRow = 1;
                    } else {
                        nextRow = shark.row + dRow[shark.direction] * (remainingSpeed % (R - 1));
                    }

                    staticMatrix[shark.row][sharkCol].remove(shark);
                    staticMatrix[nextRow][sharkCol].add(shark);
                    shark.row = nextRow;
                } else {
                    staticMatrix[shark.row][sharkCol].remove(shark);
                    staticMatrix[1][sharkCol].add(shark);
                    shark.row = 1;
                    shark.reverseDirection();

                    if (remainingSpeed % (R - 1) == 0) {
                        nextRow = R;
                    } else {
                        nextRow = shark.row + dRow[shark.direction] * (remainingSpeed % (R - 1));
                    }

                    staticMatrix[shark.row][sharkCol].remove(shark);
                    staticMatrix[nextRow][sharkCol].add(shark);
                    shark.row = nextRow;
                }
            }
        }
        // 동쪽
        else {
            int sharkRow = shark.row;
            int nextCol = shark.col + dCol[shark.direction] * shark.speed;

            if (isInMatrix(sharkRow, nextCol)) {
                staticMatrix[sharkRow][shark.col].remove(shark);
                staticMatrix[sharkRow][nextCol].add(shark);
                shark.col = nextCol;
            } else {
                int remainingSpeed = shark.speed - (C - shark.col);

                staticMatrix[sharkRow][shark.col].remove(shark);
                staticMatrix[sharkRow][C].add(shark);
                shark.col = C;
                shark.reverseDirection();

                int dividedValue = (remainingSpeed - 1) / (C - 1);
                if (dividedValue % 2 == 0) {
                    if (remainingSpeed % (C - 1) == 0) {
                        nextCol = 1;
                    } else {
                        nextCol = shark.col + dCol[shark.direction] * (remainingSpeed % (C - 1));
                    }

                    staticMatrix[sharkRow][shark.col].remove(shark);
                    staticMatrix[sharkRow][nextCol].add(shark);
                    shark.col = nextCol;
                } else {
                    staticMatrix[sharkRow][shark.col].remove(shark);
                    staticMatrix[sharkRow][1].add(shark);
                    shark.col = 1;
                    shark.reverseDirection();

                    if (remainingSpeed % (C - 1) == 0) {
                        nextCol = C;
                    } else {
                        nextCol = shark.col + dCol[shark.direction] * (remainingSpeed % (C - 1));
                    }

                    staticMatrix[sharkRow][shark.col].remove(shark);
                    staticMatrix[sharkRow][nextCol].add(shark);
                    shark.col = nextCol;
                }
            }
        }
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 1 && row <= R && col >= 1 && col <= C;
    }
}
