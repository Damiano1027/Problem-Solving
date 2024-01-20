import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static abstract class Animal {
        int row, col, direction;
        Animal copy() {
            if (this.getClass().equals(Fish.class)) {
                Fish fish = (Fish) this;
                return new Fish(fish.row, fish.col, fish.direction, fish.number);
            } else {
                return new Shark(row, col, direction);
            }
        }
    }
    static class Fish extends Animal implements Comparable<Fish> {
        int number;
        Fish(int row, int col, int direction, int number) {
            this.row = row;
            this.col = col;
            this.direction = direction;
            this.number = number;
        }
        public boolean equals(Object o) {
            Fish fish = (Fish) o;
            return number == fish.number;
        }
        public int compareTo(Fish fish) {
            return number - fish.number;
        }
        public String toString() {
            return String.format("(%d, %d) ", number, direction);
        }
    }
    static class Shark extends Animal {
        Shark(int row, int col, int direction) {
            this.row = row;
            this.col = col;
            this.direction = direction;
        }
        public String toString() {
            return "sss " + direction + " ";
        }
    }
    static class Location {
        int row, col;
        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    // 인덱스 0에 있는 값은 의미 없음
    static int[] dRow = {0, -1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dCol = {0, 0, -1, -1, -1, 0, 1, 1, 1};
    static Animal[][] matrix = new Animal[4][4];
    static Shark shark;
    static List<Fish> fishes = new LinkedList<>();
    static int result;

    public static void main(String[] args) throws Exception {
        for (int row = 0; row < 4; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 0; col < 4; col++) {
                int number = Integer.parseInt(tokenizer.nextToken());
                int direction = Integer.parseInt(tokenizer.nextToken());
                Fish fish = new Fish(row, col, direction, number);
                matrix[row][col] = fish;
                fishes.add(fish);
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        result += step1();
        step2(matrix, fishes);
        result += step3(copiedMatrix(matrix), (Shark) shark.copy(), new LinkedList<>(fishes), 0);
    }

    // 1. 상어가 (0, 0)에 들어가고, 상어의 방향은 해당 위치에 있던 물고기의 방향과 같음
    static int step1() {
        Fish fish = (Fish) matrix[0][0];
        shark = new Shark(0, 0, fish.direction);
        matrix[0][0] = shark;
        fishes.remove(0);
        return fish.number;
    }

    /*
        2. 물고기가 번호가 작은 순서부터 각 물고기가 차례대로 다음을 수행한다.
           - 한칸을 이동한다.
             - 이동할 수 있는 칸: 빈칸 or 다른 물고기가 있는 칸
             - 이동할 수 없는 칸: 상어가 있는 칸 or 공간의 경계를 넘는 칸
             - 이동할 수 있는 방향을 찾을때까지 방향을 45도 반시계 방향으로 회전
             - 이동할 수 있는 칸이 없으면 이동하지 않음
           - 이동하게 되면 물고기의 서로의 위치를 바꾼다.
     */
    static void step2(Animal[][] matrix, List<Fish> fishes) {
        Collections.sort(fishes);
        for (Fish fish: fishes) {
            step2AboutOneFish(fish, matrix);
        }
    }
    static void step2AboutOneFish(Fish fish, Animal[][] matrix) {
        for (int count = 0; count < 8; count++) {
            int nextRow = fish.row + dRow[fish.direction];
            int nextCol = fish.col + dCol[fish.direction];

            if (!isInMatrix(nextRow, nextCol)) {
                fish.direction++;
                if (fish.direction > 8) {
                    fish.direction = 1;
                }
                continue;
            }

            if (matrix[nextRow][nextCol] == null) {
                matrix[fish.row][fish.col] = null;
                fish.row = nextRow;
                fish.col = nextCol;
                matrix[nextRow][nextCol] = fish;
                break;
            } else if (matrix[nextRow][nextCol].getClass().equals(Fish.class)) {
                swapFish(fish, (Fish) matrix[nextRow][nextCol]);
                break;
            }

            fish.direction++;
            if (fish.direction > 8) {
                fish.direction = 1;
            }
        }
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 0 && row < 4 && col >= 0 && col < 4;
    }

    static void swapFish(Fish fish1, Fish fish2) {
        matrix[fish2.row][fish2.col] = fish1;
        matrix[fish1.row][fish1.col] = fish2;

        int oldFish1Row = fish1.row;
        int oldFish1Col = fish1.col;
        fish1.row = fish2.row;
        fish1.col = fish2.col;
        fish2.row = oldFish1Row;
        fish2.col = oldFish1Col;
    }

    /*
        3. 상어가 다음과 같이 이동한다.
           - 방향에 있는 칸중에 아무 칸이나 이동할 수 있음. 이동한 칸에 있는 물고기의 방향을 가지게 됨
             - 물고기가 없는 칸으로는 이동 불가능
           - 이동할 수 있는 칸이 없으면 공간에서 벗어남
     */
    static int step3(Animal[][] rMatrix, Shark rShark, List<Fish> rFishes, int fishNumberSum) {
        print(rMatrix);

        List<Location> fishLocationsInDirection = new ArrayList<>();

        int nextRow = rShark.row + dRow[rShark.direction];
        int nextCol = rShark.col + dCol[rShark.direction];

        while (isInMatrix(nextRow, nextCol)) {
            Animal animal = rMatrix[nextRow][nextCol];

            // 비어있거나 물고기가 아닌 경우 건너뜀
            if (animal == null || !animal.getClass().equals(Fish.class)) {
                nextRow += dRow[rShark.direction];
                nextCol += dCol[rShark.direction];
                continue;
            }

            fishLocationsInDirection.add(new Location(nextRow, nextCol));
            nextRow += dRow[rShark.direction];
            nextCol += dCol[rShark.direction];
        }

        if (fishLocationsInDirection.isEmpty()) {
            return fishNumberSum;
        }

        int max = Integer.MIN_VALUE;

        for (Location fishLocation: fishLocationsInDirection) {
            Animal[][] copiedMatrix = copiedMatrix(rMatrix);
            Shark copiedShark = (Shark) rShark.copy();
            List<Fish> copiedFishes = new LinkedList<>(rFishes);
            Fish copiedFish = (Fish) copiedMatrix[fishLocation.row][fishLocation.col];

            copiedMatrix[copiedShark.row][copiedShark.col] = null;
            copiedMatrix[fishLocation.row][fishLocation.col] = copiedShark;
            copiedShark.row = fishLocation.row;
            copiedShark.col = fishLocation.col;
            copiedShark.direction = copiedFish.direction;
            copiedFishes.remove(copiedFish);

            step2(copiedMatrix, copiedFishes);
            max = Math.max(max, step3(copiedMatrix, copiedShark, copiedFishes, fishNumberSum + copiedFish.number));

            fishes.add(copiedFish);
        }

        return max;
    }

    static Animal[][] copiedMatrix(Animal[][] oldMatrix) {
        Animal[][] copiedMatrix = new Animal[4][4];

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (oldMatrix[row][col] == null) {
                    continue;
                }
                copiedMatrix[row][col] = oldMatrix[row][col].copy();
            }
        }

        return copiedMatrix;
    }

    static void print(Animal[][] matrix) {
        System.out.println("-----------------------");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] == null) {
                    System.out.print("e    ");
                } else {
                    System.out.print(matrix[i][j].toString());
                }
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }
}
