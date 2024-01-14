import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[][] map;
    static boolean[][] visited;
    static int n, m;
    static int max = 0;

    static int[] dx = { -1, 0, 1, 0 };
    static int[] dy = { 0, 1, 0, -1 };

    // 'ㅗ' 모양의 블록
    static int[][][] exBlock = {
        { { 0, 0 }, { 0, 1 }, { 0, 2 }, { -1, 1 } },
        { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, -1 } },
        { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 1 } },
        { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, 1 } }
    };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][m];
        visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dfs(i, j, 0, 0);
                checkException(i, j);
            }
        }

        System.out.println(max);
    }

    static void dfs(int x, int y, int depth, int sum) {
        if (depth == 4) {
            max = Math.max(max, sum);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
            if (visited[nx][ny]) continue;

            visited[nx][ny] = true;
            dfs(nx, ny, depth + 1, sum + map[nx][ny]);
            visited[nx][ny] = false;
        }
    }

    static void checkException(int x, int y) {
        for (int i = 0; i < 4; i++) {
            int sum = 0;
            for (int j = 0; j < 4; j++) {
                int nx = x + exBlock[i][j][0];
                int ny = y + exBlock[i][j][1];

                if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;

                sum += map[nx][ny];
            }
            max = Math.max(max, sum);
        }
    }
}