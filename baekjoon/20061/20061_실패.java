import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static class Tile {
        int row, col;
        Tile(int row, int col) {
            this.row = row;
            this.col = col;
        }
        Tile right() {
            return new Tile(row, col + 1);
        }
        Tile left() {
            return new Tile(row, col - 1);
        }
        Tile down() {
            return new Tile(row + 1, col);
        }
        Tile up() {
            return new Tile(row - 1, col);
        }
        public boolean equals(Object o) {
            Tile tile = (Tile) o;
            return row == tile.row && col == tile.col;
        }
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
    static Set<Tile> currentTiles = new HashSet<>();
    static int score = 0;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int t = Integer.parseInt(tokenizer.nextToken());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());

            List<Tile> addedTiles = new ArrayList<>();

            if (t == 1) {
                Tile tile = new Tile(x, y);
                addedTiles.add(tile);
            } else if (t == 2) {
                Tile tile1 = new Tile(x, y);
                Tile tile2 = new Tile(x, y + 1);
                addedTiles.add(tile1);
                addedTiles.add(tile2);
            } else {
                Tile tile1 = new Tile(x, y);
                Tile tile2 = new Tile(x + 1, y);
                addedTiles.add(tile1);
                addedTiles.add(tile2);
            }

            step(addedTiles);
        }

        writer.write(String.format("%d\n", score));
        writer.write(String.format("%d\n", getTileCount()));
        writer.flush();

        writer.close();
    }

    /*
         - 사용할 수 있는 블록은 다음의 3개
           - 1x1, 1x2, 2x1
         - 블록을 놓을 수 있는 빨간색 위치를 선택하면 그 위치부터 초록색과 파란색으로 블록이 이동
           - 다른 블록 or 경계를 만날때까지
         - 테트리스식 블록 제거
           - 초록색 보드에서 어떤 행이 타일로 가득 차있으면 테트리스처럼 블록이 사라지고, 사라진 행의 개수만큼 점수가 추가됨
           - 파란색 보드에서 어떤 열이 타일로 가득 차있으면 테트리스처럼 블록이 사라지고, 사라진 열의 개수만큼 점수가 추가됨
         - 연한 칸에 대한 블록 제거
           - 초록색 보드에서 연한칸에 블록이 있는 행의 수만큼 아래 행이 사라지고 그 수만큼 아래로 이동
           - 파란색 보드에서 연한칸에 블록이 있는 열의 수만큼 오른쪽 열이 사라지고 그 수만큼 오른쪽으로 이동
     */
    static void step(List<Tile> addedTiles) {
        // 파란색 영역으로 블록 이동
        if (addedTiles.size() == 1) {
            Tile tile = addedTiles.get(0);
            Tile newTile = tile.right();

            while (true) {
                Tile rightTile = newTile.right();

                if (isBlueArea(newTile) && (currentTiles.contains(rightTile) || !isInMatrix(rightTile))) {
                    currentTiles.add(newTile);
                    break;
                }

                newTile = rightTile;
            }
        } else {
            Tile tile1 = addedTiles.get(0);
            Tile tile2 = addedTiles.get(1);

            if (tile2.equals(tile1.right())) {
                Tile newTile2 = tile2.right();

                while (true) {
                    Tile rightTile2 = newTile2.right();

                    if (isBlueArea(newTile2) && (currentTiles.contains(rightTile2) || !isInMatrix(rightTile2))) {
                        currentTiles.add(newTile2);
                        break;
                    }

                    newTile2 = rightTile2;
                }

                currentTiles.add(newTile2.left());
            } else {
                Tile newTile1 = tile1.right();
                Tile newTile2 = tile2.right();

                while (true) {
                    Tile rightTile1 = newTile1.right();
                    Tile rightTile2 = newTile2.right();

                    if (isBlueArea(newTile1) && (currentTiles.contains(rightTile1) || !isInMatrix(rightTile1))) {
                        currentTiles.add(newTile1);
                        currentTiles.add(newTile2);
                        break;
                    }
                    if (isBlueArea(newTile2) && (currentTiles.contains(rightTile2) || !isInMatrix(rightTile2))) {
                        currentTiles.add(newTile1);
                        currentTiles.add(newTile2);
                        break;
                    }

                    newTile1 = rightTile1;
                    newTile2 = rightTile2;
                }
            }
        }

        // 초록색 영역으로 블록 이동
        if (addedTiles.size() == 1) {
            Tile tile = addedTiles.get(0);
            Tile newTile = tile.down();

            while (true) {
                Tile downTile = newTile.down();

                if (isGreenArea(newTile) && (currentTiles.contains(downTile) || !isInMatrix(downTile))) {
                    currentTiles.add(newTile);
                    break;
                }

                newTile = downTile;
            }
        } else {
            Tile tile1 = addedTiles.get(0);
            Tile tile2 = addedTiles.get(1);

            if (tile2.equals(tile1.right())) {
                Tile newTile1 = tile1.down();
                Tile newTile2 = tile2.down();

                while (true) {
                    Tile downTile1 = newTile1.down();
                    Tile downTile2 = newTile2.down();

                    if (isGreenArea(newTile1) && (currentTiles.contains(downTile1) || !isInMatrix(downTile1))) {
                        currentTiles.add(newTile1);
                        currentTiles.add(newTile2);
                        break;
                    }
                    if (isGreenArea(newTile2) && (currentTiles.contains(downTile2) || !isInMatrix(downTile2))) {
                        currentTiles.add(newTile1);
                        currentTiles.add(newTile2);
                        break;
                    }

                    newTile1 = downTile1;
                    newTile2 = downTile2;
                }
            } else {
                Tile newTile2 = tile2.down();

                while (true) {
                    Tile downTile2 = newTile2.down();

                    if (isGreenArea(newTile2) && (currentTiles.contains(downTile2) || !isInMatrix(downTile2))) {
                        currentTiles.add(newTile2);
                        break;
                    }

                    newTile2 = downTile2;
                }

                currentTiles.add(newTile2.up());
            }
        }

        // 진한 파란색 영역에 대해 테트리스식 타일 제거
        List<Integer> removedCols = new ArrayList<>();
        for (int col = 6; col <= 9; col++) {
            boolean isOneLineFull = true;

            for (int row = 0; row <= 3; row++) {
                if (!currentTiles.contains(new Tile(row, col))) {
                    isOneLineFull = false;
                    break;
                }
            }

            if (!isOneLineFull) {
                continue;
            }

            for (int row = 0; row <= 3; row++) {
                currentTiles.remove(new Tile(row, col));
            }
            removedCols.add(col);
        }

        if (!removedCols.isEmpty()) {
            for (Tile currentTile: currentTiles) {
                if (!isBlueArea(currentTile)) {
                    continue;
                }

                if (currentTile.col < removedCols.get(removedCols.size() - 1)) {
                    currentTile.col += removedCols.size();
                }
            }
        }

        score += removedCols.size();

        // 파란색 영역에서 연한 부분에 대한 처리
        int removedLinesCount = 0;
        for (int col = 4; col <= 5; col++) {
            for (int row = 0; row <= 3; row++) {
                if (currentTiles.contains(new Tile(row, col))) {
                    removedLinesCount++;
                    break;
                }
            }
        }

        int tempCol = 9;
        for (int i = 0; i < removedLinesCount; i++) {
            for (int row = 0; row <= 3; row++) {
                currentTiles.remove(new Tile(row, tempCol));
            }
            tempCol--;
        }

        for (int i = 0; i < removedLinesCount; i++) {
            Set<Tile> copiedCurrentTiles = new HashSet<>(currentTiles);
            currentTiles = new HashSet<>();

            for (Tile copiedCurrentTile: copiedCurrentTiles) {
                if (isBlueArea(copiedCurrentTile)) {
                    currentTiles.add(copiedCurrentTile.right());
                } else {
                    currentTiles.add(copiedCurrentTile);
                }
            }
        }

        // 진한 초록색 영역에 대해 테트리스식 타일 제거
        List<Integer> removedRows = new ArrayList<>();
        for (int row = 6; row <= 9; row++) {
            boolean isOneLineFull = true;

            for (int col = 0; col <= 3; col++) {
                if (!currentTiles.contains(new Tile(row, col))) {
                    isOneLineFull = false;
                    break;
                }
            }

            if (!isOneLineFull) {
                continue;
            }

            for (int col = 0; col <= 3; col++) {
                currentTiles.remove(new Tile(row, col));
            }
            removedRows.add(row);
        }

        if (!removedRows.isEmpty()) {
            for (Tile currentTile: currentTiles) {
                if (!isGreenArea(currentTile)) {
                    continue;
                }

                if (currentTile.row < removedRows.get(removedRows.size() - 1)) {
                    currentTile.row += removedRows.size();
                }
            }
        }

        score += removedRows.size();

        // 파란색 영역에서 연한 부분에 대한 처리
        removedLinesCount = 0;
        for (int row = 4; row <= 5; row++) {
            for (int col = 0; col <= 3; col++) {
                if (currentTiles.contains(new Tile(row, col))) {
                    removedLinesCount++;
                    break;
                }
            }
        }

        int tempRow = 9;
        for (int i = 0; i < removedLinesCount; i++) {
            for (int col = 0; col <= 3; col++) {
                currentTiles.remove(new Tile(tempRow, col));
            }
            tempRow--;
        }

        for (int i = 0; i < removedLinesCount; i++) {
            Set<Tile> copiedCurrentTiles = new HashSet<>(currentTiles);
            currentTiles = new HashSet<>();

            for (Tile copiedCurrentTile: copiedCurrentTiles) {
                if (isGreenArea(copiedCurrentTile)) {
                    currentTiles.add(copiedCurrentTile.down());
                } else {
                    currentTiles.add(copiedCurrentTile);
                }
            }
        }
    }

    static boolean isBlueArea(Tile tile) {
        return tile.row >= 0 && tile.row <= 3 && tile.col >= 4 && tile.col <= 9;
    }

    static boolean isGreenArea(Tile tile) {
        return tile.row >= 4 && tile.row <= 9 && tile.col >= 0 && tile.col <= 3;
    }

    static boolean isInMatrix(Tile tile) {
        return tile.row >= 0 && tile.row <= 9 && tile.col >= 0 && tile.col <= 9;
    }

    static int getTileCount() {
        int count = 0;
        for (Tile tile: currentTiles) {
            if (isBlueArea(tile) || isGreenArea(tile)) {
                count++;
            }
        }
        return count;
    }

    /*static void print() {
        for (int row = 0; row <= 9; row++) {
            for (int col = 0; col <= 9; col++) {
                if (row >= 4 && col >= 4) {
                    continue;
                }

                if (currentTiles.contains(new Tile(row, col))) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("----------------------------");
    }*/
}
