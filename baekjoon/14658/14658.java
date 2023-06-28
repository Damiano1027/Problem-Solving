import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    private static StringTokenizer stringTokenizer;
    private static int N, M, L, K;
    private static List<Coordinate> coordinates = new ArrayList<>();
    private static int result;


    public static void main(String[] args) throws IOException {
        stringTokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(stringTokenizer.nextToken());
        M = Integer.parseInt(stringTokenizer.nextToken());
        L = Integer.parseInt(stringTokenizer.nextToken());
        K = Integer.parseInt(stringTokenizer.nextToken());

        for (int i = 0; i < K; i++) {
            stringTokenizer = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(stringTokenizer.nextToken());
            int y = Integer.parseInt(stringTokenizer.nextToken());
            coordinates.add(new Coordinate(x, y));
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    private static void solve() {
        int max = -1;

        for (Coordinate coordinate1 : coordinates) {
            for (Coordinate coordinate2 : coordinates) {
                int bouncingCount = getBouncingCount(coordinate1.x, coordinate2.y);
                if (bouncingCount > max) {
                    max = bouncingCount;
                    result = K - max;
                }
            }
        }
    }

    private static int getBouncingCount(int x, int y) {
        int boundingCount = 0;

        for (Coordinate coordinate : coordinates) {
            if (coordinate.x >= x && coordinate.x <= x + L && coordinate.y >= y && coordinate.y <= y + L) {
                boundingCount++;
            }
        }

        return boundingCount;
    }

    private static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
