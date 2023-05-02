/**
 * <h1>Tili-toli puzzle megoldása</h1>
 */
public class RunPuzzleSolver {

    /**
     * <p>Kezdeti állapot.</p>
     */
    private PuzzleSolver initialPuzzle;

    public RunPuzzleSolver(PuzzleSolver initialPuzzle) {
        this.initialPuzzle = initialPuzzle;
    }

    /**
     * <p>A megoldó algoritmus futatása.</p>
     */
    public void run() {
        if (this.initialPuzzle.getStart().isSolvable()) {
            this.initialPuzzle.doAlgorithm();
        } else {
            System.out.println("Nem megoldható.");
        }
    }

    /**
     * <p>A megoldás megjelenítése.</p>
     */
    private void printSolution() {
        System.out.println("A megold\u00E1s l\u00E9p\u00E9sei:");
        for (Puzzle s : this.initialPuzzle.getSolution()) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        int[][] puzzleArray = new int[][] {{1, 2, 3, 4}, {5, 6, 7, 0}, {10, 11, 12, 8}, {9, 13, 14, 15}};
        // int[][] puzzleArray = new int[][] {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};

        Puzzle p = new Puzzle(puzzleArray);
        PuzzleSolver ps = new PuzzleSolver(p);
        RunPuzzleSolver rps = new RunPuzzleSolver(ps);
        rps.run();
        rps.printSolution();
    }
}