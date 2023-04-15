import java.util.List;
import java.util.ArrayList;

public class TravellingSalesmanProblem {
    Integer[] cities;
    List<Integer[]> permutations = new ArrayList<>();

    public TravellingSalesmanProblem(int[][] graph, int startingCity) {
        cities = new Integer[graph[0].length - 1];
        int i = 0;
        int j = 0;
        while (i < cities.length) {
            if (j != startingCity) {
                cities[i] = j;
                i++;
            }
            j++;
        }

        printAllRecursive(cities.length, cities, ' ');
    }

    public Integer travellingSalesmanProblem(int[][] graph, int startingCity) {
        Integer minPath = Integer.MAX_VALUE;
        for (Integer[] i : permutations) { // List<Integer[]> nextPermutation = permutations;
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

    private <T> void printAllRecursive(int n, T[] elements, char delimiter) {
        if(n == 1) {
            printArray(elements, delimiter);
        } else {
            for(int i = 0; i < n-1; i++) {
                printAllRecursive(n - 1, elements, delimiter);
                if(n % 2 == 0) {
                    swap(elements, i, n-1);
                } else {
                    swap(elements, 0, n-1);
                }
            }
 
            printAllRecursive(n - 1, elements, delimiter);
        }
    }

    private <T> void swap(T[] input, int a, int b) {
        T tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

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
        // tsp.printAllRecursive(tsp.cities.length, tsp.cities, ' ');
        System.out.println(tsp.travellingSalesmanProblem(graph, startingCity));
    }
}