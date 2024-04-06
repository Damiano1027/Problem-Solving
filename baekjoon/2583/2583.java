import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int M, N, K;
    static Set<Location> map = new HashSet<>();
    static int[] dX = {0, -1, 0, 1};
    static int[] dY = {1, 0, -1, 0};
    static int result1 = 0;
    static List<Integer> result2 = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        M = Integer.parseInt(tokenizer.nextToken());
        N = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        for (int i = 0; i < K; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int leftDownX = Integer.parseInt(tokenizer.nextToken());
            int leftDownY = Integer.parseInt(tokenizer.nextToken());
            int rightUpX = Integer.parseInt(tokenizer.nextToken());
            int rightUpY = Integer.parseInt(tokenizer.nextToken());

            for (int y = leftDownY + 1; y <= rightUpY; y++) {
                for (int x = leftDownX + 1; x <= rightUpX; x++) {
                    map.add(new Location(x, y));
                }
            }
        }

        for (int y = 1; y <= M; y++) {
            for (int x = 1; x <= N; x++) {
                Location location = new Location(x, y);
                if (!map.contains(location)) {
                    result1++;
                    result2.add(bfs(location));
                }
            }
        }

        Collections.sort(result2);

        writer.write(result1 + "\n");
        for (int value : result2) {
            writer.write(String.format("%d ", value));
        }
        writer.newLine();
        writer.flush();
        writer.close();
    }

    static int bfs(Location startLocation) {
        Queue<Location> queue = new LinkedList<>() {{
            add(startLocation);
        }};
        Set<Location> visited = new HashSet<>() {{
            add(startLocation);
        }};

        int count = 0;
        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            map.add(currentLocation);
            count++;

            for (int i = 0; i < 4; i++) {
                int nextX = currentLocation.x + dX[i];
                int nextY = currentLocation.y + dY[i];
                Location nextLocation = new Location(nextX, nextY);

                if (isInRange(nextX, nextY) && !visited.contains(nextLocation) && !map.contains(nextLocation)) {
                    queue.add(nextLocation);
                    visited.add(nextLocation);
                }
            }
        }

        return count;
    }

    static boolean isInRange(int x, int y) {
        return x >= 1 && x <= N && y >= 1 && y <= M;
    }
}

class Location {
    int x, y;
    Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean equals(Object o) {
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
