import java.io.IOException;
// import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;

public class Stock {
    private List<String> stock;

    private String[] items;
    private int[] prices;
    private int[] quantities;

    public Stock(String fileName) {
        try {
            stock = readFileToList(fileName); // "raktarkeszlet.txt"
            stockToArrays();
        } catch (IOException e) {
            System.out.println("Nem sikerült a fájl beolvasása!");
        }
    }

    public String[] getItems() {
        return items;
    }

    public int[] getPrices() {
        return prices;
    }

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

    private static List<String> readFileToList(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName)); // , Charset.forName("UTF-8")
    }

    // TERMEK_NEVE|TERMEK_ARA|RENDELKEZESRE_ALLO_MENNYISEG    
    private void stockToArrays() {
        if (stock == null) {
            System.out.println("Üres a lista!");
            return;
        }

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