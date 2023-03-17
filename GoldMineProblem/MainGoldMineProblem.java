import java.util.Random;

public class MainGoldMineProblem {    
    public static void main(String[] args) {
        Random random = new Random();
        int m = ((int) (10 * random.nextFloat())) + 1;
        int n = ((int) (10 * random.nextFloat())) + 1;

        int[][] goldMine = new int[m][n];
        System.out.println("Gold mine (" + m + " x " + n + "):");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                goldMine[i][j] = ((int) (50 * random.nextFloat())) + 1;
                System.out.printf(goldMine[i][j] + " ");
            }
            System.out.println("\n");
        }

        int[][] goldQuantity = new int[m][n];;

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
}