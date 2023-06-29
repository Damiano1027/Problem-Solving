import sys
sys.setrecursionlimit(10**6)

want = []
visited = []
done = []
cnt = 0

def dfs(current_student):
    visited[current_student] = True

    next_student = want[current_student]
    if not visited[next_student]:
        dfs(next_student)
    elif not done[next_student]:
        dfs_for_make_team(next_student)

    done[current_student] = True

def dfs_for_make_team(current_student):
    global cnt

    done[current_student] = True
    cnt += 1

    next_student = want[current_student]
    if visited[next_student] and not done[next_student]:
        dfs_for_make_team(next_student)


if __name__ == '__main__':
    T = int(sys.stdin.readline())

    for _ in range(T):
        n = int(sys.stdin.readline())

        want = [None]
        want.extend(list(map(int, sys.stdin.readline().split())))
        visited = [False] * (n + 1)
        done = [False] * (n + 1)
        cnt = 0

        for student in range(1, n + 1):
            if not visited[student]:
                dfs(student)

        print(n - cnt)
