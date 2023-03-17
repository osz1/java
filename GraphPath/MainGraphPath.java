import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

class Vertex {
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

class Graph {
    List<Vertex> vertices;

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

    Set<String> visitedList = new HashSet<String>();
    ArrayList<String> currentPaths = new ArrayList<String>();
    ArrayList<ArrayList<String>> simplePaths = new ArrayList<ArrayList<String>>();

    public void dfs(Vertex u, Vertex v) {
        if (visitedList.contains(u.label)) {
            return;
        }
        visitedList.add(u.label);
        currentPaths.add(u.label);
        if (u.label.equals(v.label)) {
            simplePaths.add(currentPaths);
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

public class MainGraphPath {
    public static void main(String[] args) {
        //       1
        //      /|\
        //     2 | 3
        //    /|\|/ \
        //   4 | 5   6
        //    \|/ \ /
        //     7   8
        Vertex vertex1 = new Vertex("1");
        Vertex vertex2 = new Vertex("2");
        Vertex vertex3 = new Vertex("3");
        Vertex vertex4 = new Vertex("4");
        Vertex vertex5 = new Vertex("5");
        Vertex vertex6 = new Vertex("6");
        Vertex vertex7 = new Vertex("7");
        Vertex vertex8 = new Vertex("8");

        Graph graph = new Graph();
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addVertex(vertex4);
        graph.addVertex(vertex5);
        graph.addVertex(vertex6);
        graph.addVertex(vertex7);
        graph.addVertex(vertex8);

        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex1, vertex3);
        graph.addEdge(vertex1, vertex5);
        graph.addEdge(vertex2, vertex4);
        graph.addEdge(vertex2, vertex5);
        graph.addEdge(vertex2, vertex7);
        graph.addEdge(vertex3, vertex5);
        graph.addEdge(vertex3, vertex6);
        graph.addEdge(vertex4, vertex7);
        graph.addEdge(vertex5, vertex7);
        graph.addEdge(vertex5, vertex8);
        graph.addEdge(vertex6, vertex8);

        boolean cycle = graph.hasCycle();
        if (cycle) {
            System.out.println("Kör!");
        } else {
            graph.setUnvisited();
            graph.dfs(vertex1, vertex7);
            System.out.println(graph.simplePaths);
        }
    }
}