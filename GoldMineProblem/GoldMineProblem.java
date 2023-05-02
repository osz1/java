import java.util.Random;

/**
 * <h1>Aranybánya probléma</h1>
 */
public class GoldMineProblem {

    /**
     * <p>Véletlen generátor.</p>
     */
    public Random random = new Random();


    /**
     * <p>Sor.</p>
     */
    private final int m;

    /**
     * <p>Oszlop.</p>
     */
    private final int n;


    /**
     * <p>Aranybánya.</p>
     */
    private int[][] goldMine;


    /**
     * <p>Kibányászott aranymennyiség.</p>
     */
    private int[][] goldQuantity;

    public GoldMineProblem() {
        this.m = ((int) (10 * random.nextFloat())) + 1;
        this.n = ((int) (10 * random.nextFloat())) + 1;

        this.goldMine = new int[this.m][this.n];

        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                this.goldMine[i][j] = ((int) (50 * random.nextFloat())) + 1;
            }
        }
    }

    /**
     * <p>Aranybánya megjelenítése.</p>
     */
    public void printGoldMine() {
        System.out.println("Gold mine (" + m + " x " + n + "):");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf(goldMine[i][j] + " ");
            }

            System.out.println("\n");
        }
    }

    /**
     * <p>Megoldás megjelenítése.</p>
     */
    public void printSolution() {
        solveGoldMineProblem();

        System.out.println("Gold quantity:");

        int maxGoldQuantity = 0;
        for (int[] row : goldQuantity) {
            for (int value : row) {
                System.out.printf(value + " ");

                if (value > maxGoldQuantity) {
                    maxGoldQuantity = value;
                }
            }

            System.out.println("\n");
        }

        System.out.println("Maximum gold quantity: " + maxGoldQuantity);
    }

    /**
     * <p>Legnagyobb aranymennyiség keresése.</p>
     */
    private void solveGoldMineProblem() {
        this.goldQuantity = new int[this.m][this.n];

        for (int i = (n - 1); i >= 0; i--) {
            for (int j = 0; j < m; j++) {
                int maxGoldInNextColumn;

                int right = (i == (n - 1)) ? 0 : goldQuantity[j][i + 1];
                maxGoldInNextColumn = right;

                int rightUp = ((j == 0) || (i == (n - 1))) ? 0 : goldQuantity[j - 1][i + 1];
                maxGoldInNextColumn = (rightUp > maxGoldInNextColumn) ? rightUp : maxGoldInNextColumn;

                int rightDown = ((j == (m - 1)) || (i == (n - 1))) ? 0 : goldQuantity[j + 1][i + 1];
                maxGoldInNextColumn = (rightDown > maxGoldInNextColumn) ? rightDown : maxGoldInNextColumn;

                goldQuantity[j][i] = goldMine[j][i] + maxGoldInNextColumn;
            }
        }
    }

    public static void main(String[] args) {
        GoldMineProblem gmp = new GoldMineProblem();
        gmp.printGoldMine();
        gmp.printSolution();
    }
}