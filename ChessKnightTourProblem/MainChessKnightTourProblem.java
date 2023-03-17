import java.util.Random;

public class MainChessKnightTourProblem {
    Random random = new Random();
    private final int n = ((int) (6 * random.nextFloat())) + 3;

    private boolean isSafe(int x, int y, int solution[][]) {
        return (x >= 0) && (x < n) && (y >= 0) && (y < n) && (solution[x][y] == -1);
    }

    private void printSolution(int solution[][]) {
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++)
                System.out.print(String.format("%1$" + 2 + "s", solution[x][y]) + " ");
            System.out.println();
        }
    }

    private boolean solveKTUtil(int x, int y, int movei, int solution[][], int knightMoveX[], int knightMoveY[]) {
        int next_x;
        int next_y;

        if (movei == (n * n)) {
            return true;
        }

        for (int k = 0; k < knightMoveX.length; k++) {
            next_x = x + knightMoveX[k];
            next_y = y + knightMoveY[k];
            if (isSafe(next_x, next_y, solution)) {
                solution[next_x][next_y] = movei;
                if (solveKTUtil(next_x, next_y, (movei + 1), solution, knightMoveX, knightMoveY)) {
                    return true;
                } else {
                    solution[next_x][next_y] = -1;
                }
            }
        }

        return false;
    }

    public boolean solveKT() {
        System.out.println(n + "x" + n + " sakktábla:\n");

        int solution[][] = new int[n][n];

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                solution[x][y] = -1;
            }
        }

        int knightMoveX[] = {2, 1, -1, -2, -2, -1, 1, 2};
        int knightMoveY[] = {1, 2, 2, 1, -1, -2, -2, -1};

        solution[0][0] = 0;

        if (solveKTUtil(0, 0, 1, solution, knightMoveX, knightMoveY)) {
            printSolution(solution);
        } else {
            System.out.println("Nem létezik megoldás.");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        MainChessKnightTourProblem cktp = new MainChessKnightTourProblem();
        cktp.solveKT();
    }
}