public class RunPuzzleSolver {
    private PuzzleSolver initialPuzzle;

    public RunPuzzleSolver(PuzzleSolver initialPuzzle) {
        this.initialPuzzle = initialPuzzle;
    }
    
    public void run() {
        if (this.initialPuzzle.getStart().isSolvable()) {
            this.initialPuzzle.doAlgorithm();
        } else {
            System.out.println("Nem megoldható.");
        }
    }

    private void printSolution() {
        System.out.println("A megoldás lépései:");
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