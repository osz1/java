import java.util.List;
import java.util.ArrayList;

public class PuzzleSolver {
    private Puzzle start;

    private int numExpandedNodes;
    private List<Puzzle> solution;

    public PuzzleSolver(Puzzle initialPuzzle) {
        this.start = initialPuzzle;
    }

    public Puzzle getStart() {
        return this.start;
    }

    public void doAlgorithm() {
        List<List<Puzzle>> queue = new ArrayList<>();
        List<Puzzle> path = new ArrayList<>();
        path.add(this.start);
        queue.add(path);

        List<Puzzle> expanded = new ArrayList<>();
        int numExpandedNodes = 0;

        while (queue.size() > 0) {
            path = queue.remove(0);
            Puzzle endNode = path.get(path.size() - 1);

            if (expanded.contains(endNode)) {
                continue;
            }

            for (Puzzle move : endNode.getMoves()) {
                if (expanded.contains(move)) {
                    continue;
                }

                List<Puzzle> newPath = new ArrayList<>(path);
                newPath.add(move);
                queue.add(newPath);
            }

            expanded.add(endNode);
            numExpandedNodes++;

            if (Puzzle.puzzleEquality(endNode.getPosition(), endNode.getEndPosition())) {
                break;
            }
        }

        this.numExpandedNodes = numExpandedNodes;
        this.solution = path;
    }

    public int getNumExpandedNodes() {
        return this.numExpandedNodes;
    }

    public List<Puzzle> getSolution() {
        return this.solution;
    }
}