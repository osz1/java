import java.util.List;
import java.util.ArrayList;

public class Vertex {
    String label;
    boolean beingVisited;
    boolean visited;
    List<Vertex> adjacencyList;

    public Vertex(String label) {
        this.label = label;
        this.beingVisited = false;
        this.visited = false;
        this.adjacencyList = new ArrayList<>();
    }

    public void addNeighbor(Vertex adjacent) {
        this.adjacencyList.add(adjacent);
    }
}