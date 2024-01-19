import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static class Animal {
        int row, col, direction;
    }
    static class Fish extends Animal implements Comparable<Fish> {
        int number;
        Fish(int row, int col, int direction, int number) {
            this.row = row;
            this.col = col;
            this.direction = direction;
            this.number = number;
        }
        public int compareTo(Fish fish) {
            return number - fish.number;
        }
        public String toString() {
            return String.format("%d ", number);
        }
    }
    static class Shark extends Animal {
        Shark(int row, int col, int direction) {
            this.row = row;
            this.col = col;
            this.direction = direction;
        }
        public String toString() {
            return "s ";
        }
    }
    // 인덱스 0에 있는 값은 의미 없음
    static int[] dRow = {0, -1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dCol = {0, 0, -1, -1, -1, 0, 1, 1, 1};
    static Animal[][] matrix = new Animal[4][4];
    static Shark shark;
    static List<Fish> fishes = new LinkedList<>();

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
    }

    static void solve() {
        step1();
        step2();
        print();
    }

    // 1. 상어가 (0, 0)에 들어가고, 상어의 방향은 해당 위치에 있던 물고기의 방향과 같음
    static void step1() {
        shark = new Shark(0, 0, matrix[0][0].direction);
        matrix[0][0] = shark;
        fishes.remove(0);
    }

    static void step2() {
        subStep2();
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
    static void subStep2() {
        Collections.sort(fishes);

        for (Fish fish: fishes) {
            //System.out.println(fish);
            subStep2AboutOneFish(fish);
            //print();
            //System.out.println("-------------");
        }
    }

    static void subStep2AboutOneFish(Fish fish) {
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

    static void subStep3() {
        int nextSharkRow = shark.row + dRow[shark.direction];
        int nextSharkCol = shark.col + dCol[shark.direction];

        while (isInMatrix(nextSharkRow, nextSharkCol)) {
            if (!matrix[nextSharkRow][nextSharkCol].getClass().equals(Fish.class)) {
                continue;
            }
            
            


        }


    }



    static void print() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(matrix[i][j].toString());
            }
            System.out.println();
        }
    }
}
