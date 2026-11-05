package Homework2.wikiSearch;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class BrowserOpener {

  public void openArticle(int pageId) throws IOException {

    if (!Desktop.isDesktopSupported()) {
      throw new IOException("Открытие браузера не поддерживается.");
    }

    Desktop desktop = Desktop.getDesktop();

    if (!desktop.isSupported(Desktop.Action.BROWSE)) {
      throw new IOException("Не удалось открыть браузер.");
    }

    String articleUrl = "https://ru.wikipedia.org/w/index.php?curid=" + pageId;

    URI uri = URI.create(articleUrl);

    desktop.browse(uri);
  }
}
