# 题目

[https://leetcode-cn.com/problems/is-graph-bipartite/](https://leetcode-cn.com/problems/is-graph-bipartite/)
给定一个无向图graph，当这个图为二分图时返回true。

如果我们能将一个图的节点集合分割成两个独立的子集A和B，并使图中的每一条边的两个节点一个来自A集合，一个来自B集合，我们就将这个图称为二分图。

graph将会以邻接表方式给出，graph[i]表示图中与节点i相连的所有节点。每个节点都是一个在0到graph.length-1之间的整数。这图中没有自环和平行边： graph[i] 中不存在i，并且graph[i]中没有重复的值。

![leetcode785_判断二分图_1.png](/assets/leetcode785_判断二分图_1.png)

官方思路：
对于图中的任意两个节点 uu 和 vv，如果它们之间有一条边直接相连，那么 uu 和 vv 必须属于不同的集合。

如果给定的无向图连通，那么我们就可以任选一个节点开始，给它染成红色。随后我们对整个图进行遍历，将该节点直接相连的所有节点染成绿色，表示这些节点不能与起始节点属于同一个集合。我们再将这些绿色节点直接相连的所有节点染成红色，以此类推，直到无向图中的每个节点均被染色。

如果我们能够成功染色，那么红色和绿色的节点各属于一个集合，这个无向图就是一个二分图；如果我们未能成功染色，即在染色的过程中，某一时刻访问到了一个已经染色的节点，并且它的颜色与我们将要给它染上的颜色不相同，也就说明这个无向图不是一个二分图。



# 广度优先搜索
最先想到的可能是广度优先搜索，或者是并查集。
广度优先搜索的思路就是，比如
![leetcode785_判断二分图_BFS.png](/assets/leetcode785_判断二分图_BFS.png)
随机取一个起始点v，然后从该点开始，遍历所有的邻接点w,
如果w已经访问，那么判断颜色；
如果w没有访问，则把当前的邻接点w设置为和当前点v不一样的颜色
```
class Solution {
    public boolean isBipartite(int[][] graph) {
        // 0, 表示未访问；1和-1两种颜色，表示已经访问
        int[] visited = new int[graph.length];
        Queue<Integer> queue = new LinkedList<>();
        // 首先随机加入一个点
        // 因为可能有多个连通块，所以需要遍历所有的初始点
        for (int i = 0; i < graph.length; i++) {
            if (visited[i] != 0) continue;
            queue.offer(i);
            visited[i] = 1;
            while (!queue.isEmpty()) {
                int v = queue.poll();
                for (int w : graph[v]) {
                    if (visited[w] == visited[v]) {
                        return false;
                    }
                    if (visited[w] == 0) {
                        visited[w] = -visited[v];
                        queue.offer(w);
                    }
                }
            }
        }
        return true;
    }
}
```

# 并查集
思路类似，就是每次遍历当前点v的邻接点w，如果w已经是v的集合中了，那么就不是二分图。
```
class Solution {
    public boolean isBipartite(int[][] graph) {
        // 并查集
        UnionFind unionFind = new UnionFind(graph.length);
        for (int i = 0; i < graph.length; i++) {
            int[] adjs = graph[i];
            for (int w : adjs) {
                if (unionFind.isConnected(i, w)) {
                    return false;
                }
                unionFind.union(adjs[0], w);
            }
        }
        return true;
    }
}
class UnionFind {
    int[] roots;
    public UnionFind(int n) {
        roots = new int[n];
        for (int i = 0; i < n; i++) {
            roots[i] = i;
        }
    }
    public int find(int i) {
        if (roots[i] == i) return i;
        return roots[i] = find(roots[i]);
    }
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
    public void union(int p, int q) {
        int x1 = find(p);
        int x2 = find(q);
        if (x1 == x2) return;
        roots[x2] = x1;
    }
}
```
还可以进行深搜。

# 深度优先搜索
![leetcode785_判断二分图_DFS.png](/assets/leetcode785_判断二分图_DFS.png)

如图，和广度优先的不同之处在于，深度优先搜索实在回溯的时候，进行判断。
如果蓝色线，回来找的邻接点w应该是绿色，
但是回溯的时候，找到的确是红色，所以这种就不是二分图。
```
class Solution {
    public boolean isBipartite(int[][] graph) {
        // 0, 表示未访问；1和-1两种颜色，表示已经访问
        int[] visited = new int[graph.length];
        
        for (int i = 0; i < graph.length; i++) {
            if (visited[i] == 0) {
                // i; 当前1,表示访问;
                if (!dfs(i, 1, graph, visited)) return false;
            }
        }
        return true;
    }
    public boolean dfs(int current, int color, int[][] graph, int[] visited) {
        // 如果当前已经访问过，则判断颜色是否一样，一样的话就不行。
        if (visited[current] != 0) {
            return visited[current] == color;
        }
        visited[current] = color;
        for (int w : graph[current]) {
            if (!dfs(w, -color, graph, visited)) {
                return false;
            }
        }
        return true;
    }
}
```
