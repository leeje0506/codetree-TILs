import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
    static int[][] room;
    static int n, m, t;
    static int[] dx = {0, 0, 1, -1}; // 상, 하, 좌, 우
    static int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        room = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                room[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 시공의 돌풍 위치 찾기
        int airPurifierTop = -1;
        for (int i = 0; i < n; i++) {
            if (room[i][0] == -1) {
                airPurifierTop = i;
                break;
            }
        }

        for (int i = 0; i < t; i++) {
            spreadDust();
            cleanByAirPurifier(airPurifierTop);
        }

        System.out.println(calculateRemainingDust());
    }

    static void spreadDust() {
        int[][] temp = new int[n][m];
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                if (room[x][y] > 0) {
                    int spreadAmount = room[x][y] / 5;
                    int spreadCount = 0;
                    
                    for (int d = 0; d < 4; d++) {
                        int nx = x + dx[d];
                        int ny = y + dy[d];

                        if (nx >= 0 && nx < n && ny >= 0 && ny < m && room[nx][ny] != -1) {
                            temp[nx][ny] += spreadAmount;
                            spreadCount++;
                        }
                    }
                    room[x][y] -= spreadAmount * spreadCount;
                }
            }
        }
        
        // 확산된 먼지 합치기
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                room[x][y] += temp[x][y];
            }
        }
    }

    static void cleanByAirPurifier(int top) {
        // 시계 반대 방향 청소
        for (int i = top - 1; i > 0; i--) room[i][0] = room[i - 1][0];
        for (int i = 0; i < m - 1; i++) room[0][i] = room[0][i + 1];
        for (int i = 0; i < top; i++) room[i][m - 1] = room[i + 1][m - 1];
        for (int i = m - 1; i > 1; i--) room[top][i] = room[top][i - 1];
        room[top][1] = 0;

        // 시계 방향 청소
        int bottom = top + 1;
        for (int i = bottom + 1; i < n - 1; i++) room[i][0] = room[i + 1][0];
        for (int i = 0; i < m - 1; i++) room[n - 1][i] = room[n - 1][i + 1];
        for (int i = n - 1; i > bottom; i--) room[i][m - 1] = room[i - 1][m - 1];
        for (int i = m - 1; i > 1; i--) room[bottom][i] = room[bottom][i - 1];
        room[bottom][1] = 0;
    }

    static int calculateRemainingDust() {
        int totalDust = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (room[i][j] > 0) totalDust += room[i][j];
            }
        }
        return totalDust;
    }
}