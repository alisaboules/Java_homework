package Homework2.wikiSearch;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

public class WikipediaParser {
    
    private final Gson gson;

    public WikipediaParser() {
        this.gson = new Gson();
    }

    public List<Article> parse(String json) {
        if (json == null || json.isBlank()) {
            throw new IllegalArgumentException("Получен пустой ответ от сервера.");
        }

        try {
            Response response = gson.fromJson(json, Response.class);
            if (response == null || response.query == null || response.query.search == null) {
                return new ArrayList<>();
            }

            return response.query.search;

        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Не удалось разобрать JSON-ответ Википедии.", e);
        }
    }

    private static class Response {
        private Query query;
    }

    private static class Query {
        private List<Article> search;
    }
}