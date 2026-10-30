package Homework2.wikiSearch;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class WikipediaApi {
    private static final String API_URL = "https://ru.wikipedia.org/w/api.php";

    private final HttpClient httpClient;

    public WikipediaApi() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public String search(String query) throws IOException {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException(
               "Поисковый запрос не может быть пустым."
            );
        }

        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        String requestUrl = API_URL
                + "?action=query"
                + "&list=search"
                + "&utf8=1"
                + "&format=json"
                + "&srlimit=10"
                + "&srsearch=" + encodedQuery;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .timeout(Duration.ofSeconds(15))
                .header(
                        "User-Agent",
                        "WikipediaSearch/1.0 (educational project)"
                )
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
            );

            if (response.statusCode() != 200) {
                throw new IOException("Ошибка сервера Википедии. Код ответа: " + response.statusCode());
            }

            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new IOException("Выполнение запроса было прервано.", e);
        }
    }
}
