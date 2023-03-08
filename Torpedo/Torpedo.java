import java.util.Random;
import java.util.Scanner;

public class Torpedo {
    private final static int SIDE_LENGTH = 8;
    private final static int[] LIST_OF_SHIPS = {2, 2, 3, 3, 4};

    private Random random;
    private boolean[][] shipPlacingHelper;

    private Integer[][] shipField;

    private int[][] guessArray;

    public Torpedo() {
        random = new Random();
        shipPlacingHelper = new boolean[SIDE_LENGTH][SIDE_LENGTH]; // false

        shipField = new Integer[SIDE_LENGTH][SIDE_LENGTH]; // null
        shipPlacing();

        guessArray = new int[SIDE_LENGTH][SIDE_LENGTH]; // 0
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

    private int randomIndexGenerator() {
        return (int) ((SIDE_LENGTH - 0.5) * random.nextFloat());
    }

    private boolean enoughSpace(int i, int j, int ship, boolean isVertical) {
        if (!isVertical && !(((j + ship) - 1) < SIDE_LENGTH)) {
            return false;
        }

        if (isVertical && !(((i + ship) - 1) < SIDE_LENGTH)) {
            return false;
        }

        if (isVertical) {
            for (int n = i; n < (i + ship); n++) {
                if (shipPlacingHelper[n][j]) {
                    return false;
                }
            }
        } else {
            for (int n = j; n < (j + ship); n++) {
                if (shipPlacingHelper[i][n]) {
                    return false;
                }
            }
        }

        return true;
    }

    private void shipPlacing() {
        for (int k = 0; k < LIST_OF_SHIPS.length; k++) {
            boolean run = true;
            while (run) {
                int i = randomIndexGenerator();
                int j = randomIndexGenerator();
                if (!shipPlacingHelper[i][j]) {
                    boolean isVertical = random.nextBoolean();
                    if (enoughSpace(i, j, LIST_OF_SHIPS[k], isVertical)) {
                        if (isVertical) {
                            for (int n = i; n < (i + LIST_OF_SHIPS[k]); n++) {
                                shipField[n][j] = k;
                                shipPlacingHelper[n][j] = true;

                                if (j > 0) {
                                    shipPlacingHelper[n][j - 1] = true;
                                }

                                if ((j + 1) < SIDE_LENGTH) {
                                    shipPlacingHelper[n][j + 1] = true;
                                }
                            }

                            if (i > 0) {
                                shipPlacingHelper[i - 1][j] = true;
                                if (j > 0) {
                                    shipPlacingHelper[i - 1][j - 1] = true;
                                }

                                if ((j + 1) < SIDE_LENGTH) {
                                    shipPlacingHelper[i - 1][j + 1] = true;
                                }
                            }

                            if ((i + LIST_OF_SHIPS[k]) < SIDE_LENGTH) {
                                shipPlacingHelper[i + LIST_OF_SHIPS[k]][j] = true;
                                if (j > 0) {
                                    shipPlacingHelper[i + LIST_OF_SHIPS[k]][j - 1] = true;
                                }

                                if ((j + 1) < SIDE_LENGTH) {
                                    shipPlacingHelper[i + LIST_OF_SHIPS[k]][j + 1] = true;
                                }
                            }
                        } else {
                            for (int n = j; n < (j + LIST_OF_SHIPS[k]); n++) {
                                shipField[i][n] = k;
                                shipPlacingHelper[i][n] = true;

                                if (i > 0) {
                                    shipPlacingHelper[i - 1][n] = true;
                                }

                                if ((i + 1) < SIDE_LENGTH) {
                                    shipPlacingHelper[i + 1][n] = true;
                                }
                            }

                            if (j > 0) {
                                shipPlacingHelper[i][j - 1] = true;
                                if (i > 0) {
                                    shipPlacingHelper[i - 1][j - 1] = true;
                                }

                                if ((i + 1) < SIDE_LENGTH) {
                                    shipPlacingHelper[i + 1][j - 1] = true;
                                }
                            }

                            if ((j + LIST_OF_SHIPS[k]) < SIDE_LENGTH) {
                                shipPlacingHelper[i][j + LIST_OF_SHIPS[k]] = true;
                                if (i > 0) {
                                    shipPlacingHelper[i - 1][j  + LIST_OF_SHIPS[k]] = true;
                                }

                                if ((i + 1) < SIDE_LENGTH) {
                                    shipPlacingHelper[i + 1][j + LIST_OF_SHIPS[k]] = true;
                                }
                            }
                        }

                        run = false;
                    }
                }
            }
        }
    }

    private int[] lifeOfShips;
    private Scanner scan;

    private boolean correctGuess(String guess) {
        if (guess.length() != 2) {
            return false;
        }

        String strReg = "[" + ((char) 65) + "-" + ((char) (65 + (SIDE_LENGTH - 1))) + "]";
        String numReg = "[" + 1 + "-" + SIDE_LENGTH + "]";
        return guess.substring(0, 1).matches(strReg) && guess.substring(1).matches(numReg);
    }

    private int[] getCoordinates(String guess) {
        int i = Integer.parseInt(guess.substring(1)) - 1;
        int j = ((int) guess.charAt(0)) - 65;
        return new int[] {i, j};
    }

    private boolean allShipsAreSinked() {
        for (int ship : lifeOfShips) {
            if (ship > 0) {
                return false;
            }
        }

        return true;
    }

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

    public void runTorpedo() {
        lifeOfShips = new int[LIST_OF_SHIPS.length];
        for (int i = 0; i < LIST_OF_SHIPS.length; i++) {
            lifeOfShips[i] = LIST_OF_SHIPS[i];
        }

        scan = new Scanner(System.in);

        System.out.println("\nValdi torpedó társasjátéka");
        System.out.println("\nTipp formátuma:");
        System.out.println("    első karakter (oszlop): nagybetű (A-H)");
        System.out.println("    második karakter (sor): számok (1-8)");
        System.out.println("\nA tábla jelmagyarázata:");
        System.out.println("    O: volt tipp, de találat nem");
        System.out.println("    X: volt tipp, sikeres találat");
        System.out.println("    -: nem volt tipp");

        int maxNumberOfTips = 40;

        System.out.println("\nÖsszesen " + maxNumberOfTips + " tipp van.\n");

        while (maxNumberOfTips > 0) {
            String guess = scan.nextLine();
            if (correctGuess(guess)) {
                int[] field = getCoordinates(guess);
                if (guessArray[field[0]][field[1]] == 0) {
                    Integer index = shipField[field[0]][field[1]];
                    if (index == null) {
                        guessArray[field[0]][field[1]] = 1;
                        System.out.println("\nNem talált.\n");
                    } else {
                        guessArray[field[0]][field[1]] = 2;
                        System.out.println("\nTalált.\n");
                        lifeOfShips[index]--;
                        if (lifeOfShips[index] == 0) {
                            System.out.println("Süllyedt.\n");
                            if (allShipsAreSinked()) {
                                System.out.println("\nSikerült megtalálni az összes hajót.\n");
                                System.out.println(this);
                                break;
                            }
                        }
                    }

                    System.out.println(this);
                    maxNumberOfTips--;
                    System.out.println("\n" + maxNumberOfTips + " tipp maradt.\n");
                }
            }
        }

        if (!allShipsAreSinked()) {
            System.out.println("\nNem sikerült megtalálni az összes hajót.\n");
            System.out.println("A megoldás:\n");
            revealSolution();
            System.out.println(this);
        }
    }

    public static void main(String[] args) {
        Torpedo t = new Torpedo();
        t.runTorpedo();
    }
}