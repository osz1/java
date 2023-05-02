import java.io.IOException;
// import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * <h1>Statisztika</h1>
 * 
 * <p>Fájl beolvasása után a webshop műkédésének elemzése.</p>
 */
class Statistics {

    /**
     * <p>(Fájl beolvasásának eredménye.)</p>
     */
    private List<String> shoppingList;


    /**
     * <p>Halmazok (készletlista feldolgozása után).</p>
     * 
     * <p>Termékek.</p>
     */
    private String[] items;

    /**
     * <p>Halmazok (készletlista feldolgozása után).</p>
     * 
     * <p>Árak.</p>
     */
    private int[] prices;

    /**
     * <p>Halmazok (készletlista feldolgozása után).</p>
     * 
     * <p>Mennyiségek.</p>
     */
    private int[] quantities;


    /**
     * <p>Sikeres rendelések mennyisége, bevétele termékekként.</p>
     */
    private Map<String, Integer[]> shoppingMap;


    /**
     * <p>Teljes bevétel.</p>
     */
    private int totalIncome = 0; // teljes bevétel


    /**
     * <p>Sikeres rendelések száma.</p>
     */
    private int successShopping = 0;

    /**
     * <p>Sikertelen rendelések száma.</p>
     */
    private int unSuccessShopping = 0;


    /**
     * <p>Legtöbb rendelés mennyisége.</p>
     */
    private int maxShoppedQuantity = 0;

    /**
     * <p>Legtöbb rendelés napja.</p>
     */
    private StringBuilder maxShoppedQuantityDay; // 


    /**
     * <p>Legnépszerűbb termék mennyisége.</p>
     */
    private int maxSuccessShoppedQuantity = 0;

    public Statistics(String fileName, String[] items, int[] prices, int[] quantities) {
        try {
            shoppingList = readFileToList(fileName); // "rendelesek.txt"

            this.items = items;
            this.prices = prices;
            this.quantities = quantities;
        } catch (IOException e) {
            System.out.println("Nem siker\u00F6lt a f\u00E1jl beolvas\u00E1sa!");
        }
    }

    /**
     * <p>A statisztika.</p>
     */
    public void printStatistics() {
        shoppingStatistics();

        System.out.println("\nMelyik term\u00E1kb\u0151l h\u00E1ny darab ker\u00F6lt elad\u00E1sra?");
        for (String item : shoppingMap.keySet()) {
            System.out.println(item + ": " + shoppingMap.get(item)[0]);
        }
    
        System.out.println("\nMelyik term\u00E1kb\u0151l mennyi maradt rakt\u00E1ron a h\u00F3nap v\u00E1g\u00E1re?");
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i] + " " + quantities[i]);
        }

        System.out.println("\nMi volt a legn\u00E1pszer\u0171bb term\u00E1k a h\u00F3napban?");
        for (String item : shoppingMap.keySet()) {
            Integer value = shoppingMap.get(item)[0];
            if (value == maxSuccessShoppedQuantity) {
                System.out.println(item);
            }
        }

        System.out.println("\nMelyik term\u00E1k mennyi bev\u00E1telt hozott a webshopnak a h\u00F3napban?");
        for (String item : shoppingMap.keySet()) {
            System.out.println(item + ": " + shoppingMap.get(item)[1]);
        }

        System.out.println("\nMennyi volt a webshop teljes bev\u00E1tele a h\u00F3napban?");
        System.out.println(totalIncome);

        System.out.println("\nH\u00E1ny sikeres \u00E1s h\u00E1ny sikertelen rendel\u00E1s v\u00E1grehajt\u00E1s volt \u00D6sszesen?");
        System.out.println("sikeres: " + successShopping);
        System.out.println("sikertelen: " + unSuccessShopping);

        System.out.println("\nMelyik napon t\u00D6rt\u00E1nt a legt\u00D6bb megrendel\u00E1s?");
        System.out.println(maxShoppedQuantityDay.toString());
    }

    /**
     * <p>Fájl beolvasása.</p>
     * 
     * @param fileName a fájl elérési útvonala
     * @return a fájl tartalma "List" típusként
     * @throws IOException
     */
    private static List<String> readFileToList(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName)); // , Charset.forName("UTF-8")
    }

    /**
     * <p>Statisztikai elemzés.</p>
     */
    private void shoppingStatistics() {
        shoppingMap = new HashMap<>();

        for (int i = 1; i < shoppingList.size(); i++) {
            // TERMÉK_NEVE|RENDELT_MENNYISÉG|FIZETETT_ÖSSZEG|VÉGREHAJTÁS_DÁTUMA|SIKERES_RENDELÉS
            String[] partsOfLine = shoppingList.get(i).split(";");

            if (partsOfLine.length == 5) {
                // "teljes" sor

                // statisztika halmaz
                // első elem: sikeres rendelések mennyisége
                // második elem: sikeres rendelések bevétele
                Integer[] statisticArray = shoppingMap.containsKey(partsOfLine[0]) 
                    ? shoppingMap.get(partsOfLine[0]) : new Integer[] {0, 0};

                // rendelt mennyiség
                Integer shoppedQuantity = Integer.parseInt(partsOfLine[1]);

                // rendelés sikeressége
                if (partsOfLine[4].equals("igen")) {
                    // sikeres

                    successShopping++;

                    statisticArray[0] += shoppedQuantity;
                    if (statisticArray[0] > maxSuccessShoppedQuantity) {
                        maxSuccessShoppedQuantity = statisticArray[0];
                    }

                    Integer income = Integer.parseInt(partsOfLine[2]);
                    statisticArray[1] += income;
                    totalIncome += income;
                } else {
                    // sikertelen

                    unSuccessShopping++;
                }

                shoppingMap.put(partsOfLine[0], statisticArray);

                if (shoppedQuantity > maxShoppedQuantity) {
                    // legtöbb rendelés mennyisége
                    maxShoppedQuantity = shoppedQuantity;

                    // legtöbb rendelés mennyiségéhez tartozó napok
                    maxShoppedQuantityDay = new StringBuilder(partsOfLine[3]);
                } else if (shoppedQuantity == maxShoppedQuantity) {
                    // legtöbb rendelés mennyiségéhez tartozó napok
                    maxShoppedQuantityDay.append(" " + partsOfLine[3]);
                }
            }
        }
    }
}