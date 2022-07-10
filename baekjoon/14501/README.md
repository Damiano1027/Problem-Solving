# 백준 14501 (퇴사)
## 문제
[https://www.acmicpc.net/problem/14501](https://www.acmicpc.net/problem/14501)

### 풀이
구해야 하는 값은 **백준이가 얻을 수 있는 최대 수익**.

d[i]: i번째 날부터 퇴사 전날까지 얻을 수 있는 최대 수익

이라고 하면

d[1]을 구하면 될 것이다. (백준이가 1번째 날부터 퇴사 전날까지 얻을 수 있는 최대 수익)

점화식은 다음과 같다.
 
-  i번째 날부터 상담을 시작하면 퇴사 전날까지 상담을 마치지 못하는 경우: **d[i] = d[i + 1]**
	- 어차피 해당 날부터는 상담 시작을 못하기 때문에 d[i]는 d[i + 1]와 같다고 볼 수 있음.
- i번째 날부터 상담을 시작하면 퇴사 전날까지 상담을 마칠 수 있는 경우: **d[i] = max(p[i] + d[i + t[i]], p[i + 1])**
	- 상황은 둘로 나뉜다.
		1. 해당 날부터 상담을 시작하는 경우
			- 그 상담을 끝냈을때 얻는 수익(p[i]) + 상담이 끝난 다음날의 d 배열값(d[i + t[i]])
		2. 해당 날에 상담을 하지 않는 경우
			- 다음날의 d 배열값 (d[i + 1])
	- 두 상황으로부터 나온 값 중 더 큰 값을 d[i]에 저장한다.

### 코드
```java
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

```
 
