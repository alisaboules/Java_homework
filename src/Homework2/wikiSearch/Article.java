package Homework2.wikiSearch;

import com.google.gson.annotations.SerializedName;

import java.util.regex.Pattern;

public class Article {

    private static final Pattern HTML_TAG_PATTERN = Pattern.compile("<[^>]*>");

    @SerializedName("pageid")
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

        return decodeHtmlEntities(removeHtmlTags(snippet)).trim();
    }

    private static String removeHtmlTags(String text) {
        return HTML_TAG_PATTERN.matcher(text).replaceAll("");
    }

    private static String decodeHtmlEntities(String text) {
        return text
                .replace("&quot;", "\"")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&#039;", "'")
                .replace("&nbsp;", " ")
                .replace("&amp;", "&");
    }
}