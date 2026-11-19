package Homework3;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class ReportPrinter {

  private static final int COLUMN_WIDTH = 13;
  private static final int CITY_COLUMN_PADDING = 2;
  private static final String CITY_TITLE = "Город";

  private final PrintStream out;

  public ReportPrinter(PrintStream out) {
    this.out = out;
  }

  public void print(StatisticsService statistics, long elapsedMs) {
    out.println();
    printDuplicates(statistics.getDuplicates());
    out.println();
    printFloorTable(statistics.getFloorStatistics());
    out.println();
    out.println("Время обработки файла: " + elapsedMs + " мс");
    out.println();
  }

  private void printDuplicates(Map<Address, Integer> duplicates) {
    printHeader("Дублирующиеся записи");

    if (duplicates.isEmpty()) {
      out.println("Дубликатов не обнаружено.");
      return;
    }

    duplicates.entrySet().stream()
        .sorted(Map.Entry.<Address, Integer>comparingByValue(Comparator.reverseOrder()))
        .forEach(entry -> {
          Address a = entry.getKey();
          out.printf("%s, %s, д. %d, этажей: %d%n",
              a.city(), a.street(), a.house(), a.floor());
          out.printf("    Количество повторений: %d%n", entry.getValue());
        });
  }

  private void printFloorTable(Map<String, int[]> floorStatistics) {
    printHeader("Количество зданий по этажности в каждом городе");

    Map<String, int[]> sorted = new TreeMap<>(floorStatistics);

    int cityWidth = Math.max(CITY_TITLE.length(),
        sorted.keySet().stream().mapToInt(String::length).max().orElse(0))
        + CITY_COLUMN_PADDING;
    String cityFormat = "%-" + cityWidth + "s";
    String floorTitleFormat = "%" + (COLUMN_WIDTH - 4) + "d эт.";
    String countFormat = "%" + COLUMN_WIDTH + "d";
    int floorsCount = StatisticsService.MAX_FLOOR - StatisticsService.MIN_FLOOR + 1;

    out.printf(cityFormat, CITY_TITLE);
    for (int f = StatisticsService.MIN_FLOOR; f <= StatisticsService.MAX_FLOOR; f++) {
      out.printf(floorTitleFormat, f);
    }
    out.println();
    out.println("-".repeat(cityWidth + COLUMN_WIDTH * floorsCount));

    sorted.forEach((city, counts) -> {
      out.printf(cityFormat, city);
      for (int count : counts) {
        out.printf(countFormat, count);
      }
      out.println();
    });
  }

  private void printHeader(String title) {
    out.println(title);
    out.println("-".repeat(title.length()));
  }
}