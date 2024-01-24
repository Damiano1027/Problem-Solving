import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int M, S;
    static class Information {
        boolean shark = false;
        int[] fishCounts = new int[9]; // 1~8 인덱스에 있는 물고기는 해당 인덱스 값 방향을 가지고 있는 물고기임
        int fishSmellCount = 0;
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            if (shark) {
                stringBuilder.append("shark | ");
            }
            if (fishSmellCount > 0) {
                stringBuilder.append(String.format("%d | ", fishSmellCount));
            }
            for (int i = 1; i <= 8; i++) {
                if (fishCounts[i] > 0) {
                    stringBuilder.append(String.format("{%d %d}, ", i, fishCounts[i]));
                }
            }

            return stringBuilder.toString();
        }
    }
    static class FishChange {
        int row, col;
        int fishDirection;
        int count;
        boolean add;
        FishChange(int row, int col, int fishDirection, int count, boolean add) {
            this.row = row;
            this.col = col;
            this.fishDirection = fishDirection;
            this.count = count;
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
    static class CopiedFishHistory {
        int row, col;
        int[] fishCounts = new int[9];
        CopiedFishHistory(int row, int col) {
            this.row = row;
            this.col = col;
        }
        int getAllFishCount() {
            int count = 0;

            for (int direction = 1; direction <= 8; direction++) {
                count += fishCounts[direction];
            }

            return count;
        }
    }
    static class FishSmellHistory {
        int row, col;
        int fishSmellCount;
        FishSmellHistory(int row, int col, int fishSmellCount) {
            this.row = row;
            this.col = col;
            this.fishSmellCount = fishSmellCount;
        }
    }
    static Information[][] staticMatrix = new Information[5][5];
    static {
        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                staticMatrix[row][col] = new Information();
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

            staticMatrix[fishRow][fishCol].fishCounts[fishDirection]++;
        }

        tokenizer = new StringTokenizer(reader.readLine());
        int sharkRow = Integer.parseInt(tokenizer.nextToken());
        int sharkCol = Integer.parseInt(tokenizer.nextToken());

        staticMatrix[sharkRow][sharkCol].shark = true;

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        for (int count = 0; count < S; count++) {
            List<CopiedFishHistory> copiedFishHistories = step1();
            step2();
            step3();
            if (count >= 2) {
                step4(count - 2);
            }
            step5(copiedFishHistories);

            /*print();
            System.out.println("-----------------");*/
        }

        result = getResult();
    }

    static List<CopiedFishHistory> step1() {
        List<CopiedFishHistory> copiedFishHistories = new ArrayList<>();

        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                CopiedFishHistory history = new CopiedFishHistory(row, col);
                for (int direction = 1; direction <= 8; direction++) {
                    history.fishCounts[direction] = staticMatrix[row][col].fishCounts[direction];
                }

                if (history.getAllFishCount() > 0) {
                    copiedFishHistories.add(history);
                }
            }
        }

        return copiedFishHistories;
    }

    /*
         모든 물고기가 한 칸 이동한다.
         상어가 있는 칸, 물고기의 냄새가 있는 칸, 격자의 범위를 벗어나는 칸으로는 이동할 수 없다.
         각 물고기는 자신이 가지고 있는 이동 방향이 이동할 수 있는 칸을 향할 때까지 방향을 45도 반시계 회전시킨다.
         만약, 이동할 수 있는 칸이 없으면 이동을 하지 않는다.
         그 외의 경우에는 그 칸으로 이동을 한다.
     */
    static void step2() {
        List<FishChange> fishChanges = new ArrayList<>();

        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                Information currentLocation = staticMatrix[row][col];

                for (int direction = 1; direction <= 8; direction++) {
                    if (currentLocation.fishCounts[direction] == 0) {
                        continue;
                    }

                    int newDirection = direction;
                    for (int count = 0; count < 8; count++) {
                        int aRow = row + dRow[newDirection];
                        int aCol = col + dCol[newDirection];

                        if (!isInMatrix(aRow, aCol) || staticMatrix[aRow][aCol].shark || staticMatrix[aRow][aCol].fishSmellCount > 0) {
                            newDirection--;
                            if (newDirection < 1) {
                                newDirection = 8;
                            }
                            continue;
                        }

                        fishChanges.add(new FishChange(aRow, aCol, newDirection, currentLocation.fishCounts[direction], true));
                        fishChanges.add(new FishChange(row, col, direction, currentLocation.fishCounts[direction], false));
                        break;
                    }
                }
            }
        }

        for (FishChange fishChange: fishChanges) {
            int row = fishChange.row;
            int col = fishChange.col;
            int direction = fishChange.fishDirection;
            int count = fishChange.count;
            boolean add = fishChange.add;

            if (add) {
                staticMatrix[row][col].fishCounts[direction] += count;
            } else {
                staticMatrix[row][col].fishCounts[direction] -= count;
            }
        }
    }

    static void step3() {
        int sharkRow = 0, sharkCol = 0;

        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                if (staticMatrix[row][col].shark) {
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

        staticMatrix[sharkRow][sharkCol].shark = false;
        List<FishSmellHistory> fishSmellHistories = new ArrayList<>();

        for (SharkMove sharkMove: sharkMovesResult.sharkMoves) {
            sharkRow = sharkMove.row;
            sharkCol = sharkMove.col;

            int fishCount = getFishCount(staticMatrix[sharkRow][sharkCol]);
            FishSmellHistory fishSmellHistory = new FishSmellHistory(sharkRow, sharkCol, fishCount);
            staticMatrix[sharkRow][sharkCol].fishSmellCount += fishCount;
            removeAllFishes(staticMatrix[sharkRow][sharkCol]);

            if (fishSmellHistory.fishSmellCount > 0) {
                fishSmellHistories.add(fishSmellHistory);
            }
        }
        fishSmellHistoriesList.add(fishSmellHistories);

        staticMatrix[sharkRow][sharkCol].shark = true;
    }

    static void step4(int count) {
        List<FishSmellHistory> fishSmellHistories = fishSmellHistoriesList.get(count);

        for (FishSmellHistory history: fishSmellHistories) {
            staticMatrix[history.row][history.col].fishSmellCount -= history.fishSmellCount;
        }
    }

    static void step5(List<CopiedFishHistory> copiedFishHistories) {
        for (CopiedFishHistory history: copiedFishHistories) {
            for (int direction = 1; direction <= 8; direction++) {
                staticMatrix[history.row][history.col].fishCounts[direction] += history.fishCounts[direction];
            }
        }
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

    static int getFishCount(Information information) {
        int count = 0;

        for (int direction = 1; direction <= 8; direction++) {
            count += information.fishCounts[direction];
        }

        return count;
    }

    static void removeAllFishes(Information information) {
        for (int direction = 1; direction <= 8; direction++) {
            information.fishCounts[direction] = 0;
        }
    }

    static void print() {
        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 4; col++) {
                System.out.printf("[%d %d]: ", row, col);
                System.out.println(staticMatrix[row][col].toString());
            }
        }
    }
}
