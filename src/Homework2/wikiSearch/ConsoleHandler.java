package Homework2.wikiSearch;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class ConsoleHandler {

  private final Scanner scanner;

  public ConsoleHandler() {
    scanner = new Scanner(System.in, StandardCharsets.UTF_8);
  }

  private String readLine() {
    if (!scanner.hasNextLine()) {
      throw new IllegalStateException("Ввод завершён.");
    }

    return scanner.nextLine().trim();
  }

  public String readSearchQuery() {

    while (true) {
      System.out.print("Введите поисковый запрос: ");

      String query = readLine();

      if (!query.isEmpty()) {
        return query;
      }

      System.out.println("Поисковый запрос не может быть пустым.");
    }
  }

  public void showArticles(List<Article> articles, int startNumber) {

    if (articles.isEmpty()) {
      System.out.println("По вашему запросу ничего не найдено.");
      return;
    }

    System.out.println("\nРезультаты поиска:");

    for (int i = 0; i < articles.size(); i++) {

      Article article = articles.get(i);

      System.out.println((startNumber + i + 1) + ". " + article.getTitle());
      System.out.println("   " + article.getSnippet());
      System.out.println();
    }
  }

  public int readArticleNumber(int count) {

    while (true) {
      System.out.print("Введите номер статьи (0 - выход): ");

      String input = readLine();

      try {
        int number = Integer.parseInt(input);

        if (number >= 0 && number <= count) {
          return number;
        }

        System.out.println("Статьи с таким номером нет.");

      } catch (NumberFormatException e) {
        System.out.println("Ошибка! Введите целое число.");
      }
    }
  }

  public void showMessage(String message) {
    System.out.println(message);
  }

  public boolean askForNewSearch() {

    while (true) {
      System.out.print("Хотите выполнить новый поиск? (да/нет): ");

      String answer = readLine().toLowerCase();

      if (answer.equals("да")) {
        return true;
      }

      if (answer.equals("нет")) {
        return false;
      }

      System.out.println("Пожалуйста, введите да или нет.");
    }
  }

  public boolean askForMoreArticles() {

    while (true) {
        System.out.print("Хотите посмотреть ещё статьи? (да/нет): ");

        String answer = readLine().toLowerCase();

        if (answer.equals("да")) {
            return true;
        }

        if (answer.equals("нет")) {
            return false;
        }

        System.out.println("Пожалуйста, введите да или нет.");
    }
  }
}
