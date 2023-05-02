import java.util.Random;
import java.util.Scanner;

/**
 * <h1>Torpedó társasjáték</h1>
 */
public class Torpedo {

    /**
     * <p>Terület szélessége vagy magassága.</p>
     */
    private final static int SIDE_LENGTH = 8;

    /**
     * <p>Hajók halmaza.</p>
     */
    private final static int[] LIST_OF_SHIPS = {2, 2, 3, 3, 4};


    /**
     * <p>Véletlen generátor.</p>
     */
    private Random random;


    /**
     * <p>Hajók elhelyezkedése.</p>
     * 
     * <p>A 'hajó halmaz' indexeit tartalmaza (majd).</p>
     */
    private Integer[][] shipField;


    /**
     * <p>Tippek.</p>
     */
    private int[][] guessArray;


    /**
     * <p>Hajók "élete".</p>
     */
    private int[] lifeOfShips;


    /**
     * <p>(Billentyűzet bemenet.)</p>
     */
    private Scanner scan;

    public Torpedo() {
        random = new Random();
    }

    /**
     * <p>Játék futattása.</p>
     * 
     * @param scan (billentyűzet bemenet)
     */
    public void runTorpedo() {
        boolean run = true;
        while (run) {
            scan = new Scanner(System.in);
            runTorpedoGuessing(scan);

            System.out.println("\nKil\u00E9p\u00E9s a j\u00E1t\u00E9kb\u00F3l: q");

            String input = scan.nextLine();
            if (input.equals("q")) {
                System.out.println("\nJ\u00E1t\u00E9k v\u00E9ge!\n");

                scan.close();
                run = false;
            }
        }
    }

    /**
     * <p>Tippelés futattása. (Játékon belül.)</p>
     * 
     * @param scan (billentyűzet bemenet)
     */
    public void runTorpedoGuessing(Scanner scan) {
        createArraysAndPlaceShips();

        System.out.println("\nValdi torped\u00F3 t\u00E1rsasj\u00E1t\u00E9ka");

        System.out.println("\nKil\u00E9p\u00E9s: q");

        System.out.println("\nTipp form\u00E1tuma:");
        System.out.println("    els\u0151 karakter (oszlop): nagybet\u0171 (A-H)");
        System.out.println("    m\u00E1sodik karakter (sor): sz\u00E1mok (1-8)");

        System.out.println("\nA t\u00E1bla jelmagyar\u00E1zata:");
        System.out.println("    O: volt tipp, de tal\u00E1lat nem");
        System.out.println("    X: volt tipp, sikeres tal\u00E1lat");
        System.out.println("    -: nem volt tipp");

        int maxNumberOfGuesses = 40;  // tippek száma

        System.out.println("\n\u00D6sszesen " + maxNumberOfGuesses + " tipp van.\n");

        while (maxNumberOfGuesses > 0) {
            String guess = scan.nextLine();
            if (correctGuess(guess)) {

                int[] field = getCoordinates(guess);  // (típus átalakítás)
                if (guessArray[field[0]][field[1]] == 0) {

                    Integer index = shipField[field[0]][field[1]];
                    if (index == null) {
                        guessArray[field[0]][field[1]] = 1; // tipp megjegyzése

                        System.out.println("\nNem tal\u00E1lt.\n");
                    } else {
                        guessArray[field[0]][field[1]] = 2; // tipp megjegyzése

                        // megtalált hajó "életének" csökkentése
                        lifeOfShips[index]--;

                        System.out.println("\nTal\u00E1lt.\n");

                        if (lifeOfShips[index] == 0) {
                            System.out.println("S\u00FCllyedt.\n");

                            if (allShipsAreSinked()) {
                                System.out.println("\nSiker\u00FClt megtal\u00E1lni az \u00F6sszes haj\u00F3t.\n");
                                System.out.println(this); // tippelések megjelenítése

                                break;
                            }
                        }
                    }

                    // eddigi tippelések megjelenítése
                    System.out.println(this);

                    maxNumberOfGuesses--;

                    System.out.println("\n" + maxNumberOfGuesses + " tipp maradt.\n");
                }
            } else if (guess.equals("q")) {
                System.out.println("\nTippel\u00E9s v\u00E9ge!\n");

                break;
            }
        }

        if (!allShipsAreSinked()) {
            System.out.println("\nNem siker\u00FClt megtal\u00E1lni az \u00F6sszes haj\u00F3t.\n");
            System.out.println("A megold\u00E1s:\n");

            // hajók felfedése
            revealSolution();

            System.out.println(this);
        }
    }

    /**
     * <p>Hely kisorsolás (hajó elhelyezéséhez).</p>
     * 
     * @return véletlen szám a terület hosszán belül (0 és 7 között)
     */
    private int randomIndexGenerator() {
        return (int) ((SIDE_LENGTH - 0.5) * random.nextFloat());
    }

    /**
     * <p>Koordináta belül van-e a területen.</p>
     * 
     * @param row sor
     * @param col oszlop
     * @return igaz, ha nem lóg ki a területből
     */
    private boolean checkArrayIndex(int row, int col) {
        return ((row >= 0) && (row < SIDE_LENGTH)) && ((col >= 0) && (col < SIDE_LENGTH));
    }

