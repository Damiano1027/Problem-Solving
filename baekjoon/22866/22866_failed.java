import java.io.*;
import java.util.*;

// 시간 초과

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static class Building {
        int number;
        int height;
    }
    static Building[] buildings;
    static Building[] toBuildingArray(String str) {
        String[] strings = str.split(" ");
        Building[] buildingArray = new Building[strings.length];
        Arrays.setAll(buildingArray, i -> new Building());

        for (int i = 0; i < buildingArray.length; i++) {
            buildingArray[i].number = i + 1;
            buildingArray[i].height = Integer.parseInt(strings[i]);
        }

        return buildingArray;
    }
    static ArrayList<Integer>[] results = new ArrayList[100001];
    static {
        for (int i = 1; i < 100001; i++) {
            results[i] = new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(reader.readLine());
        buildings = toBuildingArray(reader.readLine());

        solve();

        for (int i = 1; i <= N; i++) {
            if (results[i].isEmpty()) {
                writer.write(String.format("%d\n", results[i].size()));
            } else {
                writer.write(String.format("%d %d\n", results[i].size(), getResult(results[i], i)));
            }
        }
        writer.flush();
        writer.close();
    }

    static void solve() {
        Stack<Building> stack = new Stack<>();

        for (int i = 0; i < buildings.length; i++) {
            Building building = buildings[i];

            if (i == 0 || stack.isEmpty()) {
                stack.add(building);
                continue;
            }

            while (stack.peek().height <= building.height) {
                stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
            }

            Stack<Building> tempStack = new Stack<>();
            while (!stack.isEmpty()) {
                Building currentBuilding = stack.pop();
                results[building.number].add(currentBuilding.number);
                tempStack.add(currentBuilding);
            }
            while (!tempStack.isEmpty()) {
                stack.add(tempStack.pop());
            }

            stack.add(building);
        }

        stack.clear();

        for (int i = buildings.length - 1; i >= 0; i--) {
            Building building = buildings[i];

            if (i == buildings.length - 1 || stack.isEmpty()) {
                stack.add(building);
                continue;
            }

            while (stack.peek().height <= building.height) {
                stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
            }

            Stack<Building> tempStack = new Stack<>();
            while (!stack.isEmpty()) {
                Building currentBuilding = stack.pop();
                results[building.number].add(currentBuilding.number);
                tempStack.add(currentBuilding);
            }
            while (!tempStack.isEmpty()) {
                stack.add(tempStack.pop());
            }

            stack.add(building);
        }
    }

    static int getResult(List<Integer> list, int criteria) {
        int min = Integer.MAX_VALUE;
        int minNumber = Integer.MIN_VALUE;

        for (int number : list) {
            if (Math.abs(criteria - number) < min) {
                min = Math.abs(criteria - number);
                minNumber = number;
            }
        }

        return minNumber;
    }
}
