import java.util.Scanner;

public class BottomUp {
    static int[] d = new int[1000001];

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        d[1] = 0;
        for (int i = 2; i <= N; i++) {
            if (i % 3 == 0 && i % 2 == 0) {
                d[i] = Math.min(Math.min(d[i / 3], d[i / 2]), d[i - 1]) + 1;
            } else if (i % 3 == 0) {
                d[i] = Math.min(d[i / 3], d[i - 1]) + 1;
            } else if (i % 2 == 0) {
                d[i] = Math.min(d[i / 2], d[i - 1]) + 1;
            } else {
                d[i] = d[i - 1] + 1;
            }
        }

        System.out.println(d[N]);
    }
}
