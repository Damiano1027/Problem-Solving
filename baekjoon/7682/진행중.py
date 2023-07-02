import sys

def make_matrix(str):
    matrix = []

    for i in range(0, 9, 3):
        matrix.append(list(str[i:i + 3]))

    return matrix

def solve(matrix):
    count_of_pieces = get_count_of_pieces(matrix)

    if count_of_pieces <= 4:
        return False
    elif count_of_pieces == 5:
        return is_terminable(matrix)
    else:
        if not is_terminable(matrix):
            return False

        if count_of_pieces % 2 == 0:
            for i in range(len(matrix)):
                for j in range(len(matrix[i])):
                    if matrix[i][j] == 'O':
                        matrix[i][j] = '.'
                        if terminable_matrix_exist(matrix, 'X', count_of_pieces - 1):
                            return False
                        matrix[i][j] = 'O'
        else:
            for i in range(len(matrix)):
                for j in range(len(matrix[i])):
                    if matrix[i][j] == 'X':
                        matrix[i][j] = '.'
                        if terminable_matrix_exist(matrix, 'O', count_of_pieces - 1):
                            return False
                        matrix[i][j] = 'X'

        return True

def terminable_matrix_exist(matrix, target_piece, count_of_pieces):
    if count_of_pieces <= 4:
        return False

    if is_terminable(matrix):
        return True

    for i in range(len(matrix)):
        for j in range(len(matrix[i])):
            if matrix[i][j] == target_piece:
                print(i, j)
                matrix[i][j] = '.'

                if target_piece == 'O':
                    next_target_piece = 'X'
                else:
                    next_target_piece = 'O'

                if terminable_matrix_exist(matrix, next_target_piece, count_of_pieces - 1):
                    return True

                matrix[i][j] = target_piece

    return False

def get_count_of_pieces(matrix):
    count = 0

    for row in matrix:
        for partition in row:
            if partition != '.':
                count += 1

    return count

def is_terminable(matrix):
    # 가로 확인
    for i in range(0, 3):
        if matrix[i][0] != '.' and matrix[i][0] == matrix[i][1] and matrix[i][1] == matrix[i][2] :
            return True

    # 세로 확인
    for i in range(0, 3):
        if matrix[0][i] != '.' and matrix[0][i] == matrix[1][i] and matrix[1][i] == matrix[2][i]:
            return True

    # 대각선 확인
    if matrix[0][0] != '.' and matrix[0][0] == matrix[1][1] and matrix[1][1] == matrix[2][2]:
        return True
    if matrix[0][2] != '.' and matrix[0][2] == matrix[1][1] and matrix[1][1] == matrix[2][0]:
        return True

    return False

if __name__ == '__main__':
    str = None

    while str != 'end':
        str = sys.stdin.readline()
        matrix = make_matrix(str)

        if solve(matrix):
            print('valid')
        else:
            print('invalid')
