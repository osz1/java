import java.io.IOException;
// import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class Statistics {
    private List<String> shoppingList;

    private String[] items;
    private int[] prices;
    private int[] quantities;

    private Map<String, Integer[]> shoppingMap;

    private int totalIncome = 0;
    private int successShopping = 0;
    private int unSuccessShopping = 0;

    private int maxShoppedQuantity = 0;
    private StringBuilder maxShoppedQuantityDay;

    private int maxSuccessShoppedQuantity = 0;

    public Statistics(String fileName, String[] items, int[] prices, int[] quantities) {
        try {
            shoppingList = readFileToList(fileName); // "rendelesek.txt"

            this.items = items;
            this.prices = prices;
            this.quantities = quantities;
        } catch (IOException e) {
            System.out.println("Nem sikerült a fájl beolvasása!");
        }
    }

    public void printStatistics() {
        shoppingStatistics();

        System.out.println("\nmelyik termékből hány darab került eladásra");
        for (String item : shoppingMap.keySet()) {
            System.out.println(item + ": " + shoppingMap.get(item)[0]);
        }
    
        System.out.println("\nmelyik termékből mennyi maradt raktáron a hónap végére");
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i] + " " + quantities[i]);
        }

        System.out.println("\nmi volt a legnépszerűbb termék a hónapban");
        for (String item : shoppingMap.keySet()) {
            Integer value = shoppingMap.get(item)[0];
            if (value == maxSuccessShoppedQuantity) {
                System.out.println(item);
            }
        }
        
        System.out.println("\nmelyik termék mennyi bevételt hozott a webshopnak a hónapban");
        for (String item : shoppingMap.keySet()) {
            System.out.println(item + ": " + shoppingMap.get(item)[1]);
        }
        
        System.out.println("\nmennyi volt a webshop teljes bevétele a hónapban");
        System.out.println(totalIncome);
        
        System.out.println("\nhány sikeres és hány sikertelen rendelés végrehajtás volt összesen");
        System.out.println("sikeres: " + successShopping);
        System.out.println("sikertelen: " + unSuccessShopping);
        
        System.out.println("\nmelyik napon történt a legtöbb megrendelés");
        System.out.println(maxShoppedQuantityDay.toString());
    }

    private static List<String> readFileToList(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName)); // , Charset.forName("UTF-8")
    }

    // TERMEK_NEVE|RENDELT_MENNYISEG|FIZETETT_OSSZEG|VEGREHAJTAS_DATUMA|SIKERES_RENDELES
    private void shoppingStatistics() {
        shoppingMap = new HashMap<>();

        for (int i = 1; i < shoppingList.size(); i++) {
            String[] partsOfLine = shoppingList.get(i).split(";");
            if (partsOfLine.length == 5) {
                Integer[] statisticArray = shoppingMap.containsKey(partsOfLine[0]) 
                    ? shoppingMap.get(partsOfLine[0]) : new Integer[] {0, 0};

                Integer shoppedQuantity = Integer.parseInt(partsOfLine[1]);

                if (partsOfLine[4].equals("igen")) {
                    successShopping++;

                    statisticArray[0] += shoppedQuantity;
                    if (statisticArray[0] > maxSuccessShoppedQuantity) {
                        maxSuccessShoppedQuantity = statisticArray[0];
                    }

                    Integer income = Integer.parseInt(partsOfLine[2]);
                    statisticArray[1] += income;
                    totalIncome += income;
                } else {
                    unSuccessShopping++;
                }

                shoppingMap.put(partsOfLine[0], statisticArray);

                if (shoppedQuantity > maxShoppedQuantity) {
                    maxShoppedQuantity = shoppedQuantity;

                    maxShoppedQuantityDay = new StringBuilder(partsOfLine[3]);
                } else if (shoppedQuantity == maxShoppedQuantity) {
                    maxShoppedQuantityDay.append(" " + partsOfLine[3]);
                }
            }
        }
    }
}