    /**
     * <p>Elegendő hely és szomszéd hajó hiányának ellenőrzése egy hajó elhelyezéséhez.</p>
     * 
     * @param row sor
     * @param col oszlop
     * @param ship hajó
     * @param isVertical a hajó függőleges-e
     * @return igaz, ha a kijelölt helyen el lehet helyezni a hajót
     */
    private boolean enoughSpace(int row, int col, int ship, boolean isVertical) {
        if (isVertical) { // függőleges
            // a kisorsolt hely a játékteren belül van-e
            if (!(checkArrayIndex(row, col) && checkArrayIndex(((row + ship) - 1), col))) {
                return false;
            }

            // nincs-e hajó a kisorsolt hely környékén
            for (int i = (row - 1); i < (row + ship + 1); i++) {
                for (int j = (col - 1); j <= (col + 1); j++) {
                    if (checkArrayIndex(i, j)) {
                        if (shipField[i][j] != null) {
                            return false;
                        }
                    }
                }
            }

        } else { // vízszintes
            // a kisorsolt hely a játékteren belül van-e
            if (!(checkArrayIndex(row, col) && checkArrayIndex(row, (col + ship - 1)))) {
                return false;
            }

            // nincs-e hajó a kisorsolt hely környékén
            for (int j = (col - 1); j < (col + ship + 1); j++) {
                for (int i = (row - 1); i <= (row + 1); i++) {
                    if (checkArrayIndex(i, j)) {
                        if (shipField[i][j] != null) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * <p>Hajók elhelyezése.</p>
     */
    private void shipPlacing() {
        for (int k = 0; k < LIST_OF_SHIPS.length; k++) { // hajók
            boolean run = true;
            while (run) {
                // (kisorsolt) hely kezdőkoordinátájának kisorsolása
                int row = randomIndexGenerator();
                int col = randomIndexGenerator();

                // függőleges-e vagy vízszintes
                boolean isVertical = random.nextBoolean();

                // elegendő hely van-e és a környezetben nincs-e hajó
                if (enoughSpace(row, col, LIST_OF_SHIPS[k], isVertical)) {
                    if (isVertical) { // függőleges
                        // hajó elhelyezése
                        for (int i = row; i < (row + LIST_OF_SHIPS[k]); i++) {
                            shipField[i][col] = k;
                        }
                    } else { // vízszintes
                        // hajó elhelyezése
                        for (int j = col; j < (col + LIST_OF_SHIPS[k]); j++) {
                            shipField[row][j] = k;
                        }
                    }

                    run = false;
                }
            }
        }
    }

    /**
     * <p>Halmazok létrehozása és hajók elhelyezése.</p>
     */
    private void createArraysAndPlaceShips() {
        // hajók elhelyezése
        shipField = new Integer[SIDE_LENGTH][SIDE_LENGTH]; // null
        shipPlacing();

        // tippek
        guessArray = new int[SIDE_LENGTH][SIDE_LENGTH]; // 0

        // hajók élete
        lifeOfShips = new int[LIST_OF_SHIPS.length];
        for (int i = 0; i < LIST_OF_SHIPS.length; i++) {
            lifeOfShips[i] = LIST_OF_SHIPS[i];
        }

    }

    /**
     * <p>Tipp helyes-e és a területen belül van-e.</p>
     * 
     * @param guess tipp szövege
     * @return igaz, ha helyes és területen belül van a tipp
     */
    private boolean correctGuess(String guess) {
        if (guess.length() != 2) {
            return false;
        }

        String strReg = "[" + ((char) 65) + "-" + ((char) (65 + (SIDE_LENGTH - 1))) + "]";
        String numReg = "[" + 1 + "-" + SIDE_LENGTH + "]";

        return guess.substring(0, 1).matches(strReg) && guess.substring(1).matches(numReg);
    }

    /**
     * <p>Koordináta átalakítása.</p>
     * 
     * @param guess koordináta szövege
     * @return koordináta 'int[]' típusként
     */
    private int[] getCoordinates(String guess) {
        int i = Integer.parseInt(guess.substring(1)) - 1;
        int j = ((int) guess.charAt(0)) - 65;

        return new int[] {i, j};
    }

    /**
     * <p>Elsüllyedt-e az összes hajó.</p>
     * 
     * @return igaz, ha elsüllyedt az összes hajó
     */
    private boolean allShipsAreSinked() {
        for (int ship : lifeOfShips) {
            if (ship > 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>Hajók felfedése.<p>
     * 
     * <p>Tipp "elhelyezése" ahol van hajó, de nem volt tipp.<p>
     */
    private void revealSolution() {
        for (int i = 0; i < SIDE_LENGTH; i++) {
            for (int j = 0; j < SIDE_LENGTH; j++) {
                if (guessArray[i][j] == 0) {
                    if (shipField[i][j] != null) {
                        guessArray[i][j] = 2;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("  ");
        for (int l = 65; l < (65 + SIDE_LENGTH); l++) {
            builder.append((char) l);
            builder.append(' ');
        }

        builder.append("\n");

        for (int i = 0; i < SIDE_LENGTH; i++) {
            builder.append((i + 1) + " ");
            for (int j = 0; j < SIDE_LENGTH; j++) {
                switch (guessArray[i][j]) {
                    case 0:
                        builder.append('-');
                        break;
                    case 1:
                        builder.append('O');
                        break;
                    case 2:
                        builder.append('X');
                        break;
                }

                builder.append(' ');
            }

            builder.append("\n");
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        Torpedo t = new Torpedo();
        t.runTorpedo();
    }
}