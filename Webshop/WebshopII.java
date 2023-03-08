import java.util.Random;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;

public class WebshopII {
    private List<String> stock;

    public WebshopII() {
        try {
            stock = readFileToList("raktarkeszlet.txt");
        } catch (IOException e) {
            System.out.println("Nem sikerült a fájl beolvasása!");
        }
    }

    private static List<String> readFileToList(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName)); // , Charset.forName("UTF-8")
    }

    // public static void printList(List<String> strList) {
        // if (strList != null) {
            // for (int i = 0; i < strList.size(); i++) {
                // System.out.println(strList.get(i));
            // }
        // }
    // }

    private int arraySize;
    private String[] items;
    private int[] prices;
    private int[] quantities;

    // TERMEK_NEVE|TERMEK_ARA|RENDELKEZESRE_ALLO_MENNYISEG    
    public void stockToArrays() {
        if (stock == null) {
            System.out.println("Üres a lista!");
            return;
        }

        arraySize = stock.size() - 1;
        items = new String[arraySize];
        prices = new int[arraySize];
        quantities = new int[arraySize];

        int j = 0;
        for (int i = 1; i < stock.size(); i++) {
            String[] parts = stock.get(i).split(";");
            items[j] = parts[0];
            prices[j] = Integer.parseInt(parts[1]);
            quantities[j] = Integer.parseInt(parts[2]);
            j++;
        }
    }

    private DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    private Random random = new Random();

    private int randomIndexGenerator() {
        return (int) ((arraySize - 0.5) * random.nextFloat());
    }

    private int randomNumberGenerator() {
        return (int) (14 * random.nextFloat()) + 1;
    }

    // TERMEK_NEVE|RENDELT_MENNYISEG|FIZETETT_OSSZEG|VEGREHAJTAS_DATUMA|SIKERES_RENDELES
    public void shoppingSimulation() {
        if ((stock == null) || (items == null)) {
            System.out.println("Üres a lista!");
            return;
        }

        LocalDate date = LocalDate.of(2022, 6, 1);
        int startMonth = date.getMonthValue();
        while (date.getMonthValue() == startMonth) {
            int i = randomIndexGenerator();
            int quantity = randomNumberGenerator();

            StringBuilder line = new StringBuilder(items[i] + ";" + quantity + ";" + 
                (quantity * prices[i]) + ";" + localDateFormatter.format(date));
            if ((quantities[i] - quantity) < 0) {
                line.append(";nem");
            } else {
                line.append(";igen");
                quantities[i] -= quantity;
            }
            line.append(System.lineSeparator());

            try {
                Files.writeString(Paths.get("rendelesek.txt"), line, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.out.println("Nem sikerült a hozzáfűzés!");
            }
            
            date = date.plusDays(1L);
        }
    }

    private List<String> shoppingList;
    private Map<String, Integer[]> shoppingMap;

    private int totalIncome = 0;
    private int successShopping = 0;
    private int unSuccessShopping = 0;

    private int maxShoppedQuantity = 0;
    private StringBuilder maxShoppedQuantityDay;

    private int maxSuccessShoppedQuantity = 0;

    private void shoppingStatistics() {
        try {
            shoppingList = readFileToList("rendelesek.txt");
            // printList(shoppingList);
        } catch (IOException e) {
            System.out.println("Nem sikerült a fájl beolvasása!");
        }

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

        System.out.println("\nmelyik termékből hány darab került eladásra");
        for (String item : shoppingMap.keySet()) {
            System.out.println(item + ": " + shoppingMap.get(item)[0]);
        }
    
        System.out.println("\nmelyik termékből mennyi maradt raktáron a hónap végére");
        for (int i = 0; i < arraySize; i++) {
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

    public static void main(String[] args) {
        WebshopII ws = new WebshopII();
        // printList(ws.stock);
        ws.stockToArrays();
        ws.shoppingSimulation();
        ws.shoppingStatistics();
    }
}