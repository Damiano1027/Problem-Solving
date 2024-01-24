import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int M, S;
    interface Information {}
    static class Fish implements Information {
        int direction;
        Fish(int direction) {
            this.direction = direction;
        }
        Fish copy() {
            return new Fish(direction);
        }
        public String toString() {
            return String.format("f%d", direction);
        }
    }
    static class FishSmell implements Information {
        public String toString() {
            return "fs";
        }
    }
    static class Shark implements Information {
        public String toString() {
            return "sk";
        }
    }
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
        public String toString() {
            return String.format("[%d %d | %d]", row, col, direction);
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
    static class CopiedFishHistory {
        int row, col;
        List<Fish> fishes = new ArrayList<>();
        CopiedFishHistory(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    static class FishSmellHistory {
        int row, col;
        List<FishSmell> fishSmells = new ArrayList<>();
        FishSmellHistory(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    static List<Information>[][] staticMatrix = new List[5][5];
    static {
        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                staticMatrix[row][col] = new LinkedList<>();
            }
        }
    }
    static int[] dRow = {0, 0, -1, -1, -1, 0, 1, 1, 1}; // 인덱스 0은 의미 없음
    static int[] dCol = {0, -1, -1, 0, 1, 1, 1, 0, -1}; // 인덱스 0은 의미 없음
    static int[] odRow = {0, -1, 0, 1, 0}; // 인덱스 0은 의미 없음
    static int[] odCol = {0, 0, -1, 0, 1}; // 인덱스 0은 의미 없음
    static List<SharkMovesResult> sharkMovesResultList = new ArrayList<>();
    static List<List<FishSmellHistory>> fishSmellHistoriesList = new ArrayList<>();
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        M = Integer.parseInt(tokenizer.nextToken());
        S = Integer.parseInt(tokenizer.nextToken());

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int fishRow = Integer.parseInt(tokenizer.nextToken());
            int fishCol = Integer.parseInt(tokenizer.nextToken());
            int fishDirection = Integer.parseInt(tokenizer.nextToken());

            staticMatrix[fishRow][fishCol].add(new Fish(fishDirection));
        }

        tokenizer = new StringTokenizer(reader.readLine());
        int sharkRow = Integer.parseInt(tokenizer.nextToken());
        int sharkCol = Integer.parseInt(tokenizer.nextToken());

        staticMatrix[sharkRow][sharkCol].add(0, new Shark());

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        for (int count = 0; count < S; count++) {
            //System.out.println(count);
            List<CopiedFishHistory> copiedFishHistories = step1();
            step2();
            step3();
            if (count >= 2) {
                step4(count - 2);
            }
            step5(copiedFishHistories);
            /*print();
            System.out.println("--------------------------");*/
        }

        result = getResult();
    }

    static List<CopiedFishHistory> step1() {
        List<CopiedFishHistory> copiedFishHistories = new ArrayList<>();

        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                CopiedFishHistory history = new CopiedFishHistory(row, col);

                for (Information information: staticMatrix[row][col]) {
                    if (!information.getClass().equals(Fish.class)) {
                        continue;
                    }

                    history.fishes.add(((Fish) information).copy());
                }

                if (!history.fishes.isEmpty()) {
                    copiedFishHistories.add(history);
                }
            }
        }

        //System.out.println("step1 completed!");

        return copiedFishHistories;
    }

    static void step2() {
        List<FishChange> fishChanges = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                /*if (!containsFish(staticMatrix[row][col])) {
                    continue;
                }*/

                for (Information information: staticMatrix[row][col]) {
                    //System.out.println(information);
                    if (!information.getClass().equals(Fish.class)) {
                        continue;
                    }
                    Fish fish = (Fish) information;

                    for (int i = 0; i < 8; i++) {
                        int aRow = row + dRow[fish.direction];
                        int aCol = col + dCol[fish.direction];

                        if (!isInMatrix(aRow, aCol) || containsShark(staticMatrix[aRow][aCol]) || containsFishSmell(staticMatrix[aRow][aCol])) {
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
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        startTime = System.currentTimeMillis();
        for (FishChange fishChange: fishChanges) {
            if (fishChange.add) {
                staticMatrix[fishChange.row][fishChange.col].add(fishChange.fish);
            } else {
                staticMatrix[fishChange.row][fishChange.col].remove(fishChange.fish);
            }
        }
        endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);

        System.out.println("step2 completed!");
    }

    static void step3() {
        int sharkRow = 0, sharkCol = 0;

        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                if (containsShark(staticMatrix[row][col])) {
                    sharkRow = row;
                    sharkCol = col;
                    break;
                }
            }
        }

        boolean[][] visited = new boolean[5][5];
        visited[sharkRow][sharkCol] = true;
        makeSharkMovesList(new ArrayList<>(), 0, visited, sharkRow, sharkCol);

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

        Shark shark = removeShark(staticMatrix[sharkRow][sharkCol]);
        List<FishSmellHistory> fishSmellHistories = new ArrayList<>();

        // System.out.println(sharkMovesResult.sharkMoves);

        for (SharkMove sharkMove: sharkMovesResult.sharkMoves) {
            sharkRow = sharkMove.row;
            sharkCol = sharkMove.col;

            FishSmellHistory fishSmellHistory = new FishSmellHistory(sharkRow, sharkCol);

            if (containsFish(staticMatrix[sharkMove.row][sharkMove.col])) {
                int fishesCount = getFishCount(staticMatrix[sharkMove.row][sharkMove.col]);
                removeFishes(staticMatrix[sharkMove.row][sharkMove.col]);
                for (int i = 0; i < fishesCount; i++) {
                    FishSmell fishSmell = new FishSmell();
                    staticMatrix[sharkMove.row][sharkMove.col].add(fishSmell);
                    fishSmellHistory.fishSmells.add(fishSmell);
                }
            }

            if (!fishSmellHistory.fishSmells.isEmpty()) {
                fishSmellHistories.add(fishSmellHistory);
            }
        }

        fishSmellHistoriesList.add(fishSmellHistories);
        staticMatrix[sharkRow][sharkCol].add(shark);

        //System.out.println("step3 completed!");
    }

    static void step4(int count) {
        List<FishSmellHistory> fishSmellHistories = fishSmellHistoriesList.get(count);

        for (FishSmellHistory history: fishSmellHistories) {
            for (FishSmell fishSmell: history.fishSmells) {
                removeFishSmell(fishSmell, staticMatrix[history.row][history.col]);
            }
        }

        //System.out.println("step4 completed!");
    }

    static void step5(List<CopiedFishHistory> copiedFishHistories) {
        for (CopiedFishHistory history: copiedFishHistories) {
            for (Fish fish: history.fishes) {
                staticMatrix[history.row][history.col].add(fish);
            }
        }

        //System.out.println("step5 completed!");
    }

    static void makeSharkMovesList(List<SharkMove> sharkMoves, int fishesCount, boolean[][] visited, int startRow, int startCol) {
        SharkMove currentMove;

        if (sharkMoves.isEmpty()) {
            currentMove = new SharkMove(startRow, startCol, -1);
        } else {
            currentMove = sharkMoves.get(sharkMoves.size() - 1);
            fishesCount += getFishCount(staticMatrix[currentMove.row][currentMove.col]);
        }

        if (sharkMoves.size() >= 3) {
            sharkMovesResultList.add(new SharkMovesResult(new ArrayList<>(sharkMoves), fishesCount));
            return;
        }

        for (int i = 1; i <= 4; i++) {
            int aRow = currentMove.row + odRow[i];
            int aCol = currentMove.col + odCol[i];

            if (!isInMatrix(aRow, aCol) || visited[aRow][aCol]) {
                continue;
            }

            sharkMoves.add(new SharkMove(aRow, aCol, i));
            visited[aRow][aCol] = true;

            makeSharkMovesList(sharkMoves, fishesCount, visited, startRow, startCol);

            visited[aRow][aCol] = false;
            sharkMoves.remove(sharkMoves.size() - 1);
        }
    }

    static int getResult() {
        int fishesCount = 0;

        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                fishesCount += getFishCount(staticMatrix[row][col]);
            }
        }

        return fishesCount;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 1 && row <= 4 && col >= 1 && col <= 4;
    }

    static boolean containsFish(List<Information> informations) {
        if (informations.isEmpty()) {
            return false;
        }

        for (Information information: informations) {
            if (information.getClass().equals(Fish.class)) {
                return true;
            }
        }
        return false;
    }

    static boolean containsShark(List<Information> informations) {
        if (informations.isEmpty()) {
            return false;
        }

        for (Information information: informations) {
            if (information.getClass().equals(Shark.class)) {
                return true;
            }
        }
        return false;
    }

    static boolean containsFishSmell(List<Information> informations) {
        if (informations.isEmpty()) {
            return false;
        }

        for (Information information: informations) {
            if (information.getClass().equals(FishSmell.class)) {
                return true;
            }
        }
        return false;
    }

    static int getFishCount(List<Information> informations) {
        int count = 0;

        for (Information information: informations) {
            if (information.getClass().equals(Fish.class)) {
                count++;
            }
        }

        return count;
    }

    static void removeFishes(List<Information> informations) {
        for (int i = 0; i < informations.size(); i++) {
            if (informations.get(i).getClass().equals(Fish.class)) {
                informations.remove(i);
                i--;
            }
        }
    }

    static Shark removeShark(List<Information> informations) {
        Shark shark = null;

        for (int i = 0; i < informations.size(); i++) {
            if (informations.get(i).getClass().equals(Shark.class)) {
                shark = (Shark) informations.get(i);
                informations.remove(i);
                break;
            }
        }

        return shark;
    }

    static void removeFishSmell(FishSmell fishSmell, List<Information> informations) {
        for (int i = 0; i < informations.size(); i++) {
            if (informations.get(i).getClass().equals(FishSmell.class) && informations.get(i).equals(fishSmell)) {
                informations.remove(i);
                break;
            }
        }
    }

    static void print() {
        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                if (staticMatrix[row][col].isEmpty()) {
                    continue;
                }

                /*System.out.printf("[%d %d]: ", row, col);
                System.out.println(staticMatrix[row][col]);*/
            }
        }
    }
}
