import java.util.List;
import java.util.ArrayList;

/**
 * <h1>Csúcs</h1>
 */
public class Vertex {

    /**
     * <p>Címke.</p>
     */
    String label; // 


    /**
     * <p>Meglátogatás.</p>
     * 
     * <p>Kör ellenőrzéséhez.</p>
     */
    boolean beingVisited;

    /**
     * <p>Meglátogatva.</p>
     * 
     * <p>Kör ellenőrzéséhez.</p>
     */
    boolean visited;

    /**
     * <p>Szomszédok.</p>
     */
    List<Vertex> adjacencyList;

    public Vertex(String label) {
        this.label = label;

        this.beingVisited = false;
        this.visited = false;

        this.adjacencyList = new ArrayList<>();
    }

    /**
     * <p>Egy (másik) csúcs megjegyzése szomszédként.</p>
     * 
     * @param adjacent szomszéd csúcs
     */
    public void addNeighbor(Vertex adjacent) {
        this.adjacencyList.add(adjacent);
    }
}