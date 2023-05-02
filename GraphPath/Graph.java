import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * <h1>Gráf</h1>
 */
public class Graph {

    /**
     * <p>Csúcsok.</p>
     */
    List<Vertex> vertices;


    /**
     * <p>Meglátogatott csúcsok.</p>
     * 
     * <p>Útvonal(ak) kereséséhez két csúcs között.</p>
     */
    Set<String> visitedList = new HashSet<String>();

    /**
     * <p>Jelenlegi útvonal.</p>
     * 
     * <p>Útvonal(ak) kereséséhez két csúcs között.</p>
     */
    ArrayList<String> currentPaths = new ArrayList<String>();

    /**
     * <p>Lehetséges útvonalak.</p>
     * 
     * <p>Útvonal(ak) kereséséhez két csúcs között.</p>
     */
    ArrayList<ArrayList<String>> simplePaths = new ArrayList<ArrayList<String>>(); // 

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    /**
     * <p>Csúcs hozzáadása (a gráfhoz).</p>
     * 
     * @param vertex csúcs
     */
    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    /**
     * <p>Egy csúcs hozzáadása szomszédként egy másik csúcshoz.</p>
     * 
     * @param from a csúcs
     * @param to szomszéd csúcs
     */
    public void addEdge(Vertex from, Vertex to) {
        from.addNeighbor(to);
    }

    /**
     * <p>Kör ellenőrzése a gráfban.</p>
     * 
     * @return igaz, ha van kör
     */
    public boolean hasCycle() {
        for (Vertex vertex : vertices) {
            if ((vertex.visited == false) && hasCycle(vertex)) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>Kör ellenőrzése a gráfban.</p>
     * 
     * @param sourceVertex vizsgálandó csúcs
     * @return igaz, ha van kör
     */
    public boolean hasCycle(Vertex sourceVertex) {
        // miközben a "szomszéd csúcsoknak a szomszéd csúcsainak" vizsgálata
        // történik, a csúcs végig "meglátogatás" állapotban van
        sourceVertex.beingVisited = true;
    
        for (Vertex neighbor : sourceVertex.adjacencyList) {
            if (neighbor.beingVisited == true) {
                return true;
            } else if ((neighbor.visited == false) && hasCycle(neighbor)) {
                return true;
            }
        }

        sourceVertex.beingVisited = false;
        sourceVertex.visited = true;
        return false;
    }

    /**
     * <p>"Meglátogatási" értékek viszzaállítása (kör ellenőrzés után).</p>
     */
    public void setUnvisited() {
        for (Vertex current : this.vertices) {
            current.beingVisited = false;
            current.visited = false;
        }
    }

    /**
     * <p>Lehetséges útvonalak keresése a kezdő- és végcsúcs között.</p>
     * 
     * @param u kezdőcsúcs
     * @param v végcsúcs
     */
    public void dfs(Vertex u, Vertex v) {
        // (vége)
        if (visitedList.contains(u.label)) {
            // kezdőcsúcs meglátogatva
            return;
        }

        // kezdőcsúcs meglátogatva
        // és a jelenlegi útvonal (végéhez) hozzáadása
        visitedList.add(u.label);
        currentPaths.add(u.label);

        // (vége)
        if (u.label.equals(v.label)) {
            // kezdő- és végcsúcs egyezik

            // jelenlegi útvonal hozzáadása a lehetséges útvonalakhoz
            simplePaths.add(new ArrayList<>(currentPaths));
            // kezdőcsúcs (ami végcsúcs) "nincs meglátogatva"
            visitedList.remove(u.label);

            // jelenlegi útvonal utolsó csúcsának (ami végcsúcs) eltávolítása
            int i = currentPaths.size() - 1;
            currentPaths.remove(i);

            return;
        }

        // lehetséges útvonalak keresése a kezdőcsúcs szomszédai
        // és a végcsúcs között
        for (Vertex next : u.adjacencyList) {
            dfs(next, v);
        }

        // nem sikerült a végcsúcsot elérni

        // jelenlegi útvonal utolsó csúcsának eltávolítása
        int i = currentPaths.size() - 1;
        currentPaths.remove(i);

        // kezdőcsúcs "nincs meglátogatva"
        visitedList.remove(u.label);
    }
}