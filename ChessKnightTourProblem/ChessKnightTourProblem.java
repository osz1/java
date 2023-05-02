import java.util.Random;

/**
 * <h1>Huszárvándorlás-probléma</h1>
 */
public class ChessKnightTourProblem {

    /**
     * <p>Véletlen generátor.</p>
     */
    public Random random = new Random();


    /**
     * <p>A sakktábla szélessége.</p>
     */
    private final int n = ((int) (6 * random.nextFloat())) + 3;

    /**
     * <p>Sakktábla létrehozása és a huszár vándorlása (ha lehetséges).</p>
     * 
     * <p>Adott sakktábla szélességnél.</p>
     * 
     * @return igaz, ha a huszár minden mezőt csak egyszer látogat meg
     */
    public boolean solveKT() {
        System.out.println(n + "x" + n + " sakkt\u00E1bla:\n");

        // sakktábla
        int solution[][] = new int[n][n];

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                solution[x][y] = -1;
            }
        }

        // huszár mozgásai
        int knightMoveX[] = {2, 1, -1, -2, -2, -1, 1, 2};
        int knightMoveY[] = {1, 2, 2, 1, -1, -2, -2, -1};

        // kezdőlépés
        solution[0][0] = 0;

        // huszár "utazása"
        if (solveKnightTourProblem(0, 0, 1, solution, knightMoveX, knightMoveY)) {
            // sikerült a huszárnak minden mezőre csak egyszer "rálépnie"

            printSolution(solution);
        } else {
            System.out.println("Nem l\u00E9tezik megold\u00E1s.");

            return false;
        }

        return true;
    }

    /**
     * <p>Tartózkodott-e a huszár a megadott mezőn és a sakktáblán belül van-e.</p>
     * 
     * @param x sor
     * @param y oszlop
     * @param solution a sakktábla
     * @return igaz, ha a mező a sakktáblán van és nem járt rajta a huszár
     */
    private boolean isSafe(int x, int y, int solution[][]) {
        return (x >= 0) && (x < n) && (y >= 0) && (y < n) && (solution[x][y] == -1);
    }

    /**
     * <p>Sakktábla megjelenítése.</p>
     * 
     * @param solution a sakktábla
     */
    private void printSolution(int solution[][]) {
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++)
                System.out.print(String.format("%1$" + 2 + "s", solution[x][y]) + " ");
            System.out.println();
        }
    }

    /**
     * <p>Adott mezőnél a lehetséges következő lépések vizsgálata 
     * az adott lépészám elhelyezése céljából.</p>
     * 
     * @param x sor
     * @param y oszlop
     * @param movei lépészám
     * @param solution a sakktábla
     * @param knightMoveX huszár mozgásai (sor)
     * @param knightMoveY huszár mozgásai (oszlop)
     * @return igaz, ha a huszár minden mezőt csak egyszer látogat meg
     */
    private boolean solveKnightTourProblem(int x, int y, int movei, int solution[][], int knightMoveX[], int knightMoveY[]) {
        int next_x;
        int next_y;

        // (sikerült csak egyszer az összes mezőre "rálépni")
        if (movei == (n * n)) {
            return true;
        }

        // minden lehetséges következő lépés vizsgálata
        for (int k = 0; k < knightMoveX.length; k++) {
            // következő lépés helye (mező)
            next_x = x + knightMoveX[k];
            next_y = y + knightMoveY[k];
            if (isSafe(next_x, next_y, solution)) {
                // nem tartózkodott az adott mezőn
                // és a sakktáblán belül van

                // lépésszám "ráírása" az adott mezőre
                solution[next_x][next_y] = movei;

                // következő lépés(ek) vizsgálata
                // az adott mezőből indulva
                if (solveKnightTourProblem(next_x, next_y, (movei + 1), solution, knightMoveX, knightMoveY)) {
                    return true;
                } else {
                    // nem sikerült az "utazás" az adott mezőből

                    // lépésszám törlése az adott mezőről
                    solution[next_x][next_y] = -1;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        ChessKnightTourProblem cktp = new ChessKnightTourProblem();
        cktp.solveKT();
    }
}