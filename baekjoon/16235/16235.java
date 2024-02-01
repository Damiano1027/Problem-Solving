import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer tokenizer;
    static int N, M, K;
    static int[][] A;
    static class Information {
        int nutrients;
        List<Tree> aliveTrees = new LinkedList<>();
        List<Tree> deadTrees = new ArrayList<>();
    }
    static class Tree implements Comparable<Tree> {
        int age;
        Tree(int age) {
            this.age = age;
        }
        public int compareTo(Tree o) {
            return age - o.age;
        }
    }
    static Information[][] staticMatrix;
    static int[] dRow = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dCol = {-1, 0, 1, -1, 1, -1, 0, 1};
    static int result;

    public static void main(String[] args) throws Exception {
        tokenizer = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        A = new int[N + 1][N + 1];
        staticMatrix = new Information[N + 1][N + 1];
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                staticMatrix[row][col] = new Information();
                staticMatrix[row][col].nutrients = 5;
            }
        }

        for (int row = 1; row <= N; row++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int col = 1; col <= N; col++) {
                A[row][col] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());
            int z = Integer.parseInt(tokenizer.nextToken());

            staticMatrix[x][y].aliveTrees.add(new Tree(z));
        }

        solve();

        writer.write(String.format("%d\n", result));
        writer.flush();

        writer.close();
    }

    static void solve() {
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                if (!staticMatrix[row][col].aliveTrees.isEmpty()) {
                    Collections.sort(staticMatrix[row][col].aliveTrees);
                }
            }
        }

        for (int i = 0; i < K; i++) {
            spring();
            summer();
            fall();
            winter();
        }

        result = getResult();
    }

    /*
          봄
          - 나무가 자신의 나이만큼 해당 칸의 양분을 먹고, 나이가 1 증가
            - 나이가 어린순으로 양분을 먹음
          - 자신의 나이만큼 양분을 먹을 수 없는 나무는 죽음

          여름
          - 봄에 죽은 나무들이 각각 자신의 나이가 2로 나누어진 값을 해당 칸에 양분으로 추가

          가을
          - 나이가 5의 배수인 나무는 인접한 칸에 나이가 1인 나무를 추가시킴

          가을
          - 땅에 양분 추가 (A 배열)
     */

    static void spring() {
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                if (staticMatrix[row][col].aliveTrees.isEmpty()) {
                    continue;
                }

                List<Tree> aliveTrees = staticMatrix[row][col].aliveTrees;

                for (int i = 0; i < aliveTrees.size(); i++) {
                    Tree tree = aliveTrees.get(i);

                    if (tree.age <= staticMatrix[row][col].nutrients) {
                        staticMatrix[row][col].nutrients -= tree.age;
                        tree.age++;
                    } else {
                        for (int j = i; j < aliveTrees.size();) {
                            staticMatrix[row][col].deadTrees.add(aliveTrees.remove(j));
                        }
                        break;
                    }
                }
            }
        }
    }

    static void summer() {
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                if (staticMatrix[row][col].deadTrees.isEmpty()) {
                    continue;
                }

                for (Tree deadTree: staticMatrix[row][col].deadTrees) {
                    staticMatrix[row][col].nutrients += (deadTree.age / 2);
                }

                staticMatrix[row][col].deadTrees = new ArrayList<>();
            }
        }
    }

    static void fall() {
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                if (staticMatrix[row][col].aliveTrees.isEmpty()) {
                    continue;
                }

                List<Tree> aliveTrees = staticMatrix[row][col].aliveTrees;

                for (Tree tree: aliveTrees) {
                    if (tree.age % 5 == 0) {
                        for (int i = 0; i < 8; i++) {
                            int aRow = row + dRow[i];
                            int aCol = col + dCol[i];

                            if (isInMatrix(aRow, aCol)) {
                                staticMatrix[aRow][aCol].aliveTrees.add(0, new Tree(1));
                            }
                        }
                    }
                }
            }
        }
    }

    static void winter() {
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                staticMatrix[row][col].nutrients += A[row][col];
            }
        }
    }

    static int getResult() {
        int count = 0;

        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                count += staticMatrix[row][col].aliveTrees.size();
            }
        }

        return count;
    }

    static boolean isInMatrix(int row, int col) {
        return row >= 1 && row <= N && col >= 1 && col <= N;
    }
}
