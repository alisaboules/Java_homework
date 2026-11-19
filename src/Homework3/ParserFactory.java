package Homework3;

import java.nio.file.Path;
import java.util.Locale;

public final class ParserFactory {

    private ParserFactory() {
    }

    public static AddressParser forFile(Path file) {
        String name = file.getFileName().toString().toLowerCase(Locale.ROOT);
        if (name.endsWith(".csv")) {
            return new CsvAddressParser();
        }
        if (name.endsWith(".xml")) {
            return new XmlAddressParser();
        }
        throw new IllegalArgumentException("Неподдерживаемый формат файла (нужен .csv или .xml): " + file);
    }
}