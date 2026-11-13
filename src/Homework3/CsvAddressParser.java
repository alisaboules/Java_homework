package Homework3;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class CsvAddressParser implements AddressParser {

  @Override
  public void parse(Path file, Consumer<Address> consumer) throws IOException {
    try (var reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
      String line;

      reader.readLine();

      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(";", -1);

        if (parts.length != 4) {
          throw new IOException("Неверный формат строки CSV: " + line);
        }

        String city = parts[0].trim().replace("\"", "");
        String street = parts[1].trim().replace("\"", "");

        int house;
        int floor;

        try {
          house = Integer.parseInt(parts[2].trim());
          floor = Integer.parseInt(parts[3].trim());
        } catch (NumberFormatException e) {
          throw new IOException("Неверный номер дома или этажность: " + line, e);
        }

        Address address = new Address(city, street, house, floor);
        consumer.accept(address);
      }
    }
  }
}
