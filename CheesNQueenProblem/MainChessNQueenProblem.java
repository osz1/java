import java.util.Random;

public class MainChessNQueenProblem {
    Random random = new Random();
    private final int n = ((int) (8 * random.nextFloat())) + 1;

    private boolean isSafe(int x, int y, int solution[][]) {
        int i = 0;
        while (i < y) {
            if (solution[x][i] == 1) {
                return false;
            }
            i++;
        }

        i = x;
        int j = y;
        while ((i >= 0) && (j >= 0)) {
            if (solution[i][j] == 1) {
                return false;
            }
            i--;
            j--;
        }

        i = x;
        j = y;
        while ((i < n) && (j >= 0)) {
            if (solution[i][j] == 1) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    private void printSolution(int solution[][]) {
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++)
                System.out.print(solution[x][y] + " ");
            System.out.println();
        }
    }

    private boolean solveNQUtil(int solution[][], int y) {
        if (y >= n) {
            return true;
        }

        for (int i = 0; i < n; i++) {
            if (isSafe(i, y, solution)) {
                solution[i][y] = 1;
 
                if (solveNQUtil(solution, (y + 1)) == true) {
                    return true;
                }
 
                solution[i][y] = 0;
            }
        }

        return false;
    }

    public boolean solveNQ() {
        System.out.println(n + " db királynő:\n");

        int solution[][] = new int[n][n];

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                solution[x][y] = 0;
            }
        }

        if (solveNQUtil(solution, 0) == false) {
            System.out.println("Nem létezik megoldás.");
            return false;
        }
        
        printSolution(solution);
        return true;
    }

    public static void main(String[] args) {
        MainChessNQueenProblem nqp = new MainChessNQueenProblem();
        nqp.solveNQ();
    }
}