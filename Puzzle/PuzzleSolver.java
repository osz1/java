import java.util.List;
import java.util.ArrayList;

/**
 * <h1>Tili-toli puzzle megoldó</h1>
 */
public class PuzzleSolver {

    /**
     * <p>Kezdeti állapot.</p>
     */
    private Puzzle start;


    /**
     * <p>Lépések száma.</p>
     */
    private int numExpandedNodes;

    /**
     * <p>A megoldás útvonala.</p>
     */
    private List<Puzzle> solution;

    public PuzzleSolver(Puzzle initialPuzzle) {
        this.start = initialPuzzle;
    }

    /**
     * @return kezdeti állapot
     */
    public Puzzle getStart() {
        return this.start;
    }

    /**
     * @return a megoldás útvonala
     */
    public int getNumExpandedNodes() {
        return this.numExpandedNodes;
    }

    /**
     * @return kezdeti állapot
     */
    public List<Puzzle> getSolution() {
        return this.solution;
    }

    /**
     * <p>A megoldó algoritmus.</p>
     */
    public void doAlgorithm() {
        List<List<Puzzle>> queue = new ArrayList<>(); // "útvonalak"
        List<Puzzle> path = new ArrayList<>(); // "aktuális útvonal"
        path.add(this.start); // kezdeti állapot hozzáadása "aktuális útvonal" végéhez
        queue.add(path); // "aktuális útvonal" hozzáadása "útvonalak" végéhez

        List<Puzzle> expanded = new ArrayList<>(); // megvizsgált állapot
        int numExpandedNodes = 0; // lépések száma

        while (queue.size() > 0) {
            // "aktuális útvonal", "útvonalak" első eleme (eltávolítva)
            path = queue.remove(0);

            // "aktuális útvonal" végén lévő állapot
            Puzzle endNode = path.get(path.size() - 1);

            if (expanded.contains(endNode)) {
                // az állapot meg volt vizsgálva
                continue;
            }

            // az állapotból történő további mozgások (állapotok) vizsgálata
            for (Puzzle move : endNode.getMoves()) {
                if (expanded.contains(move)) {
                    // a mozgás (állapot) meg volt vizsgálva
                    continue;
                }

                // "új útvonal" létrehozása az "aktuális útvonal" másolásával
                List<Puzzle> newPath = new ArrayList<>(path);

                // a mozgás (állapot) hozzáadása az "új útvonal" végéhez
                newPath.add(move);

                // "új útvonal" hozzáadása az "útvonalak" végéhez
                queue.add(newPath);
            }

            // az állapot meg lett vizsgálva
            expanded.add(endNode);

            // lépésszám növelése
            numExpandedNodes++;

            if (Puzzle.puzzleEquality(endNode.getPosition(), endNode.getEndPosition())) {
                // sikerült elérni a végső állapotot
                break;
            }
        }

        this.numExpandedNodes = numExpandedNodes;// lépésszám
        this.solution = path; // a megoldás útvonala
    }
}