import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[][] grid;
    static boolean[][] visited;
    static int n, L, R;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        grid = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(getTotalMovements());
    }

    static int getTotalMovements() {
        int totalMovements = 0;

        while (true) {
            visited = new boolean[n][n];
            boolean isMoved = false;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (!visited[i][j]) {
                        int sum = dfs(i, j, 0, 0);
                        if (sum > 1) { // If more than one cell is combined
                            isMoved = true;
                        }
                    }
                }
            }

            if (!isMoved) {
                break;
            }
            totalMovements++;
        }

        return totalMovements;
    }

    static int dfs(int x, int y, int sum, int cells) {
        visited[x][y] = true;
        sum += grid[x][y];
        cells++;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if (nx >= 0 && ny >= 0 && nx < n && ny < n && !visited[nx][ny]) {
                    int diff = Math.abs(grid[cur[0]][cur[1]] - grid[nx][ny]);
                    if (diff >= L && diff <= R) {
                        visited[nx][ny] = true;
                        sum = dfs(nx, ny, sum, cells);
                        cells++;
                    }
                }
            }
        }

        // Redistribute the eggs
        int avg = sum / cells;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j]) {
                    grid[i][j] = avg;
                }
            }
        }

        return cells;
    }
}