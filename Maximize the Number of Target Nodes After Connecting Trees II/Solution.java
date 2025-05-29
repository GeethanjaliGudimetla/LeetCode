import java.util.*;

public class Solution {
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2) {
        int n = edges1.length + 1;
        int m = edges2.length + 1;

        // Build adjacency lists for both trees
        List<Integer>[] graph1 = buildGraph(edges1, n);
        List<Integer>[] graph2 = buildGraph(edges2, m);

        // Arrays to store parity (even or odd depth) for each node
        boolean[] parity1 = new boolean[n];
        boolean[] parity2 = new boolean[m];

        // Count of even-depth nodes in both trees
        int evenCount1 = dfs(graph1, 0, -1, parity1, true);
        int evenCount2 = dfs(graph2, 0, -1, parity2, true);

        int oddCount1 = n - evenCount1;
        int oddCount2 = m - evenCount2;

        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            // Determine the count in tree1 based on the node's parity
            int count1 = parity1[i] ? evenCount1 : oddCount1;
            // Choose the maximum count from tree2
            int count2 = Math.max(evenCount2, oddCount2);
            result[i] = count1 + count2;
        }

        return result;
    }

    // Helper method to build the adjacency list for a tree
    private List<Integer>[] buildGraph(int[][] edges, int size) {
        List<Integer>[] graph = new ArrayList[size];
        for (int i = 0; i < size; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        return graph;
    }

    // DFS to assign parity and count even-depth nodes
    private int dfs(List<Integer>[] graph, int node, int parent, boolean[] parity, boolean isEven) {
        parity[node] = isEven;
        int count = isEven ? 1 : 0;
        for (int neighbor : graph[node]) {
            if (neighbor != parent) {
                count += dfs(graph, neighbor, node, parity, !isEven);
            }
        }
        return count;
    }
}
