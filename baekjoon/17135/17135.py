import sys

matrix = None
temp_matrix = None
N = None
M = None
D = None
max_attacked_enemy_count = 0

class Location:
    def __init__(self, row, col):
        self.row = row
        self.col = col

    def __eq__(self, other):
        if other is None:
            return False
        if other is self:
            return True
        if type(other) is not Location:
            return False

        return self.row == other.row and self.col == other.col

    def __hash__(self):
        return hash((self.row, self.col))

def solve():
    global matrix, temp_matrix, N, M, D, max_attacked_enemy_count

    for i in range(0, M - 2):
        for j in range(i + 1, M - 1):
            for k in range(j + 1, M):
                temp_matrix = [[0] * M for _ in range(N)]
                copy_matrix(matrix, temp_matrix)

                first_archer_col = i
                second_archer_col = j
                third_archer_col = k

                attacked_enemy_count = 0

                while is_enemy_exist():
                    attacked_locations = set()
                    attack(first_archer_col, attacked_locations)
                    attack(second_archer_col, attacked_locations)
                    attack(third_archer_col, attacked_locations)

                    for attacked_location in attacked_locations:
                        temp_matrix[attacked_location.row][attacked_location.col] = 0

                    attacked_enemy_count += len(attacked_locations)

                    move_enemy()

                if attacked_enemy_count > max_attacked_enemy_count:
                    max_attacked_enemy_count = attacked_enemy_count


def copy_matrix(matrix1, matrix2):
    global matrix, temp_matrix, N, M, D, max_attacked_enemy_count

    for i in range(N):
        for j in range(M):
            matrix2[i][j] = matrix1[i][j]

def is_enemy_exist():
    global matrix, temp_matrix, N, M, D, max_attacked_enemy_count

    for i in range(N):
        for j in range(M):
            if temp_matrix[i][j] == 1:
                return True

    return False

def attack(archer_col, attacked_locations):
    global matrix, temp_matrix, N, M, D, max_attacked_enemy_count

    min_distance_enemy_location = None
    min_distance = None

    for j in range(M):
        for i in range(N):
            if temp_matrix[i][j] == 1 and get_distance(N, archer_col, i, j) <= D:
                if min_distance_enemy_location is None:
                    min_distance = get_distance(N, archer_col, i, j)
                    min_distance_enemy_location = Location(i, j)
                    continue

                distance = get_distance(N, archer_col, i, j)
                if distance < min_distance:
                    min_distance = distance
                    min_distance_enemy_location = Location(i, j)

    if min_distance_enemy_location is not None:
        attacked_locations.add(min_distance_enemy_location)


def get_distance(row1, col1, row2, col2):
    global matrix, temp_matrix, N, M, D, max_attacked_enemy_count

    return abs(row1 - row2) + abs(col1 - col2)

def move_enemy():
    global matrix, temp_matrix, N, M, D, max_attacked_enemy_count

    for i in range(N - 1, 0, -1):
        temp_matrix[i] = temp_matrix[i - 1]

    temp_matrix[0] = [0] * M

if __name__ == '__main__':
    N, M, D = map(int, sys.stdin.readline().split())
    matrix = [list(map(int, sys.stdin.readline().split())) for _ in range(N)]

    solve()

    print(max_attacked_enemy_count)
