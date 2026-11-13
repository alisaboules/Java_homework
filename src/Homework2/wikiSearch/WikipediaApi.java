package Homework2.wikiSearch;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class WikipediaApi {

    public static final int RESULTS_PER_PAGE = 10;
    private static final String API_URL = "https://ru.wikipedia.org/w/api.php";
    private static final String USER_AGENT = "WikipediaSearch/1.0 (educational project)";
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);
    
    private final HttpClient httpClient;

    public WikipediaApi() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(CONNECT_TIMEOUT)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public String search(String query, int offset) throws IOException {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Поисковый запрос не может быть пустым.");
        }

        if (offset < 0) {
            throw new IllegalArgumentException("Смещение не может быть отрицательным.");
        }

        HttpRequest request = buildRequest(query, offset);

        try {
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException("Ошибка сервера Википедии. Код ответа: " + response.statusCode());
            }

            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new IOException("Выполнение запроса было прервано.", e);
        }
    }

    private HttpRequest buildRequest(String query, int offset) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        String requestUrl = API_URL
                + "?action=query"
                + "&list=search"
                + "&utf8=1"
                + "&format=json"
                + "&srlimit=" + RESULTS_PER_PAGE
                + "&sroffset=" + offset
                + "&srsearch=" + encodedQuery;

        return HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .timeout(REQUEST_TIMEOUT)
                .header("User-Agent", USER_AGENT)
                .header("Accept", "application/json")
                .GET()
                .build();
    }
}