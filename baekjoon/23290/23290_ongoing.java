import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int M, S;
    static abstract class Information {}
    static class Fishes extends Information {
        List<Fish> fishes = new LinkedList<>();
    }
    static class Fish {
        int direction;
        Fish(int direction) {
            this.direction = direction;
        }
    }
    static class FishSmells extends Information {
        List<FishSmell> fishSmells = new ArrayList<>();
    }
    static class FishSmell {}
    static class Shark extends Information {}
    static class FishChange {
        Fish fish;
        int row, col;
        boolean add;
        FishChange(Fish fish, int row, int col, boolean add) {
            this.fish = fish;
            this.row = row;
            this.col = col;
            this.add = add;
        }
    }
    static class SharkMove {
        int row, col, direction;
        SharkMove(int row, int col, int direction) {
            this.row = row;
            this.col = col;
            this.direction = direction;
        }
    }
    static class SharkMovesResult {
        List<SharkMove> sharkMoves;
        int fishesCount;
        SharkMovesResult(List<SharkMove> sharkMoves, int fishesCount) {
            this.sharkMoves = sharkMoves;
            this.fishesCount = fishesCount;
        }
        int directionNumber() {
            StringBuilder stringBuilder = new StringBuilder();
            for (SharkMove sharkMove: sharkMoves) {
                stringBuilder.append(sharkMove.direction);
            }
            return Integer.parseInt(stringBuilder.toString());
        }
    }

    static Information[][] staticMatrix = new Information[5][5];
    static int[] dRow = {0, 0, -1, -1, -1, 0, 1, 1, 1}; // 인덱스 0은 의미 없음
    static int[] dCol = {0, -1, -1, 0, 1, 1, 1, 0, -1}; // 인덱스 0은 의미 없음
    static int[] odRow = {0, -1, 0, 1, 0}; // 인덱스 0은 의미 없음
    static int[] odCol = {0, 0, -1, 0, 1}; // 인덱스 0은 의미 없음
    static List<SharkMovesResult> sharkMovesResultList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        M = Integer.parseInt(tokenizer.nextToken());
        S = Integer.parseInt(tokenizer.nextToken());

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int fishRow = Integer.parseInt(tokenizer.nextToken());
            int fishCol = Integer.parseInt(tokenizer.nextToken());
            int fishDirection = Integer.parseInt(tokenizer.nextToken());

            if (staticMatrix[fishRow][fishCol] == null) {
                staticMatrix[fishRow][fishCol] = new Fishes();
            }
            ((Fishes) staticMatrix[fishRow][fishCol]).fishes.add(new Fish(fishDirection));
        }

        tokenizer = new StringTokenizer(reader.readLine());
        int sharkRow = Integer.parseInt(tokenizer.nextToken());
        int sharkCol = Integer.parseInt(tokenizer.nextToken());

        staticMatrix[sharkRow][sharkCol] = new Shark();
    }

    static void solve() {

    }

    static void step2() {
        List<FishChange> fishChanges = new ArrayList<>();

        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                if (!isFishes(staticMatrix[row][col])) {
                    continue;
                }

                Fishes fishes = (Fishes) staticMatrix[row][col];

                for (Fish fish: fishes.fishes) {
                    for (int i = 0; i < 8; i++) {
                        int aRow = row + dRow[fish.direction];
                        int aCol = col + dCol[fish.direction];

                        if (!isInMatrix(aRow, aCol) || isShark(staticMatrix[aRow][aCol]) || isFishSmell(staticMatrix[aRow][aCol])) {
                            fish.direction--;
                            if (fish.direction < 1) {
                                fish.direction = 8;
                            }
                            continue;
                        }

                        fishChanges.add(new FishChange(fish, row, col, false));
                        fishChanges.add(new FishChange(fish, aRow, aCol, true));
                        break;
                    }
                }
            }
        }

        for (FishChange fishChange: fishChanges) {
            if (fishChange.add) {
                if (staticMatrix[fishChange.row][fishChange.col] == null) {
                    staticMatrix[fishChange.row][fishChange.col] = new Fishes();
                }
                ((Fishes) staticMatrix[fishChange.row][fishChange.col]).fishes.add(fishChange.fish);
            } else {
                ((Fishes) staticMatrix[fishChange.row][fishChange.col]).fishes.remove(fishChange.fish);
                if (((Fishes) staticMatrix[fishChange.row][fishChange.col]).fishes.isEmpty()) {
                    staticMatrix[fishChange.row][fishChange.col] = null;
                }
            }
        }
    }

    static void step3() {
        int sharkRow = 0, sharkCol = 0;

        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                if (isShark(staticMatrix[row][col])) {
                    sharkRow = row;
                    sharkCol = col;
                    break;
                }
            }
        }

        makeSharkMovesList(new ArrayList<>(), 0, sharkRow, sharkCol);

        sharkMovesResultList.sort(((o1, o2) -> {
            if (o1.fishesCount > o2.fishesCount) {
                return -1;
            } else if (o1.fishesCount < o2.fishesCount) {
                return 1;
            }

            return o1.directionNumber() - o2.directionNumber();
        }));

        SharkMovesResult sharkMovesResult = sharkMovesResultList.get(0);
        sharkMovesResultList = new ArrayList<>();

        for (SharkMove sharkMove: sharkMovesResult.sharkMoves) {
            if (isFishes(staticMatrix[sharkMove.row][sharkMove.col])) {
                int fishesCount = ((Fishes) staticMatrix[sharkMove.row][sharkMove.col]).fishes.size();
                staticMatrix[sharkMove.row][sharkMove.col] = new FishSmells();
                for (int i = 0; i < 3; i++) {
                    ((FishSmells) staticMatrix[sharkMove.row][sharkMove.col]).fishSmells.add(new FishSmell());
                }
            }
        }


        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {

            }
        }
    }

    static void makeSharkMovesList(List<SharkMove> sharkMoves, int fishesCount, int startRow, int startCol) {
        SharkMove currentMove;

        if (sharkMoves.isEmpty()) {
            currentMove = new SharkMove(startRow, startCol, -1);
        } else {
            currentMove = sharkMoves.get(sharkMoves.size() - 1);
            if (isFishes(staticMatrix[currentMove.row][currentMove.col])) {
                fishesCount += ((Fishes) staticMatrix[currentMove.row][currentMove.col]).fishes.size();
            }
        }

        if (sharkMoves.size() >= 3) {
            sharkMovesResultList.add(new SharkMovesResult(new ArrayList<>(sharkMoves), fishesCount));
            return;
        }

        for (int i = 1; i <= 4; i++) {
            int aRow = currentMove.row + odRow[i];
            int aCol = currentMove.col + odCol[i];

            if (!isInMatrix(aRow, aCol)) {
                continue;
            }

            sharkMoves.add(new SharkMove(aRow, aCol, i));
            makeSharkMovesList(sharkMoves, fishesCount, startRow, startCol);
            sharkMoves.remove(sharkMoves.size() - 1);
        }
    }

    static Information[][] copiedMatrix() {
        Information[][] copiedMatrix = new Information[5][5];

        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                copiedMatrix[row][col] = staticMatrix[row][col];
            }
        }

        return copiedMatrix;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 1 && row <= 4 && col >= 1 && col <= 4;
    }

    static boolean isFishes(Information information) {
        return information.getClass().equals(Fishes.class);
    }

    static boolean isShark(Information information) {
        return information.getClass().equals(Shark.class);
    }

    static boolean isFishSmell(Information information) {
        return information.getClass().equals(FishSmells.class);
    }
}
