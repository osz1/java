/**
 * <h1>Útvonalak a gráfban</h1>
 */
public class GraphPath {
    public static void main(String[] args) {
        //       1
        //      /|\
        //     2 | 3
        //    /|\|/ \
        //   4 | 5   6
        //    \|/ \ /
        //     7   8

        // csúcsok létrehozása
        Vertex vertex1 = new Vertex("1");
        Vertex vertex2 = new Vertex("2");
        Vertex vertex3 = new Vertex("3");
        Vertex vertex4 = new Vertex("4");
        Vertex vertex5 = new Vertex("5");
        Vertex vertex6 = new Vertex("6");
        Vertex vertex7 = new Vertex("7");
        Vertex vertex8 = new Vertex("8");

        // gráf létrehozása
        Graph graph = new Graph();

        // csúcsok hozzáadása a gráfhoz
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addVertex(vertex4);
        graph.addVertex(vertex5);
        graph.addVertex(vertex6);
        graph.addVertex(vertex7);
        graph.addVertex(vertex8);

        // csúcsok "szomszédságának beállítása"
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

        // kör ellenőrzése
        boolean cycle = graph.hasCycle();
        if (cycle) {
            System.out.println("Kör!");
        } else {
            // nincs kör

            graph.setUnvisited();

            // lehetséges útvonalak (1 és 7 csúcsok között)
            graph.dfs(vertex1, vertex7);

            System.out.println(graph.simplePaths);

            // for (int i = 0; i < graph.simplePaths.size(); i++) {
                // System.out.println(graph.simplePaths.get(i));
            // }
        }
    }
}