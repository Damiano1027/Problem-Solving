import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, L;
    static Integer[][] matrix;
    static final List<List<Integer>> lines = new ArrayList<>();
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        L = Integer.parseInt(tokenizer.nextToken());

        matrix = new Integer[N][N];

        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < N; j++) {
                matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();
        writer.close();
    }

    static void solve() {
        setLines();
        int count = 0;

        for (List<Integer> line: lines) {
            if (isValid(line)) {
                count++;
            }
        }

        result = count;
    }

    static void setLines() {
        for (int row = 0; row < N; row++) {
            lines.add(Arrays.asList(matrix[row]));
        }

        for (int col = 0; col < N; col++) {
            List<Integer> line = new ArrayList<>();

            for (int row = 0; row < N; row++) {
                line.add(matrix[row][col]);
            }

            lines.add(line);
        }
    }

    /*
          하나의 라인 기준

          이중 하나라도 해동되면 경사로를 정상적으로 놓을 수 없는 경우임
          1. 낮은 칸과 높은 칸의 높이 차이가 1이 아닌 경우
          2. 낮은 지점의 칸의 높이가 L개 이상으로 연속되지 않은 경우
          3. 낮은 칸의 양쪽에 높은 칸이 있고 & 낮은 칸의 길이가 L * 2보다 작은 경우
     */
    static boolean isValid(List<Integer> line) {
        // 1번조건 검사
        if (isHeightDifferenceNoneOneExist(line)) {
            return false;
        }

        List<List<Integer>> lowSectionLocationsList = getLowSectionLocationsList(line);

        // 2번조건 검사
        if (isLowSectionsNotValid(lowSectionLocationsList)) {
            return false;
        }
        // 3번조건 검사
        if (isSlopeImpossible(lowSectionLocationsList, line)) {
            return false;
        }

        return true;
    }

    static boolean isHeightDifferenceNoneOneExist(List<Integer> line) {
        for (int i = 0; i < line.size() - 1; i++) {
            int height1 = line.get(i);
            int height2 = line.get(i + 1);

            if (Math.abs(height1 - height2) >= 2) {
                return true;
            }
        }
        return false;
    }

    static List<List<Integer>> getLowSectionLocationsList(List<Integer> line) {
        List<List<Integer>> locationsList = new ArrayList<>();

        for (int i = 0; i < line.size() - 1; i++) {
            int leftHeight = line.get(i);
            int rightHeight = line.get(i + 1);

            if (leftHeight > rightHeight) {
                List<Integer> locations = new ArrayList<>();
                locations.add(i + 1);
                for (int j = i + 2; j < line.size(); j++) {
                    if (!line.get(j).equals(rightHeight)) {
                        break;
                    }
                    locations.add(j);
                }
                locationsList.add(locations);
            }
        }

        for (int i = line.size() - 1; i > 0; i--) {
            int leftHeight = line.get(i - 1);
            int rightHeight = line.get(i);

            if (leftHeight < rightHeight) {
                List<Integer> locations = new ArrayList<>();
                locations.add(i - 1);
                for (int j = i - 2; j >= 0; j--) {
                    if (!line.get(j).equals(leftHeight)) {
                        break;
                    }
                    locations.add(j);
                }

                List<Integer> reversedLocations = new ArrayList<>();
                for (int j = locations.size() - 1; j >= 0; j--) {
                    reversedLocations.add(locations.get(j));
                }

                if (!locationsList.contains(reversedLocations)) {
                    locationsList.add(reversedLocations);
                }
            }
        }

        return locationsList;
    }

    static boolean isLowSectionsNotValid(List<List<Integer>> lowSectionLocationsList) {
        for (List<Integer> lowSectionLocations: lowSectionLocationsList) {
            if (lowSectionLocations.size() < L) {
                return true;
            }
        }
        return false;
    }

    static boolean isSlopeImpossible(List<List<Integer>> lowSectionLocationsList, List<Integer> line) {
        for (List<Integer> lowSectionLocations: lowSectionLocationsList) {
            int leftLocation = lowSectionLocations.get(0);
            int rightLocation = lowSectionLocations.get(lowSectionLocations.size() - 1);
            int leftLeftLocation = leftLocation - 1;
            int rightRightLocation = rightLocation + 1;

            if (isInRange(leftLeftLocation, line) && isInRange(rightRightLocation, line)) {
                if (line.get(leftLeftLocation) > line.get(leftLocation)
                        && line.get(rightRightLocation) > line.get(rightLocation)
                        && lowSectionLocations.size() < 2 * L) {
                    return true;
                }
            }
        }

        return false;
    }

    static boolean isInRange(Integer location, List<Integer> line) {
        return location >= 0 && location < line.size();
    }
}
