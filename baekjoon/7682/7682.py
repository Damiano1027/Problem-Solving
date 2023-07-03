import sys

def make_matrix(str):
    matrix = []

    for i in range(0, 9, 3):
        matrix.append(list(str[i:i + 3]))

    return matrix

"""
조건
- X가 승리할 경우
    - X는 연속적인 3칸 존재해야 함
    - O는 연속적인 3칸 존재하면 안됨
    - X가 O보다 1개 더 많아야 함
- O가 승리할 경우
    - O는 연속적인 3칸 존재해야 함
    - X는 연속적인 3칸 존재하면 안됨
    - X와 O의 개수가 같아야 함
- 비길 경우
    - X는 연속적인 3칸 존재하면 안됨
    - O는 연속적인 3칸 존재하면 안됨
    - X가 5개, O가 4개여야 함    
"""

def is_valid(matrix):
    count_of_X = get_count_of_X(matrix)
    count_of_O = get_count_of_O(matrix)
    exist_3_partitions_of_X = is_exist_3_partitions_of_X(matrix)
    exist_3_partitions_of_O = is_exist_3_partitions_of_O(matrix)

    if count_of_X == 5 and count_of_O == 4:
        if not exist_3_partitions_of_X and not exist_3_partitions_of_O:
            return True

    if count_of_X == count_of_O:
        if exist_3_partitions_of_O and not exist_3_partitions_of_X:
            return True

    if count_of_X == count_of_O + 1:
        if exist_3_partitions_of_X and not exist_3_partitions_of_O:
            return True

    return False

def get_count_of_X(matrix):
    count = 0

    for row in matrix:
        for partition in row:
            if partition == 'X':
                count += 1

    return count

def get_count_of_O(matrix):
    count = 0

    for row in matrix:
        for partition in row:
            if partition == 'O':
                count += 1

    return count

def is_exist_3_partitions_of_X(matrix):
    # 가로 확인
    for i in range(0, 3):
        if matrix[i][0] == 'X' and matrix[i][0] == matrix[i][1] and matrix[i][1] == matrix[i][2]:
            return True

    # 세로 확인
    for i in range(0, 3):
        if matrix[0][i] == 'X' and matrix[0][i] == matrix[1][i] and matrix[1][i] == matrix[2][i]:
            return True

    # 대각선 확인
    if matrix[0][0] == 'X' and matrix[0][0] == matrix[1][1] and matrix[1][1] == matrix[2][2]:
        return True
    if matrix[0][2] == 'X' and matrix[0][2] == matrix[1][1] and matrix[1][1] == matrix[2][0]:
        return True

    return False

def is_exist_3_partitions_of_O(matrix):
    # 가로 확인
    for i in range(0, 3):
        if matrix[i][0] == 'O' and matrix[i][0] == matrix[i][1] and matrix[i][1] == matrix[i][2]:
            return True

    # 세로 확인
    for i in range(0, 3):
        if matrix[0][i] == 'O' and matrix[0][i] == matrix[1][i] and matrix[1][i] == matrix[2][i]:
            return True

    # 대각선 확인
    if matrix[0][0] == 'O' and matrix[0][0] == matrix[1][1] and matrix[1][1] == matrix[2][2]:
        return True
    if matrix[0][2] == 'O' and matrix[0][2] == matrix[1][1] and matrix[1][1] == matrix[2][0]:
        return True

    return False

if __name__ == '__main__':
    while True:
        str = sys.stdin.readline().strip()
        if str == 'end':
            break

        matrix = make_matrix(str)

        if is_valid(matrix):
            print('valid')
        else:
            print('invalid')
