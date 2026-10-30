package Homework2.wikiSearch;


public class Article {

    private final int pageId;
    private final String title;
    private final String snippet;

    public Article(int pageId, String title, String snippet) {
        this.pageId = pageId;
        this.title = title;
        this.snippet = snippet;
    }

    public int getPageId() {
        return pageId;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        if (snippet == null) {
            return "";
        }

        return snippet
                .replaceAll("<[^>]*>", "")
                .replace("&quot;", "\"")
                .replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&#039;", "'")
                .replace("&nbsp;", " ")
                .trim();
    }
}
