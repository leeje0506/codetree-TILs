import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[][] grid; // 격자판의 계란의 양을 저장할 배열
    static boolean[][] visited; // 방문 여부를 체크할 배열
    static int n, L, R; // 격자판의 크기 n, 조건 L, R
    static int[] dx = {-1, 1, 0, 0}; // x축 이동 (상, 하)
    static int[] dy = {0, 0, -1, 1}; // y축 이동 (좌, 우)

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

        System.out.println(getTotalMovements()); // 결과 출력
    }

    static int getTotalMovements() {
        int totalMovements = 0; // 계란 이동 횟수

        while (true) {
            visited = new boolean[n][n];
            boolean isMoved = false; // 이동이 발생했는지 여부

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (!visited[i][j]) {
                        int sum = bfs(i, j); // BFS 실행
                        if (sum > 1) { // 두 개 이상의 계란틀이 합쳐졌다면
                            isMoved = true;
                        }
                    }
                }
            }

            if (!isMoved) { // 더 이상 이동할 계란틀이 없다면 반복 종료
                break;
            }
            totalMovements++; // 이동 횟수 증가
        }

        return totalMovements; // 총 이동 횟수 반환
    }

    static int bfs(int x, int y) {
        visited[x][y] = true;
        Queue<int[]> queue = new LinkedList<>();
        Queue<int[]> toUpdate = new LinkedList<>();
        queue.add(new int[]{x, y});
        toUpdate.add(new int[]{x, y});

        int sum = grid[x][y];
        int cells = 1; // 합쳐진 계란틀의 수

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if (nx >= 0 && ny >= 0 && nx < n && ny < n && !visited[nx][ny]) {
                    int diff = Math.abs(grid[cur[0]][cur[1]] - grid[nx][ny]);
                    // 조건에 맞는 인접 계란틀 탐색
                    if (diff >= L && diff <= R) {
                        visited[nx][ny] = true;
                        queue.add(new int[]{nx, ny});
                        toUpdate.add(new int[]{nx, ny});
                        sum += grid[nx][ny]; // 계란의 총합
                        cells++; // 합쳐진 계란틀의 개수
                    }
                }
            }
        }

        // 합쳐진 계란틀이 있다면 평균값으로 업데이트
        if (cells > 1) {
            int avg = sum / cells;
            while (!toUpdate.isEmpty()) {
                int[] cur = toUpdate.poll();
                grid[cur[0]][cur[1]] = avg; // 평균값으로 계란의 양 업데이트
            }
            return cells; // 합쳐진 계란틀의 수 반환
        }

        return 1; // 합쳐진 계란틀이 없다면 1 반환
    }
}