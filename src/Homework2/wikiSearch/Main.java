package Homework2.wikiSearch;

import java.io.IOException;
import java.util.List;

public class Main {

  public static void main(String[] args) {

    ConsoleHandler console = new ConsoleHandler();
    WikipediaApi api = new WikipediaApi();
    WikipediaParser parser = new WikipediaParser();
    BrowserOpener browser = new BrowserOpener();

    boolean continueSearch = true;

    while (continueSearch) {

      try {
        String query = console.readSearchQuery();

        String json = api.search(query);

        List<Article> articles = parser.parse(json);

        console.showArticles(articles);

        if (!articles.isEmpty()) {

          int number = console.readArticleNumber(articles.size());

          if (number == 0) {
            console.showMessage("Статья не выбрана.");
          } else {
            Article selectedArticle = articles.get(number - 1);

            browser.openArticle(selectedArticle.getPageId());

            console.showMessage("Команда открытия статьи выполнена.");
          }
        }

      } catch (IllegalStateException e) {
        console.showMessage("\n" + e.getMessage());
        break;

      } catch (IOException e) {
        console.showMessage("Ошибка сети или открытия браузера: " + e.getMessage());

      } catch (IllegalArgumentException e) {
        console.showMessage("Ошибка обработки данных: " + e.getMessage());

      } catch (SecurityException e) {
        console.showMessage("Доступ запрещён: " + e.getMessage());
      }

      console.showMessage("");

      try {
        continueSearch = console.askForNewSearch();

      } catch (IllegalStateException e) {
        console.showMessage("\n" + e.getMessage());
        break;
      }

      console.showMessage("");
    }

    console.showMessage("Программа завершена.");
  }
}
