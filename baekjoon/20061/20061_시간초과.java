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
        public String toString() {
            return String.format("(%d %d)", row, col);
        }
    }
    static class Block {
        List<Tile> tiles;
        Block(List<Tile> tiles) {
            this.tiles = tiles;
        }
        Block right() {
            List<Tile> rightTiles = new LinkedList<>();
            for (Tile tile: tiles) {
                rightTiles.add(new Tile(tile.row, tile.col + 1));
            }
            return new Block(rightTiles);
        }
        Block down() {
            List<Tile> downTiles = new LinkedList<>();
            for (Tile tile: tiles) {
                downTiles.add(new Tile(tile.row + 1, tile.col));
            }
            return new Block(downTiles);
        }
    }
    static List<Block> currentBlocks = new ArrayList<>();
    static int score = 0;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int t = Integer.parseInt(tokenizer.nextToken());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());

            List<Tile> tiles = new LinkedList<>();

            if (t == 1) {
                tiles.add(new Tile(x, y));
            } else if (t == 2) {
                tiles.add(new Tile(x, y));
                tiles.add(new Tile(x, y + 1));
            } else {
                tiles.add(new Tile(x, y));
                tiles.add(new Tile(x + 1, y));
            }

            step(new Block(tiles));
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
    static void step(Block addedBlock) {
        // 파란색 영역으로 블록 이동
        Block movedBLock = addedBlock.right();
        while (!isEnableBlueArea(movedBLock)) {
            movedBLock = movedBLock.right();
        }

        while (true) {
            Block rightBlock = movedBLock.right();
            if (!isEnableBlueArea(rightBlock)) {
                break;
            }
            movedBLock = rightBlock;
        }
        currentBlocks.add(movedBLock);

        // 초록색 영역으로 블록 이동
        movedBLock = addedBlock.down();
        while (!isEnableGreenArea(movedBLock)) {
            movedBLock = movedBLock.down();
        }

        while (true) {
            Block downBlock = movedBLock.down();
            if (!isEnableGreenArea(downBlock)) {
                break;
            }
            movedBLock = downBlock;
        }
        currentBlocks.add(movedBLock);

        // 진한 파란색 영역에 대해 테트리스식 타일 제거
        List<Integer> removedCols = new ArrayList<>();
        for (int col = 6; col <= 9; col++) {
            boolean isOneLineFull = true;
            for (int row = 0; row <= 3; row++) {
                if (!contains(row, col)) {
                    isOneLineFull = false;
                    break;
                }
            }

            if (!isOneLineFull) {
                continue;
            }

            for (int row = 0; row <= 3; row++) {
                removeBlock(row, col);
            }
            removedCols.add(col);
        }
        if (!removedCols.isEmpty()) {
            for (Block currentBlock: currentBlocks) {
                if (!isBlueArea(currentBlock)) {
                    continue;
                }

                for (Tile currentTile: currentBlock.tiles) {
                    if (currentTile.col < removedCols.get(removedCols.size() - 1)) {
                        currentTile.col += removedCols.size();
                    }
                }
            }
        }
        score += removedCols.size();

        // 파란색 영역에서 연한 부분에 대한 처리
        int removedLinesCount = 0;
        for (int col = 4; col <= 5; col++) {
            for (int row = 0; row <= 3; row++) {
                if (contains(row, col)) {
                    removedLinesCount++;
                    break;
                }
            }
        }

        int tempCol = 9;
        for (int i = 0; i < removedLinesCount; i++) {
            for (int row = 0; row <= 3; row++) {
                removeBlock(row, tempCol);
            }
            tempCol--;
        }

        for (int i = 0; i < removedLinesCount; i++) {
            List<Block> copiedCurrentBlocks = new ArrayList<>(currentBlocks);
            currentBlocks = new ArrayList<>();
            for (Block copiedCurrentBlock: copiedCurrentBlocks) {
                if (isBlueArea(copiedCurrentBlock)) {
                    currentBlocks.add(copiedCurrentBlock.right());
                } else {
                    currentBlocks.add(copiedCurrentBlock);
                }
            }
        }

        // 진한 초록색 영역에 대해 테트리스식 타일 제거
        List<Integer> removedRows = new ArrayList<>();
        for (int row = 6; row <= 9; row++) {
            boolean isOneLineFull = true;
            for (int col = 0; col <= 3; col++) {
                if (!contains(row, col)) {
                    isOneLineFull = false;
                    break;
                }
            }

            if (!isOneLineFull) {
                continue;
            }

            for (int col = 0; col <= 3; col++) {
                removeBlock(row, col);
            }
            removedRows.add(row);
        }
        if (!removedRows.isEmpty()) {
            for (Block currentBlock: currentBlocks) {
                if (!isGreenArea(currentBlock)) {
                    continue;
                }

                for (Tile currentTile: currentBlock.tiles) {
                    if (currentTile.row < removedRows.get(removedRows.size() - 1)) {
                        currentTile.row += removedRows.size();
                    }
                }
            }
        }
        score += removedRows.size();

        // 초록색 영역에서 연한 부분에 대한 처리
        removedLinesCount = 0;
        for (int row = 4; row <= 5; row++) {
            for (int col = 0; col <= 3; col++) {
                if (contains(row, col)) {
                    removedLinesCount++;
                    break;
                }
            }
        }

        int tempRow = 9;
        for (int i = 0; i < removedLinesCount; i++) {
            for (int col = 0; col <= 3; col++) {
                removeBlock(tempRow, col);
            }
            tempRow--;
        }

        for (int i = 0; i < removedLinesCount; i++) {
            List<Block> copiedCurrentBlocks = new ArrayList<>(currentBlocks);
            currentBlocks = new ArrayList<>();
            for (Block copiedCurrentBlock: copiedCurrentBlocks) {
                if (isGreenArea(copiedCurrentBlock)) {
                    currentBlocks.add(copiedCurrentBlock.down());
                } else {
                    currentBlocks.add(copiedCurrentBlock);
                }
            }
        }
    }

    static boolean isBlueArea(Block block) {
        for (Tile tile: block.tiles) {
            if (!isInBlueMatrix(tile.row, tile.col)) {
                return false;
            }
        }
        return true;
    }

    static boolean isGreenArea(Block block) {
        for (Tile tile: block.tiles) {
            if (!isInGreenMatrix(tile.row, tile.col)) {
                return false;
            }
        }
        return true;
    }

    static boolean isEnableBlueArea(Block block) {
        for (Tile tile: block.tiles) {
            if (!isInBlueMatrix(tile.row, tile.col)) {
                return false;
            }
        }

        return !isOverlap(block);
    }

    static boolean isInBlueMatrix(int row, int col) {
        return row >= 0 && row <= 3 && col >= 4 && col <= 9;
    }

    static boolean isEnableGreenArea(Block block) {
        for (Tile tile: block.tiles) {
            if (!isInGreenMatrix(tile.row, tile.col)) {
                return false;
            }
        }

        return !isOverlap(block);
    }

    static boolean isInGreenMatrix(int row, int col) {
        return row >= 4 && row <= 9 && col >= 0 && col <= 3;
    }

    static boolean isOverlap(Block block) {
        for (Block currentBlock: currentBlocks) {
            for (Tile currentTile: currentBlock.tiles) {
                for (Tile blockTile: block.tiles) {
                    if (blockTile.row == currentTile.row && blockTile.col == currentTile.col) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static boolean contains(int row, int col) {
        for (Block currentBlock: currentBlocks) {
            for (Tile currentTile: currentBlock.tiles) {
                if (currentTile.row == row && currentTile.col == col) {
                    return true;
                }
            }
        }
        return false;
    }

    static void removeBlock(int row, int col) {
        for (Block currentBlock: currentBlocks) {
            for (int i = 0; i < currentBlock.tiles.size(); i++) {
                Tile currentTile = currentBlock.tiles.get(i);
                if (currentTile.row == row && currentTile.col == col) {
                    currentBlock.tiles.remove(i);
                    break;
                }
            }
        }
    }

    static int getTileCount() {
        int count = 0;
        for (Block block: currentBlocks) {
            if (isBlueArea(block) || isGreenArea(block)) {
                count += block.tiles.size();
            }
        }
        return count;
    }
}
