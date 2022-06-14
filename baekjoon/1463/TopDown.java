import java.util.Scanner;

public class TopDown {
    static int[] visited = new int[1000001];

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int result = dp(N);

        System.out.println(result);
    }

    static int dp(int N) {
        if (N == 1) {
            return 0;
        }
        if (visited[N] > 0) {
            return visited[N];
        }

        if (N % 3 == 0 && N % 2 == 0) {
            return visited[N] = Math.min(Math.min(dp(N / 3), dp(N / 2)), dp(N - 1)) + 1;
        } else if (N % 3 == 0) {
            return visited[N] = Math.min(dp(N / 3), dp(N - 1)) + 1;
        } else if (N % 2 == 0) {
            return visited[N] = Math.min(dp(N / 2), dp(N - 1)) + 1;
        } else {
            return visited[N] = dp(N - 1) + 1;
        }
    }
}
