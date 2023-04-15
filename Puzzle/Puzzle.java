import java.util.List;
import java.util.ArrayList;

public class Puzzle {
    private int[][] position;

    private final int numRows;
    private final int numColums;
    private final int[][] endPosition;

    public Puzzle(int[][] position) {
        if (position.length != position[0].length) {
            throw new IllegalArgumentException("Négyzetes tili-toli kell!");
        }

        this.position = position;

        this.numRows = position.length;
        this.numColums = position[0].length;
        this.endPosition = generateEndPosition(position.length);
    }

    public int[][] getPosition() {
        return this.position;
    }

    public int[][] getEndPosition() {
        return this.endPosition;
    }

    public static boolean puzzleEquality(int[][] p1, int[][] p2) {
        // if ((p1.length != p2.length) || (p1[0].length != p2[0].length)) {
            // return false;
        // }

        for (int i = 0; i < p1.length; i++) {
            for (int j = 0; j < p1[i].length; j++) {
                if (p1[i][j] != p2[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isOdd(int num) {
        return (num % 2) != 0;
    }

    public static boolean isEven(int num) {
        return (num % 2) == 0;
    }

    public List<Puzzle> getMoves() {
        List<Puzzle> moves = new ArrayList<>();
        int[] blank = getCoordinates(0); // üres mező koordinátái

        if (blank[0] > 0) {
            moves.add(new Puzzle(swap(blank[0], blank[1], (blank[0] - 1), blank[1]))); // felfelé tolunk egy mezőt
        }

        if (blank[1] < (this.numColums - 1)) {
            moves.add(new Puzzle(swap(blank[0], blank[1], blank[0], (blank[1] + 1)))); // jobbra tolunk egy mezőt
        }

        if (blank[1] > 0) {
            moves.add(new Puzzle(swap(blank[0], blank[1], blank[0], (blank[1] - 1)))); // balra tolunk egy mezőt
        }

        if (blank[0] < (this.numRows - 1)) {
            moves.add(new Puzzle(swap(blank[0], blank[1], (blank[0] + 1), blank[1]))); // lefelé tolunk egy mezőt
        }

        return moves;
    }

    public boolean isSolvable() {
        int inversionsCount = getInversionsCount();
        int blankPosition = getBlankSpaceRowCountingFromBottom();

        if (isOdd(this.numRows) && isEven(inversionsCount)) {
            return true;
        } else if (isEven(this.numRows) && isEven(blankPosition) && isOdd(inversionsCount)) {
            return true;
        } else if (isEven(this.numRows) && isOdd(blankPosition) && isEven(inversionsCount)) {
            return true;
        } else {
            return false;
        }
    }

    private static int[][] generateEndPosition(int n) {
        int[][] finalPosition = new int[n][n];

        int m = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                finalPosition[i][j] = m;
                m++;
            }
        }

        finalPosition[n - 1][n - 1] = 0;

        return finalPosition;
    }

    private int[][] swap(int x1, int y1, int x2, int y2) {
        int[][] puzzleCopy = new int[this.numRows][this.numColums];

        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numColums; j++) {
                puzzleCopy[i][j] = this.position[i][j];
            }
        }

        int tmp = puzzleCopy[x1][y1];
        puzzleCopy[x1][y1] = puzzleCopy[x2][y2];
        puzzleCopy[x2][y2] = tmp;

        return puzzleCopy;
    }

    private int getBlankSpaceRowCountingFromBottom() {
        return this.numRows - getCoordinates(0)[0];
    }

    private int[] getCoordinates(int tile) {
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numColums; j++) {
                if (this.position[i][j] == tile) {
                    return new int[] {i, j};
                }
            }
        }

        throw new RuntimeException("Nincs ilyen cella érték!");
    }

    private int[] getCoordinates(int tile, int[][] position) {
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numColums; j++) {
                if (position[i][j] == tile) {
                    return new int[] {i, j};
                }
            }
        }

        throw new RuntimeException("Nincs ilyen cella érték!");
    }

    private int getInversionsCount() {
        int invCount = 0;

        int[] puzzleList = new int[(this.numRows * this.numColums) - 1];

        int n = 0;
        for (int[] row : this.position) {
            for (int value : row) {
                if (value != 0) {
                    puzzleList[n] = value;
                    n++;
                } else {
                    continue;
                }
            }
        }

        for (int i = 0; i < puzzleList.length; i++) {
            for (int j = (i + 1); j < puzzleList.length; j++) {
                if (puzzleList[i] > puzzleList[j]) {
                    invCount++;
                }
            }
        }

        return invCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int[] row : this.position) {
            for (int value : row) {
                builder.append(String.format("%1$" + 2 + "s", value) + " ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || (getClass() != o.getClass())) {
            return false;
        }

        Puzzle that = (Puzzle) o;

        return puzzleEquality(this.getPosition(), that.getPosition());
    }
}