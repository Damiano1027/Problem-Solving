import sys
from collections import deque

def dfs(current_node, graph, visited):
    visited[current_node] = True
    print(str(current_node), end=" ")
    for adjoin_node in graph[current_node]:
        if not visited[adjoin_node]:
            dfs(adjoin_node, graph, visited)

def bfs(start_node, graph, visited):
    queue = deque()
    queue.append(start_node)
    visited[start_node] = True

    while bool(queue):
        current_node = queue.popleft()
        print(current_node, end=' ')
        for adjoin_node in graph[current_node]:
            if not visited[adjoin_node]:
                queue.append(adjoin_node)
                visited[adjoin_node] = True

if __name__ == '__main__':
    N, M, V = map(int, sys.stdin.readline().split())
    graph = [[] for _ in range(N + 1)]
    
    for _ in range(M):
        node1, node2 = map(int, sys.stdin.readline().split())
        graph[node1].append(node2)
        graph[node2].append(node1)

    for i in range(N):
        graph[i + 1].sort()

    dfs(V, graph, [False] * (N + 1))
    print()
    bfs(V, graph, [False] * (N + 1))
