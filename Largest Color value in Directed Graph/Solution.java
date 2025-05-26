import java.util.*;

public class Solution {
    public int largestPathValue(String colors, int[][] edges) {
        int n = colors.length();
        List<List<Integer>> graph = new ArrayList<>();
        int[] inDegree = new int[n];
        int[][] dp = new int[n][26]; // dp[node][color]

        // Build graph
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            inDegree[edge[1]]++;
        }

        // Topological sort (Kahn’s algorithm)
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) queue.add(i);
        }

        int processed = 0, result = 0;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            processed++;

            // Increment color count for current node
            int colorIndex = colors.charAt(node) - 'a';
            dp[node][colorIndex]++;
            result = Math.max(result, dp[node][colorIndex]);

            // Visit neighbors
            for (int neighbor : graph.get(node)) {
                for (int c = 0; c < 26; c++) {
                    dp[neighbor][c] = Math.max(dp[neighbor][c], dp[node][c]);
                }

                if (--inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return processed == n ? result : -1; // If cycle exists, return -1
    }
}
