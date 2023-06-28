import sys
from collections import deque
from functools import cmp_to_key

class Location:
    def __init__(self, row, col):
        self.row = row
        self.col = col

class BFSInformation:
    def __init__(self, location, distance):
        self.location = location
        self.distance = distance

class Shark:
    def __init__(self, location, size):
        self.location = location
        self.size = size

matrix = []
N = 0
adjacent_directions = [
    Location(-1, 0),
    Location(0, -1),
    Location(1, 0),
    Location(0, 1)
]
result = 0

def solve():
    baby_shark_location = None

    for i in range(N):
        for j in range(N):
            if matrix[i][j] == 9:
                baby_shark_location = Location(i, j)
                break

    baby_shark = Shark(baby_shark_location, 2)

    eaten = True
    eaten_count = 0
    while eaten:
        eaten = bfs(baby_shark)
        if eaten:
            eaten_count += 1
        if eaten_count >= baby_shark.size:
            baby_shark.size += 1
            eaten_count = 0

def bfs(baby_shark):
    global result

    # 방문 배열 setting
    visited = [[False for _ in range(N)] for _ in range(N)]
    visited[baby_shark.location.row][baby_shark.location.col] = True

    # queue 세팅
    queue = deque()
    queue.append(BFSInformation(Location(baby_shark.location.row, baby_shark.location.col), 0))

    # 먹을 수 있는 물고기들에 대한 bfs 정보 list
    eatable_fish_informations = []

    while bool(queue):
        current_bfs_information = queue.popleft()

        for adjoin_direction in adjacent_directions:
            adjoin_location = Location(current_bfs_information.location.row + adjoin_direction.row, current_bfs_information.location.col + adjoin_direction.col)

            # 이동이 불가능하거나 이미 방문한 위치라면 pass
            if not can_move_to(adjoin_location, baby_shark.size) or visited[adjoin_location.row][adjoin_location.col]:
                continue

            # 지금부턴 이동이 가능한 경우임.

            visited[adjoin_location.row][adjoin_location.col] = True

            # 먹을 수 있는 물고기가 위치한 곳이라면 list에 추가
            if can_eat(adjoin_location, baby_shark.size):
                eatable_fish_informations.append(BFSInformation(Location(adjoin_location.row, adjoin_location.col), current_bfs_information.distance + 1))
            else:
                queue.append(BFSInformation(Location(adjoin_location.row, adjoin_location.col), current_bfs_information.distance + 1))

    # 먹을 수 있는 물고기가 없으면 없다고 알림
    if len(eatable_fish_informations) == 0:
        return False

    # 아래에서부턴 먹을 수 있는 물고기가 존재하는 경우임
    eatable_fish_informations.sort(key=cmp_to_key(compare))
    next_baby_shark_information = eatable_fish_informations[0]

    # 최신화
    matrix[baby_shark.location.row][baby_shark.location.col] = 0
    matrix[next_baby_shark_information.location.row][next_baby_shark_information.location.col] = 0
    baby_shark.location.row = next_baby_shark_information.location.row
    baby_shark.location.col = next_baby_shark_information.location.col

    # 시간(거리) 누적
    result += next_baby_shark_information.distance

    # 먹을 수 있는 물고기가 있었다고 알림
    return True

def can_move_to(location, baby_shark_size):
    if location.row < 0 or location.row >= N or location.col < 0 or location.col >= N:
        return False

    return matrix[location.row][location.col] <= baby_shark_size

def can_eat(location, baby_shark_size):
    if matrix[location.row][location.col] == 0:
        return False

    return matrix[location.row][location.col] < baby_shark_size

def compare(bfs_information1, bfs_information2):
    if bfs_information1.distance != bfs_information2.distance:
        return bfs_information1.distance - bfs_information2.distance
    elif bfs_information1.location.row != bfs_information2.location.row:
        return bfs_information1.location.row - bfs_information2.location.row
    else:
        return bfs_information1.location.col - bfs_information2.location.col

if __name__ == '__main__':
    N = int(sys.stdin.readline())
    for _ in range(N):
        matrix.append(list(map(int, sys.stdin.readline().split())))

    solve()

    print(result)
