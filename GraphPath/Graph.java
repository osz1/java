import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Graph {
    List<Vertex> vertices;

    Set<String> visitedList = new HashSet<String>();
    ArrayList<String> currentPaths = new ArrayList<String>();
    ArrayList<ArrayList<String>> simplePaths = new ArrayList<ArrayList<String>>();

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public void addEdge(Vertex from, Vertex to) {
        from.addNeighbor(to);
    }

    public boolean hasCycle() {
        for (Vertex vertex : vertices) {
            if ((vertex.visited == false) && hasCycle(vertex)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasCycle(Vertex sourceVertex) {
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

    public void setUnvisited() {
        for (Vertex current : this.vertices) {
            current.beingVisited = false;
            current.visited = false;
        }
    }

    public void dfs(Vertex u, Vertex v) {
        if (visitedList.contains(u.label)) {
            return;
        }

        visitedList.add(u.label);
        currentPaths.add(u.label);

        if (u.label.equals(v.label)) {
            simplePaths.add(new ArrayList<>(currentPaths));
            visitedList.remove(u.label);

            int i = currentPaths.size() - 1;
            currentPaths.remove(i);

            return;
        }

        for (Vertex next : u.adjacencyList) {
            dfs(next, v);
        }

        int i = currentPaths.size() - 1;
        currentPaths.remove(i);
        visitedList.remove(u.label);
    }
}