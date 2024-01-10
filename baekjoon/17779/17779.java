import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N;
    static int[][] matrix;
    static class Combination {
        int x, y, d1, d2;
        Combination(int x, int y, int d1, int d2) {
            this.x = x;
            this.y = y;
            this.d1 = d1;
            this.d2 = d2;
        }

        /*public String toString() {
            return String.format("(%d, %d) -> d1: %d, d2: %d", x, y, d1, d2);
        }*/
    }
    static class Location {
        int row, col;
        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Location)) {
                return false;
            }

            Location other = (Location) obj;
            return row == other.row && col == other.col;
        }
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
    static List<Combination> combinations = new ArrayList<>();
    static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine());

        matrix = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 1; j <= N; j++) {
                matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        makeCombinations();

        for (Combination combination: combinations) {
            //System.out.print(combination.toString());
            result = Math.min(result, minDifferencePopulation(combination));
        }
    }

    /*
          d1 >= 1
          d2 >= 1
          x >= 1
          y >= d1 + 1

          d1 + d2 <= N - x

          d1 <= y - 1
          d2 <= N - y
     */
    static void makeCombinations() {
        for (int x = 1; x <= N; x++) {
            for (int y = 2; y <= N; y++) {
               for (int d1 = 1; d1 <= y - 1; d1++) {
                   for (int d2 = 1; d2 <= N - y; d2++) {
                       if (d1 + d2 <= N - x) {
                           combinations.add(new Combination(x, y, d1, d2));
                       }
                   }
               }
            }
        }
    }

    static int minDifferencePopulation(Combination combination) {
        int[] areaPopulations = new int[5];
        Set<Location> area5Locations = area5Locations(combination);

        int x = combination.x;
        int y = combination.y;
        int d1 = combination.d1;
        int d2 = combination.d2;

        for (int row = 1; row < x + d1; row++) {
            for (int col = 1; col <= y; col++) {
                if (area5Locations.contains(new Location(row, col))) {
                    break;
                }
                areaPopulations[0] += matrix[row][col];
            }
        }
        for (int row = 1; row <= x + d2; row++) {
            for (int col = N; col > y; col--) {
                if (area5Locations.contains(new Location(row, col))) {
                    break;
                }
                areaPopulations[1] += matrix[row][col];
            }
        }
        for (int row = x + d1; row <= N; row++) {
            for (int col = 1; col < y - d1 + d2; col++) {
                if (area5Locations.contains(new Location(row, col))) {
                    break;
                }
                areaPopulations[2] += matrix[row][col];
            }
        }
        for (int row = x + d2 + 1; row <= N; row++) {
            for (int col = N; col >= y - d1 + d2; col--) {
                if (area5Locations.contains(new Location(row, col))) {
                    break;
                }
                areaPopulations[3] += matrix[row][col];
            }
        }

        for (Location area5Location: area5Locations) {
            areaPopulations[4] += matrix[area5Location.row][area5Location.col];
        }

        Arrays.sort(areaPopulations);
        return areaPopulations[4] - areaPopulations[0];
    }

    static Set<Location> area5Locations(Combination combination) {
        Set<Location> area5Locations = new HashSet<>();
        boolean[][] matrix = new boolean[N + 1][N + 1];

        int x = combination.x;
        int y = combination.y;
        int d1 = combination.d1;
        int d2 = combination.d2;

        for (int i = 0; i <= d1; i++) {
            int row = x + i;
            int col = y - i;
            area5Locations.add(new Location(row, col));
            matrix[row][col] = true;
        }
        for (int i = 0; i <= d2; i++) {
            int row = x + i;
            int col = y + i;
            area5Locations.add(new Location(row, col));
            matrix[row][col] = true;
        }
        for (int i = 0; i <= d2; i++) {
            int row = x + d1 + i;
            int col = y - d1 + i;
            area5Locations.add(new Location(row, col));
            matrix[row][col] = true;
        }
        for (int i = 0; i <= d1; i++) {
            int row = x + d2 + i;
            int col = y + d2 - i;
            area5Locations.add(new Location(row, col));
            matrix[row][col] = true;
        }

        for (int row = 1; row <= N; row++) {
            List<Integer> cols = new ArrayList<>();

            for (int col = 1; col <= N; col++) {
                if (matrix[row][col]) {
                    cols.add(col);
                }
            }

            if (!cols.isEmpty()) {
                int begin = cols.get(0);
                int end = cols.get(cols.size() - 1);

                for (int i = begin + 1; i < end; i++) {
                    area5Locations.add(new Location(row, i));
                }
            }
        }

        return area5Locations;
    }
}
