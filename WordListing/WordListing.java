import java.nio.file.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * <h1>Szólistázó</h1>
 * 
 * <p>Szavak kilistázása egy fájlból.</p>
 */
public class WordListing {

    /**
     * </p>Egész számok.<p>
     */
    private Map<Integer, Integer> integersFromText = new HashMap<>();

    /**
     * </p>Tört számok.<p>
     */
    private Map<Double, Integer> doubleValuesFromText = new HashMap<>();

    /**
     * </p>Dátum és idő.<p>
     */
    private Map<String, Integer> dateAndTimeFromText = new HashMap<>();

    /**
     * </p>Szavak.<p>
     */
    private Map<String, Integer> stringsFromText = new HashMap<>();


    /**
     * </p>Dátum formázás.<p>
     * 
     * </p>Év, hónap, nap.<p>
     * 
     * </p><code>yyyy.MM.dd</code><p>
     */
    private DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");

    /**
     * </p>Dátum formázás.<p>
     * 
     * </p>Év, hónap, nap, óra, perc.<p>
     * 
     * </p><code>yyyy.MM.dd.HH:mm</code><p>
     */
    private DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH:mm");

    /**
     * </p>Dátum formázás.<p>
     * 
     * </p>Óra, perc.<p>
     * 
     * </p><code>HH:mm</code><p>
     */
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public WordListing(String text) {
        wordMapping(text);
    }

    /**
     * @return egész számok
     */
    public Map<Integer, Integer> getIntegersFromText() {
        return integersFromText;
    }

    /**
     * @return tört számok
     */
    public Map<Double, Integer> getDoubleValuesFromText() {
        return doubleValuesFromText;
    }

    /**
     * @return dátumok
     */
    public Map<String, Integer> getDateAndTimeFromText() {
        return dateAndTimeFromText;
    }

    /**
     * @return szövegek
     */
    public Map<String, Integer> getStringsFromText() {
        return stringsFromText;
    }

    /**
     * <p>Fájl beolvasása szövegbe.</p>
     * 
     * @param p bemeneti fájl elérési útvonala
     * @return a fájl tartalma szövegként
     * @throws IOException
     */
    public static String whenReadUTFEncodedFile_thenCorrect(Path p) throws IOException {
        BufferedReader reader = Files.newBufferedReader(p);
        StringBuilder content = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            content.append((line));
            content.append(System.lineSeparator());
        }
        reader.close();

