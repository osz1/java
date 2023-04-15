import java.util.Random;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WebshopSimulation {
    private String fileName;

    private String[] items;
    private int[] prices;
    private int[] quantities;

    private DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    private Random random = new Random();

    public WebshopSimulation(String fileName, String[] items, int[] prices, int[] quantities) {
        if ((items.length != prices.length) || (items.length != quantities.length)) {
            throw new IllegalArgumentException("Nem azonos méretűek a halmazok.");
        }

        this.fileName = fileName;

        this.items = items;
        this.prices = prices;
        this.quantities = quantities;
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

    // TERMEK_NEVE|RENDELT_MENNYISEG|FIZETETT_OSSZEG|VEGREHAJTAS_DATUMA|SIKERES_RENDELES
    public void shoppingSimulation() {
        if (items == null) {
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
                Files.writeString(Paths.get(fileName), line, Charset.forName("UTF-8"), StandardOpenOption.APPEND); // "rendelesek.txt"
            } catch (IOException e) {
                System.out.println("Nem sikerült a hozzáfűzés!");
            }
            
            date = date.plusDays(1L);
        }
    }

    private int randomIndexGenerator() {
        return (int) ((items.length - 0.5) * random.nextFloat());
    }

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