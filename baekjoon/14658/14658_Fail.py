# Fail
# 시간 초과, 메모리 초과

import sys

N = None
M = None
L = None
K = None
matrix = []

def is_positionable(start_row, start_col):
    for i in range(start_row, start_row + L + 1):
        for j in range(start_col, start_col + L + 1):
            if i > M or j > N:
                return False

    return True

def get_bouncing_count(start_row, start_col):
    bouncing_count = 0

    for i in range(start_row, start_row + L + 1):
        for j in range(start_col, start_col + L + 1):
            if matrix[i][j]:
                bouncing_count += 1

    return bouncing_count

def solve():
    max = -1
    result = None

    for i in range(M + 1):
        for j in range(N + 1):
            if is_positionable(i, j):
                bouncing_count = get_bouncing_count(i, j)
                if bouncing_count > max:
                    max = bouncing_count
                    result = K - max

    return result

if __name__ == '__main__':
    N, M, L, K = map(int, sys.stdin.readline().split())

    for _ in range(M + 1):
        matrix.append([False] * (N + 1))

    for _ in range(K):
        x, y = map(int, sys.stdin.readline().split())
        matrix[y][x] = True

    print(solve())



