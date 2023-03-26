"""
   [문제 설명]
    H*W 크기의 게임판이 있습니다. 게임판은 검은 칸과 흰 칸으로 구성된 격자 모양을 하고 있습니다. 주어진 게임판의 흰 칸을 3칸 짜리 L자 모양의 블록으로 덮고 싶습니다. 이때 블록은 자유롭게 회전하여 높을 수 있지만, 서로 겹치거나 검은 칸을 덮거나 게임판 밖으로 나가서는 안 됩니다. 다음의 예시입니다.
   게임판이 주어질 때 이를 덮는 방법의 수를 찾아주세요.
    https://www.algospot.com/judge/problem/read/BOARDCOVER

   [입력 설명]
    첫 줄에는 테스트케이스의 수 T(1<=T<=30)가 주어집니다. 각 테스트케이스의 첫 줄에는 2개의 정수 H와 W(1<=H, W<=20)가 주어집니다. 다음 H 줄에 각 W 글자로 게임판의 모양이 주어집니다. #은 검은 칸, .은 흰 칸을 나타냅니다. 입력에 주어지는 게임판에 있는 흰 칸의 수는 50을 넘지 않습니다.
   [출력 설명]
    주어진 게임판의 흰 칸을 모두 덮는 방법의 수를 출력합니다.

   [입력 예시]
    3 
    3 7 
    #.....# 
    #.....# 
    ##...## 
    3 7 
    #.....# 
    #.....# 
    ##..### 
    8 10 
    ########## 
    #........# 
    #........# 
    #........# 
    #........# 
    #........# 
    #........# 
    ##########
    
    [출력 예시]
     0
     2
     1514
"""

cover_shapes = [
    [[0, 0], [0, 1], [1, 0]],
    [[0, 0], [0, 1], [1, 1]],
    [[0, 0], [1, 0], [1, 1]],
    [[0, 0], [1, 0], [1, -1]]
]

def get_white_cell_location(matrix):
    for row in range(len(matrix)):
        for col in range(len(matrix[row])):
            if matrix[row][col] == '.':
                return [row, col]

    return None


def get_white_cell_count(matrix):
    count = 0

    for row in range(len(matrix)):
        for col in range(len(matrix[row])):
            if matrix[row][col] == '.':
                count += 1

    return count


def is_range(matrix, row, col):
    if row < 0 or row >= len(matrix) or col < 0 or col >= len(matrix[0]):
        return False
    if matrix[row][col] != '.':
        return False

    return True

def is_coverable(matrix, start_row, start_col, cover_shape):
    for i in range(len(cover_shape)):
        row = start_row + cover_shape[i][0]
        col = start_col + cover_shape[i][1]

        if not is_range(matrix, row, col):
            return False

    return True

def cover(matrix, start_row, start_col, cover_shape):
    for i in range(len(cover_shape)):
        row = start_row + cover_shape[i][0]
        col = start_col + cover_shape[i][1]
        matrix[row][col] = '#'

def revert(matrix, start_row, start_col, cover_shape):
    for i in range(len(cover_shape)):
        row = start_row + cover_shape[i][0]
        col = start_col + cover_shape[i][1]
        matrix[row][col] = '.'

def get_cover_count(matrix):
    next_white_cell_location = get_white_cell_location(matrix)

    if next_white_cell_location is None:  # 흰칸이 더이상 없는 경우
        return 1

    count = 0

    row = next_white_cell_location[0]
    col = next_white_cell_location[1]
    for j in range(4):
        if is_coverable(matrix, row, col, cover_shapes[j]):
            cover(matrix, row, col, cover_shapes[j])
            count += get_cover_count(matrix)
            revert(matrix, row, col, cover_shapes[j])

    return count

def main():
    T = int(input())

    for _ in range(T):
        H, W = map(int, input().split())

        matrix = [list(input()) for _ in range(H)]
        if get_white_cell_count(matrix) % 3 != 0:
            print(0)
        else:
            print(get_cover_count(matrix))

if __name__ == '__main__':
    main()
