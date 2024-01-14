import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    // 각 블록의 모든 가능한 모양
    static int[][][] block = {
        {{0, 0}, {0, 1}, {0, 2}, {0, 3}}, // ㅡ
        {{0, 0}, {1, 0}, {2, 0}, {3, 0}}, // ㅣ
        {{0, 0}, {0, 1}, {1, 0}, {1, 1}}, // ㅁ
        {{0, 0}, {1, 0}, {2, 0}, {2, 1}}, // ㄴ
        {{0, 1}, {1, 1}, {2, 1}, {2, 0}}, // ㄴ 반대
        {{0, 0}, {1, 0}, {1, 1}, {2, 1}}, // ㄴ 대칭
        {{0, 1}, {1, 1}, {1, 0}, {2, 0}}, // ㄴ 대칭 반대
        {{0, 0}, {0, 1}, {0, 2}, {1, 1}}, // ㅗ
        {{0, 0}, {1, 0}, {2, 0}, {1, 1}}, // ㅏ
        {{0, 0}, {0, 1}, {0, 2}, {1, 1}}, // ㅜ
        {{0, 1}, {1, 0}, {1, 1}, {2, 1}}, // ㅓ
        {{0, 0}, {1, 0}, {1, 1}, {2, 1}}, // z
        {{0, 1}, {1, 1}, {1, 0}, {2, 0}}, // z 대칭
        {{0, 0}, {1, 0}, {0, 1}, {1, 1}}, // ㅁ
    };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < block.length; k++) {
                    int sum = 0;
                    for (int l = 0; l < 4; l++) {
                        int nx = i + block[k][l][0];
                        int ny = j + block[k][l][1];
                        if (0 <= nx && nx < n && 0 <= ny && ny < m) {
                            sum += map[nx][ny];
                        } else {
                            sum = 0;
                            break;
                        }
                    }
                    max = Math.max(max, sum);
                }
            }
        }
        System.out.println(max);
    }
}