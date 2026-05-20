import java.util.Scanner;

public class GameOfLife {
    // 网格大小 : 20 * 20
    private static final int SIZE = 20;

    // 两种状态：死、活：用boolean方便
    // 存活: true  死亡: false
    private boolean[][] grid;

    //构造函数
    public GameOfLife() {
        grid = new boolean[SIZE][SIZE];
        initRandom();
    }
    // 随机初始化细胞
    private void initRandom() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = Math.random() < 0.3;
            }
        }
    }

    // 统计某个细胞8邻域存活数量
    private int countAliveNeighbor(int x, int y) {
        int count = 0;
        // 8个方向偏移量
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int d = 0; d < 8; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            // 边界合法判断
            if (nx >= 0 && nx < SIZE && ny >= 0 && ny < SIZE) {
                if (grid[nx][ny]) {
                    count++;
                }
            }
        }
        return count;
    }

    // 演化下一代
    public void nextGeneration() {
        boolean[][] newGrid = new boolean[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int alive = countAliveNeighbor(i, j);
                boolean currAlive = grid[i][j];

                // 生命游戏经典四条规则
                if (currAlive) {
                    // 存活：2或3个邻居；否则孤独/拥挤死亡
                    newGrid[i][j] = (alive == 2 || alive == 3);
                } else {
                    // 死亡细胞恰好3个邻居：新生
                    newGrid[i][j] = (alive == 3);
                }
            }
        }
        // 替换为新一代网格
        grid = newGrid;
    }

    // 绘制网格
    public void printGrid() {
        // 清屏换行模拟刷新
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j] ? "■ " : "□ ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        GameOfLife game = new GameOfLife();
        Scanner sc = new Scanner(System.in);
        System.out.println("按回车逐代演化，输入exit退出");

        while (true) {
            game.printGrid();
            String input = sc.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            game.nextGeneration();
        }
        sc.close();
    }
}