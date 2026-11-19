package Homework3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConsoleApp {

  private static final String EXIT_COMMAND = "exit";

  private final BufferedReader input;
  private final PrintStream out;
  private final ReportPrinter reportPrinter;

  public ConsoleApp(BufferedReader input, PrintStream out) {
    this.input = input;
    this.out = out;
    this.reportPrinter = new ReportPrinter(out);
  }

  public void run() {
    out.println("Введите путь до файла-справочника (.csv или .xml) либо '" + EXIT_COMMAND
        + "' (или Ctrl+D / Ctrl+Z) для выхода.");
    while (true) {
      out.print("> ");
      out.flush();

      String line;
      try {
        line = input.readLine();
      } catch (IOException e) {
        out.println("Ошибка чтения ввода: " + e.getMessage());
        break;
      }
      if (line == null || EXIT_COMMAND.equalsIgnoreCase(line.trim())) {
        out.println("Завершение работы.");
        break;
      }
      if (line.isBlank()) {
        continue;
      }
      processSafely(line.trim());
    }
  }

  private void processSafely(String rawPath) {
    try {
      process(unquote(rawPath));
    } catch (Exception e) {
      out.println("Ошибка: " + e.getMessage());
    }
  }

  private void process(String pathText) throws IOException {
    Path path = Paths.get(pathText);
    if (!Files.isRegularFile(path)) {
      throw new IOException("файл не найден: " + path);
    }
    AddressParser parser = ParserFactory.forFile(path);
    StatisticsService statistics = new StatisticsService();

    long start = System.nanoTime();
    parser.parse(path, statistics::accept);
    long elapsedMs = (System.nanoTime() - start) / 1_000_000;

    reportPrinter.print(statistics, elapsedMs);
  }

  private String unquote(String s) {
    if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
      return s.substring(1, s.length() - 1);
    }
    return s;
  }
}