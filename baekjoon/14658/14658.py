import sys

N = None
M = None
L = None
K = None
coordinates = []

def get_bouncing_count(x, y):
    bouncing_count = 0

    for coordinate in coordinates:
        if x <= coordinate[0] <= x + L and y <= coordinate[1] <= y + L:
            bouncing_count += 1

    return bouncing_count

def solve():
    result = None
    max = -1

    for coordinate1 in coordinates:
        for coordinate2 in coordinates:
            bouncing_count = get_bouncing_count(coordinate1[0], coordinate2[1])
            if bouncing_count > max:
                max = bouncing_count
                result = K - max

    return result

if __name__ == '__main__':
    N, M, L, K = map(int, sys.stdin.readline().split())

    for _ in range(K):
        x, y = map(int, sys.stdin.readline().split())
        coordinates.append((x, y))

    print(solve())
