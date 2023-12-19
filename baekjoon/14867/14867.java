import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int aCapacity, bCapacity;
    static Pair target = new Pair();

    public static void main(String[] args) throws Exception {
        StringTokenizer stringTokenizer = new StringTokenizer(reader.readLine());
        aCapacity = Integer.parseInt(stringTokenizer.nextToken());
        bCapacity = Integer.parseInt(stringTokenizer.nextToken());
        target.aSize = Integer.parseInt(stringTokenizer.nextToken());
        target.bSize = Integer.parseInt(stringTokenizer.nextToken());

        writer.write(String.format("%d\n", bfs()));
        writer.flush();
        writer.close();
    }

    static int bfs() {
        Queue<State> queue = new LinkedList<>();
        Set<Pair> visited = new HashSet<>();
        Pair firstPair = new Pair(0, 0);
        queue.add(new State(firstPair, 0));
        visited.add(firstPair);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            /*  target equals 검사를 각 연산 수행마다 하게 되면 제일 처음 queue에 삽입된 pair의 상태를 검사할 수 없음. 
                (이것 때문에 틀렸다고 나왔었음)
              */
            if (currentState.pair.equals(target)) {
                return currentState.count;
            }
          
            Pair pair1 = fillA(currentState.pair);
            if (!visited.contains(pair1)) {
                visited.add(pair1);
                queue.add(new State(pair1, currentState.count + 1));
            }

            Pair pair2 = fillB(currentState.pair);
            if (!visited.contains(pair2)) {
                visited.add(pair2);
                queue.add(new State(pair2, currentState.count + 1));
            }

            Pair pair3 = emptyA(currentState.pair);
            if (!visited.contains(pair3)) {
                visited.add(pair3);
                queue.add(new State(pair3, currentState.count + 1));
            }

            Pair pair4 = emptyB(currentState.pair);
            if (!visited.contains(pair4)) {
                visited.add(pair4);
                queue.add(new State(pair4, currentState.count + 1));
            }

            Pair pair5 = moveAB(currentState.pair);
            if (!visited.contains(pair5)) {
                visited.add(pair5);
                queue.add(new State(pair5, currentState.count + 1));
            }

            Pair pair6 = moveBA(currentState.pair);
            if (!visited.contains(pair6)) {
                visited.add(pair6);
                queue.add(new State(pair6, currentState.count + 1));
            }
        }

        return -1;
    }

    static Pair fillA(Pair old) {
        return new Pair(aCapacity, old.bSize);
    }
    static Pair fillB(Pair old) {
        return new Pair(old.aSize, bCapacity);
    }
    static Pair emptyA(Pair old) {
        return new Pair(0, old.bSize);
    }
    static Pair emptyB(Pair old) {
        return new Pair(old.aSize, 0);
    }
    static Pair moveAB(Pair old) {
        int remainingCapacity = bCapacity - old.bSize;

        if (old.aSize <= remainingCapacity) {
            return new Pair(0, old.bSize + old.aSize);
        } else {
            return new Pair(old.aSize - remainingCapacity, bCapacity);
        }
    }

    static Pair moveBA(Pair old) {
        int remainingCapacity = aCapacity - old.aSize;

        if (old.bSize <= remainingCapacity) {
            return new Pair(old.aSize + old.bSize, 0);
        } else {
            return new Pair(aCapacity, old.bSize - remainingCapacity);
        }
    }

    static class Pair {
        int aSize;
        int bSize;

        Pair() {}

        Pair(int aSize, int bSize) {
            this.aSize = aSize;
            this.bSize = bSize;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Pair)) {
                return false;
            }

            Pair other = (Pair) obj;
            return aSize == other.aSize && bSize == other.bSize;
        }

        @Override
        public int hashCode() {
            return Objects.hash(aSize, bSize);
        }
    }

    static class State {
        Pair pair;
        int count;

        public State(Pair pair, int count) {
            this.pair = pair;
            this.count = count;
        }
    }
}
