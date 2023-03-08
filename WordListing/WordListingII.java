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

class FileHandling {
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

    public static void whenWriteStringUsingBufferedWritter_thenCorrect(String fileName, String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(text);
        writer.close();
    }
}

public class WordListingII {
    private Map<Integer, Integer> integersFromText = new HashMap<>(); // egész számok
    private Map<Double, Integer> doubleValuesFromText = new HashMap<>(); // tört számok
    private Map<String, Integer> dateAndTimeFromText = new HashMap<>(); // dátum és idő
    private Map<String, Integer> stringsFromText = new HashMap<>(); // szavak

    private DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    private DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH:mm");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public WordListingII(String text) {
        wordMapping(text);
    }

    public Map<Integer, Integer> getIntegersFromText() {
        return integersFromText;
    }

    public Map<Double, Integer> getDoubleValuesFromText() {
        return doubleValuesFromText;
    }

    public Map<String, Integer> getDateAndTimeFromText() {
        return dateAndTimeFromText;
    }

    public Map<String, Integer> getStringsFromText() {
        return stringsFromText;
    }

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

    private static boolean isInteger(String word) {
        try {
            int i = Integer.parseInt(word);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static boolean isDouble(String word) {
        try {
            double d = Double.parseDouble(word);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean isLocalDate(String word) {
        try {
            localDateFormatter.parse(word);
        } catch (DateTimeParseException ex) {
            return false;
        }
        return true;
    }

    private boolean isLocalDateTime(String word) {
        try {
            localDateTimeFormatter.parse(word);
        } catch (DateTimeParseException ex) {
            return false;
        }
        return true;
    }

    private boolean isTime(String word) {
        try {
            timeFormatter.parse(word);
        } catch (DateTimeParseException ex) {
            return false;
        }
        return true;
    }

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

    // többinél is hasonló formázás kellene
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

    public static void main(String[] args) {
        System.out.println("\nValdi szavakat list\u00E1z\u00F3 programja.");
        boolean run1 = true;
        Scanner scan = new Scanner(System.in);
        while (run1) {
            System.out.println("\nOlvasand\u00F3 f\u00E1jl neve (\u00FCres mez\u0151: kil\u00E9p\u00E9s a programb\u00F3l):");
            String input = scan.nextLine();
            if (input.isEmpty()) {
                System.out.println("\nV\u00E9ge!");
                run1 = false;
            } else {
                Path p = Paths.get(input);

                boolean r = true;
                if (Files.exists(p)) {
                    if (!Files.isRegularFile(p)) {
                        System.out.println("\nEz nem f\u00E1jl.");
                        r = false;
                    }
                    if (!Files.isReadable(p)) {
                        System.out.println("\nA k\u00F6nyvt\u00E1r/f\u00E1jl nem olvashat\u00F3.");
                        r = false;
                    }
                } else {
                    System.out.println("\nNem l\u00E9tezik ilyen nev\u0171 k\u00F6nyvt\u00E1r/f\u00E1jl.");
                    r = false;
                }

                String result = "";
                if (r) {
                    try {
                        String textStr = FileHandling.whenReadUTFEncodedFile_thenCorrect(p);
                        WordListingII wl = new WordListingII(textStr);
                        result = ("Eg\u00E9sz sz\u00E1mok:" + System.lineSeparator());
                        result += wl.formatIntValues(wl.getIntegersFromText());
                        result += ("T\u00F6rt sz\u00E1mok:" + System.lineSeparator());
                        result += wl.formatDoubleValues(wl.getDoubleValuesFromText());
                        result += ("D\u00E1tumok:" + System.lineSeparator());
                        result += wl.formatValues(wl.getDateAndTimeFromText());
                        result += ("Sz\u00F6vegek:" + System.lineSeparator());
                        result += wl.formatValues(wl.getStringsFromText());
                        System.out.println(result);
                        System.out.println(p.toAbsolutePath().toString());
                    } catch (Exception e) {
                        System.out.println("\nValami hiba t\u00F6rt\u00E9nt a f\u00E1jl feldolgoz\u00E1s\u00E1n\u00E1l!");
                        r = false;
                    }
                }

                if (r) {
                    boolean run2 = true;
                    while (run2) {
                        r = true;
                        System.out.println("\nKimeneti f\u00E1jl neve (\u00FCres mez\u0151: nincs kiment\u00E9s):");
                        input = scan.nextLine();
                        if (input.isEmpty()) {
                            run2 = false;
                        } else {
                            p = Paths.get(input);
                            if (Files.exists(p)) {
                                System.out.println("\nM\u00E1r l\u00E9tezik ilyen nev\u0171 k\u00F6nyvt\u00E1r/f\u00E1jl.");
                                if (Files.isRegularFile(p)) {
                                    if (Files.isReadable(p)) {
                                        System.out.println("\n\u00CDrjon be valamit a f\u00E1jl fel\u00FCl\u00EDr\u00E1s\u00E1hoz.");
                                        String input2 = scan.nextLine();
                                        if (input2.isEmpty()) {
                                            r = false;
                                        }
                                    } else {
                                        System.out.println("\nA f\u00E1jl nem olvashat\u00F3.");
                                        r = false;
                                    }
                                } else {
                                    System.out.println("\nEz nem f\u00E1jl.");
                                    r = false;
                                }
                            }

                            if (r) {
                                try {
                                    FileHandling.whenWriteStringUsingBufferedWritter_thenCorrect(input, result);
                                    System.out.println("\nK\u00E9sz!");
                                } catch (Exception e) {
                                    System.out.println("\nValami hiba t\u00F6rt\u00E9nt a kiment\u00E9s sor\u00E1n!");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



