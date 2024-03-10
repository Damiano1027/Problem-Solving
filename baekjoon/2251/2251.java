import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int[] max = new int[3];
    static Set<State> visited = new HashSet<>();
    static Set<Integer> result = new TreeSet<>();

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());

        for (int i = 0; i < 3; i++) {
            max[i] = Integer.parseInt(tokenizer.nextToken());
        }

        int[] water = new int[3];
        water[2] = max[2];
        State originalState = new State(water);

        solve(originalState);

        for (int r : result) {
            writer.write(String.format("%d ", r));
        }
        writer.newLine();

        writer.flush();
        writer.close();
    }

    static void solve(State originalState) {
        dfs(originalState);
    }

    static void dfs(State state) {
        if (visited.contains(state)) {
            return;
        }
        if (state.water[0] == 0) {
            result.add(state.water[2]);
        }

        visited.add(state);

        dfs(moveWater(state, 0, 1));
        dfs(moveWater(state, 1, 0));
        dfs(moveWater(state, 1, 2));
        dfs(moveWater(state, 2, 1));
        dfs(moveWater(state, 0, 2));
        dfs(moveWater(state, 2, 0));

        visited.remove(state);
    }

    static State moveWater(State originalState, int fromIndex, int toIndex) {
        int[] newWater = new int[3];
        for (int i = 0; i < 3; i++) {
            newWater[i] = originalState.water[i];
        }

        if (newWater[fromIndex] <= max[toIndex] - newWater[toIndex]) {
            newWater[toIndex] += newWater[fromIndex];
            newWater[fromIndex] = 0;
        } else {
            int addedWater = max[toIndex] - newWater[toIndex];
            newWater[toIndex] = max[toIndex];
            newWater[fromIndex] -= addedWater;
        }

        return new State(newWater);
    }
}

class State {
    int[] water;
    State(int[] water) {
        this.water = water;
    }
    public boolean equals(Object o) {
        State state = (State) o;
        return Arrays.equals(water, state.water);
    }
    public int hashCode() {
        return Objects.hash(water[0], water[1], water[2]);
    }
}