        return content.toString();
    }

    /**
     * <p>Szöveg kimentése fájlba.</p>
     * 
     * @param fileName kimeneti fájl elérési útvonala
     * @param text a szöveg
     * @throws IOException
     */
    public static void whenWriteStringUsingBufferedWritter_thenCorrect(String fileName, String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(text);
        writer.close();
    }

    /**
     * <p>Fájl vagy könyvtár létezésének és olvashatóságának ellenőrzése.</p>
     * 
     * <p>(Bemeneti fájl ellenőrzéséhez.)</p>
     * 
     * @param p fájl elérési útvonala
     * @return üres szöveg esetén nincs hiba, hiba esetén a hibaüzenet szövege
     */
    public static String checkFile(Path p) {
        if (Files.exists(p)) {
            if (!Files.isRegularFile(p)) {
                return "\nEz nem f\u00E1jl (k\u00F6nyvt\u00E1r).";
            }

            if (!Files.isReadable(p)) {
                return "\nA k\u00F6nyvt\u00E1r/f\u00E1jl nem olvashat\u00F3.";
            }
        } else {
            return "\nNem l\u00E9tezik ilyen nev\u0171 k\u00F6nyvt\u00E1r/f\u00E1jl.";
        }

        return "";
    }

    /**
     * <p>Fájl vagy könyvtár létezésének ellenőrzése.</p>
     * 
     * <p>(KImeneti fájl ellenőrzéséhez.)</p>
     * 
     * @param p fájl vagy könyvtár elérési útvonala
     * @return üres szöveg esetén nem létezik, szöveg esetén létezik
     */
    public static String fileIsExist(Path p) {
        if (Files.exists(p)) {
            return "\nM\u00E1r l\u00E9tezik ilyen nev\u0171 k\u00F6nyvt\u00E1r/f\u00E1jl.";
        }

        return "";
    }

    /**
     * <p>Fájl olvashatóságának ellenőrzése.</p>
     * 
     * <p>(KImeneti fájl ellenőrzéséhez.)</p>
     * 
     * @param p fájl elérési útvonala
     * @return üres szöveg esetén nincs hiba, hiba esetén a hibaüzenet szövege
     */
    public static String checkFileIsReadable(Path p) {
        if (Files.isRegularFile(p)) {
            if (Files.isReadable(p)) {
                return "";
            } else {
                return "\nA f\u00E1jl nem olvashat\u00F3.";
            }
        } else {
            return "\nEz nem f\u00E1jl.";
        }
    }

    /**
     * <p>Eredmény szövegbe való gyűjtése.<p>
     * 
     * @return a kész szöveg (fájlba való kiíráshoz)
     */
    public String result() {
        String result = ("Eg\u00E9sz sz\u00E1mok:" + System.lineSeparator());
        result += formatIntValues(getIntegersFromText());
        result += ("T\u00F6rt sz\u00E1mok:" + System.lineSeparator());
        result += formatDoubleValues(getDoubleValuesFromText());
        result += ("D\u00E1tumok:" + System.lineSeparator());
        result += formatValues(getDateAndTimeFromText());
        result += ("Sz\u00F6vegek:" + System.lineSeparator());
        result += formatValues(getStringsFromText());

        return result;
    }

    /**
     * <p>Program futattása</p>
     * 
     * @param scan (billentyűzet bemenet)
     */
    public static void run(Scanner scan) {
        System.out.println("\nValdi szavakat list\u00E1z\u00F3 programja.");

        boolean run1 = true;
        while (run1) {
            System.out.println("\nOlvasand\u00F3 f\u00E1jl neve (\u00FCres mez\u0151: kil\u00E9p\u00E9s a programb\u00F3l):");

            // bemeneti fájl
            String input = scan.nextLine();
            if (input.isEmpty()) {
                System.out.println("\nV\u00E9ge!");

                run1 = false;
            } else {
                Path p = Paths.get(input);

                String fileCheckingForRead = checkFile(p);

                System.out.println(fileCheckingForRead);

                boolean r = fileCheckingForRead.isEmpty();

                // az eredmény
                String result = "";
                if (r) {
                    // létezik és nincs probléma a bemeneti fájllal

                    try {
                        // a kész eredmény
                        WordListing wl = new WordListing(whenReadUTFEncodedFile_thenCorrect(p));
                        result = wl.result();

                        System.out.println(result);
                        System.out.println(p.toAbsolutePath().toString());
                    } catch (Exception e) {
                        System.out.println("\nValami hiba t\u00F6rt\u00E9nt a f\u00E1jl feldolgoz\u00E1s\u00E1n\u00E1l!");

                        r = false;
                    }
                }

                if (r) {
                    // nem történt hiba

                    writeResultToFile(scan, result);
                }
            }
        }
    }

    /**
     * <p>Szavak listázása.</p>
     * 
     * @param str a vizsgálandó szöveg
     */
    private void wordMapping(String str) {
        for (String word : str.split("[\n ]")) {
            if (word.isEmpty()) {
                continue;
            } else if (isInteger(word)) {
                Integer i = Integer.parseInt(word);
                integersFromText.put(i, integersFromText.containsKey(i) ? integersFromText.get(i) + 1 : 1);
            } else if (isDouble(word)) {
                Double d = Double.parseDouble(word);
                doubleValuesFromText.put(d, doubleValuesFromText.containsKey(d) ? doubleValuesFromText.get(d) + 1 : 1);
            } else if (isLocalDate(word) || isLocalDateTime(word) || isTime(word)) {
                dateAndTimeFromText.put(word, dateAndTimeFromText.containsKey(word) ? dateAndTimeFromText.get(word) + 1 : 1);
            } else {
                // biztosan szöveg
                stringsFromText.put(word, stringsFromText.containsKey(word) ? stringsFromText.get(word) + 1 : 1);
            }
        }
    }

    /**
     * <p>Szó egész szám-e.</p>
     * 
     * @param word szó
     * @return igaz, ha egész szám
     */
    private static boolean isInteger(String word) {
        try {
            int i = Integer.parseInt(word);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    /**
     * <p>Szó tört szám-e.</p>
     * 
     * @param word szó
     * @return igaz, ha tört szám
     */
    private static boolean isDouble(String word) {
        try {
            double d = Double.parseDouble(word);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    /**
     * <p>Szó dátum-e.</p>
     * 
     * </p>Év, hónap, nap.<p>
     * 
     * </p><code>yyyy.MM.dd</code><p>
     * 
     * @param word szó
     * @return igaz, ha dátum
     */
    private boolean isLocalDate(String word) {
        try {
            localDateFormatter.parse(word);
        } catch (DateTimeParseException ex) {
            return false;
        }

        return true;
    }

    /**
     * <p>Szó dátum-e.</p>
     * 
     * </p>Év, hónap, nap, óra, perc.<p>
     * 
     * </p><code>yyyy.MM.dd.HH:mm</code><p>
     * 
     * @param word szó
     * @return igaz, ha dátum
     */
    private boolean isLocalDateTime(String word) {
        try {
            localDateTimeFormatter.parse(word);
        } catch (DateTimeParseException ex) {
            return false;
        }

        return true;
    }

    /**
     * <p>Szó dátum-e.</p>
     * 
     * </p>Óra, perc.<p>
     * 
     * </p><code>HH:mm</code><p>
     * 
     * @param word szó
     * @return igaz, ha dátum
     */
    private boolean isTime(String word) {
        try {
            timeFormatter.parse(word);
        } catch (DateTimeParseException ex) {
            return false;
        }

        return true;
    }

    /**
     * <p>Szöveg (dátum) előfordulások formázása.</p>
     * 
     * @param values előfordulások
     * @return előfordulások szöveg típusként
     */
    private String formatValues(Map<String, Integer> values) {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<String, Integer> value : values.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toCollection(LinkedHashSet::new))) {
            str.append("\u00E9rt\u00E9k: ").append(value.getKey())
                    .append(", el\u0151fordul\u00E1sa: ").append(value.getValue().toString()).append(System.lineSeparator());
        }
        return str.toString();
    }

    /**
     * <p>Egész szám előfordulások formázása.</p>
     * 
     * @param values előfordulások
     * @return előfordulások szöveg típusként
     */
    private String formatIntValues(Map<Integer, Integer> values) {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Integer, Integer> value : values.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toCollection(LinkedHashSet::new))) {
            str.append("\u00E9rt\u00E9k: ").append(value.getKey())
                    .append(", el\u0151fordul\u00E1sa: ").append(value.getValue().toString()).append(System.lineSeparator());
        }
        return str.toString();
    }

    /**
     * <p>Tört szám előfordulások formázása.</p>
     * 
     * @param values előfordulások
     * @return előfordulások szöveg típusként
     */
    private String formatDoubleValues(Map<Double, Integer> values) {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Double, Integer> value : values.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toCollection(LinkedHashSet::new))) {
            str.append("\u00E9rt\u00E9k: ").append(value.getKey())
                    .append(", el\u0151fordul\u00E1sa: ").append(value.getValue().toString()).append(System.lineSeparator());
        }
        return str.toString();
    }

    /**
     * <p>Eredmény kiírása fájlba.</p>
     * 
     * @param scan (billentyűzet bemenet)
     * @param result az eredmény szövege
     */
    private static void writeResultToFile(Scanner scan, String result) {
        boolean run2 = true;
        while (run2) {
            boolean r = true;

            System.out.println("\nKimeneti f\u00E1jl neve (\u00FCres mez\u0151: nincs kiment\u00E9s):");

            // kimeneti fájl
            String input = scan.nextLine();
            if (input.isEmpty()) {
                run2 = false;
            } else {
                Path p = Paths.get(input);

                String fileExisting = fileIsExist(p);
                if (!fileExisting.isEmpty()) {
                    // létezik a kimeneti fájl

                    System.out.println(fileExisting);

                    String fileCheckingForOverwrite = checkFileIsReadable(p);
                    if (fileCheckingForOverwrite.isEmpty()) {
                        // olvasható a kimeneti fájl

                        System.out.println("\n\u00CDrjon be valamit a f\u00E1jl fel\u00FCl\u00EDr\u00E1s\u00E1hoz.");

                        // kimentés
                        String input2 = scan.nextLine();
                        if (input2.isEmpty()) {
                            // nincs kimentés

                            r = false;
                        }
                    } else {
                        System.out.println(fileCheckingForOverwrite);

                        r = false;
                    }
                }
            }

            if (r) {
                // nem létezik vagy nincs probléma a kimeneti fájllal

                try {
                    // kiírás a kimeneti fájlba
                    whenWriteStringUsingBufferedWritter_thenCorrect(input, result);

                    System.out.println("\nK\u00E9sz!");
                } catch (Exception e) {
                    System.out.println("\nValami hiba t\u00F6rt\u00E9nt a kiment\u00E9s sor\u00E1n!");
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        run(scan);
        scan.close();
    }
}