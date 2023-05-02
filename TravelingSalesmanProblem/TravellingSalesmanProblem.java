import java.util.List;
import java.util.ArrayList;

/**
 * <h1>Utazó ügynök probléma</h1>
 */
public class TravellingSalesmanProblem {

    /** 
     * <p>Gráf.</p>
     * 
     * <p>A gráf indexei egyúttal a városokat is jelentik.</p>
     * 
     * <p>A gráfban lévő számok a két város (sor index és oszlop index) közötti távolság.</p>
    */
    private int[][] graph;

    /** 
     * <p>Kezdő város (index).</p>
    */
    private int startingCity;


    /** 
     * <p>Városok (indexek) halmaza.</p>
    */
    private Integer[] cities;


    /** 
     * <p>Permutációk (indexek).</p>
    */
    private List<Integer[]> permutations = new ArrayList<>();

    public TravellingSalesmanProblem(int[][] graph, int startingCity) {
        this.graph = graph;
        this.startingCity = startingCity;
    }

    /**
     * <p>Megoldás megjelenítése.</p>
     */
    public void printSolution() {
        // városok (indexek) "kigyűjtése", a kezdőváros nincs benne
        cities = new Integer[graph[0].length - 1];
        int i = 0;
        int j = 0; // város (index)
        while (i < cities.length) {
            if (j != startingCity) {
                cities[i] = j;
                i++;
            }
            j++;
        }

        printAllRecursive(cities.length, cities, ' '); // permutációk
        System.out.println(travellingSalesmanProblem(graph, startingCity)); // legrövidebb útvonal
    }

    /**
     * <p>Legrövidebb útvonal hossza a gráf és a permutációk alapján.</p>
     * 
     * @param graph gráf
     * @param startingCity kezdő város (index)
     * @return legrövidebb útvonal hossza
     */
    private Integer travellingSalesmanProblem(int[][] graph, int startingCity) {
        Integer minPath = Integer.MAX_VALUE; // legrövidebb útvonal hossza
        for (Integer[] i : permutations) { // List<Integer[]> nextPermutation = permutations;
            // adott permutáció útvonalának hossza
            Integer currentPathweight = 0;

            int k = startingCity;
            for (Integer j : i) {
                currentPathweight += graph[k][j];
                k = j;
            }
            currentPathweight += graph[k][startingCity];

            minPath = Math.min(minPath, currentPathweight);
        }

        return minPath;
    }

    /**
     * <p>Permutációk.</p>
     * 
     * @param <T> típus (Integer)
     * @param n "meddig" legyen a halmaz vizsgálva
     * @param elements városok halmaza
     * @param delimiter elválasztó karakter (megjelenítéshez)
     */
    private <T> void printAllRecursive(int n, T[] elements, char delimiter) {
        if (n == 1) { // halmaz hossza egységnyi
            printArray(elements, delimiter);
        } else {
            // (ciklus)
            for (int i = 0; i < (n - 1); i++) {
                // permutációk az utolsó város nélkül
                printAllRecursive((n - 1), elements, delimiter);

                // helycsere a halmazban
                if ((n % 2) == 0) { // páros
                    // "i-edik" város és az utolsó város helyet cserélnek
                    swap(elements, i, (n - 1));
                } else { // páratlan
                    // az első város és az utolsó város helyet cserélnek
                    swap(elements, 0, (n - 1));
                }
            }

            // permutációk az utolsó város nélkül
            // (ciklus után, az utolsó helycsere után)
            printAllRecursive((n - 1), elements, delimiter);
        }
    }

    /**
     * <p>Helycsere halmazban.</p>
     * 
     * @param <T> típus (Integer)
     * @param input halmaz
     * @param a halmaz egyik eleme
     * @param b halmaz másik eleme
     */
    private <T> void swap(T[] input, int a, int b) {
        T tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    /**
     * <p>Halmaz megjelenítése és hozzáadása permutációkhoz.</p>
     * 
     * @param <T> típus (Integer)
     * @param input halmaz
     * @param delimiter elválasztó karakter
     */
    private <T> void printArray(T[] input, char delimiter) {
        // String delimiterSpace = delimiter + " ";
        // for(int i = 0; i < input.length; i++) {
        //     System.out.print(input[i] + delimiterSpace);
        // }
        // System.out.print('\n');

        Integer[] result = new Integer[input.length];
        for(int i = 0; i < input.length; i++) {
            result[i] = (Integer) input[i];
            System.out.print(input[i] + " ");
        }

        System.out.print("\n");
        permutations.add(result);
    }

    public static void main(String[] args) {
        // 1-2
        // |X|
        // 0-3

        int[][] graph = {{0, 3, 5, 4}, {3, 0, 4, 5}, {5, 4, 0, 3}, {4, 5, 3, 0}};

        int startingCity = 0;
        
        TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(graph, startingCity);
        tsp.printSolution();
    }
}