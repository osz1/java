import java.util.Random;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * <h1>Webshop szimuláció</h1>
 * 
 * <p>Egy webshop havi működésének modellezése.</p>
 * 
 * <p>Termékek, árak és mennyiségek halmazazából történik. 
 * Az elkészült modell egy fájlba mentődik el.</p>
 */
public class WebshopSimulation {

    /**
     * Kimeneti fájl neve.
     */
    private String fileName;


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
     * <p>Dátum formázás.</p>
     */
    private DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");


    /**
     * <p>Véletlen generátor.</p>
     */
    private Random random = new Random();

    public WebshopSimulation(String fileName, String[] items, int[] prices, int[] quantities) {
        if ((items.length != prices.length) || (items.length != quantities.length)) {
            throw new IllegalArgumentException("Nem azonos m\u00E9ret\u0171ek a halmazok.");
        }

        this.fileName = fileName;

        this.items = items;
        this.prices = prices;
        this.quantities = quantities;
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

    /**
     * <p>Webshop működésének modellezése.</p>
     */
    public void shoppingSimulation() {
        if (items == null) {
            System.out.println("\u00DCres a lista!");

            return;
        }

        // TERMÉK_NEVE|RENDELT_MENNYISÉG|FIZETETT_ÖSSZEG|VÉGREHAJTÁS_DÁTUMA|SIKERES_RENDELÉS

        // kezdő dátum
        LocalDate date = LocalDate.of(2022, 6, 1);

        // kezdő hónap
        int startMonth = date.getMonthValue();

        while (date.getMonthValue() == startMonth) {
            // véletlen termék és mennyiség
            int i = randomIndexGenerator();
            int quantity = randomNumberGenerator();

            // sor
            StringBuilder line = new StringBuilder(items[i] + ";" + quantity + ";" + 
                (quantity * prices[i]) + ";" + localDateFormatter.format(date));

            if ((quantities[i] - quantity) < 0) {
                // sikertelen (nincs elég készlet)

                line.append(";nem");
            } else {
                // sikeres

                line.append(";igen");
                quantities[i] -= quantity;
            }

            // sortörés
            line.append(System.lineSeparator());

            // sor hozzáadása a kimeneti fájlhoz
            try {
                Files.writeString(Paths.get(fileName), line, Charset.forName("UTF-8"), StandardOpenOption.APPEND); // "rendelesek.txt"
            } catch (IOException e) {
                System.out.println("Nem siker\u00FClt a hozz\u00E1f\u0171z\u00E9s!");
            }
            
            // következő nap
            date = date.plusDays(1L);
        }
    }

    /**
     * <p>Véletlenül kiválaszt egy (osztályon belüli) halmaz indexet. 
     * (Termék kiválasztásához.)</p>
     * 
     * @return halmaz index
     */
    private int randomIndexGenerator() {
        return (int) ((items.length - 0.5) * random.nextFloat());
    }

    /**
     * <p>Véletlen szám generátor. 
     * (Mennyiséghez.)</p>
     * 
     * @return egész szám 1 és 15 között
     */
    private int randomNumberGenerator() {
        return (int) (14 * random.nextFloat()) + 1;
    }

    public static void main(String[] args) {
        Stock s = new Stock("raktarkeszlet.txt");

        // for (int i = 0; i < s.getItems().length; i++) {
            // System.out.println(s.getItems()[i] + " " + s.getPrices()[i] + " " + s.getQuantities()[i]);
        // }

        WebshopSimulation ws = new WebshopSimulation("rendelesek.txt", s.getItems(), s.getPrices(), s.getQuantities());
        ws.shoppingSimulation();

        // for (int i = 0; i < s.getItems().length; i++) {
            // System.out.println(ws.getItems()[i] + " " + ws.getPrices()[i] + " " + ws.getQuantities()[i]);
        // }

        Statistics stat = new Statistics("rendelesek.txt", ws.getItems(), ws.getPrices(), ws.getQuantities());
        stat.printStatistics();
    }
}