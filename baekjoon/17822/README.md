### 문제
- https://www.acmicpc.net/problem/17822

### 접근
- 단순 구현 문제라고 판단된다.
- 각각의 원판을 int 배열로 표현하고, 엣지 케이스만 잘 체크하면 충분히 해결할 수 있다.

### 실수했던 부분
- x의 배수에 대해서만 1번 과정을 수행하기 위한 for문에서, 값을 증가시킬때 `+= x` 로 해주어야하지만 `*= 2`로 잘못 작성하였다.
#### 올바른 코드
```java
for (int number = x; number <= N; number += x) {
    step1(number, d, k);
}
```
- 코드 129번째 줄에서 `removable = true`로 작성해야 하는데 `removable = false`로 잘못 작성하였다.
#### 올바른 코드
```java
if (leftValue == rightValue) {
    removable = true;
    locations.add(new Location(circleNumber, j));
    if (j == M - 1) {
        locations.add(new Location(circleNumber, 0));
    } else {
        locations.add(new Location(circleNumber, j + 1));
    }
}
```
