import java.io.IOException;
// import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;

/**
 * <h1>Készlet</h1>
 * 
 * <p>Fájl beolvasása halmaz típusba.</p>
 */
public class Stock {

    /**
     * <p>Készletlista (fájl beolvasása után).</p>
     */
    private List<String> stock;


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

    public Stock(String fileName) {
        try {
            stock = readFileToList(fileName); // "raktarkeszlet.txt"
            stockToArrays();
        } catch (IOException e) {
            System.out.println("Nem sikerült a fájl beolvasása!");
        }
    }

    /**
     * @return termékek
     */
    public String[] getItems() {
        return items;
    }

    /**
     * @returnárak
     */
    public int[] getPrices() {
        return prices;
    }

    /**
     * @return mennyiségek
     */
    public int[] getQuantities() {
        return quantities;
    }

    // public static void printList(List<String> strList) {
        // if (strList != null) {
            // for (int i = 0; i < strList.size(); i++) {
                // System.out.println(strList.get(i));
            // }
        // }
    // }

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
     * <p>Halmazok létrehozása a készletlistából.</p>
     */
    private void stockToArrays() {
        if (stock == null) {
            System.out.println("Üres a lista!");

            return;
        }

        // TERMÉK_NEVE|TERMÉK_ÁRA|RENDELKEZÉSRE_ÁLLÓ_MENNYISÉG

        int arraySize = stock.size() - 1;

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
}