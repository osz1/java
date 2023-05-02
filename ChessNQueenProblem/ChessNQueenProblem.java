import java.util.Random;

/**
 * <h1>N királynő probléma</h1>
 */
public class ChessNQueenProblem {

    /**
     * <p>Véletlen generátor.</p>
     */
    public Random random = new Random();


    /**
     * <p>Királynők száma és a sakktábla szélessége.</p>
     */
    private final int n = ((int) (8 * random.nextFloat())) + 1;

    /**
     * <p>Sakktábla létrehozása és a királynők elhelyezése (ha lehetséges).</p>
     * 
     * <p>Adott királynő szám és sakktábla szélességnél.</p>
     * 
     * @return igaz, ha el lehet helyezni a királynőket
     */
    public boolean solveNQueenProblem() {
        System.out.println(n + " db kir\u00E1lyn\u0151:\n");

        // sakktábla
        int solution[][] = new int[n][n];

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                solution[x][y] = 0;
            }
        }

        // királynők elhelyezése
        if (placeQueens(solution, 0) == false) {
            System.out.println("Nem l\u00E9tezik megold\u00E1s.");
            return false;
        }

        printSolution(solution);
        return true;
    }

    /**
     * <p>Királynő nem üti-e ki egy másik (balra lévő) királynőt.</p>
     * 
     * @param x sor
     * @param y oszlop
     * @param solution a sakktábla
     * @return igaz, ha nincs kiütés
     */
    private boolean isSafe(int x, int y, int solution[][]) {
        // balra
        int i = 0;
        while (i < y) {
            if (solution[x][i] == 1) {
                return false;
            }

            i++;
        }

        // balra fel
        i = x;
        int j = y;
        while ((i >= 0) && (j >= 0)) {
            if (solution[i][j] == 1) {
                return false;
            }

            i--;
            j--;
        }

        // balra le
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

    /**
     * <p>Sakktábla megjelenítése.</p>
     * 
     * @param solution a sakktábla
     */
    private void printSolution(int solution[][]) {
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                System.out.print(solution[x][y] + " ");
            }
            System.out.println();
        }
    }

    /**
     * <p>Királynő elhelyezése (adott oszlopban).</p>
     * 
     * <p>(Balról jobbra.)</p>
     * 
     * @param solution a sakktábla
     * @param y oszlop
     * @return igaz, ha kiütés nélkül el lehet helyezni a királynőket
     */
    private boolean placeQueens(int solution[][], int y) {
        // (sikerült az össszes oszlopban királynőt elhelyezni)
        if (y >= n) {
            return true;
        }

        // adott oszlopban sorokként próbálkozva
        for (int i = 0; i < n; i++) {
            if (isSafe(i, y, solution)) {
                // nem üt(ne) ki más (balra lévő) királynőt

                // királynő elhelyezése
                solution[i][y] = 1;

                // királynő(k) elhelyezése a következő oszlop(ok)ban
                if (placeQueens(solution, (y + 1)) == true) {
                    return true;
                }

                // nem skerült az elhelyezés(ek)

                // királynő eltávolítása
                solution[i][y] = 0;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        ChessNQueenProblem nqp = new ChessNQueenProblem();
        nqp.solveNQueenProblem();
    }
}