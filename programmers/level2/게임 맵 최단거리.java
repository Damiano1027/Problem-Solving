import java.util.*;

/*
    bfs로 간단히 처리할 수 있다.
*/

class Solution {
    public int solution(int[][] maps) {
        return bfs(0, 0, maps);
    }
    
    public int bfs(int row, int col, int[][] maps) {
        Queue<Location> queue = new LinkedList<>();
        queue.add(new Location(0, 0, 1));
        boolean[][] visited = new boolean[maps.length][maps[0].length];
        
        int maxRow = maps.length;
        int maxCol = maps[0].length;
        
        while (!queue.isEmpty()) {
            Location location = queue.poll();
            
            int currentRow = location.row;
            int currentCol = location.col;
            int currentCnt = location.cnt;
            
            if (currentRow == maxRow - 1 && currentCol == maxCol - 1) {
                return currentCnt;
            }
                     
            if (isPossibbleMove(currentRow - 1, currentCol, maps) && !visited[currentRow - 1][currentCol]) {
                queue.add(new Location(currentRow - 1, currentCol, currentCnt + 1));
                visited[currentRow - 1][currentCol] = true;
            }
            if (isPossibbleMove(currentRow + 1, currentCol, maps) && !visited[currentRow + 1][currentCol]) {
                queue.add(new Location(currentRow + 1, currentCol, currentCnt + 1));
                visited[currentRow + 1][currentCol] = true;
            }
            if (isPossibbleMove(currentRow, currentCol - 1, maps) && !visited[currentRow][currentCol - 1]) {
                queue.add(new Location(currentRow, currentCol - 1, currentCnt + 1));
                visited[currentRow][currentCol - 1] = true;
            }
            if (isPossibbleMove(currentRow, currentCol + 1, maps) && !visited[currentRow][currentCol + 1]) {
                queue.add(new Location(currentRow, currentCol + 1, currentCnt + 1));
                visited[currentRow][currentCol + 1] = true;
            }
        }
        
        return -1;
    }
    
    public boolean isPossibbleMove(int row, int col, int[][] maps) {
        return isMapRange(row, col, maps.length, maps[0].length) && isWall(row, col, maps); 
    }
    
    public boolean isMapRange(int row, int col, int maxRow, int maxCol) {
        return row >= 0 && row < maxRow && col >= 0 && col < maxCol;
    }
    
    public boolean isWall(int row, int col, int[][] maps) {
        return maps[row][col] == 1;
    }
    
    public static class Location {
        int row;
        int col;
        int cnt;
        
        Location(int row, int col, int cnt) {
            this.row = row;
            this.col = col;
            this.cnt = cnt;
        }
    }
}
