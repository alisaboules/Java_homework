package Homework2.wikiSearch;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class WikiSearchApp {

  private final ConsoleHandler console;
  private final WikipediaApi api;
  private final WikipediaParser parser;
  private final BrowserOpener browser;

  public WikiSearchApp() {
    this.console = new ConsoleHandler();
    this.api = new WikipediaApi();
    this.parser = new WikipediaParser();
    this.browser = new BrowserOpener();
  }

  public void run() {

    boolean continueSearch = true;

    while (continueSearch) {

      try {
        String query = console.readSearchQuery();

        List<Article> allArticles = new ArrayList<>();

        int offset = 0;

        while (true) {
          String json = api.search(query, offset);

          List<Article> articles = parser.parse(json);

          if (articles.isEmpty()) {

            if (offset == 0) {
              console.showMessage("По вашему запросу ничего не найдено.");
            } else {
              console.showMessage("Больше статей нет.");
            }

            break;
          }

          console.showArticles(articles, allArticles.size());
          allArticles.addAll(articles);

          if (articles.size() < WikipediaApi.RESULTS_PER_PAGE) {
            console.showMessage("Это все найденные статьи.");
            break;
          }

          if (!console.askForMoreArticles()) {
            break;
          }

          offset += articles.size();
        }

        if (!allArticles.isEmpty()) {

          int number = console.readArticleNumber(allArticles.size());

          if (number == 0) {
            console.showMessage("Статья не выбрана.");
          } else {
            Article selectedArticle = allArticles.get(number - 1);

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
