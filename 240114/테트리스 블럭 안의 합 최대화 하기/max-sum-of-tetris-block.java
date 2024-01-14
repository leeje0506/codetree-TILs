import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[][] map;
    static boolean[][] visited;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};
    static int max = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                visited[i][j] = true;
                dfs(i, j, 1, map[i][j]);
                visited[i][j] = false;
            }
        }

        System.out.println(max);
    }

    static void dfs(int x, int y, int count, int sum) {
        if (count == 4) {
            max = Math.max(max, sum);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && ny >= 0 && nx < N && ny < M) {
                if (!visited[nx][ny]) {
                    visited[nx][ny] = true;
                    dfs(nx, ny, count + 1, sum + map[nx][ny]);
                    visited[nx][ny] = false;
                }
            }
        }

        // 'ㅗ' 모양 처리
        if (count == 2) {
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                // 추가로 탐색하는 블록이 이전에 탐색한 블록들과 인접해 있는지 확인
                if (nx >= 0 && ny >= 0 && nx < N && ny < M && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    for (int j = 0; j < 4; j++) {
                        int nnx = nx + dx[j];
                        int nny = ny + dy[j];
                        if (nnx >= 0 && nny >= 0 && nnx < N && nny < M && visited[nnx][nny]) {
                            dfs(nnx, nny, count + 1, sum + map[nx][ny]);
                        }
                    }
                    visited[nx][ny] = false;
                }
            }
        }
    }
}