import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] t = new int[17]; // t[i]: (i번째 날부터) 상담을 완료하는데 걸리는 기간
    static int[] p = new int[17]; // p[i]: 상담을 완료하였을때 받을 수 있는 금액 (i번째 날부터 시작하여 상담을 완료하였을 경우)
    static int[] d = new int[17];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        for (int i = 1; i <= N; i++) {
            // 한줄 읽고
            String input = br.readLine();
            StringTokenizer st = new StringTokenizer(input, " ");

            // 배열 세팅
            int first = Integer.parseInt(st.nextToken());
            int second = Integer.parseInt(st.nextToken());
            t[i] = first;
            p[i] = second;
        }

        // d[i]: i번째 날부터 퇴사날 전까지 벌 수 있는 최대 금액
        for (int i = N; i >= 1; i--) {
            // i번째 날부터 상담하게 되면 퇴사날 이상이 되어 상담할 수 없는 경우
            if (i + t[i] - 1 > N) {
                d[i] = d[i + 1];
            }
            // i번째 날부터 상담을 시작하여 완료해도 퇴사날 이상이 되지 않는 경우
            else {
                /*
                   상담을 진행할 경우: 상담 후 받게되는 금액(p[i]) + 상담을 마친 다음 날부터 벌 수 있는 최대 금액(d[i + t[i]])
                   상담을 진행하지 않을 경우: 다음날부터 퇴사날 전까지 벌 수 있는 최대 금액(d[i + 1])
                   둘중, 더 큰 값을 d[i]에 저장
                 */
                d[i] = Math.max(p[i] + d[i + t[i]], d[i + 1]);
            }
        }

        // 1번째 날부터 퇴사날 전까지 벌 수 있는 최대 금액
        System.out.println(d[1]);
    }
}
